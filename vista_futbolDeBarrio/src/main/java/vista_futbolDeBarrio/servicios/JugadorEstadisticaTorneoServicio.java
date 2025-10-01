package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import vista_futbolDeBarrio.dtos.EventoPartidoDto;

/**
 * Servicio que maneja la lógica de actualización de estadísticas de un jugador en un torneo.
 * Ahora soporta tanto sumar como restar estadísticas según el evento.
 */
public class JugadorEstadisticaTorneoServicio {

    /**
     * Obtiene todas las estadísticas de jugadores en torneos desde la API.
     * @return JSON con la lista de estadísticas o null si ocurre un error.
     */
    public String obtenerTodasJugadorEstadisticasTorneo() {
        try {
            String urlApi = "http://localhost:9527/api/mostrarJugadorEstadisticaTorneo";
            URL url = new URL(urlApi);
            HttpURLConnection conex = (HttpURLConnection) url.openConnection();
            conex.setRequestMethod("GET");

            int responseCode = conex.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) response.append(line);
                in.close();
                return response.toString();
            } else {
                System.out.println("Error al obtener estadísticas: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Obtiene la estadística de un jugador específico en un torneo.
     * @param jugadorId ID del jugador
     * @return JSON con la estadística o null si ocurre un error.
     */
    public String obtenerJugadorEstadisticasTorneo(Long jugadorId) {
        try {
            String urlApi = "http://localhost:9527/api/jugadorEstadisticaTorneo/" + jugadorId;
            URL url = new URL(urlApi);
            HttpURLConnection conex = (HttpURLConnection) url.openConnection();
            conex.setRequestMethod("GET");

            int responseCode = conex.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) response.append(line);
                in.close();
                return response.toString();
            } else {
                System.out.println("Error al obtener estadística del jugador: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

  
}
