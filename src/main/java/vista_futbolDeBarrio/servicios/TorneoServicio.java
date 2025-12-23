package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import vista_futbolDeBarrio.dtos.EquipoTorneoDto;
import vista_futbolDeBarrio.dtos.PartidoTorneoDto;
import vista_futbolDeBarrio.dtos.TorneoDto;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.utilidades.Utilidades;

public class TorneoServicio {

	private final EquipoTorneoServicio equipoTorneoServicio = new EquipoTorneoServicio();

	private static final String API_URL_ID = "http://localhost:9527/api/torneo";
	private static final String API_URL = "http://localhost:9527/api/mostrarTorneo";
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	// ---------- CRUD Torneo ----------
	public void guardarTorneo(TorneoDto torneo) {
		try {
			JSONObject json = crearJsonTorneo(torneo);
			ejecutarPost("http://localhost:9527/api/guardarTorneo", json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean modificarTorneo(long idTorneo, TorneoDto torneo) {
		try {
			JSONObject json = crearJsonTorneo(torneo);
			return ejecutarPut("http://localhost:9527/api/modificarTorneo/" + idTorneo, json);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean eliminarTorneo(Long idTorneo) {
		try {
			URL url = new URL("http://localhost:9527/api/eliminarTorneo/" + idTorneo);
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("DELETE");
			conex.setRequestProperty("Accept", "application/json");
			return conex.getResponseCode() == HttpURLConnection.HTTP_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<TorneoDto> obtenerTodosLosTorneos() throws Exception {
		return hacerLlamadaApi(API_URL);
	}

	public List<TorneoDto> obtenerTorneosPorInstalacion(Long instalacionId) throws Exception {
		return hacerLlamadaApi(API_URL_ID + "?instalacionId=" + instalacionId);
	}

	public TorneoDto obtenerTorneo(Long torneoId) throws Exception {
		return obtenerTodosLosTorneos().stream().filter(t -> t.getIdTorneo() == torneoId).findFirst()
				.orElseThrow(() -> new RuntimeException("Torneo no encontrado con ID " + torneoId));
	}

	public Long obtenerInstalacionDeTorneo(Long torneoId) throws Exception {
		return obtenerTorneo(torneoId).getInstalacionId();
	}

	// ---------- Generación de Bracket ----------
	/**
	 * Método principal para generar bracket, usando la lógica de fechas válidas.
	 */
	public void generarBracket(Long torneoId, TorneoServicio torneoServicio, EquipoTorneoServicio equipoTorneoServicio,
			PartidoTorneoServicio partidoServicio) throws Exception {

		TorneoDto torneo = torneoServicio.obtenerTorneo(torneoId);

// Lista de equipos
		List<EquipoTorneoDto> equipos = equipoTorneoServicio.listaEquiposTorneo().stream()
				.filter(e -> e.getTorneoId() == (torneoId)).collect(Collectors.toList());

		if (equipos.size() != 16) {
			throw new Exception("El torneo necesita exactamente 16 equipos para generar los octavos.");
		}

// Orden aleatorio de equipos
		Collections.shuffle(equipos);

// Fecha inicial del torneo
		LocalDate fechaInicio = LocalDate.parse(torneo.getFechaInicioTorneo(),
				DateTimeFormatter.ofPattern("yyyy-MM-dd"));

// Control de fechas usadas
		List<LocalDateTime> fechasUsadas = new ArrayList<>();

// Crear OCTAVOS (8 partidos)
		int ubicacion = 1;
		for (int i = 0; i < 16; i += 2) {

			EquipoTorneoDto local = equipos.get(i);
			EquipoTorneoDto visitante = equipos.get(i + 1);

			PartidoTorneoDto partido = new PartidoTorneoDto();
			partido.setTorneoId(torneoId);
			partido.setRonda("octavos");
			partido.setEstado("pendiente");
			partido.setUbicacionRonda(ubicacion++);

			partido.setEquipoLocalId(local.getIdEquipoTorneo());
			partido.setEquipoVisitanteId(visitante.getIdEquipoTorneo());
			partido.setClubLocalId(local.getClubId());
			partido.setClubVisitanteId(visitante.getClubId());
			partido.setInstalacionId(torneoServicio.obtenerInstalacionDeTorneo(torneoId));

// Asignar fecha/hora automáticamente  
			LocalDateTime fecha = torneoServicio.asignarFechaPartido(fechasUsadas, fechaInicio);
			partido.setFechaPartido(fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

			partidoServicio.guardarPartido(partido);
		}
	}

	public void avanzarGanador(PartidoTorneoDto partido, Long idGanador, PartidoTorneoServicio partidoServicio,
			EquipoTorneoServicio equipoTorneoServicio, TorneoServicio torneoServicio) throws Exception {

		partido.setEstado("finalizado");
		partido.setEquipoGanadorId(idGanador);
		partidoServicio.modificarPartido(partido.getIdPartidoTorneo(), partido);

		Map<Long, Long> flujo = Utilidades.obtenerMapa(partido.getTorneoId());
		if (flujo == null)
			flujo = new HashMap<>();

		String[] rondas = { "octavos", "cuartos", "semifinal", "partidoFinal", "tercerpuesto" };
		int indexRonda = Arrays.asList(rondas).indexOf(partido.getRonda());
		if (indexRonda == -1)
			return;

		TorneoDto torneo = torneoServicio.obtenerTorneo(partido.getTorneoId());

		if (indexRonda < rondas.length - 2) {
			String siguienteRonda = rondas[indexRonda + 1];

			List<PartidoTorneoDto> partidosRondaActual = partidoServicio.listaPartidos().stream()
					.filter(p -> p.getTorneoId().equals(partido.getTorneoId())
							&& p.getRonda().equalsIgnoreCase(partido.getRonda()))
					.sorted(Comparator.comparingInt(PartidoTorneoDto::getUbicacionRonda)).collect(Collectors.toList());

			boolean todosFinalizados = partidosRondaActual.stream()
					.allMatch(p -> "finalizado".equalsIgnoreCase(p.getEstado()));

			List<PartidoTorneoDto> partidosSiguienteRonda = partidoServicio.listaPartidos().stream().filter(
					p -> p.getTorneoId().equals(partido.getTorneoId()) && p.getRonda().equalsIgnoreCase(siguienteRonda))
					.collect(Collectors.toList());

			if (partidosSiguienteRonda.isEmpty() && todosFinalizados) {
				List<Long> ganadores = partidosRondaActual.stream().map(PartidoTorneoDto::getEquipoGanadorId)
						.collect(Collectors.toList());

				List<LocalDateTime> fechasUsadas = partidoServicio.listaPartidos().stream()
						.filter(p -> p.getTorneoId().equals(partido.getTorneoId()))
						.map(PartidoTorneoDto::getFechaPartido).filter(fp -> fp != null && !fp.isEmpty())
						.map(fp -> LocalDateTime.parse(fp, FORMATO_FECHA)).collect(Collectors.toList());

				LocalDate ultimaFechaRonda = obtenerUltimaFecha(partidosRondaActual);
				LocalDate inicioSemana = ultimaFechaRonda.plusWeeks(1).with(java.time.DayOfWeek.MONDAY);

				for (int i = 0; i < ganadores.size(); i += 2) {
					final int indexLocal = i;
					final int indexVisitante = i + 1;

					PartidoTorneoDto nuevoPartido = crearPartidoBase(partido.getTorneoId(), siguienteRonda, i / 2 + 1);
					nuevoPartido.setEstado("pendiente");

					EquipoTorneoDto eLocal = equipoTorneoServicio.listaEquiposTorneo().stream()
							.filter(e -> e.getIdEquipoTorneo() == ganadores.get(indexLocal)).findFirst().orElse(null);

					EquipoTorneoDto eVisit = equipoTorneoServicio.listaEquiposTorneo().stream()
							.filter(e -> e.getIdEquipoTorneo() == ganadores.get(indexVisitante)).findFirst()
							.orElse(null);

					if (eLocal != null) {
						nuevoPartido.setEquipoLocalId(eLocal.getIdEquipoTorneo());
						nuevoPartido.setClubLocalId(eLocal.getClubId());
					}
					if (eVisit != null) {
						nuevoPartido.setEquipoVisitanteId(eVisit.getIdEquipoTorneo());
						nuevoPartido.setClubVisitanteId(eVisit.getClubId());
					}

					nuevoPartido.setInstalacionId(torneoServicio.obtenerInstalacionDeTorneo(partido.getTorneoId()));

					LocalDateTime fechaPartido = asignarFechaPartido(fechasUsadas, inicioSemana);
					nuevoPartido.setFechaPartido(fechaPartido.format(FORMATO_FECHA));

					Long idNuevo = partidoServicio.guardarPartido(nuevoPartido);

					flujo.put(partidosRondaActual.get(indexLocal).getIdPartidoTorneo(), idNuevo);
					flujo.put(partidosRondaActual.get(indexVisitante).getIdPartidoTorneo(), idNuevo);

					fechasUsadas.add(fechaPartido);
				}
			}
		}

		if ("semifinal".equalsIgnoreCase(partido.getRonda())) {
			boolean existeTercerPuesto = partidoServicio.listaPartidos().stream()
					.anyMatch(p -> p.getTorneoId().equals(partido.getTorneoId())
							&& "tercerpuesto".equalsIgnoreCase(p.getRonda()));

			if (!existeTercerPuesto) {
				List<PartidoTorneoDto> semis = partidoServicio.listaPartidos().stream()
						.filter(p -> p.getTorneoId().equals(partido.getTorneoId())
								&& "semifinal".equalsIgnoreCase(p.getRonda()))
						.sorted(Comparator.comparingInt(PartidoTorneoDto::getUbicacionRonda))
						.collect(Collectors.toList());

				if (semis.size() == 2 && semis.stream().allMatch(p -> "finalizado".equalsIgnoreCase(p.getEstado()))) {

					Long perdedor1 = semis.get(0).getEquipoGanadorId().equals(semis.get(0).getEquipoLocalId())
							? semis.get(0).getEquipoVisitanteId()
							: semis.get(0).getEquipoLocalId();
					Long perdedor2 = semis.get(1).getEquipoGanadorId().equals(semis.get(1).getEquipoLocalId())
							? semis.get(1).getEquipoVisitanteId()
							: semis.get(1).getEquipoLocalId();

					PartidoTorneoDto tercerPuesto = crearPartidoBase(partido.getTorneoId(), "tercerpuesto", 1);
					tercerPuesto.setEstado("pendiente");
					tercerPuesto.setEquipoLocalId(perdedor1);
					tercerPuesto.setEquipoVisitanteId(perdedor2);

					EquipoTorneoDto local = equipoTorneoServicio.listaEquiposTorneo().stream()
							.filter(e -> e.getIdEquipoTorneo() == perdedor1).findFirst().orElse(null);

					EquipoTorneoDto visitante = equipoTorneoServicio.listaEquiposTorneo().stream()
							.filter(e -> e.getIdEquipoTorneo() == perdedor2).findFirst().orElse(null);

					if (local != null)
						tercerPuesto.setClubLocalId(local.getClubId());
					if (visitante != null)
						tercerPuesto.setClubVisitanteId(visitante.getClubId());

					tercerPuesto.setInstalacionId(torneoServicio.obtenerInstalacionDeTorneo(partido.getTorneoId()));

					LocalDate ultimaFechaSemis = obtenerUltimaFecha(semis);
					LocalDate inicioSemanaTercer = ultimaFechaSemis.plusWeeks(1).with(java.time.DayOfWeek.MONDAY);

					List<LocalDateTime> fechasUsadas = partidoServicio.listaPartidos().stream()
							.filter(p -> p.getTorneoId().equals(partido.getTorneoId()))
							.map(PartidoTorneoDto::getFechaPartido).filter(fp -> fp != null && !fp.isEmpty())
							.map(fp -> LocalDateTime.parse(fp, FORMATO_FECHA)).collect(Collectors.toList());

					LocalDateTime fechaTercer = asignarFechaPartido(fechasUsadas, inicioSemanaTercer);
					tercerPuesto.setFechaPartido(fechaTercer.format(FORMATO_FECHA));

					Long idTercer = partidoServicio.guardarPartido(tercerPuesto);
					flujo.put(-1L, idTercer);
				}
			}
		}

		Utilidades.guardarMapa(partido.getTorneoId(), flujo);
	}

// ---------- Progreso de Equipos ----------
	public String progresoEquipos(Long torneoId) {
		int inscritos = equipoTorneoServicio.contarEquiposPorTorneo(torneoId);
		return inscritos + " / 16";
	}

	public void actualizarClubesInscritos(Long torneoId) {
		try {
			int inscritos = equipoTorneoServicio.contarEquiposPorTorneo(torneoId);
			TorneoDto torneo = obtenerTorneo(torneoId);
			torneo.setClubesInscritos(inscritos + " / 16");
			modificarTorneo(torneoId, torneo);
		} catch (Exception e) {
			Log.ficheroLog("Error al actualizar clubesInscritos: " + e.getMessage());
		}
	}

	// ---------- Métodos privados ----------
	private JSONObject crearJsonTorneo(TorneoDto torneo) {
		JSONObject json = new JSONObject();
		json.put("nombreTorneo", torneo.getNombreTorneo());
		json.put("fechaInicioTorneo", torneo.getFechaInicioTorneo().toString());
		json.put("fechaFinTorneo", torneo.getFechaFinTorneo().toString());
		json.put("descripcionTorneo", torneo.getDescripcionTorneo());
		json.put("clubesInscritos", torneo.getClubesInscritos());
		json.put("modalidad", torneo.getModalidad().name());
		json.put("estaActivo", torneo.isEstaActivo());
		json.put("instalacionId", torneo.getInstalacionId());
		json.put("nombreInstalacion", torneo.getNombreInstalacion());
		json.put("direccionInstalacion", torneo.getDireccionInstalacion());

		return json;
	}

	private void ejecutarPost(String urlApi, JSONObject json) throws Exception {
		URL url = new URL(urlApi);
		HttpURLConnection conex = (HttpURLConnection) url.openConnection();
		conex.setRequestMethod("POST");
		conex.setRequestProperty("Content-Type", "application/json");
		conex.setDoOutput(true);
		try (OutputStream os = conex.getOutputStream()) {
			os.write(json.toString().getBytes("utf-8"), 0, json.toString().getBytes("utf-8").length);
		}
		conex.getResponseCode();
	}

	private boolean ejecutarPut(String urlApi, JSONObject json) throws Exception {
		URL url = new URL(urlApi);
		HttpURLConnection conex = (HttpURLConnection) url.openConnection();
		conex.setRequestMethod("PUT");
		conex.setRequestProperty("Content-Type", "application/json");
		conex.setDoOutput(true);
		try (OutputStream os = conex.getOutputStream()) {
			os.write(json.toString().getBytes("utf-8"), 0, json.toString().getBytes("utf-8").length);
		}
		return conex.getResponseCode() == HttpURLConnection.HTTP_OK;
	}

	private List<TorneoDto> hacerLlamadaApi(String urlStr) throws Exception {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null)
				sb.append(line);
			in.close();
			return new Gson().fromJson(sb.toString(), new TypeToken<List<TorneoDto>>() {
			}.getType());
		} else
			throw new RuntimeException("Error en la llamada a la API: código " + conn.getResponseCode());
	}

	/**
	 * Crea un partido base sin asignar equipos aún.
	 */
	private PartidoTorneoDto crearPartidoBase(Long torneoId, String ronda, int ubicacion) throws Exception {
		PartidoTorneoDto partido = new PartidoTorneoDto();
		partido.setTorneoId(torneoId);
		partido.setInstalacionId(obtenerInstalacionDeTorneo(torneoId));
		partido.setRonda(ronda);
		partido.setEstado("pendiente");
		partido.setUbicacionRonda(ubicacion);
		return partido;
	}

	// ---------- Métodos auxiliares ----------


	/**
	 * Genera fecha y hora para un partido de manera aleatoria dentro del rango
	 * permitido, evitando solapamientos y respetando margen de 1h30 entre partidos.
	 */
	private LocalDateTime asignarFechaPartido(List<LocalDateTime> usados, LocalDate inicioSemana) {
		List<LocalTime> horarios = Arrays.asList(LocalTime.of(19, 0), LocalTime.of(20, 30), LocalTime.of(22, 0));

		LocalDate fecha = inicioSemana;

		while (true) {
			// Saltar fines de semana
			while (fecha.getDayOfWeek().getValue() > 5) {
				fecha = fecha.plusDays(1);
			}

			for (LocalTime hora : horarios) {
				LocalDateTime fechaTentativa = LocalDateTime.of(fecha, hora);

				if (!usados.contains(fechaTentativa)) {
					usados.add(fechaTentativa);
					return fechaTentativa;
				}
			}

			// Si se llenaron todos los horarios del día, pasar al siguiente día hábil
			fecha = fecha.plusDays(1);
		}
	}

	private LocalDate obtenerUltimaFecha(List<PartidoTorneoDto> partidos) {
		return partidos.stream().map(PartidoTorneoDto::getFechaPartido).filter(fp -> fp != null && !fp.isEmpty())
				.map(fp -> LocalDateTime.parse(fp, FORMATO_FECHA).toLocalDate()).max(LocalDate::compareTo)
				.orElse(LocalDate.now());
	}

}
