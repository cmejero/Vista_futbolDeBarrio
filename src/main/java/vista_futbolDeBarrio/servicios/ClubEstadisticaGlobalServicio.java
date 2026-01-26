package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import vista_futbolDeBarrio.dtos.ClubEstadisticaGlobalDto;

public class ClubEstadisticaGlobalServicio {

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Obtiene todas las estadísticas de clubes desde la API.
     * @return Lista de ClubEstadisticaGlobalDto
     */
	    public List<ClubEstadisticaGlobalDto> obtenerTodasClubEstadisticasGlobal() {
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
	
	                while ((line = in.readLine()) != null) {
	                    response.append(line);
	                }
	                in.close();
	
	                // 👉 Parsear JSON a DTOs aquí (LÓGICA MOVIDA)
	                ClubEstadisticaGlobalDto[] lista = objectMapper.readValue(
	                        response.toString(),
	                        ClubEstadisticaGlobalDto[].class
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
     * Obtiene la estadística de un club.
     * @param clubId ID del club
     * @return ClubEstadisticaGlobalDto
     */
    public ClubEstadisticaGlobalDto obtenerClubEstadisticasGlobal(Long clubId) {
        try {
            String urlApi = "http://localhost:9527/api/clubEstadisticaGlobal/club/" + clubId;
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

                // 👉 Parsear JSON a DTO aquí (LÓGICA MOVIDA)
                return objectMapper.readValue(response.toString(), ClubEstadisticaGlobalDto.class);
            } else {
                System.out.println("Error al obtener estadística del club: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
