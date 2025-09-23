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
	
	    // -------------------------
	    // BRACKET Y PARTIDOS
	    // -------------------------
	    public void generarBracket(Long torneoId, List<EquipoTorneoDto> equipos, PartidoTorneoServicio partidoServicio) throws Exception {
	        if (equipos == null || equipos.size() != 16) {
	            throw new IllegalArgumentException("Actualmente solo se soportan torneos de 16 equipos.");
	        }

	        Collections.shuffle(equipos);

	        Map<Long, Long> flujoPartidos = new HashMap<>();

	        // ======================
	        // 🔹 CREAR OCTAVOS
	        // ======================
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
	            partido.setUbicacionRonda(i / 2 + 1); // octavo1 = 1, octavo2 = 2, ..., octavo8 = 8

	            Long idGuardado = partidoServicio.guardarPartido(partido);
	            partido.setIdPartidoTorneo(idGuardado);
	            octavos.add(partido);
	        }

	        // ======================
	        // 🔹 CREAR CUARTOS
	        // ======================
	        List<PartidoTorneoDto> cuartos = new ArrayList<>();
	        for (int i = 0; i < 8; i += 2) {
	            PartidoTorneoDto partido = new PartidoTorneoDto();
	            partido.setTorneoId(torneoId);
	            partido.setInstalacionId(obtenerInstalacionDeTorneo(torneoId));
	            partido.setFechaPartido(LocalDate.now().toString());
	            partido.setRonda("cuartos");
	            partido.setEstado("pendiente");
	            partido.setUbicacionRonda(i / 2 + 1); // cuartos 1 a 4

	            Long idGuardado = partidoServicio.guardarPartido(partido);
	            partido.setIdPartidoTorneo(idGuardado);
	            cuartos.add(partido);

	            // 🔹 ENLAZAR OCTAVOS → CUARTOS
	            flujoPartidos.put(octavos.get(i).getIdPartidoTorneo(), idGuardado);
	            flujoPartidos.put(octavos.get(i + 1).getIdPartidoTorneo(), idGuardado);
	        }

	        // ======================
	        // 🔹 CREAR SEMIFINALES
	        // ======================
	        List<PartidoTorneoDto> semifinales = new ArrayList<>();
	        for (int i = 0; i < 4; i += 2) {
	            PartidoTorneoDto partido = new PartidoTorneoDto();
	            partido.setTorneoId(torneoId);
	            partido.setInstalacionId(obtenerInstalacionDeTorneo(torneoId));
	            partido.setFechaPartido(LocalDate.now().toString());
	            partido.setRonda("semifinal");
	            partido.setEstado("pendiente");
	            partido.setUbicacionRonda(i / 2 + 1); // semifinal 1 y 2

	            Long idGuardado = partidoServicio.guardarPartido(partido);
	            partido.setIdPartidoTorneo(idGuardado);
	            semifinales.add(partido);

	            // 🔹 ENLAZAR CUARTOS → SEMIFINALES
	            flujoPartidos.put(cuartos.get(i).getIdPartidoTorneo(), idGuardado);
	            flujoPartidos.put(cuartos.get(i + 1).getIdPartidoTorneo(), idGuardado);
	        }

	        // ======================
	        // 🔹 CREAR FINAL
	        // ======================
	        PartidoTorneoDto partidoFinal = new PartidoTorneoDto();
	        partidoFinal.setTorneoId(torneoId);
	        partidoFinal.setInstalacionId(obtenerInstalacionDeTorneo(torneoId));
	        partidoFinal.setFechaPartido(LocalDate.now().toString());
	        partidoFinal.setRonda("final");
	        partidoFinal.setEstado("pendiente");
	        partidoFinal.setUbicacionRonda(1); // única final

	        Long idFinal = partidoServicio.guardarPartido(partidoFinal);
	        partidoFinal.setIdPartidoTorneo(idFinal);

	        flujoPartidos.put(semifinales.get(0).getIdPartidoTorneo(), idFinal);
	        flujoPartidos.put(semifinales.get(1).getIdPartidoTorneo(), idFinal);

	        // ======================
	        // 🔹 CREAR TERCER PUESTO
	        // ======================
	        PartidoTorneoDto tercerPuesto = new PartidoTorneoDto();
	        tercerPuesto.setTorneoId(torneoId);
	        tercerPuesto.setInstalacionId(obtenerInstalacionDeTorneo(torneoId));
	        tercerPuesto.setFechaPartido(LocalDate.now().toString());
	        tercerPuesto.setRonda("tercerpuesto");
	        tercerPuesto.setEstado("pendiente");
	        tercerPuesto.setUbicacionRonda(1); // único tercer puesto

	        Long idTercerPuesto = partidoServicio.guardarPartido(tercerPuesto);
	        tercerPuesto.setIdPartidoTorneo(idTercerPuesto);

	        flujoPartidos.put(semifinales.get(0).getIdPartidoTorneo(), idTercerPuesto);
	        flujoPartidos.put(semifinales.get(1).getIdPartidoTorneo(), idTercerPuesto);

	        // ======================
	        // 🔹 GUARDAR FLUJO EN MEMORIA
	        // ======================
	        Utilidades.guardarMapa(torneoId, flujoPartidos);

	        System.out.println(">>> Bracket generado y flujo guardado en memoria para torneo " + torneoId);
	    }

	    public void avanzarGanador(PartidoTorneoDto partido, Long idGanador, PartidoTorneoServicio partidoServicio) throws Exception {
	        // 1️⃣ Marcar el partido actual como finalizado
	        partido.setEstado("finalizado");
	        partidoServicio.modificarPartido(partido.getIdPartidoTorneo(), partido);

	        // 2️⃣ Obtener flujo del torneo
	        Map<Long, Long> flujo = Utilidades.obtenerMapa(partido.getTorneoId());
	        if (flujo == null) return;

	        Long idSiguientePartido = flujo.get(partido.getIdPartidoTorneo());

	        PartidoTorneoDto siguientePartido;
	        if (idSiguientePartido == null) {
	            // Crear partido de la siguiente ronda
	            siguientePartido = new PartidoTorneoDto();
	            siguientePartido.setTorneoId(partido.getTorneoId());
	            siguientePartido.setEstado("pendiente");
	            siguientePartido.setRonda(rondaSiguiente(partido.getRonda()));

	            // Guardar en DB
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

	        // 3️⃣ Asignar ganador al siguiente partido
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

	    
	    public String progresoEquipos(Long torneoId) {
	        int inscritos = equipoTorneoServicio.contarEquiposPorTorneo(torneoId);
	        int maxEquipos = 16; // fijo
	
	        return inscritos + " / " + maxEquipos;
	    }
	    
	    public void actualizarClubesInscritos(Long torneoId) {
	        try {
	            // Obtener número de equipos inscritos
	            int inscritos = equipoTorneoServicio.contarEquiposPorTorneo(torneoId);
	
	            // Obtener el torneo actual
	            List<TorneoDto> torneos = obtenerTodosLosTorneos();
	            TorneoDto torneo = torneos.stream()
	                                      .filter(t -> t.getIdTorneo() == torneoId)
	                                      .findFirst()
	                                      .orElse(null);
	
	            if (torneo != null) {
	                // Actualizar el campo
	                torneo.setClubesInscritos(inscritos + " / 16");
	                // Guardar cambios en la API
	                modificarTorneo(torneo.getIdTorneo(), torneo);
	            }
	
	        } catch (Exception e) {
	            e.printStackTrace();
	            Log.ficheroLog("Error al actualizar clubesInscritos: " + e.getMessage());
	        }
	    }
	    private String rondaSiguiente(String rondaActual) {
	        switch (rondaActual.toLowerCase()) {
	            case "octavos":
	                return "cuartos";
	            case "cuartos":
	                return "semifinal";
	            case "semifinal":
	                return "final";
	            default:
	                return null; // No hay siguiente ronda después de final o tercer puesto
	        }
	    }
	    public TorneoDto obtenerTorneo(Long torneoId) throws Exception {
	        // Obtener todos los torneos desde la API
	        List<TorneoDto> torneos = obtenerTodosLosTorneos();
	
	        // Buscar el torneo por ID usando comparación de primitivos
	        TorneoDto torneo = torneos.stream()
	                .filter(t -> t.getIdTorneo() == torneoId) // getIdTorneo() devuelve long primitivo
	                .findFirst()
	                .orElse(null);
	
	        if (torneo == null) {
	            throw new RuntimeException("Torneo no encontrado con ID " + torneoId);
	        }
	
	        return torneo;
	    }
	
	
	
	}
