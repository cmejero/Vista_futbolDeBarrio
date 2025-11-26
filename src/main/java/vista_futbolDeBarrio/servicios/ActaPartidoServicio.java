package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import vista_futbolDeBarrio.dtos.ActaPartidoDto;
import vista_futbolDeBarrio.dtos.EventoPartidoDto;
import vista_futbolDeBarrio.log.Log;

/**
 * Clase que se encarga de la lógica de los métodos CRUD de ActaPartido.
 */
public class ActaPartidoServicio {

    // ------------------------- CRUD Acta -------------------------

    /**
     * Obtiene la lista de todas las actas de partidos del sistema.
     * 
     * @return Lista de objetos ActaPartidoDto con los datos de cada acta.
     */
    public ArrayList<ActaPartidoDto> listaActaPartido() {
        ArrayList<ActaPartidoDto> lista = new ArrayList<>();
        try {
            String urlApi = "http://localhost:9527/api/listarActasPartidos";
            HttpURLConnection conex = crearConexion(urlApi, "GET");

            if (conex.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) response.append(inputLine);
                in.close();

                JSONArray jsonArray = new JSONArray(response.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonActa = jsonArray.getJSONObject(i);
                    ActaPartidoDto acta = new ActaPartidoDto();
                    acta.setIdActaPartido(jsonActa.getLong("idActaPartido"));
                    acta.setTorneoId(jsonActa.getLong("torneoId"));
                    acta.setInstalacionId(jsonActa.getLong("instalacionId"));
                    acta.setClubLocalId(jsonActa.getLong("clubLocalId"));
                    acta.setClubVisitanteId(jsonActa.getLong("clubVisitanteId"));
                    acta.setEquipoLocalId(jsonActa.getLong("equipoLocalId"));
                    acta.setEquipoVisitanteId(jsonActa.getLong("equipoVisitanteId"));
                    acta.setGolesLocal(jsonActa.getInt("golesLocal"));
                    acta.setGolesPenaltisLocal(jsonActa.getInt("golesPenaltisLocal"));
                    acta.setGolesPenaltisVisitante(jsonActa.getInt("golesPenaltisVisitante"));
                    acta.setGolesVisitante(jsonActa.getInt("golesVisitante"));
                    acta.setClubGanadorId(jsonActa.getLong("clubGanadorId"));
                    acta.setFechaPartido(jsonActa.getString("fechaPartido"));
                    acta.setCerrado(jsonActa.getBoolean("cerrado"));
                    lista.add(acta);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("Error listando actas: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Guarda un nuevo acta de partido en el sistema.
     * 
     * @param acta Objeto ActaPartidoDto con los datos del acta a guardar.
     * @return ID del acta guardada si la operación fue exitosa, null en caso contrario.
     */
    public Long guardarActaPartido(ActaPartidoDto acta) {
        try {
            JSONObject jsonActa = mapearActaAJSON(acta);
            String urlApi = "http://localhost:9527/api/guardarActaPartido";
            HttpURLConnection conex = crearConexion(urlApi, "POST");

            try (OutputStream os = conex.getOutputStream()) {
                os.write(jsonActa.toString().getBytes("utf-8"));
            }

            if (conex.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                return jsonResponse.getLong("idActaPartido");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("Error guardando acta: " + e.getMessage());
        }
        return null;
    }

    /**
     * Modifica los datos de un acta de partido existente.
     * 
     * @param idActa ID del acta que se desea modificar.
     * @param acta Objeto ActaPartidoDto con los nuevos datos.
     * @return true si la modificación fue exitosa, false en caso contrario.
     */
    public boolean modificarActaPartido(Long idActa, ActaPartidoDto acta) {
        try {
            JSONObject jsonActa = mapearActaAJSON(acta);
            jsonActa.put("idActaPartido", idActa);

            String urlApi = "http://localhost:9527/api/modificarActaPartido/" + idActa;
            HttpURLConnection conex = crearConexion(urlApi, "PUT");

            try (OutputStream os = conex.getOutputStream()) {
                os.write(jsonActa.toString().getBytes("utf-8"));
            }

            return conex.getResponseCode() == HttpURLConnection.HTTP_OK;

        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("Error modificando acta: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un acta de partido del sistema.
     * 
     * @param idActa ID del acta que se desea eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminarActaPartido(Long idActa) {
        try {
            String urlApi = "http://localhost:9527/api/eliminarActaPartido/" + idActa;
            HttpURLConnection conex = crearConexion(urlApi, "DELETE");
            return conex.getResponseCode() == HttpURLConnection.HTTP_OK;

        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("Error eliminando acta: " + e.getMessage());
            return false;
        }
    }

    // ------------------------- Utilidades -------------------------

    /**
     * Crea una conexión HTTP con el método y la URL indicados.
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
     * Convierte un objeto ActaPartidoDto a JSONObject para enviarlo al servicio.
     * 
     * @param acta Objeto ActaPartidoDto a mapear.
     * @return JSONObject con los datos del acta.
     */
    private JSONObject mapearActaAJSON(ActaPartidoDto acta) {
        JSONObject json = new JSONObject();
        json.put("torneoId", acta.getTorneoId());
        json.put("instalacionId", acta.getInstalacionId());
        json.put("clubLocalId", acta.getClubLocalId());
        json.put("clubVisitanteId", acta.getClubVisitanteId());
        json.put("equipoLocalId", acta.getEquipoLocalId());
        json.put("equipoVisitanteId", acta.getEquipoVisitanteId());
        json.put("partidoTorneoId", acta.getPartidoTorneoId());
        json.put("golesLocal", acta.getGolesLocal());
        json.put("golesVisitante", acta.getGolesVisitante());
        json.put("golesPenaltisLocal", acta.getGolesPenaltisLocal());
        json.put("golesPenaltisVisitante", acta.getGolesPenaltisVisitante());
        json.put("clubGanadorId", acta.getClubGanadorId());
        json.put("fechaPartido", acta.getFechaPartido());
        json.put("observaciones", acta.getObservaciones());
        json.put("cerrado", acta.isCerrado());

        // ⚠️ Agregar eventos
        if (acta.getEventos() != null) {
            JSONArray eventosJson = new JSONArray();
            for (EventoPartidoDto evento : acta.getEventos()) {
                JSONObject jsonEvento = new JSONObject();
                jsonEvento.put("jugadorId", evento.getJugadorId());
                jsonEvento.put("clubId", evento.getClubId());
                jsonEvento.put("equipoTorneoId", evento.getEquipoTorneoId());
                jsonEvento.put("tipoEvento", evento.getTipoEvento());
                jsonEvento.put("minuto", evento.getMinuto());
                eventosJson.put(jsonEvento);
            }
            json.put("eventos", eventosJson);
        }

        return json;
    }

    /**
     * Marca un acta de partido como cerrada.
     * 
     * @param idPartido ID del partido cuyo acta se desea cerrar.
     * @return true si el acta fue cerrada correctamente, false en caso contrario.
     */
    public boolean cerrarActa(Long idPartido) {
        try {
            String urlApi = "http://localhost:9527/api/cerrarActa/" + idPartido;
            HttpURLConnection conex = crearConexion(urlApi, "PUT");
            return conex.getResponseCode() == HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            Log.ficheroLog("Error cerrando acta del partido: " + e.getMessage());
            return false;
        }
    }
}
