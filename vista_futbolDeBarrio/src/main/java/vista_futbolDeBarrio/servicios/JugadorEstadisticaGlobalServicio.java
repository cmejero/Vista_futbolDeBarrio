package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import vista_futbolDeBarrio.dtos.EventoPartidoDto;

/**
 * Servicio que maneja la lógica de actualización de estadísticas globales de un jugador.
 * Soporta tanto sumar como restar estadísticas según el evento.
 */
public class JugadorEstadisticaGlobalServicio {

	
	   /**
     * Obtiene todas las estadísticas de jugadores desde la API.
     * @return JSON con la lista de estadísticas o null si ocurre un error.
     */
    public String obtenerTodasJugadorEstadisticasGlobal() {
        try {
            String urlApi = "http://localhost:9527/api/mostrarJugadorEstadisticaGlobal";
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
     * Obtiene la estadística de un jugador.
     * @param jugadorId ID del jugador
     * @return JSON con la estadística o null si ocurre un error.
     */
    public String obtenerJugadorEstadisticasGlobal(Long jugadorId) {
        try {
            String urlApi = "http://localhost:9527/api/jugadorEstadisticaGlobal/" + jugadorId;
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
     * Actualiza las estadísticas globales de un jugador según un evento.
     * @param evento Evento del partido
     */
    public void actualizarJugadorEstadisticasGlobal(EventoPartidoDto evento) {
        actualizarJugadorEstadisticasGlobal(evento, false);
    }

    /**
     * Resta las estadísticas globales de un jugador según un evento.
     * @param evento Evento del partido
     */
    public void restarJugadorEstadisticasGlobal(EventoPartidoDto evento) {
        actualizarJugadorEstadisticasGlobal(evento, true);
    }

    /**
     * Método interno que aplica la actualización o resta de estadísticas globales.
     * @param evento Evento del partido
     * @param restar Si es true, resta; si es false, suma
     */
    private void actualizarJugadorEstadisticasGlobal(EventoPartidoDto evento, boolean restar) {
        try {
            int factor = restar ? -1 : 1;

            JSONObject json = new JSONObject();
            json.put("jugadorGlobalId", evento.getJugadorId());

            // Ajuste según tipo de evento
            switch (evento.getTipoEvento()) {
                case "GOL":
                    json.put("golesGlobal", 1 * factor);
                    break;
                case "ASISTENCIA":
                    json.put("asistenciasGlobal", 1 * factor);
                    break;
                case "AMARILLA":
                    json.put("amarillasGlobal", 1 * factor);
                    break;
                case "ROJA":
                    json.put("rojasGlobal", 1 * factor);
                    break;
                default:
                    break;
            }

            // Minutos jugados y partidos jugados
            json.put("minutosJugadosGlobal", evento.getMinuto() * factor);
            json.put("partidosJugadosGlobal", 1 * factor);

            // Llamada a la API
            String urlApi = "http://localhost:9527/api/actualizarJugadorEstadisticaGlobal";
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
                System.out.println("Error al actualizar estadísticas globales del jugador: " + response.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
