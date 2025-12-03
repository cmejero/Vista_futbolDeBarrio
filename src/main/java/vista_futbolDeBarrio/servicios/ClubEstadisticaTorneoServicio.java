package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import vista_futbolDeBarrio.dtos.ClubEstadisticaTorneoDto;

/**
 * Servicio que maneja la lógica de actualización de estadísticas de un club en un torneo.
 * Soporta sumar y restar estadísticas según el evento de partido.
 */
public class ClubEstadisticaTorneoServicio {

	 /**
     * Obtiene todas las estadísticas de clubes en torneos desde la API.
     * @return JSON con la lista de estadísticas o null si ocurre un error.
     */
	 public ArrayList<ClubEstadisticaTorneoDto> obtenerTodasClubEstadisticasTorneo() {
	        ArrayList<ClubEstadisticaTorneoDto> lista = new ArrayList<>();
	        try {
	            String urlApi = "http://localhost:9527/api/mostrarClubEstadisticaTorneo";
	            URL url = new URL(urlApi);
	            HttpURLConnection conex = (HttpURLConnection) url.openConnection();
	            conex.setRequestMethod("GET");
	            conex.setRequestProperty("Accept", "application/json");

	            int responseCode = conex.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK) {
	                BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
	                StringBuilder response = new StringBuilder();
	                String line;
	                while ((line = in.readLine()) != null) response.append(line);
	                in.close();

	                JSONArray jsonLista = new JSONArray(response.toString());
	                for (int i = 0; i < jsonLista.length(); i++) {
	                    JSONObject json = jsonLista.getJSONObject(i);
	                    ClubEstadisticaTorneoDto dto = new ClubEstadisticaTorneoDto();

	                    dto.setIdEstadisticaTorneo(json.optLong("idClubEstadisticaTorneo"));
	                    dto.setClubId(json.optLong("clubId"));
	                    dto.setNombreClub(json.optString("nombreClub", "Sin nombre"));
	                    dto.setAbreviaturaClub(json.optString("abreviaturaClub", ""));
	                    dto.setTorneoId(json.optLong("torneoId"));
	                    dto.setPartidosJugados(json.optInt("partidosJugados", 0));
	                    dto.setGanados(json.optInt("ganados", 0));
	                    dto.setEmpatados(json.optInt("empatados", 0));
	                    dto.setPerdidos(json.optInt("perdidos", 0));
	                    dto.setGolesFavor(json.optInt("golesFavor", 0));
	                    dto.setGolesContra(json.optInt("golesContra", 0));

	                    lista.add(dto);
	                }

	            } else {
	                System.out.println("Error al obtener estadísticas de clubes: " + responseCode);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return lista;
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
	
    public ArrayList<ClubEstadisticaTorneoDto> obtenerClubEstadisticasTorneoPorClubId(Long clubId) {
        ArrayList<ClubEstadisticaTorneoDto> lista = new ArrayList<>();
        try {
            String urlApi = "http://localhost:9527/api/clubEstadisticaTorneo/club/" + clubId;
            URL url = new URL(urlApi);
            HttpURLConnection conex = (HttpURLConnection) url.openConnection();
            conex.setRequestMethod("GET");
            conex.setRequestProperty("Accept", "application/json");

            int responseCode = conex.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) response.append(line);
                in.close();

                JSONArray jsonLista = new JSONArray(response.toString());
                for (int i = 0; i < jsonLista.length(); i++) {
                    JSONObject json = jsonLista.getJSONObject(i);
                    ClubEstadisticaTorneoDto dto = new ClubEstadisticaTorneoDto();

                    dto.setIdEstadisticaTorneo(json.optLong("idClubEstadisticaTorneo"));
                    dto.setClubId(json.optLong("clubId"));
                    dto.setNombreClub(json.optString("nombreClub", "Sin nombre"));
                    dto.setNombreTorneo(json.optString("nombreTorneo", "Sin nombre"));
                    dto.setAbreviaturaClub(json.optString("abreviaturaClub", ""));
                    dto.setTorneoId(json.optLong("torneoId"));
                    dto.setPartidosJugados(json.optInt("partidosJugados", 0));
                    dto.setGanados(json.optInt("ganados", 0));
                    dto.setEmpatados(json.optInt("empatados", 0));
                    dto.setPerdidos(json.optInt("perdidos", 0));
                    dto.setGolesFavor(json.optInt("golesFavor", 0));
                    dto.setGolesContra(json.optInt("golesContra", 0));

                    lista.add(dto);
                }

            } else {
                System.out.println("Error al obtener estadísticas del club: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

	
    
}
