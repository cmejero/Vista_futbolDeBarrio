package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

    /** Guarda un nuevo evento, evitando duplicados */
    public boolean guardarEventoPartido(EventoPartidoDto evento) {
        try {
            // Validar duplicado opcional
            boolean existe = listaEventosPorActa(evento.getActaPartidoId())
                    .stream()
                    .anyMatch(e -> e.getJugadorId().equals(evento.getJugadorId())
                            && e.getMinuto() == evento.getMinuto()
                            && e.getTipoEvento().equals(evento.getTipoEvento()));

            if (existe) {
                System.out.println("Evento ya existe, no se guarda.");
                return false;
            }

            JSONObject json = mapearEventoAJSON(evento);
            HttpURLConnection conex = crearConexion("http://localhost:9527/api/guardarEventoPartido", "POST");

            try (OutputStream os = conex.getOutputStream()) {
                os.write(json.toString().getBytes("utf-8"));
            }

            if (conex.getResponseCode() == HttpURLConnection.HTTP_OK) {
                jugadorEstadisticaTorneoServicio.actualizarJugadorEstadisticasTorneo(evento, evento.getEquipoTorneoId());
                jugadorEstadisticaGlobalServicio.actualizarJugadorEstadisticasGlobal(evento);
                clubEstadisticaTorneoServicio.actualizarClubEstadisticasTorneo(evento, evento.getEquipoTorneoId());
                clubEstadisticaGlobalServicio.actualizarClubEstadisticasGlobal(evento);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /** Modifica un evento existente de forma segura */
    public boolean modificarEventoPartido(EventoPartidoDto evento) {
        try {
            Long idEvento = evento.getIdEventoPartido();

            // 1️⃣ Modificar en API primero
            JSONObject json = mapearEventoAJSON(evento);
            HttpURLConnection conex = crearConexion(
                    "http://localhost:9527/api/modificarEventoPartido/" + idEvento, "PUT");

            try (OutputStream os = conex.getOutputStream()) {
                os.write(json.toString().getBytes("utf-8"));
            }

            if (conex.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Error al modificar evento en API");
                return false;
            }

            // 2️⃣ Obtener evento anterior para restar estadísticas
            EventoPartidoDto eventoAnterior = listaEventosPorActa(evento.getActaPartidoId())
                    .stream()
                    .filter(e -> e.getIdEventoPartido().equals(idEvento))
                    .findFirst()
                    .orElse(null);

            if (eventoAnterior != null) {
                // Asegurarse de usar el torneo correcto
                Long torneoId = eventoAnterior.getEquipoTorneoId();
                jugadorEstadisticaTorneoServicio.restarJugadorEstadisticasTorneo(eventoAnterior, torneoId);
                jugadorEstadisticaGlobalServicio.restarJugadorEstadisticasGlobal(eventoAnterior);
                clubEstadisticaTorneoServicio.restarClubEstadisticasTorneo(eventoAnterior, torneoId);
                clubEstadisticaGlobalServicio.restarClubEstadisticasGlobal(eventoAnterior);
            }

            // 3️⃣ Sumar estadísticas con los datos nuevos
            jugadorEstadisticaTorneoServicio.actualizarJugadorEstadisticasTorneo(evento, evento.getEquipoTorneoId());
            jugadorEstadisticaGlobalServicio.actualizarJugadorEstadisticasGlobal(evento);
            clubEstadisticaTorneoServicio.actualizarClubEstadisticasTorneo(evento, evento.getEquipoTorneoId());
            clubEstadisticaGlobalServicio.actualizarClubEstadisticasGlobal(evento);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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

    /** Mapea un EventoPartidoDto a JSONObject */
    private JSONObject mapearEventoAJSON(EventoPartidoDto evento) {
        JSONObject json = new JSONObject();
        json.put("actaPartidoId", evento.getActaPartidoId());
        json.put("jugadorId", evento.getJugadorId());
        json.put("clubId", evento.getClubId());
        json.put("equipoTorneoId", evento.getEquipoTorneoId());
        json.put("tipoEvento", evento.getTipoEvento());
        json.put("minuto", evento.getMinuto());
        return json;
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
