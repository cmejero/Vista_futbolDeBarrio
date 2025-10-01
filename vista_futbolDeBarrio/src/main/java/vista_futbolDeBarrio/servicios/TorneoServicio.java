	package vista_futbolDeBarrio.servicios;
	
	import java.io.BufferedReader;
	import java.io.InputStreamReader;
	import java.io.OutputStream;
	import java.net.HttpURLConnection;
	import java.net.URL;
	import java.time.LocalDate;
	import java.util.ArrayList;
	import java.util.Collections;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	
	import org.json.JSONObject;
	
	import com.google.gson.Gson;
	import com.google.gson.reflect.TypeToken;
	
	import vista_futbolDeBarrio.dtos.EquipoTorneoDto;
	import vista_futbolDeBarrio.dtos.PartidoTorneoDto;
	import vista_futbolDeBarrio.dtos.TorneoDto;
	import vista_futbolDeBarrio.log.Log;
	import vista_futbolDeBarrio.utilidades.Utilidades;
	
	/**
	 * Clase que se encarga de la logica de los metodos CRUD de torneo
	 */
	public class TorneoServicio {
		
		EquipoTorneoServicio equipoTorneoServicio = new EquipoTorneoServicio();
		PartidoTorneoServicio partidoTorneoServicio = new PartidoTorneoServicio();
	
		   /**
	     * Guarda un nuevo torneo.
	     * 
	     * @param torneo El torneo a guardar.
	     */
	    public void guardarTorneo(TorneoDto torneo) {
	        try {
	            JSONObject json = new JSONObject();
	            json.put("nombreTorneo", torneo.getNombreTorneo());
	            json.put("fechaInicioTorneo", torneo.getFechaInicioTorneo().toString());
	            json.put("fechaFinTorneo", torneo.getFechaFinTorneo().toString());
	            json.put("descripcionTorneo", torneo.getDescripcionTorneo());
	            json.put("clubesInscritos", torneo.getClubesInscritos());
	            json.put("modalidad", torneo.getModalidad().name());
	            json.put("estaActivo", torneo.isEstaActivo());
	            json.put("instalacionId", torneo.getInstalacionId());
	
	            String urlApi = "http://localhost:9527/api/guardarTorneo";
	            URL url = new URL(urlApi);
	
	            HttpURLConnection conex = (HttpURLConnection) url.openConnection();
	            conex.setRequestMethod("POST");
	            conex.setRequestProperty("Content-Type", "application/json");
	            conex.setDoOutput(true);
	
	            try (OutputStream os = conex.getOutputStream()) {
	                byte[] input = json.toString().getBytes("utf-8");
	                os.write(input, 0, input.length);
	            }
	
	            int responseCode = conex.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK) {
	                // System.out.println("Torneo guardado correctamente.");
	            } else {
	                // System.out.println("Error al guardar torneo: " + responseCode);
	            }
	
	        } catch (Exception e) {
	            // System.out.println("ERROR- [TorneoServicio] " + e);
	        }
	    }
	
	    private static final String API_URL_ID = "http://localhost:9527/api/torneo";
	    private static final String API_URL = "http://localhost:9527/api/mostrarTorneo";
	
	    /**
	     * Obtiene todos los torneos asociados a una instalación.
	     * 
	     * @param instalacionId El ID de la instalación.
	     * @return Lista de torneos de la instalación.
	     * @throws Exception Si ocurre un error en la llamada a la API.
	     */
	    public List<TorneoDto> obtenerTorneosPorInstalacion(Long instalacionId) throws Exception {
	        String endpoint = API_URL_ID + "?instalacionId=" + instalacionId;
	        return hacerLlamadaApi(endpoint);
	    }
	
	    /**
	     * Obtiene todos los torneos.
	     * 
	     * @return Lista de todos los torneos.
	     * @throws Exception Si ocurre un error en la llamada a la API.
	     */
	    public List<TorneoDto> obtenerTodosLosTorneos() throws Exception {
	        return hacerLlamadaApi(API_URL);
	    }
	    
	    public Long obtenerInstalacionDeTorneo(Long torneoId) throws Exception {
	        List<TorneoDto> torneos = obtenerTodosLosTorneos();
	        TorneoDto torneo = torneos.stream()
	                .filter(t -> t.getIdTorneo() == torneoId)
	                .findFirst()
	                .orElse(null);
	
	        if (torneo == null) {
	            throw new RuntimeException("Torneo no encontrado con ID " + torneoId);
	        }
	        return torneo.getInstalacionId();
	    }
	
	
	    /**
	     * Realiza la llamada a la API para obtener los torneos.
	     * 
	     * @param urlStr La URL de la API.
	     * @return Lista de torneos.
	     * @throws Exception Si ocurre un error en la llamada a la API.
	     */
	    private List<TorneoDto> hacerLlamadaApi(String urlStr) throws Exception {
	        URL url = new URL(urlStr);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	
	        int status = conn.getResponseCode();
	        if (status == 200) {
	            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            StringBuilder respuesta = new StringBuilder();
	            String inputLine;
	            while ((inputLine = in.readLine()) != null) {
	                respuesta.append(inputLine);
	            }
	            in.close();
	
	            return new Gson().fromJson(respuesta.toString(), new TypeToken<List<TorneoDto>>(){}.getType());
	        } else {
	            throw new RuntimeException("Error en la llamada a la API: código " + status);
	        }
	    }
	
	    /**
	     * Modifica un torneo existente.
	     * 
	     * @param idTorneo El ID del torneo a modificar.
	     * @param torneo El objeto con los nuevos datos del torneo.
	     * @return true si el torneo fue modificado con éxito, false en caso contrario.
	     */
	    public boolean modificarTorneo(long idTorneo, TorneoDto torneo) {
	        try {
	            JSONObject json = new JSONObject();
	            json.put("nombreTorneo", torneo.getNombreTorneo());
	            json.put("fechaInicioTorneo", torneo.getFechaInicioTorneo().toString());
	            json.put("fechaFinTorneo", torneo.getFechaFinTorneo().toString());
	            json.put("descripcionTorneo", torneo.getDescripcionTorneo());
	            json.put("clubesInscritos", torneo.getClubesInscritos());
	            json.put("modalidad", torneo.getModalidad().name());
	            json.put("estaActivo", torneo.isEstaActivo());
	            json.put("instalacionId", torneo.getInstalacionId());
	
	            String urlApi = "http://localhost:9527/api/modificarTorneo/" + idTorneo;
	            URL url = new URL(urlApi);
	
	            HttpURLConnection conex = (HttpURLConnection) url.openConnection();
	            conex.setRequestMethod("PUT");
	            conex.setRequestProperty("Content-Type", "application/json");
	            conex.setDoOutput(true);
	
	            try (OutputStream os = conex.getOutputStream()) {
	                byte[] input = json.toString().getBytes("utf-8");
	                os.write(input, 0, input.length);
	            }
	
	            int responseCode = conex.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK) {
	                return true; 
	            } else {
	                return false; 
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false; 
	        }
	    }
	    
	    /**
	     * Elimina un torneo por su ID.
	     * 
	     * @param idTorneo El ID del torneo a eliminar.
	     * @return true si el torneo fue eliminado con éxito, false en caso contrario.
	     */
	    public boolean eliminarTorneo(Long idTorneo) {
	        try {
	       
	            String urlApi = "http://localhost:9527/api/eliminarTorneo/" + idTorneo;  // Ajustar URL según tu API
	            URL url = new URL(urlApi);
	
	            HttpURLConnection conex = (HttpURLConnection) url.openConnection();
	            conex.setRequestMethod("DELETE");
	            conex.setRequestProperty("Accept", "application/json");
	
	            int responseCode = conex.getResponseCode();
	
	            if (responseCode == HttpURLConnection.HTTP_OK) {
	                // System.out.println("Torneo eliminado correctamente.");
	                return true;
	            } else {
	                // System.out.println("Error al eliminar torneo: " + responseCode);
	                return false;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            // System.out.println("ERROR- [Servicio.eliminarTorneo]: " + e.getMessage());
	            return false;
	        }
	    }
	

	 /**
	  * Genera el bracket completo de un torneo de 16 equipos y guarda los partidos en memoria.
	  *
	  * @param torneoId ID del torneo.
	  * @param equipos Lista de equipos inscritos (exactamente 16).
	  * @param partidoServicio Servicio para manejar partidos.
	  * @throws Exception si ocurre un error al crear los partidos.
	  */
	 public void generarBracket(Long torneoId, List<EquipoTorneoDto> equipos, PartidoTorneoServicio partidoServicio) throws Exception {
	     if (equipos == null || equipos.size() != 16) {
	         throw new IllegalArgumentException("Actualmente solo se soportan torneos de 16 equipos.");
	     }

	     Collections.shuffle(equipos);
	     Map<Long, Long> flujoPartidos = new HashMap<>();

	     List<PartidoTorneoDto> octavos = crearOctavos(torneoId, equipos, partidoServicio);
	     List<PartidoTorneoDto> cuartos = crearCuartos(torneoId, octavos, partidoServicio, flujoPartidos);
	     List<PartidoTorneoDto> semifinales = crearSemifinales(torneoId, cuartos, partidoServicio, flujoPartidos);
	     crearFinal(torneoId, semifinales, partidoServicio, flujoPartidos);
	     crearTercerPuesto(torneoId, semifinales, partidoServicio, flujoPartidos);

	     Utilidades.guardarMapa(torneoId, flujoPartidos);
	     System.out.println(">>> Bracket generado y flujo guardado en memoria para torneo " + torneoId);
	 }

	 /**
	  * Crea los partidos de octavos de final.
	  */
	 private List<PartidoTorneoDto> crearOctavos(Long torneoId, List<EquipoTorneoDto> equipos, PartidoTorneoServicio partidoServicio) throws Exception {
	     List<PartidoTorneoDto> octavos = new ArrayList<>();
	     for (int i = 0; i < 16; i += 2) {
	         EquipoTorneoDto local = equipos.get(i);
	         EquipoTorneoDto visitante = equipos.get(i + 1);

	         PartidoTorneoDto partido = new PartidoTorneoDto();
	         partido.setTorneoId(torneoId);
	         partido.setInstalacionId(obtenerInstalacionDeTorneo(torneoId));
	         partido.setEquipoLocalId(local.getIdEquipoTorneo());
	         partido.setClubLocalId(local.getClubId());
	         partido.setEquipoVisitanteId(visitante.getIdEquipoTorneo());
	         partido.setClubVisitanteId(visitante.getClubId());
	         partido.setFechaPartido(LocalDate.now().toString());
	         partido.setRonda("octavos");
	         partido.setEstado("pendiente");
	         partido.setUbicacionRonda(i / 2 + 1);

	         Long idGuardado = partidoServicio.guardarPartido(partido);
	         partido.setIdPartidoTorneo(idGuardado);
	         octavos.add(partido);
	     }
	     return octavos;
	 }

	 /**
	  * Crea los partidos de cuartos de final y enlaza octavos → cuartos.
	  */
	 private List<PartidoTorneoDto> crearCuartos(Long torneoId, List<PartidoTorneoDto> octavos, PartidoTorneoServicio partidoServicio, Map<Long, Long> flujoPartidos) throws Exception {
	     List<PartidoTorneoDto> cuartos = new ArrayList<>();
	     for (int i = 0; i < 8; i += 2) {
	         PartidoTorneoDto partido = new PartidoTorneoDto();
	         partido.setTorneoId(torneoId);
	         partido.setInstalacionId(obtenerInstalacionDeTorneo(torneoId));
	         partido.setFechaPartido(LocalDate.now().toString());
	         partido.setRonda("cuartos");
	         partido.setEstado("pendiente");
	         partido.setUbicacionRonda(i / 2 + 1);

	         Long idGuardado = partidoServicio.guardarPartido(partido);
	         partido.setIdPartidoTorneo(idGuardado);
	         cuartos.add(partido);

	         flujoPartidos.put(octavos.get(i).getIdPartidoTorneo(), idGuardado);
	         flujoPartidos.put(octavos.get(i + 1).getIdPartidoTorneo(), idGuardado);
	     }
	     return cuartos;
	 }

	 /**
	  * Crea los partidos de semifinales y enlaza cuartos → semifinales.
	  */
	 private List<PartidoTorneoDto> crearSemifinales(Long torneoId, List<PartidoTorneoDto> cuartos, PartidoTorneoServicio partidoServicio, Map<Long, Long> flujoPartidos) throws Exception {
	     List<PartidoTorneoDto> semifinales = new ArrayList<>();
	     for (int i = 0; i < 4; i += 2) {
	         PartidoTorneoDto partido = new PartidoTorneoDto();
	         partido.setTorneoId(torneoId);
	         partido.setInstalacionId(obtenerInstalacionDeTorneo(torneoId));
	         partido.setFechaPartido(LocalDate.now().toString());
	         partido.setRonda("semifinal");
	         partido.setEstado("pendiente");
	         partido.setUbicacionRonda(i / 2 + 1);

	         Long idGuardado = partidoServicio.guardarPartido(partido);
	         partido.setIdPartidoTorneo(idGuardado);
	         semifinales.add(partido);

	         flujoPartidos.put(cuartos.get(i).getIdPartidoTorneo(), idGuardado);
	         flujoPartidos.put(cuartos.get(i + 1).getIdPartidoTorneo(), idGuardado);
	     }
	     return semifinales;
	 }

	 /**
	  * Crea el partido final y enlaza semifinales → final.
	  */
	 private void crearFinal(Long torneoId, List<PartidoTorneoDto> semifinales, PartidoTorneoServicio partidoServicio, Map<Long, Long> flujoPartidos) throws Exception {
	     PartidoTorneoDto partidoFinal = new PartidoTorneoDto();
	     partidoFinal.setTorneoId(torneoId);
	     partidoFinal.setInstalacionId(obtenerInstalacionDeTorneo(torneoId));
	     partidoFinal.setFechaPartido(LocalDate.now().toString());
	     partidoFinal.setRonda("final");
	     partidoFinal.setEstado("pendiente");
	     partidoFinal.setUbicacionRonda(1);

	     Long idFinal = partidoServicio.guardarPartido(partidoFinal);
	     partidoFinal.setIdPartidoTorneo(idFinal);

	     flujoPartidos.put(semifinales.get(0).getIdPartidoTorneo(), idFinal);
	     flujoPartidos.put(semifinales.get(1).getIdPartidoTorneo(), idFinal);
	 }

	 /**
	  * Crea el partido de tercer puesto y enlaza semifinales → tercer puesto.
	  */
	 private void crearTercerPuesto(Long torneoId, List<PartidoTorneoDto> semifinales, PartidoTorneoServicio partidoServicio, Map<Long, Long> flujoPartidos) throws Exception {
	     PartidoTorneoDto tercerPuesto = new PartidoTorneoDto();
	     tercerPuesto.setTorneoId(torneoId);
	     tercerPuesto.setInstalacionId(obtenerInstalacionDeTorneo(torneoId));
	     tercerPuesto.setFechaPartido(LocalDate.now().toString());
	     tercerPuesto.setRonda("tercerpuesto");
	     tercerPuesto.setEstado("pendiente");
	     tercerPuesto.setUbicacionRonda(1);

	     Long idTercerPuesto = partidoServicio.guardarPartido(tercerPuesto);
	     tercerPuesto.setIdPartidoTorneo(idTercerPuesto);

	     flujoPartidos.put(semifinales.get(0).getIdPartidoTorneo(), idTercerPuesto);
	     flujoPartidos.put(semifinales.get(1).getIdPartidoTorneo(), idTercerPuesto);
	 }

	 /**
	  * Marca un partido como finalizado y asigna al ganador al siguiente partido.
	  *
	  * @param partido Partido actual que finalizó.
	  * @param idGanador ID del equipo ganador.
	  * @param partidoServicio Servicio para modificar los partidos.
	  * @throws Exception si ocurre un error al actualizar el siguiente partido.
	  */
	 public void avanzarGanador(PartidoTorneoDto partido, Long idGanador, PartidoTorneoServicio partidoServicio) throws Exception {
	     partido.setEstado("finalizado");
	     partidoServicio.modificarPartido(partido.getIdPartidoTorneo(), partido);

	     Map<Long, Long> flujo = Utilidades.obtenerMapa(partido.getTorneoId());
	     if (flujo == null) return;

	     Long idSiguientePartido = flujo.get(partido.getIdPartidoTorneo());
	     PartidoTorneoDto siguientePartido;

	     if (idSiguientePartido == null) {
	         siguientePartido = new PartidoTorneoDto();
	         siguientePartido.setTorneoId(partido.getTorneoId());
	         siguientePartido.setEstado("pendiente");
	         siguientePartido.setRonda(rondaSiguiente(partido.getRonda()));

	         idSiguientePartido = partidoServicio.guardarPartido(siguientePartido);
	         if (idSiguientePartido == null) throw new RuntimeException("No se pudo crear siguiente partido");

	         siguientePartido.setIdPartidoTorneo(idSiguientePartido);
	         flujo.put(partido.getIdPartidoTorneo(), idSiguientePartido);
	         Utilidades.guardarMapa(partido.getTorneoId(), flujo);
	     } else {
	         final Long idSiguiente = idSiguientePartido;
	         siguientePartido = partidoServicio.listaPartidos().stream()
	                 .filter(p -> p.getIdPartidoTorneo().equals(idSiguiente))
	                 .findFirst()
	                 .orElseThrow(() -> new RuntimeException("Siguiente partido no encontrado"));
	     }

	     EquipoTorneoDto equipoGanador = equipoTorneoServicio.listaEquiposTorneo().stream()
	             .filter(e -> e.getIdEquipoTorneo() == idGanador)
	             .findFirst().orElse(null);

	     if (equipoGanador != null) {
	         Long clubGanador = equipoGanador.getClubId();
	         if (siguientePartido.getEquipoLocalId() == null) {
	             siguientePartido.setEquipoLocalId(idGanador);
	             siguientePartido.setClubLocalId(clubGanador);
	         } else if (siguientePartido.getEquipoVisitanteId() == null) {
	             siguientePartido.setEquipoVisitanteId(idGanador);
	             siguientePartido.setClubVisitanteId(clubGanador);
	         }
	     }

	     partidoServicio.modificarPartido(siguientePartido.getIdPartidoTorneo(), siguientePartido);
	 }

	 /**
	  * Retorna el progreso de equipos inscritos en un torneo.
	  *
	  * @param torneoId ID del torneo.
	  * @return String con formato "equipos inscritos / 16".
	  */
	 public String progresoEquipos(Long torneoId) {
	     int inscritos = equipoTorneoServicio.contarEquiposPorTorneo(torneoId);
	     int maxEquipos = 16;
	     return inscritos + " / " + maxEquipos;
	 }

	 /**
	  * Actualiza la información de clubes inscritos en el torneo.
	  *
	  * @param torneoId ID del torneo.
	  */
	 public void actualizarClubesInscritos(Long torneoId) {
	     try {
	         int inscritos = equipoTorneoServicio.contarEquiposPorTorneo(torneoId);
	         List<TorneoDto> torneos = obtenerTodosLosTorneos();
	         TorneoDto torneo = torneos.stream()
	                 .filter(t -> t.getIdTorneo() == torneoId)
	                 .findFirst()
	                 .orElse(null);

	         if (torneo != null) {
	             torneo.setClubesInscritos(inscritos + " / 16");
	             modificarTorneo(torneo.getIdTorneo(), torneo);
	         }
	     } catch (Exception e) {
	         e.printStackTrace();
	         Log.ficheroLog("Error al actualizar clubesInscritos: " + e.getMessage());
	     }
	 }

	 /**
	  * Devuelve la siguiente ronda según la ronda actual.
	  *
	  * @param rondaActual Ronda actual ("octavos", "cuartos", "semifinal", etc.).
	  * @return Nombre de la siguiente ronda, o null si no existe.
	  */
	 private String rondaSiguiente(String rondaActual) {
	     switch (rondaActual.toLowerCase()) {
	         case "octavos": return "cuartos";
	         case "cuartos": return "semifinal";
	         case "semifinal": return "final";
	         default: return null;
	     }
	 }

	 /**
	  * Obtiene un torneo por su ID.
	  *
	  * @param torneoId ID del torneo.
	  * @return TorneoDto correspondiente al ID.
	  * @throws Exception si no se encuentra el torneo.
	  */
	 public TorneoDto obtenerTorneo(Long torneoId) throws Exception {
	     List<TorneoDto> torneos = obtenerTodosLosTorneos();
	     TorneoDto torneo = torneos.stream()
	             .filter(t -> t.getIdTorneo() == torneoId)
	             .findFirst()
	             .orElse(null);

	     if (torneo == null) {
	         throw new RuntimeException("Torneo no encontrado con ID " + torneoId);
	     }

	     return torneo;
	 }

	
	}
