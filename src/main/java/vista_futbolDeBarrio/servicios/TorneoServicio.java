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

/**
 * Clase que se encarga de la logica de los metodos de torneos
 */
public class TorneoServicio {

	private final EquipoTorneoServicio equipoTorneoServicio = new EquipoTorneoServicio();

	private static final String API_URL_ID = "http://localhost:9527/api/torneo";
	private static final String API_URL = "http://localhost:9527/api/mostrarTorneo";
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	
	
	/**
	 * Crea un torneo a partir de los datos recibidos en un formulario HTTP.
	 *
	 * @param request Petición HTTP con los parámetros del torneo.
	 * @return "ok" si se crea correctamente, o un mensaje de error si falla la operación.
	 */
	public String crearTorneoDesdeFormulario(HttpServletRequest request) {

	    try {
	        // Tomamos parámetros del formulario o de AJAX
	        String nombreTorneo = request.getParameter("nombreTorneo");
	        String descripcion = request.getParameter("descripcionTorneo");
	        String modalidadStr = request.getParameter("modalidad");
	        String fechaInicio = request.getParameter("fechaInicioTorneo");
	        String fechaFin = request.getParameter("fechaFinTorneo");
	        Long instalacionId = Long.valueOf(request.getParameter("instalacionId"));

	        if (nombreTorneo == null || nombreTorneo.isEmpty()) return "Nombre del torneo vacío";
	        if (modalidadStr == null || modalidadStr.isEmpty()) return "Modalidad no definida";

	        if (fechaFin == null || fechaFin.isEmpty()) fechaFin = "9999-01-01";

	        Modalidad modalidad;
	        try {
	            modalidad = Modalidad.valueOf(modalidadStr.trim());
	        } catch (IllegalArgumentException e) {
	            return "Modalidad inválida: " + modalidadStr;
	        }

	        TorneoDto torneo = new TorneoDto();
	        torneo.setNombreTorneo(nombreTorneo);
	        torneo.setDescripcionTorneo(descripcion);
	        torneo.setModalidad(modalidad);
	        torneo.setEstaActivo(false);
	        torneo.setInstalacionId(instalacionId);
	        torneo.setFechaInicioTorneo(fechaInicio);
	        torneo.setFechaFinTorneo(fechaFin);

	        // Guardamos torneo directamente usando servicio local (igual que antes)
	        guardarTorneo(torneo, request);

	        return "ok";

	    } catch (Exception e) {
	        e.printStackTrace();
	        return e.getMessage(); // Devuelve el mensaje real al cliente
	    }
	}

  

	/**
	 * Envía los datos de un torneo a la API para guardarlo.
	 *
	 * @param torneo Objeto TorneoDto con la información del torneo.
	 * @param request Petición HTTP desde la que se puede obtener información adicional si es necesario.
	 */
	public void guardarTorneo(TorneoDto torneo, HttpServletRequest request) {
	    try {
	        JSONObject json = crearJsonTorneo(torneo);
	        ejecutarPost("http://localhost:9527/api/guardarTorneo", json, request);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	/**
	 * Modifica un torneo a partir de los datos enviados en un formulario HTTP.
	 *
	 * @param request Petición HTTP con los parámetros del torneo a modificar.
	 * @return true si la modificación fue exitosa, false en caso contrario.
	 */
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
                Log.ficheroLog("Torneo modificado.");

            return exito;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Modifica un torneo en la API usando su ID y los datos proporcionados.
     *
     * @param idTorneo ID del torneo a modificar.
     * @param torneo Objeto TorneoDto con la información actualizada.
     * @param request Petición HTTP desde la que se obtiene el token JWT de sesión.
     * @return true si la modificación fue exitosa, false en caso contrario.
     */
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
	
	
	/**
	 * Elimina un torneo a partir del ID recibido en un formulario HTTP.
	 *
	 * @param request Petición HTTP que contiene el ID del torneo a eliminar.
	 * @return true si la eliminación fue exitosa, false en caso contrario.
	 */
    public boolean eliminarTorneoDesdeServicio(HttpServletRequest request) {
        try {
            Long idTorneo = Long.parseLong(request.getParameter("idTorneo"));
            boolean eliminado = eliminarTorneo(idTorneo, request);

            if (eliminado)
                Log.ficheroLog("Torneo eliminado.");

            return eliminado;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    /**
     * Elimina un torneo en la API usando su ID y un token de sesión.
     *
     * @param idTorneo ID del torneo a eliminar.
     * @param request Petición HTTP desde la que se obtiene el token JWT.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
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


	/**
	 * Obtiene la lista de todos los torneos desde la API.
	 *
	 * @return Lista de objetos TorneoDto con todos los torneos disponibles.
	 * @throws Exception Si ocurre un error durante la llamada a la API.
	 */
	public List<TorneoDto> obtenerTodosLosTorneos() throws Exception {
		return hacerLlamadaApi(API_URL);
	}

	
	/**
	 * Obtiene la lista de torneos asociados a una instalación específica desde la API.
	 *
	 * @param instalacionId ID de la instalación.
	 * @return Lista de objetos TorneoDto correspondientes a la instalación.
	 * @throws Exception Si ocurre un error durante la llamada a la API.
	 */
	public List<TorneoDto> obtenerTorneosPorInstalacion(Long instalacionId) throws Exception {
		return hacerLlamadaApi(API_URL_ID + "?instalacionId=" + instalacionId);
	}

	
	/**
	 * Obtiene un torneo específico por su ID.
	 *
	 * @param torneoId ID del torneo a buscar.
	 * @return Objeto TorneoDto correspondiente al ID proporcionado.
	 * @throws RuntimeException Si no se encuentra un torneo con el ID dado.
	 * @throws Exception Si ocurre un error al obtener la lista de torneos.
	 */
	public TorneoDto obtenerTorneo(Long torneoId) throws Exception {
		return obtenerTodosLosTorneos().stream().filter(t -> t.getIdTorneo() == torneoId).findFirst()
				.orElseThrow(() -> new RuntimeException("Torneo no encontrado con ID " + torneoId));
	}

	
	/**
	 * Obtiene el ID de la instalación asociada a un torneo específico.
	 *
	 * @param torneoId ID del torneo.
	 * @return ID de la instalación correspondiente al torneo.
	 * @throws Exception Si ocurre un error al obtener el torneo.
	 */
	public Long obtenerInstalacionDeTorneo(Long torneoId) throws Exception {
		return obtenerTorneo(torneoId).getInstalacionId();
	}

	
    // ------------------ GENERAR BRACKET ------------------
	
	/**
	 * Genera el bracket de un torneo a partir del ID recibido en un formulario HTTP.
	 *
	 * @param request Petición HTTP que contiene el ID del torneo.
	 * @return "ok" si el bracket se generó correctamente, "error" en caso contrario.
	 */
    public String generarBracketDesdeServicio(HttpServletRequest request) {
        try {
            Long torneoId = Long.parseLong(request.getParameter("torneoId"));
            generarBracket(torneoId, this, equipoTorneoServicio, new PartidoTorneoServicio(), request);
            Log.ficheroLog("Bracket generado para torneo.");
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    
    
    
    /**
     * Genera el bracket completo de un torneo (octavos) para 16 equipos, asignando partidos y fechas automáticamente.
     *
     * @param torneoId ID del torneo para el cual se genera el bracket.
     * @param torneoServicio Servicio de torneos para obtener datos y fechas.
     * @param equipoTorneoServicio Servicio de equipos de torneo para obtener los equipos inscritos.
     * @param partidoServicio Servicio de partidos para guardar los partidos generados.
     * @param request Petición HTTP asociada, utilizada para operaciones que requieren contexto de sesión.
     * @throws Exception Si no hay exactamente 16 equipos o ocurre un error durante la generación.
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

	
	/**
	 * Avanza al equipo ganador a la siguiente ronda y crea los partidos necesarios.
	 *
	 * @param partido Partido que se ha finalizado.
	 * @param idGanador ID del equipo ganador.
	 * @param partidoServicio Servicio de gestión de partidos.
	 * @param equipoTorneoServicio Servicio de gestión de equipos del torneo.
	 * @param torneoServicio Servicio de gestión de torneos.
	 * @param request Objeto HTTP de la solicitud.
	 * @throws Exception Si ocurre algún error al actualizar o crear partidos.
	 */
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
	
	/**
	 * Obtiene la lista de torneos con el progreso de los equipos.
	 *
	 * @param request Objeto HTTP que puede incluir el parámetro "instalacionId".
	 * @return Lista de torneos en formato JSON con el progreso de los equipos.
	 */
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
    
    /**
     * Calcula el progreso de inscripción de equipos en un torneo.
     *
     * @param torneoId ID del torneo.
     * @return Cadena con el número de equipos inscritos sobre el total (16).
     */
	public String progresoEquipos(Long torneoId) {
		int inscritos = equipoTorneoServicio.contarEquiposPorTorneo(torneoId);
		return inscritos + " / 16";
	}

	
	
	/**
	 * Actualiza el número de clubes inscritos en un torneo.
	 *
	 * @param torneoId ID del torneo a actualizar.
	 * @param request Objeto HTTP de la solicitud.
	 */
	public void actualizarClubesInscritos(Long torneoId, HttpServletRequest request) {
	    try {
	        int inscritos = equipoTorneoServicio.contarEquiposPorTorneo(torneoId);

	        TorneoDto torneo = obtenerTorneo(torneoId);

	        if (torneo != null) {

	            torneo.setClubesInscritos(inscritos + " / 16");

	            boolean resultado = modificarTorneo(torneoId, torneo, request);

	            if (!resultado) {
	                Log.ficheroLog("No se pudo actualizar clubesInscritos.");
	            }
	        } else {
	            Log.ficheroLog("Torneo no encontrado.");
	        }
	    } catch (Exception e) {
	        Log.ficheroLog("Error al actualizar clubesInscritos: " + e.getMessage());
	        e.printStackTrace();
	    }
	}


	// ---------- Métodos privados ----------
	
	
	/**
	 * Convierte un objeto TorneoDto en un JSONObject con sus datos principales.
	 *
	 * @param torneo Objeto TorneoDto a convertir.
	 * @return JSONObject con la información del torneo.
	 */
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
	
	
	/**
	 * Realiza una solicitud HTTP POST a la URL indicada con un JSON y token de sesión.
	 *
	 * @param urlApi URL del endpoint al que se enviará la solicitud.
	 * @param json Objeto JSON que se enviará en el cuerpo de la petición.
	 * @param request Objeto HTTP que contiene la sesión con el token JWT.
	 * @throws Exception Si ocurre un error en la conexión o la respuesta HTTP no es 200.
	 */
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

	/**
	 * Realiza una solicitud HTTP PUT a la URL indicada usando un token de autorización.
	 *
	 * @param urlApi URL del endpoint al que se enviará la solicitud.
	 * @param json Objeto JSON que se enviará en el cuerpo de la petición.
	 * @param token Token JWT para autorización.
	 * @return true si la respuesta HTTP es 200, false en caso contrario.
	 * @throws Exception Si ocurre un error en la conexión.
	 */
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

	
	/**
	 * Realiza una solicitud HTTP GET a la URL indicada y devuelve la lista de torneos.
	 *
	 * @param urlStr URL del endpoint de la API.
	 * @return Lista de objetos TorneoDto obtenidos de la API.
	 * @throws Exception Si ocurre un error en la conexión o la respuesta no es 200.
	 */
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
	 * Crea un objeto PartidoTorneoDto básico con los datos esenciales.
	 *
	 * @param torneoId ID del torneo al que pertenece el partido.
	 * @param ronda Ronda del torneo (octavos, cuartos, semifinal, etc.).
	 * @param ubicacion Posición del partido dentro de la ronda.
	 * @return PartidoTorneoDto inicializado y listo para usarse.
	 * @throws Exception Si ocurre un error al obtener la instalación del torneo.
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
	 * Asigna la próxima fecha y hora disponible para un partido evitando conflictos.
	 *
	 * @param usados Lista de fechas ya asignadas.
	 * @param inicioSemana Fecha de inicio de la semana para programar partidos.
	 * @return LocalDateTime disponible para el partido.
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

	
	/**
	 * Obtiene la última fecha programada entre una lista de partidos.
	 *
	 * @param partidos Lista de partidos del torneo.
	 * @return Última fecha de partido; si no hay fechas, retorna la fecha actual.
	 */
	private LocalDate obtenerUltimaFecha(List<PartidoTorneoDto> partidos) {
		return partidos.stream().map(PartidoTorneoDto::getFechaPartido).filter(fp -> fp != null && !fp.isEmpty())
				.map(fp -> LocalDateTime.parse(fp, FORMATO_FECHA).toLocalDate()).max(LocalDate::compareTo)
				.orElse(LocalDate.now());
	}
	
	
	
	/**
	 * Activa un torneo si tiene 16 equipos inscritos y genera el bracket.
	 *
	 * @param request Objeto HTTP que contiene el parámetro "idTorneo".
	 * @return "ok" si se activó correctamente, "no_16" si no hay 16 equipos,
	 *         "no_encontrado" si no se encuentra el torneo, "error" en caso de fallo.
	 */
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
                Log.ficheroLog("Torneo activado.");
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
