package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.PartidoTorneoDto;
import vista_futbolDeBarrio.dtos.UsuarioDto;
import vista_futbolDeBarrio.log.Log;

/**
 * Clase que se encarga de la lógica de los métodos CRUD y consultas
 * relacionados con los partidos de torneo.
 */
public class PartidoTorneoServicio {

    /**
     * Obtiene la lista de todos los partidos de torneo.
     * 
     * @return Lista de objetos PartidoTorneoDto con los datos de cada partido.
     */
    public ArrayList<PartidoTorneoDto> listaPartidos() {
        ArrayList<PartidoTorneoDto> lista = new ArrayList<>();
        try {
            String urlApi = "http://localhost:9527/api/mostrarPartidosTorneo";
            HttpURLConnection conex = crearConexion(urlApi, "GET");

            if (conex.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) response.append(inputLine);
                in.close();

                JSONArray jsonArray = new JSONArray(response.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonPartido = jsonArray.getJSONObject(i);
                    PartidoTorneoDto partido = mapearJSONAPartido(jsonPartido);
                    lista.add(partido);
                }
            }

        } catch (Exception e) {
            Log.ficheroLog("Error listando partidos: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Obtiene un partido específico por su ID.
     * 
     * @param partidoId ID del partido que se desea obtener.
     * @return Objeto PartidoTorneoDto con los datos del partido, o null si no se encuentra.
     */
    public PartidoTorneoDto obtenerPartidoPorId(Long partidoId) {
        try {
            String urlApi = "http://localhost:9527/api/partidoTorneo/" + partidoId; 
            HttpURLConnection conex = crearConexion(urlApi, "GET");

            if (conex.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) response.append(inputLine);
                in.close();

                JSONObject jsonPartido = new JSONObject(response.toString());
                return mapearJSONAPartido(jsonPartido);
            }
        } catch (Exception e) {
            Log.ficheroLog("Error obteniendo partido por ID: " + e.getMessage());
        }
        return null;
    }


    /**
     * Obtiene la lista de partidos correspondientes a un torneo específico.
     * 
     * @param idTorneo ID del torneo.
     * @return Lista de objetos PartidoTorneoDto correspondientes al torneo.
     */
    public ArrayList<PartidoTorneoDto> listaPartidosPorTorneo(Long idTorneo) {
        ArrayList<PartidoTorneoDto> lista = new ArrayList<>();
        try {
            String urlApi = "http://localhost:9527/api/partidosPorTorneo/" + idTorneo;
            HttpURLConnection conex = crearConexion(urlApi, "GET");

            if (conex.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) response.append(inputLine);
                in.close();

                JSONArray jsonArray = new JSONArray(response.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonPartido = jsonArray.getJSONObject(i);
                    PartidoTorneoDto partido = mapearJSONAPartido(jsonPartido);
                    lista.add(partido);
                }
            }

        } catch (Exception e) {
            Log.ficheroLog("Error listando partidos por torneo: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Guarda un nuevo partido de torneo en el sistema.
     * 
     * @param partido Objeto PartidoTorneoDto con los datos del partido.
     * @return ID del partido guardado si la operación fue exitosa, null en caso contrario.
     * @throws Exception En caso de error al realizar la conexión o enviar el JSON.
     */
    public Long guardarPartido(PartidoTorneoDto partido, HttpServletRequest request) throws Exception {
        JSONObject json = mapearPartidoAJSON(partido);

        URL url = new URL("http://localhost:9527/api/guardarPartidoTorneo");
        HttpURLConnection conex = (HttpURLConnection) url.openConnection();
        conex.setRequestMethod("POST");
        conex.setRequestProperty("Content-Type", "application/json");

        // 🔑 Enviar token JWT
        HttpSession session = request.getSession(false);
        if (session == null) throw new IllegalStateException("No hay sesión activa");
        String token = (String) session.getAttribute("token");
        if (token == null || token.isEmpty()) throw new IllegalStateException("No se encontró token JWT");
        conex.setRequestProperty("Authorization", "Bearer " + token);

        conex.setDoOutput(true);

        try (OutputStream os = conex.getOutputStream()) {
            byte[] input = json.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conex.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream(), "utf-8"))) {
                StringBuilder respuesta = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    respuesta.append(inputLine);
                }

                JSONObject jsonRespuesta = new JSONObject(respuesta.toString());
                
                // Aquí cogemos el ID correcto según lo que devuelve la API
                Long idPartido = null;
                if (jsonRespuesta.has("idPartidoTorneo")) {
                    idPartido = jsonRespuesta.optLong("idPartidoTorneo", -1);
                } else if (jsonRespuesta.has("id")) {
                    idPartido = jsonRespuesta.optLong("id", -1);
                }

                if (idPartido != null && idPartido != -1) {
                    partido.setIdPartidoTorneo(idPartido);
                    System.out.println("✅ Partido guardado.");
                    return idPartido;
                } else {
                    System.err.println("⚠️ No se recibió ID del partido en la respuesta");
                    return null;
                }
            }
        } else {
            System.err.println("Error al guardar partido: HTTP " + responseCode);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conex.getErrorStream(), "utf-8"))) {
                StringBuilder errorResp = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    errorResp.append(line);
                }
                System.err.println("Detalle del error: " + errorResp);
            }
            return null;
        }
    }


    /**
     * Modifica los datos de un partido existente.
     * 
     * @param idPartido ID del partido a modificar.
     * @param partido Objeto PartidoTorneoDto con los nuevos datos.
     * @return true si la modificación fue exitosa, false en caso contrario.
     */
    public boolean modificarPartido(Long idPartido, PartidoTorneoDto partido, HttpServletRequest request) {
        try {
            JSONObject json = mapearPartidoAJSON(partido);

            String urlApi = "http://localhost:9527/api/modificarPartidoTorneo/" + idPartido;
            HttpURLConnection conex = (HttpURLConnection) new URL(urlApi).openConnection();
            conex.setRequestMethod("PUT");
            conex.setRequestProperty("Content-Type", "application/json");

            // 🔑 Enviar token JWT de la sesión
            HttpSession session = request.getSession(false);
            if (session == null) throw new IllegalStateException("No hay sesión activa");
            String token = (String) session.getAttribute("token");
            if (token == null || token.isEmpty()) throw new IllegalStateException("No se encontró token JWT");
            conex.setRequestProperty("Authorization", "Bearer " + token);

            conex.setDoOutput(true);

            try (OutputStream os = conex.getOutputStream()) {
                os.write(json.toString().getBytes("utf-8"));
            }

            int responseCode = conex.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;

        } catch (Exception e) {
            Log.ficheroLog("Error modificando partido: " + e.getMessage());
            return false;
        }
    }




    /**
     * Elimina un partido del sistema.
     * 
     * @param idPartido ID del partido a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminarPartido(Long idPartido) {
        try {
            String urlApi = "http://localhost:9527/api/eliminarPartidoTorneo/" + idPartido;
            HttpURLConnection conex = crearConexion(urlApi, "DELETE");
            return conex.getResponseCode() == HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            Log.ficheroLog("Error eliminando partido: " + e.getMessage());
            return false;
        }
    }


    /**
     * Filtra los partidos de un torneo por la ronda específica.
     * 
     * @param torneoId ID del torneo.
     * @param ronda Nombre de la ronda (ej. "Cuartos", "Semifinal").
     * @return Lista de partidos que pertenecen a la ronda indicada.
     */
    public List<PartidoTorneoDto> obtenerPartidosPorRonda(Long torneoId, String ronda) {
        List<PartidoTorneoDto> resultado = new ArrayList<>();
        List<PartidoTorneoDto> partidos = listaPartidosPorTorneo(torneoId);

        for (PartidoTorneoDto p : partidos) {
            if (p.getRonda().equalsIgnoreCase(ronda)) {
                resultado.add(p);
            }
        }
        return resultado;
    }


    /**
     * Crea y configura una conexión HTTP con la URL y método indicados.
     * 
     * @param urlApi URL del servicio web.
     * @param metodo Método HTTP (GET, POST, PUT, DELETE).
     * @return HttpURLConnection configurada.
     * @throws Exception En caso de error al abrir la conexión.
     */
    private HttpURLConnection crearConexion(String urlApi, String metodo) throws Exception {
        URL url = new URL(urlApi);
        HttpURLConnection conex = (HttpURLConnection) url.openConnection();
        conex.setRequestMethod(metodo);
        conex.setRequestProperty("Content-Type", "application/json");
        if (metodo.equals("POST") || metodo.equals("PUT")) conex.setDoOutput(true);
        return conex;
    }

    /**
     * Convierte un JSONObject a un objeto PartidoTorneoDto.
     * 
     * @param json JSONObject con los datos del partido.
     * @return Objeto PartidoTorneoDto mapeado.
     */
    private PartidoTorneoDto mapearJSONAPartido(JSONObject json) {
        PartidoTorneoDto partido = new PartidoTorneoDto();
        // Se mantiene la lógica de mapeo original
        partido.setIdPartidoTorneo(json.optLong("idPartidoTorneo", 0));
        partido.setTorneoId(json.optLong("torneoId", 0));
        partido.setInstalacionId(json.optLong("instalacionId", 0));
        partido.setClubLocalId(json.optLong("clubLocalId", 0));
        partido.setClubVisitanteId(json.optLong("clubVisitanteId", 0));
        partido.setEquipoLocalId(json.optLong("equipoLocalId", 0));
        partido.setEquipoVisitanteId(json.optLong("equipoVisitanteId", 0));
        partido.setActaPartidoId(json.has("actaPartidoId") ? json.optLong("actaPartidoId") : null);
        partido.setClubLocalNombre(json.optString("clubLocalNombre", "Local"));
        partido.setClubVisitanteNombre(json.optString("clubVisitanteNombre", "Visitante"));
        partido.setClubLocalAbreviatura(json.optString("clubLocalAbreviatura", ""));
        partido.setClubVisitanteAbreviatura(json.optString("clubVisitanteAbreviatura", ""));
        partido.setGolesLocal(json.optInt("golesLocal", 0));
        partido.setGolesVisitante(json.optInt("golesVisitante", 0));
        partido.setFechaPartido(json.optString("fechaPartido", ""));
        partido.setRonda(json.optString("ronda", ""));
        partido.setEstado(json.optString("estado", ""));
        partido.setUbicacionRonda(json.optInt("ubicacionRonda", 0));
        partido.setNombreTorneo(json.optString("nombreTorneo", ""));
        partido.setNombreInstalacion(json.optString("nombreInstalacion", ""));
        
        if (json.has("jugadoresLocal")) {
            JSONArray jugadoresLocal = json.getJSONArray("jugadoresLocal");
            List<UsuarioDto> listaLocal = new ArrayList<>();
            for (int i = 0; i < jugadoresLocal.length(); i++) {
                JSONObject j = jugadoresLocal.getJSONObject(i);
                UsuarioDto jugador = new UsuarioDto();
                jugador.setIdUsuario(j.optLong("idUsuario", 0));
                jugador.setNombreCompletoUsuario(j.optString("nombreCompletoUsuario", ""));
                listaLocal.add(jugador);
            }
            partido.setJugadoresLocal(listaLocal);
        }

        if (json.has("jugadoresVisitante")) {
            JSONArray jugadoresVisitante = json.getJSONArray("jugadoresVisitante");
            List<UsuarioDto> listaVisitante = new ArrayList<>();
            for (int i = 0; i < jugadoresVisitante.length(); i++) {
                JSONObject j = jugadoresVisitante.getJSONObject(i);
                UsuarioDto jugador = new UsuarioDto();
                jugador.setIdUsuario(j.optLong("idUsuario", 0));
                jugador.setNombreCompletoUsuario(j.optString("nombreCompletoUsuario", ""));
                listaVisitante.add(jugador);
            }
            partido.setJugadoresVisitante(listaVisitante);
        }
        
        partido.setEquipoGanadorId(
        	    (json.has("equipoGanadorId") && !json.isNull("equipoGanadorId")) ? 
        	        json.optLong("equipoGanadorId") : null
        	);

        partido.setActaCerrada(json.optBoolean("actaCerrada"));
        return partido;
    }

    /**
     * Convierte un objeto PartidoTorneoDto a JSONObject para enviarlo.
     * 
     * @param partido Objeto PartidoTorneoDto a mapear.
     * @return JSONObject con los datos del partido.
     */
    public JSONObject mapearPartidoAJSON(PartidoTorneoDto partido) {
        JSONObject json = new JSONObject();
        json.put("torneoId", partido.getTorneoId());
        json.put("instalacionId", partido.getInstalacionId());
        json.put("clubLocalId", partido.getClubLocalId());
        json.put("clubVisitanteId", partido.getClubVisitanteId());
        json.put("equipoLocalId", partido.getEquipoLocalId());
        json.put("equipoVisitanteId", partido.getEquipoVisitanteId());
        json.put("golesLocal", partido.getGolesLocal());
        json.put("golesVisitante", partido.getGolesVisitante());
        json.put("fechaPartido", partido.getFechaPartido());
        json.put("ronda", partido.getRonda());
        json.put("estado", partido.getEstado());
        json.put("actaPartidoId", partido.getActaPartidoId() != null ? partido.getActaPartidoId() : JSONObject.NULL);
        json.put("ubicacionRonda", partido.getUbicacionRonda());

        return json;
    }
}
