package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import vista_futbolDeBarrio.dtos.TorneoDto;

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

    private static final String API_URL_ID = "http://localhost:9527/api/torneo";
    private static final String API_URL = "http://localhost:9527/api/mostrarTorneo";

    public List<TorneoDto> obtenerTorneosPorInstalacion(Long instalacionId) throws Exception {
        String endpoint = API_URL_ID + "?instalacionId=" + instalacionId;
        return hacerLlamadaApi(endpoint);
    }

    public List<TorneoDto> obtenerTodosLosTorneos() throws Exception {
        return hacerLlamadaApi(API_URL);
    }

    private List<TorneoDto> hacerLlamadaApi(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int status = conn.getResponseCode();
        if (status == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder respuesta = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                respuesta.append(inputLine);
            }
            in.close();

            return new Gson().fromJson(respuesta.toString(), new TypeToken<List<TorneoDto>>(){}.getType());
        } else {
            throw new RuntimeException("Error en la llamada a la API: código " + status);
        }
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
    
    public boolean eliminarTorneo(Long idTorneo) {
        try {
       
            
            // Construir la URL para la solicitud DELETE
            String urlApi = "http://localhost:9527/api/eliminarTorneo/" + idTorneo;  // Ajustar URL según tu API
            URL url = new URL(urlApi);

            // Abrir la conexión HTTP
            HttpURLConnection conex = (HttpURLConnection) url.openConnection();
            conex.setRequestMethod("DELETE");
            conex.setRequestProperty("Accept", "application/json");

            // Obtener el código de respuesta
            int responseCode = conex.getResponseCode();

            // Verificar si la eliminación fue exitosa
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Torneo eliminado correctamente.");
                return true;
            } else {
                System.out.println("Error al eliminar torneo: " + responseCode);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR- [Servicio.eliminarTorneo]: " + e.getMessage());
            return false;
        }
    }


}
