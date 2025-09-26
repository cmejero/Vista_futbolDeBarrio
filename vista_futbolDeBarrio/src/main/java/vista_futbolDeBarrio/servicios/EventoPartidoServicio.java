package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import vista_futbolDeBarrio.dtos.EventoPartidoDto;

public class EventoPartidoServicio {

    private JugadorEstadisticaTorneoServicio jugadorEstadisticaTorneoServicio = new JugadorEstadisticaTorneoServicio();
    private JugadorEstadisticaGlobalServicio jugadorEstadisticaGlobalServicio = new JugadorEstadisticaGlobalServicio();
    private ClubEstadisticaTorneoServicio clubEstadisticaTorneoServicio = new ClubEstadisticaTorneoServicio();
    private ClubEstadisticaGlobalServicio clubEstadisticaGlobalServicio = new ClubEstadisticaGlobalServicio();
    
    

    /** Lista todos los eventos de un acta de partido */
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

   

   
    /** Elimina un evento y resta estadísticas */
    public boolean eliminarEventoPartido(EventoPartidoDto evento) {
        try {
            Long idEvento = evento.getIdEventoPartido();
            HttpURLConnection conex = crearConexion(
                    "http://localhost:9527/api/eliminarEventoPartido/" + idEvento, "DELETE");

            if (conex.getResponseCode() == HttpURLConnection.HTTP_OK) {
                jugadorEstadisticaTorneoServicio.restarJugadorEstadisticasTorneo(evento, evento.getEquipoTorneoId());
                jugadorEstadisticaGlobalServicio.restarJugadorEstadisticasGlobal(evento);
                clubEstadisticaTorneoServicio.restarClubEstadisticasTorneo(evento, evento.getEquipoTorneoId());
                clubEstadisticaGlobalServicio.restarClubEstadisticasGlobal(evento);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

   

    /** Crea conexión HTTP */
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
