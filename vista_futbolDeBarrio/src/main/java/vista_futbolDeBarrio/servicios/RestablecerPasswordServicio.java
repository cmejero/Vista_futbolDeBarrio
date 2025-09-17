package vista_futbolDeBarrio.servicios;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import vista_futbolDeBarrio.log.Log;

/**
 * Claseque gestiona la comunicación con la API para restablecer la contraseña.
 */
public class RestablecerPasswordServicio {

    /**
     * Envía el token y la nueva contraseña a la API para actualizar la contraseña.
     *
     * @param token Token recibido en el enlace de recuperación.
     * @param nuevaPassword Nueva contraseña que el usuario desea establecer.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizarPassword(String token, String nuevaPassword) {
        try {
            JSONObject json = construirJson(token, nuevaPassword);

            String urlApi = "http://localhost:9527/api/restablecer-contrasena";
            HttpURLConnection conex = prepararConexion(urlApi, json);

            Log.ficheroLog("Solicitud enviada a API para actualizar password con token: " + token);
            int codigoRespuesta = conex.getResponseCode();
            Log.ficheroLog("Respuesta de API al actualizar password: " + codigoRespuesta);

            return codigoRespuesta == HttpURLConnection.HTTP_OK;

        } catch (Exception e) {
            Log.ficheroLog("Error en actualizarPassword: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Construye el objeto JSON con el token y las contraseñas.
     *
     * @param token Token recibido.
     * @param nuevaPassword Nueva contraseña.
     * @return JSONObject listo para enviar.
     */
    private JSONObject construirJson(String token, String nuevaPassword) {
        JSONObject json = new JSONObject();
        json.put("token", token);
        json.put("nuevaContrasena", nuevaPassword);
        json.put("repetirContrasena", nuevaPassword);
        return json;
    }

    /**
     * Configura y envía la conexión HTTP POST con el JSON.
     *
     * @param urlApi URL de la API.
     * @param json JSON con los datos a enviar.
     * @return HttpURLConnection con la conexión ya enviada.
     * @throws Exception en caso de error en la conexión o envío.
     */
    private HttpURLConnection prepararConexion(String urlApi, JSONObject json) throws Exception {
        HttpURLConnection conex = (HttpURLConnection) new URL(urlApi).openConnection();
        conex.setRequestMethod("POST");
        conex.setRequestProperty("Content-Type", "application/json");
        conex.setDoOutput(true);

        try (OutputStream os = conex.getOutputStream()) {
            byte[] input = json.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        return conex;
    }
}
