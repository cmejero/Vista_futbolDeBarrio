package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
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
	public void generarBracket(Long torneoId, List<EquipoTorneoDto> equipos, PartidoTorneoServicio partidoServicio)
			throws Exception {
		if (equipos == null || equipos.size() != 16)
			throw new IllegalArgumentException("Actualmente solo se soportan torneos de 16 equipos.");

		Collections.shuffle(equipos);
		Map<Long, Long> flujoPartidos = new HashMap<>();

		// Octavos
		List<PartidoTorneoDto> octavos = new ArrayList<>();
		for (int i = 0; i < 16; i += 2) {
			PartidoTorneoDto p = crearPartidoBase(torneoId, "octavos", i / 2 + 1);
			p.setEquipoLocalId(equipos.get(i).getIdEquipoTorneo());
			p.setClubLocalId(equipos.get(i).getClubId());
			p.setEquipoVisitanteId(equipos.get(i + 1).getIdEquipoTorneo());
			p.setClubVisitanteId(equipos.get(i + 1).getClubId());
			p.setIdPartidoTorneo(partidoServicio.guardarPartido(p));
			octavos.add(p);
		}

		// Cuartos, Semis, Final y Tercer puesto
		List<PartidoTorneoDto> cuartos = crearRonda(torneoId, "cuartos", octavos, 4, partidoServicio, flujoPartidos);
		List<PartidoTorneoDto> semis = crearRonda(torneoId, "semifinal", cuartos, 2, partidoServicio, flujoPartidos);
		crearRonda(torneoId, "partidoFinal", semis, 1, partidoServicio, flujoPartidos);
		crearRonda(torneoId, "tercerpuesto", semis, 1, partidoServicio, flujoPartidos);

		Utilidades.guardarMapa(torneoId, flujoPartidos);
	}

	public void avanzarGanador(PartidoTorneoDto partido, Long idGanador,
	        PartidoTorneoServicio partidoServicio, EquipoTorneoServicio equipoTorneoServicio,
	        TorneoServicio torneoServicio) throws Exception {

	    Log.ficheroLog("=== Iniciando avanzarGanador ===");

	    // 1️⃣ Marcar el partido como finalizado
	    partido.setEstado("finalizado");
	    partido.setEquipoGanadorId(idGanador);
	    partidoServicio.modificarPartido(partido.getIdPartidoTorneo(), partido);

	    // 2️⃣ Cargar flujo de partidos existente
	    Map<Long, Long> flujo = Utilidades.obtenerMapa(partido.getTorneoId());
	    if (flujo == null) flujo = new HashMap<>();

	    // 3️⃣ Definir orden de rondas
	    String[] rondas = { "octavos", "cuartos", "semifinal", "partidoFinal", "tercerpuesto" };
	    int indexRonda = Arrays.asList(rondas).indexOf(partido.getRonda());
	    if (indexRonda == -1) return;

	    // 4️⃣ Crear siguiente ronda (excepto final y tercer puesto)
	    if (indexRonda < rondas.length - 2) {
	        String siguienteRonda = rondas[indexRonda + 1];

	        List<PartidoTorneoDto> partidosRondaActual = partidoServicio.listaPartidos().stream()
	                .filter(p -> p.getTorneoId().equals(partido.getTorneoId()) && p.getRonda().equalsIgnoreCase(partido.getRonda()))
	                .sorted(Comparator.comparingInt(PartidoTorneoDto::getUbicacionRonda))
	                .collect(Collectors.toList());

	        boolean todosFinalizados = partidosRondaActual.stream()
	                .allMatch(p -> "finalizado".equalsIgnoreCase(p.getEstado()));

	        List<PartidoTorneoDto> partidosSiguienteRonda = partidoServicio.listaPartidos().stream()
	                .filter(p -> p.getTorneoId().equals(partido.getTorneoId()) && p.getRonda().equalsIgnoreCase(siguienteRonda))
	                .collect(Collectors.toList());

	        if (partidosSiguienteRonda.isEmpty() && todosFinalizados) {
	            List<Long> ganadores = partidosRondaActual.stream()
	                    .map(PartidoTorneoDto::getEquipoGanadorId)
	                    .collect(Collectors.toList());

	            for (int i = 0; i < ganadores.size(); i += 2) {
	                final int indexLocal = i;
	                final int indexVisitante = i + 1;

	                PartidoTorneoDto nuevoPartido = crearPartidoBase(partido.getTorneoId(), siguienteRonda, i / 2 + 1);
	                nuevoPartido.setEstado("pendiente");

	                // Asignar equipos y clubes
	                EquipoTorneoDto eLocal = equipoTorneoServicio.listaEquiposTorneo().stream()
	                        .filter(e -> e.getIdEquipoTorneo() == ganadores.get(indexLocal))
	                        .findFirst().orElse(null);

	                EquipoTorneoDto eVisit = equipoTorneoServicio.listaEquiposTorneo().stream()
	                        .filter(e -> e.getIdEquipoTorneo() == ganadores.get(indexVisitante))
	                        .findFirst().orElse(null);


	                if (eLocal != null) {
	                    nuevoPartido.setEquipoLocalId(eLocal.getIdEquipoTorneo());
	                    nuevoPartido.setClubLocalId(eLocal.getClubId());
	                }
	                if (eVisit != null) {
	                    nuevoPartido.setEquipoVisitanteId(eVisit.getIdEquipoTorneo());
	                    nuevoPartido.setClubVisitanteId(eVisit.getClubId());
	                }

	                nuevoPartido.setInstalacionId(torneoServicio.obtenerInstalacionDeTorneo(partido.getTorneoId()));
	                Long idNuevo = partidoServicio.guardarPartido(nuevoPartido);

	                flujo.put(partidosRondaActual.get(indexLocal).getIdPartidoTorneo(), idNuevo);
	                flujo.put(partidosRondaActual.get(indexVisitante).getIdPartidoTorneo(), idNuevo);
	            }
	        }
	    }

	    // 5️⃣ Crear partido de tercer puesto si semifinales completadas
	    if ("semifinal".equalsIgnoreCase(partido.getRonda())) {
	        boolean existeTercerPuesto = partidoServicio.listaPartidos().stream()
	                .anyMatch(p -> p.getTorneoId().equals(partido.getTorneoId()) && "tercerpuesto".equalsIgnoreCase(p.getRonda()));

	        if (!existeTercerPuesto) {
	            List<PartidoTorneoDto> semis = partidoServicio.listaPartidos().stream()
	                    .filter(p -> p.getTorneoId().equals(partido.getTorneoId()) && "semifinal".equalsIgnoreCase(p.getRonda()))
	                    .collect(Collectors.toList());

	            if (semis.size() == 2 && semis.stream().allMatch(p -> "finalizado".equalsIgnoreCase(p.getEstado()))) {
	                PartidoTorneoDto tercerPuesto = crearPartidoBase(partido.getTorneoId(), "tercerpuesto", 1);
	                tercerPuesto.setEstado("pendiente");

	                // Determinar perdedores
	                Long perdedor1 = semis.get(0).getEquipoGanadorId().equals(semis.get(0).getEquipoLocalId())
	                        ? semis.get(0).getEquipoVisitanteId() : semis.get(0).getEquipoLocalId();
	                Long perdedor2 = semis.get(1).getEquipoGanadorId().equals(semis.get(1).getEquipoLocalId())
	                        ? semis.get(1).getEquipoVisitanteId() : semis.get(1).getEquipoLocalId();

	                tercerPuesto.setEquipoLocalId(perdedor1);
	                tercerPuesto.setEquipoVisitanteId(perdedor2);

	                // Asignar clubes de los perdedores
	                EquipoTorneoDto local = equipoTorneoServicio.listaEquiposTorneo().stream()
	                        .filter(e -> e.getIdEquipoTorneo() == perdedor1)
	                        .findFirst().orElse(null);

	                EquipoTorneoDto visitante = equipoTorneoServicio.listaEquiposTorneo().stream()
	                        .filter(e -> e.getIdEquipoTorneo() == perdedor2)
	                        .findFirst().orElse(null);


	                if (local != null) tercerPuesto.setClubLocalId(local.getClubId());
	                if (visitante != null) tercerPuesto.setClubVisitanteId(visitante.getClubId());

	                tercerPuesto.setInstalacionId(torneoServicio.obtenerInstalacionDeTorneo(partido.getTorneoId()));
	                Long idTercer = partidoServicio.guardarPartido(tercerPuesto);

	                flujo.put(-1L, idTercer); // marcador temporal
	            }
	        }
	    }

	    // 6️⃣ Guardar flujo actualizado
	    Utilidades.guardarMapa(partido.getTorneoId(), flujo);
	    Log.ficheroLog("=== Fin avanzarGanador ===");
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

	private PartidoTorneoDto crearPartidoBase(Long torneoId, String ronda, int ubicacion) throws Exception {
		PartidoTorneoDto p = new PartidoTorneoDto();
		p.setTorneoId(torneoId);
		p.setInstalacionId(obtenerInstalacionDeTorneo(torneoId));
		p.setFechaPartido(LocalDate.now().toString());
		p.setRonda(ronda);
		p.setEstado("pendiente");
		p.setUbicacionRonda(ubicacion);
		return p;
	}

	private List<PartidoTorneoDto> crearRonda(Long torneoId, String ronda, List<PartidoTorneoDto> prev, int cantidad,
			PartidoTorneoServicio servicio, Map<Long, Long> flujo) throws Exception {
		List<PartidoTorneoDto> nueva = new ArrayList<>();
		for (int i = 0; i < cantidad; i++) {
			PartidoTorneoDto p = crearPartidoBase(torneoId, ronda, i + 1);
			Long id = servicio.guardarPartido(p);
			p.setIdPartidoTorneo(id);
			nueva.add(p);
			if (prev != null && i * 2 + 1 < prev.size()) {
				flujo.put(prev.get(i * 2).getIdPartidoTorneo(), id);
				flujo.put(prev.get(i * 2 + 1).getIdPartidoTorneo(), id);
			}
		}
		return nueva;
	}
}
