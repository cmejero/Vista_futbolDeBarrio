package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.EquipoTorneoDto;
import vista_futbolDeBarrio.dtos.PartidoTorneoDto;
import vista_futbolDeBarrio.dtos.TorneoDto;
import vista_futbolDeBarrio.enums.Modalidad;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.utilidades.Utilidades;

public class TorneoServicio {

	private final EquipoTorneoServicio equipoTorneoServicio = new EquipoTorneoServicio();

	private static final String API_URL_ID = "http://localhost:9527/api/torneo";
	private static final String API_URL = "http://localhost:9527/api/mostrarTorneo";
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	
	
	   // ------------------ CREAR TORNEO ------------------
    public String crearTorneoDesdeFormulario(HttpServletRequest request) {
        try {
            String nombreTorneo = request.getParameter("nombreTorneo");
            String descripcion = request.getParameter("descripcionTorneo");
            String modalidadStr = request.getParameter("modalidad");
            Long instalacionId = (Long) request.getSession().getAttribute("idInstalacion");
            String fechaInicio = request.getParameter("fechaInicioTorneo");
            String fechaFin = request.getParameter("fechaFinTorneo");

            if (fechaFin == null || fechaFin.isEmpty())
                fechaFin = "9999-01-01";

            Modalidad modalidad = Modalidad.valueOf(modalidadStr.trim());

            TorneoDto torneo = new TorneoDto();
            torneo.setNombreTorneo(nombreTorneo);
            torneo.setDescripcionTorneo(descripcion);
            torneo.setModalidad(modalidad);
            torneo.setEstaActivo(false);
            torneo.setInstalacionId(instalacionId);
            torneo.setFechaInicioTorneo(fechaInicio);
            torneo.setFechaFinTorneo(fechaFin);

            guardarTorneo(torneo, request);

            Log.ficheroLog("Torneo creado: " + nombreTorneo);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

   
  

	// ---------- CRUD Torneo ----------
	public void guardarTorneo(TorneoDto torneo, HttpServletRequest request) {
	    try {
	        JSONObject json = crearJsonTorneo(torneo);
	        ejecutarPost("http://localhost:9527/api/guardarTorneo", json, request);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	 // ------------------ MODIFICAR TORNEO ------------------
    public boolean modificarTorneoDesdeFormulario(HttpServletRequest request) {
        try {
            Long idTorneo = Long.parseLong(request.getParameter("idTorneo"));
            String nombreTorneo = request.getParameter("nombreTorneo");
            String descripcion = request.getParameter("descripcionTorneo");
            String modalidadStr = request.getParameter("modalidad");
            String fechaInicio = request.getParameter("fechaInicioTorneo");
            String fechaFin = request.getParameter("fechaFinTorneo");
            String estaActivoStr = request.getParameter("estaActivo");

            Modalidad modalidad = Modalidad.valueOf(modalidadStr.trim());
            boolean estaActivo = "true".equalsIgnoreCase(estaActivoStr);

            TorneoDto torneo = new TorneoDto();
            torneo.setNombreTorneo(nombreTorneo);
            torneo.setDescripcionTorneo(descripcion);
            torneo.setModalidad(modalidad);
            torneo.setFechaInicioTorneo(fechaInicio);
            torneo.setFechaFinTorneo(fechaFin);
            torneo.setEstaActivo(estaActivo);

            boolean exito = modificarTorneo(idTorneo, torneo, request);

            if (exito)
                Log.ficheroLog("Torneo modificado: id=" + idTorneo);

            return exito;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


	public boolean modificarTorneo(long idTorneo, TorneoDto torneo, HttpServletRequest request) {
	    try {
	        // 1️⃣ Obtener token JWT de la sesión
	        HttpSession session = request.getSession(false);
	        if (session == null) throw new IllegalStateException("No hay sesión activa");
	        String token = (String) session.getAttribute("token");
	        if (token == null || token.isEmpty()) throw new IllegalStateException("No se encontró token JWT");

	        // 2️⃣ Crear JSON del torneo
	        JSONObject json = crearJsonTorneo(torneo);

	        // 3️⃣ Ejecutar PUT con token
	        return ejecutarPutConToken("http://localhost:9527/api/modificarTorneo/" + idTorneo, json, token);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	  // ------------------ ELIMINAR TORNEO ------------------
    public boolean eliminarTorneoDesdeServicio(HttpServletRequest request) {
        try {
            Long idTorneo = Long.parseLong(request.getParameter("idTorneo"));
            boolean eliminado = eliminarTorneo(idTorneo, request);

            if (eliminado)
                Log.ficheroLog("Torneo eliminado: id=" + idTorneo);

            return eliminado;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

	public boolean eliminarTorneo(Long idTorneo, HttpServletRequest request) {
	    try {
	        // 1️⃣ Obtener token JWT de la sesión
	        HttpSession session = request.getSession(false);
	        if (session == null) throw new IllegalStateException("No hay sesión activa");
	        String token = (String) session.getAttribute("token");
	        if (token == null || token.isEmpty()) throw new IllegalStateException("No se encontró token JWT");

	        // 2️⃣ Configurar conexión HTTP DELETE
	        URL url = new URL("http://localhost:9527/api/eliminarTorneo/" + idTorneo);
	        HttpURLConnection conex = (HttpURLConnection) url.openConnection();
	        conex.setRequestMethod("DELETE");
	        conex.setRequestProperty("Accept", "application/json");
	        conex.setRequestProperty("Authorization", "Bearer " + token); // 🔑 enviar token

	        // 3️⃣ Ejecutar y validar
	        int responseCode = conex.getResponseCode();
	        return responseCode == HttpURLConnection.HTTP_OK;

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

	
    // ------------------ GENERAR BRACKET ------------------
    public String generarBracketDesdeServicio(HttpServletRequest request) {
        try {
            Long torneoId = Long.parseLong(request.getParameter("torneoId"));
            generarBracket(torneoId, this, equipoTorneoServicio, new PartidoTorneoServicio(), request);
            Log.ficheroLog("Bracket generado para torneo id=" + torneoId);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
	/**
	 * Método principal para generar bracket, usando la lógica de fechas válidas.
	 */
	public void generarBracket(Long torneoId, TorneoServicio torneoServicio, EquipoTorneoServicio equipoTorneoServicio,
			PartidoTorneoServicio partidoServicio, HttpServletRequest request) throws Exception {

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

			partidoServicio.guardarPartido(partido, request);
		}
	}

	public void avanzarGanador(PartidoTorneoDto partido, Long idGanador, PartidoTorneoServicio partidoServicio,
			EquipoTorneoServicio equipoTorneoServicio, TorneoServicio torneoServicio, HttpServletRequest request) throws Exception {

		partido.setEstado("finalizado");
		partido.setEquipoGanadorId(idGanador);
		partidoServicio.modificarPartido(partido.getIdPartidoTorneo(), partido, request);

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

					Long idNuevo = partidoServicio.guardarPartido(nuevoPartido , request);

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

					Long idTercer = partidoServicio.guardarPartido(tercerPuesto, request);
					flujo.put(-1L, idTercer);
				}
			}
		}

		Utilidades.guardarMapa(partido.getTorneoId(), flujo);
	}

// ---------- Progreso de Equipos ----------
	
	  // ------------------ OBTENER TORNEOS CON PROGRESO ------------------
    public List<JSONObject> obtenerTorneosConProgreso(HttpServletRequest request) {
        try {
            String idParam = request.getParameter("instalacionId");
            List<TorneoDto> torneos;

            if (idParam != null && !idParam.isEmpty()) {
                Long instalacionId = Long.parseLong(idParam);
                torneos = obtenerTorneosPorInstalacion(instalacionId);
            } else {
                torneos = obtenerTodosLosTorneos();
            }

            List<JSONObject> torneosJson = new ArrayList<>();
            for (TorneoDto torneo : torneos) {
                String progreso = progresoEquipos(torneo.getIdTorneo());
                torneo.setClubesInscritos(progreso);
                JSONObject obj = new JSONObject(new Gson().toJson(torneo));
                obj.put("progresoEquipos", progreso);
                torneosJson.add(obj);
            }
            return torneosJson;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    
	public String progresoEquipos(Long torneoId) {
		int inscritos = equipoTorneoServicio.contarEquiposPorTorneo(torneoId);
		return inscritos + " / 16";
	}

	public void actualizarClubesInscritos(Long torneoId, HttpServletRequest request) {
	    try {
	        int inscritos = equipoTorneoServicio.contarEquiposPorTorneo(torneoId);

	        TorneoDto torneo = obtenerTorneo(torneoId);

	        if (torneo != null) {

	            torneo.setClubesInscritos(inscritos + " / 16");

	            boolean resultado = modificarTorneo(torneoId, torneo, request);

	            if (!resultado) {
	                Log.ficheroLog("No se pudo actualizar clubesInscritos para torneoId=" + torneoId);
	            }
	        } else {
	            Log.ficheroLog("Torneo no encontrado para torneoId=" + torneoId);
	        }
	    } catch (Exception e) {
	        Log.ficheroLog("Error al actualizar clubesInscritos: " + e.getMessage());
	        e.printStackTrace();
	    }
	}


	// ---------- Métodos privados ----------
	private JSONObject crearJsonTorneo(TorneoDto torneo) {
	    JSONObject json = new JSONObject();
	    json.put("nombreTorneo", torneo.getNombreTorneo());
	    json.put("fechaInicioTorneo", torneo.getFechaInicioTorneo() != null ? torneo.getFechaInicioTorneo() : "");
	    json.put("fechaFinTorneo", torneo.getFechaFinTorneo() != null ? torneo.getFechaFinTorneo() : "");
	    json.put("descripcionTorneo", torneo.getDescripcionTorneo() != null ? torneo.getDescripcionTorneo() : "");
	    json.put("clubesInscritos", torneo.getClubesInscritos() != null ? torneo.getClubesInscritos() : "");
	    json.put("modalidad", torneo.getModalidad() != null ? torneo.getModalidad().name() : "");
	    json.put("estaActivo", torneo.isEstaActivo());
	    json.put("instalacionId", torneo.getInstalacionId());
	    json.put("nombreInstalacion", torneo.getNombreInstalacion() != null ? torneo.getNombreInstalacion() : "");
	    json.put("direccionInstalacion", torneo.getDireccionInstalacion() != null ? torneo.getDireccionInstalacion() : "");

	    return json;
	}
	private void ejecutarPost(String urlApi, JSONObject json, HttpServletRequest request) throws Exception {

	    // 1️⃣ Obtener token de la sesión
	    HttpSession session = request.getSession(false);
	    if (session == null) {
	        throw new IllegalStateException("No hay sesión activa");
	    }

	    String token = (String) session.getAttribute("token");
	    if (token == null || token.isEmpty()) {
	        throw new IllegalStateException("No se encontró token JWT");
	    }

	    // 2️⃣ Conexión HTTP
	    URL url = new URL(urlApi);
	    HttpURLConnection conex = (HttpURLConnection) url.openConnection();
	    conex.setRequestMethod("POST");
	    conex.setRequestProperty("Content-Type", "application/json");
	    conex.setRequestProperty("Authorization", "Bearer " + token); // 🔑 CLAVE
	    conex.setDoOutput(true);

	    // 3️⃣ Enviar cuerpo
	    try (OutputStream os = conex.getOutputStream()) {
	        byte[] input = json.toString().getBytes("utf-8");
	        os.write(input, 0, input.length);
	    }

	    // 4️⃣ Forzar ejecución y validar
	    int responseCode = conex.getResponseCode();
	    if (responseCode != HttpURLConnection.HTTP_OK) {
	        throw new RuntimeException("Error POST " + urlApi + ". Código: " + responseCode);
	    }
	}


	private boolean ejecutarPutConToken(String urlApi, JSONObject json, String token) throws Exception {
	    URL url = new URL(urlApi);
	    HttpURLConnection conex = (HttpURLConnection) url.openConnection();
	    conex.setRequestMethod("PUT");
	    conex.setRequestProperty("Content-Type", "application/json");
	    conex.setRequestProperty("Authorization", "Bearer " + token); // 🔑 Token enviado
	    conex.setDoOutput(true);

	    try (OutputStream os = conex.getOutputStream()) {
	        os.write(json.toString().getBytes(StandardCharsets.UTF_8));
	    }

	    int responseCode = conex.getResponseCode();
	    return responseCode == HttpURLConnection.HTTP_OK;
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
	
	 // ------------------ ACTIVAR TORNEO ------------------
    public String activarTorneoDesdeServicio(HttpServletRequest request) {
        try {
            Long torneoId = Long.parseLong(request.getParameter("idTorneo"));

            actualizarClubesInscritos(torneoId, request);

            int inscritos = equipoTorneoServicio.contarEquiposPorTorneo(torneoId);
            if (inscritos != 16) {
                return "no_16";
            }

            TorneoDto torneo = obtenerTodosLosTorneos().stream()
                    .filter(t -> t.getIdTorneo() == torneoId)
                    .findFirst()
                    .orElse(null);

            if (torneo == null) return "no_encontrado";

            generarBracket(torneoId, this, equipoTorneoServicio, new PartidoTorneoServicio(), request);

            torneo.setEstaActivo(true);
            boolean exito = modificarTorneo(torneoId, torneo, request);

            if (exito) {
                Log.ficheroLog("Torneo activado: id=" + torneoId);
                return "ok";
            } else {
                return "error";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

}
