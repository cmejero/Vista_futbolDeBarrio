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

    /**
     * Actualiza las estadísticas de un jugador en un torneo a partir de un evento de partido.
     * Este método **suma** las estadísticas.
     * @param evento Evento del partido
     * @param torneoId ID del torneo
     */
    public void actualizarJugadorEstadisticasTorneo(EventoPartidoDto evento, Long torneoId) {
        actualizarJugadorEstadisticasTorneo(evento, torneoId, false);
    }

    /**
     * Resta las estadísticas de un jugador en un torneo a partir de un evento de partido.
     * Usado cuando se elimina o modifica un evento antiguo.
     * @param evento Evento que se quiere restar
     * @param torneoId ID del torneo
     */
    public void restarJugadorEstadisticasTorneo(EventoPartidoDto evento, Long torneoId) {
        actualizarJugadorEstadisticasTorneo(evento, torneoId, true);
    }

    /**
     * Método interno que aplica la actualización o resta de estadísticas.
     * @param evento Evento del partido
     * @param torneoId ID del torneo
     * @param restar Si es true, resta las estadísticas; si es false, las suma
     */
    private void actualizarJugadorEstadisticasTorneo(EventoPartidoDto evento, Long torneoId, boolean restar) {
        try {
            int factor = restar ? -1 : 1;

            JSONObject json = new JSONObject();
            json.put("jugadorId", evento.getJugadorId());
            json.put("torneoId", torneoId);

            // Ajustamos según el tipo de evento
            switch (evento.getTipoEvento()) {
                case "GOL":
                    json.put("golesTorneo", 1 * factor);
                    break;
                case "ASISTENCIA":
                    json.put("asistenciasTorneo", 1 * factor);
                    break;
                case "AMARILLA":
                    json.put("amarillasTorneo", 1 * factor);
                    break;
                case "ROJA":
                    json.put("rojasTorneo", 1 * factor);
                    break;
                default:
                    break;
            }

            // Minutos jugados
            json.put("minutosJugadosTorneo", evento.getMinuto() * factor);

            // Partidos jugados
            json.put("partidosJugadosTorneo", 1 * factor);

            // Llamada a la API
            String urlApi = "http://localhost:9527/api/actualizarJugadorEstadisticaTorneo";
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
                System.out.println("Error al actualizar estadísticas jugador torneo: " + response.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
