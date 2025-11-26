package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

import vista_futbolDeBarrio.dtos.JugadorEstadisticaGlobalDto;

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
    public JugadorEstadisticaGlobalDto obtenerJugadorEstadisticasGlobal(Long jugadorId) {
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

                // Convertir JSON a DTO
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(response.toString(), JugadorEstadisticaGlobalDto.class);
            } else {
                System.out.println("Error al obtener estadística del jugador: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	
	
	
	
   
}
