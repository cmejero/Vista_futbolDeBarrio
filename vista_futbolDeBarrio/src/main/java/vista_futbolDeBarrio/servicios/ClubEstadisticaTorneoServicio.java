package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import vista_futbolDeBarrio.dtos.EventoPartidoDto;

/**
 * Servicio que maneja la lógica de actualización de estadísticas de un club en un torneo.
 * Soporta sumar y restar estadísticas según el evento de partido.
 */
public class ClubEstadisticaTorneoServicio {

	 /**
     * Obtiene todas las estadísticas de clubes en torneos desde la API.
     * @return JSON con la lista de estadísticas o null si ocurre un error.
     */
    public String obtenerTodasClubEstadisticasTorneo() {
        try {
            String urlApi = "http://localhost:9527/api/mostrarClubEstadisticaTorneo";
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
     * Obtiene la estadística de un club específico en un torneo.
     * @param clubId ID del club
     * @return JSON con la estadística o null si ocurre un error.
     */
    public String obtenerClubEstadisticasTorneo(Long clubId) {
        try {
            String urlApi = "http://localhost:9527/api/clubEstadisticaTorneo/" + clubId;
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
                System.out.println("Error al obtener estadística del club: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	
	
    /**
     * Actualiza las estadísticas del club en un torneo a partir de un evento.
     * @param evento Evento del partido
     * @param torneoId ID del torneo
     */
    public void actualizarClubEstadisticasTorneo(EventoPartidoDto evento, Long torneoId) {
        actualizarClubEstadisticasTorneo(evento, torneoId, false);
    }

    /**
     * Resta las estadísticas del club en un torneo a partir de un evento.
     * @param evento Evento del partido
     * @param torneoId ID del torneo
     */
    public void restarClubEstadisticasTorneo(EventoPartidoDto evento, Long torneoId) {
        actualizarClubEstadisticasTorneo(evento, torneoId, true);
    }

    /**
     * Método interno que aplica la actualización o resta de estadísticas.
     * @param evento Evento del partido
     * @param torneoId ID del torneo
     * @param restar Si es true, resta; si es false, suma
     */
    private void actualizarClubEstadisticasTorneo(EventoPartidoDto evento, Long torneoId, boolean restar) {
        try {
            int factor = restar ? -1 : 1;

            JSONObject json = new JSONObject();
            json.put("clubId", evento.getClubId());
            json.put("torneoId", torneoId);

            // Partidos jugados
            json.put("partidosJugados", 1 * factor);

            // Ajustes según tipo de evento: goles a favor / en contra, ganados/empatados/perdidos
            switch (evento.getTipoEvento()) {
                case "GOL":
                    json.put("golesFavor", 1 * factor);
                    break;
                case "AUTOGOL":
                    json.put("golesContra", 1 * factor);
                    break;
                case "VICTORIA":
                    json.put("ganados", 1 * factor);
                    break;
                case "EMPATE":
                    json.put("empatados", 1 * factor);
                    break;
                case "DERROTA":
                    json.put("perdidos", 1 * factor);
                    break;
                default:
                    break;
            }

            // Llamada a la API
            String urlApi = "http://localhost:9527/api/clubEstadisticaTorneo/actualizar";
            URL url = new URL(urlApi);
            HttpURLConnection conex = (HttpURLConnection) url.openConnection();
            conex.setRequestMethod("POST");
            conex.setRequestProperty("Content-Type", "application/json");
            conex.setDoOutput(true);

            try (OutputStream os = conex.getOutputStream()) {
                os.write(json.toString().getBytes("utf-8"));
            }

            int responseCode = conex.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conex.getErrorStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) response.append(line);
                in.close();
                System.out.println("Error al actualizar estadísticas de club en torneo: " + response.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
