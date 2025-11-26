package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import vista_futbolDeBarrio.dtos.JugadorEstadisticaTorneoDto;

/**
 * Servicio que maneja la lógica de actualización de estadísticas de un jugador en un torneo.
 * Ahora soporta tanto sumar como restar estadísticas según el evento.
 */
public class JugadorEstadisticaTorneoServicio {

    /**
     * Obtiene todas las estadísticas de jugadores en torneos desde la API.
     * @return JSON con la lista de estadísticas o null si ocurre un error.
     */
	public ArrayList<JugadorEstadisticaTorneoDto> obtenerTodasJugadorEstadisticasTorneo() {
	    ArrayList<JugadorEstadisticaTorneoDto> lista = new ArrayList<>();
	    try {
	        String urlApi = "http://localhost:9527/api/mostrarJugadorEstadisticaTorneo";
	        URL url = new URL(urlApi);
	        HttpURLConnection conex = (HttpURLConnection) url.openConnection();
	        conex.setRequestMethod("GET");
	        conex.setRequestProperty("Accept", "application/json");

	        int responseCode = conex.getResponseCode();
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
	            StringBuilder response = new StringBuilder();
	            String line;
	            while ((line = in.readLine()) != null) {
	                response.append(line);
	            }
	            in.close();

	            // Convertir la respuesta JSON en JSONArray
	            JSONArray jsonLista = new JSONArray(response.toString());
	            for (int i = 0; i < jsonLista.length(); i++) {
	                JSONObject json = jsonLista.getJSONObject(i);
	                JugadorEstadisticaTorneoDto dto = new JugadorEstadisticaTorneoDto();

	                dto.setIdJugadorEstadisticaTorneo(json.optLong("idJugadorEstadisticaTorneo"));
	                dto.setJugadorId(json.optLong("jugadorId"));
	                dto.setNombreJugador(json.optString("nombreJugador", "Desconocido"));
	                dto.setTorneoId(json.optLong("torneoId"));
	                dto.setNombreTorneo(json.optString("nombreTorneo", "Sin nombre"));
	                dto.setNombreClub(json.optString("nombreClub", "Sin club"));

	                dto.setGolesTorneo(json.optInt("golesTorneo", 0));
	                dto.setAsistenciasTorneo(json.optInt("asistenciasTorneo", 0));
	                dto.setAmarillasTorneo(json.optInt("amarillasTorneo", 0));
	                dto.setRojasTorneo(json.optInt("rojasTorneo", 0));
	                dto.setPartidosJugadosTorneo(json.optInt("partidosJugadosTorneo", 0));
	                dto.setPartidosGanadosTorneo(json.optInt("partidosGanadosTorneo", 0));
	                dto.setPartidosPerdidosTorneo(json.optInt("partidosPerdidosTorneo", 0));
	                dto.setMinutosJugadosTorneo(json.optInt("minutosJugadosTorneo", 0));

	                lista.add(dto);
	            }

	        } else {
	            System.out.println("Error al obtener estadísticas: " + responseCode);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return lista;
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
