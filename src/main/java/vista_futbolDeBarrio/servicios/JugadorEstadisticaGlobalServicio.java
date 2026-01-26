package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import vista_futbolDeBarrio.dtos.JugadorEstadisticaGlobalDto;

public class JugadorEstadisticaGlobalServicio {

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Obtiene todas las estadísticas de jugadores desde la API.
     * @return Lista de JugadorEstadisticaGlobalDto
     */
    public List<JugadorEstadisticaGlobalDto> obtenerTodosJugadorEstadisticasGlobal() {
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

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                // Parsear JSON a DTOs aquí
                JugadorEstadisticaGlobalDto[] lista = objectMapper.readValue(
                        response.toString(),
                        JugadorEstadisticaGlobalDto[].class
                );

                return Arrays.asList(lista);
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
     * @return JugadorEstadisticaGlobalDto
     */
    public JugadorEstadisticaGlobalDto obtenerJugadorEstadisticasGlobal(Long jugadorId) {
        try {
            String urlApi = "http://localhost:9527/api/jugadorEstadisticaGlobal/jugador/" + jugadorId;
            URL url = new URL(urlApi);
            HttpURLConnection conex = (HttpURLConnection) url.openConnection();
            conex.setRequestMethod("GET");

            int responseCode = conex.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                // Parsear JSON a DTO aquí
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
