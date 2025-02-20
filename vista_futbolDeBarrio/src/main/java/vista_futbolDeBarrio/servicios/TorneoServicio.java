package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import vista_futbolDeBarrio.dtos.TorneoDto;
import vista_futbolDeBarrio.enums.Modalidad;

public class TorneoServicio {

    // Método para guardar un nuevo torneo
    public void guardarTorneo(TorneoDto torneo) {
        try {
            // Crear el objeto JSON a partir del TorneoDto
            JSONObject json = new JSONObject();
            json.put("nombreTorneo", torneo.getNombreTorneo());
            json.put("fechaInicioTorneo", torneo.getFechaInicioTorneo().toString());
            json.put("fechaFinTorneo", torneo.getFechaFinTorneo().toString());
            json.put("descripcionTorneo", torneo.getDescripcionTorneo());
            json.put("modalidad", torneo.getModalidad().name());
            json.put("instalacionId", torneo.getInstalacionId());

            // Definir la URL de la API para guardar el torneo
            String urlApi = "http://localhost:9527/api/guardarTorneo";
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
                System.out.println("Torneo guardado correctamente.");
            } else {
                System.out.println("Error al guardar torneo: " + responseCode);
            }

        } catch (Exception e) {
            System.out.println("ERROR- [TorneoServicio] " + e);
        }
    }

    // Método para obtener la lista de torneos
    public ArrayList<TorneoDto> listaTorneos() {
        ArrayList<TorneoDto> lista = new ArrayList<>();

        try {
            // Definir la URL de la API para obtener los torneos
            String urlApi = "http://localhost:9527/api/torneos";
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
                JSONArray jsonLista = new JSONArray(response.toString());
                for (int i = 0; i < jsonLista.length(); i++) {
                    JSONObject jsonTorneo = jsonLista.getJSONObject(i);
                    TorneoDto torneo = new TorneoDto();
                    torneo.setIdTorneo(jsonTorneo.getLong("idTorneo"));
                    torneo.setNombreTorneo(jsonTorneo.getString("nombreTorneo"));
                    torneo.setFechaInicioTorneo(LocalDate.parse(jsonTorneo.getString("fechaInicioTorneo")));
                    torneo.setFechaFinTorneo(LocalDate.parse(jsonTorneo.getString("fechaFinTorneo")));
                    torneo.setDescripcionTorneo(jsonTorneo.getString("descripcionTorneo"));
                    torneo.setModalidad(Modalidad.valueOf(jsonTorneo.getString("modalidad").toUpperCase()));
                    torneo.setInstalacionId(jsonTorneo.getLong("instalacionId"));

                    lista.add(torneo);
                }
            } else {
                System.out.println("Error al obtener torneos: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR- [TorneoServicio] listaTorneos: " + e.getMessage());
        }

        return lista;
    }

    // Método para modificar un torneo existente
    public boolean modificarTorneo(long idTorneo, TorneoDto torneo) {
        try {
            // Crear el objeto JSON a partir del TorneoDto
            JSONObject json = new JSONObject();
            json.put("nombreTorneo", torneo.getNombreTorneo());
            json.put("fechaInicioTorneo", torneo.getFechaInicioTorneo().toString());
            json.put("fechaFinTorneo", torneo.getFechaFinTorneo().toString());
            json.put("descripcionTorneo", torneo.getDescripcionTorneo());
            json.put("modalidad", torneo.getModalidad().name());
            json.put("instalacionId", torneo.getInstalacionId());

            // Definir la URL de la API para modificar el torneo
            String urlApi = "http://localhost:9527/api/modificarTorneo/" + idTorneo;
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
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true; // Torneo actualizado correctamente
            } else {
                return false; // Error al actualizar
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Error
        }
    }
}
