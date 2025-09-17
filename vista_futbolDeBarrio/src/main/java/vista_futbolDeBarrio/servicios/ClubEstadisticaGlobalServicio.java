package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import vista_futbolDeBarrio.dtos.EventoPartidoDto;

/**
 * Servicio que maneja la lógica de actualización de estadísticas globales de un club.
 * Soporta sumar y restar estadísticas según el evento de partido.
 */
public class ClubEstadisticaGlobalServicio {

	 /**
     * Obtiene todas las estadísticas de clubes desde la API.
     * @return JSON con la lista de estadísticas o null si ocurre un error.
     */
    public String obtenerTodasClubEstadisticasGlobal() {
        try {
            String urlApi = "http://localhost:9527/api/mostrarClubEstadisticaGlobal";
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
     * Obtiene la estadística de un club.
     * @param clubId ID del club
     * @return JSON con la estadística o null si ocurre un error.
     */
    public String obtenerClubEstadisticasGlobal(Long clubId) {
        try {
            String urlApi = "http://localhost:9527/api/clubEstadisticaGlobal/" + clubId;
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
     * Actualiza las estadísticas globales del club según un evento.
     * @param evento Evento del partido
     */
    public void actualizarClubEstadisticasGlobal(EventoPartidoDto evento) {
        actualizarClubEstadisticasGlobal(evento, false);
    }

    /**
     * Resta las estadísticas globales del club según un evento.
     * @param evento Evento del partido
     */
    public void restarClubEstadisticasGlobal(EventoPartidoDto evento) {
        actualizarClubEstadisticasGlobal(evento, true);
    }

    /**
     * Método interno que aplica la actualización o resta de estadísticas globales.
     * @param evento Evento del partido
     * @param restar Si es true, resta; si es false, suma
     */
    private void actualizarClubEstadisticasGlobal(EventoPartidoDto evento, boolean restar) {
        try {
            int factor = restar ? -1 : 1;

            JSONObject json = new JSONObject();
            json.put("clubGlobalId", evento.getClubId());

            // Partidos jugados
            json.put("partidosJugadosGlobal", 1 * factor);

            // Ajustes según tipo de evento: goles a favor / en contra, ganados/empatados/perdidos
            switch (evento.getTipoEvento()) {
                case "GOL":
                    json.put("golesFavorGlobal", 1 * factor);
                    break;
                case "AUTOGOL":
                    json.put("golesContraGlobal", 1 * factor);
                    break;
                case "VICTORIA":
                    json.put("ganadosGlobal", 1 * factor);
                    break;
                case "EMPATE":
                    json.put("empatadosGlobal", 1 * factor);
                    break;
                case "DERROTA":
                    json.put("perdidosGlobal", 1 * factor);
                    break;
                default:
                    break;
            }

            // Llamada a la API
            String urlApi = "http://localhost:9527/api/actualizarClubEstadisticaGlobal";
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
                System.out.println("Error al actualizar estadísticas globales del club: " + response.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
