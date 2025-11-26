package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import vista_futbolDeBarrio.dtos.EventoPartidoDto;

/**
 * Clase que se encarga de la lógica de los métodos relacionados con los
 * eventos de partidos de un acta.
 */
public class EventoPartidoServicio {

    /**
     * Obtiene la lista de eventos asociados a un acta de partido específica.
     * 
     * @param actaPartidoId ID del acta de partido para filtrar los eventos.
     * @return Lista de objetos EventoPartidoDto con los datos de cada evento.
     */
    public ArrayList<EventoPartidoDto> listaEventosPorActa(Long actaPartidoId) {
        ArrayList<EventoPartidoDto> lista = new ArrayList<>();
        try {
            String urlApi = "http://localhost:9527/api/eventoPartido/" + actaPartidoId;
            HttpURLConnection conex = crearConexion(urlApi, "GET");

            if (conex.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) response.append(inputLine);

                    JSONArray jsonArray = new JSONArray(response.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonEvento = jsonArray.getJSONObject(i);
                        EventoPartidoDto evento = new EventoPartidoDto();
                        evento.setIdEventoPartido(jsonEvento.getLong("idEventoPartido"));
                        evento.setActaPartidoId(jsonEvento.getLong("actaPartidoId"));
                        evento.setJugadorId(jsonEvento.getLong("jugadorId"));
                        evento.setClubId(jsonEvento.getLong("clubId"));
                        evento.setEquipoTorneoId(jsonEvento.getLong("equipoTorneoId"));
                        evento.setTipoEvento(jsonEvento.getString("tipoEvento"));
                        evento.setMinuto(jsonEvento.getInt("minuto"));
                        lista.add(evento);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Crea y configura una conexión HTTP con la URL y método especificados.
     * 
     * @param urlApi URL del servicio web al que se desea conectar.
     * @param metodo Método HTTP a utilizar (GET, POST, PUT, DELETE).
     * @return HttpURLConnection configurada para su uso.
     * @throws Exception En caso de error al crear la conexión.
     */
    private HttpURLConnection crearConexion(String urlApi, String metodo) throws Exception {
        URL url = new URL(urlApi);
        HttpURLConnection conex = (HttpURLConnection) url.openConnection();
        conex.setRequestMethod(metodo);
        conex.setRequestProperty("Content-Type", "application/json");
        if (metodo.equals("POST") || metodo.equals("PUT")) {
            conex.setDoOutput(true);
        }
        return conex;
    }
}
