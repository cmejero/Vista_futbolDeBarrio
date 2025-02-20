package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import vista_futbolDeBarrio.dtos.EquipoTorneoDto;
import vista_futbolDeBarrio.enums.EstadoParticipacion;

public class EquipoTorneoServicio {

    // Método para guardar un nuevo EquipoTorneo
    public void guardarEquipoTorneo(EquipoTorneoDto equipoTorneo) {
        try {
            // Crear el objeto JSON a partir del DTO
            JSONObject json = new JSONObject();
            json.put("fechaInicioParticipacion", equipoTorneo.getFechaInicioParticipacion().toString());
            json.put("fechaFinParticipacion", equipoTorneo.getFechaFinParticipacion().toString());
            json.put("estadoParticipacion", equipoTorneo.getEstadoParticipacion().name());
            json.put("torneoId", equipoTorneo.getTorneoId());
            json.put("clubId", equipoTorneo.getClubId());

            // Definir la URL de la API para guardar el EquipoTorneo
            String urlApi = "http://localhost:9527/api/guardarEquipoTorneo";
            URL url = new URL(urlApi);

            // Abrir la conexión HTTP
            HttpURLConnection conex = (HttpURLConnection) url.openConnection();
            conex.setRequestMethod("POST");
            conex.setRequestProperty("Content-Type", "application/json");
            conex.setDoOutput(true);

            // Enviar el JSON en la solicitud
            try (OutputStream os = conex.getOutputStream()) {
                byte[] input = json.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Verificar la respuesta del servidor
            int responseCode = conex.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("EquipoTorneo guardado correctamente.");
            } else {
                System.out.println("Error al guardar EquipoTorneo: " + responseCode);
            }

        } catch (Exception e) {
            System.out.println("ERROR- [EquipoTorneoServicio] " + e);
        }
    }

    // Método para obtener la lista de EquiposTorneo
    public ArrayList<EquipoTorneoDto> listaEquiposTorneo() {
        ArrayList<EquipoTorneoDto> lista = new ArrayList<>();

        try {
            // Definir la URL de la API para obtener los EquiposTorneo
            String urlApi = "http://localhost:9527/api/equiposTorneo";
            URL url = new URL(urlApi);

            // Abrir la conexión HTTP
            HttpURLConnection conex = (HttpURLConnection) url.openConnection();
            conex.setRequestMethod("GET");
            conex.setRequestProperty("Accept", "application/json");

            // Verificar la respuesta del servidor
            int responseCode = conex.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer la respuesta
                BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Procesar la respuesta JSON
                JSONArray jsonArray = new JSONArray(response.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonEquipoTorneo = jsonArray.getJSONObject(i);
                    EquipoTorneoDto equipoTorneo = new EquipoTorneoDto();

                    equipoTorneo.setIdEquipoTorneo(jsonEquipoTorneo.getLong("idEquipoTorneo"));
                    equipoTorneo.setFechaInicioParticipacion(java.sql.Date.valueOf(jsonEquipoTorneo.getString("fechaInicioParticipacion")));
                    equipoTorneo.setFechaFinParticipacion(java.sql.Date.valueOf(jsonEquipoTorneo.getString("fechaFinParticipacion")));
                    equipoTorneo.setEstadoParticipacion(EstadoParticipacion.valueOf(jsonEquipoTorneo.getString("estadoParticipacion")));
                    equipoTorneo.setTorneoId(jsonEquipoTorneo.getLong("torneoId"));
                    equipoTorneo.setClubId(jsonEquipoTorneo.getLong("clubId"));

                    lista.add(equipoTorneo);
                }
            } else {
                System.out.println("Error al obtener la lista de EquiposTorneo: " + responseCode);
            }
        } catch (Exception e) {
            System.out.println("ERROR- [EquipoTorneoServicio] listaEquiposTorneo: " + e.getMessage());
        }

        return lista;
    }

    // Método para modificar un EquipoTorneo existente
    public boolean modificarEquipoTorneo(long idEquipoTorneo, EquipoTorneoDto equipoTorneo) {
        try {
            // Crear el objeto JSON a partir del DTO
            JSONObject json = new JSONObject();
            json.put("fechaInicioParticipacion", equipoTorneo.getFechaInicioParticipacion().toString());
            json.put("fechaFinParticipacion", equipoTorneo.getFechaFinParticipacion().toString());
            json.put("estadoParticipacion", equipoTorneo.getEstadoParticipacion().name());
            json.put("torneoId", equipoTorneo.getTorneoId());
            json.put("clubId", equipoTorneo.getClubId());

            // Definir la URL de la API para modificar el EquipoTorneo
            String urlApi = "http://localhost:9527/api/modificarEquipoTorneo/" + idEquipoTorneo;
            URL url = new URL(urlApi);

            // Abrir la conexión HTTP
            HttpURLConnection conex = (HttpURLConnection) url.openConnection();
            conex.setRequestMethod("PUT");
            conex.setRequestProperty("Content-Type", "application/json");
            conex.setDoOutput(true);

            // Enviar el JSON en la solicitud
            try (OutputStream os = conex.getOutputStream()) {
                byte[] input = json.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Verificar la respuesta del servidor
            int responseCode = conex.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;

        } catch (Exception e) {
            System.out.println("ERROR- [EquipoTorneoServicio] modificarEquipoTorneo: " + e.getMessage());
            return false;
        }
    }
}
