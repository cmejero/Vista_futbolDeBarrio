package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import vista_futbolDeBarrio.log.Log;

/**
 * Clase que gestiona la comunicación con la API para la recuperación de contraseña.
 */
public class RecuperarCuentaServicio {

    /**
     * Envía una solicitud POST a la API para iniciar la recuperación de contraseña.
     *
     * @param email Email del usuario para recuperación.
     * @return true si la API responde con éxito, false en caso contrario.
     */
    public boolean enviarEnlaceRecuperacion(String email, String tipoUsuario) {
        try {
            Log.ficheroLog("Iniciando recuperación de contraseña para email: " + email);

            JSONObject json = new JSONObject();
            json.put("email", email);
            json.put("tipoUsuario", tipoUsuario);


            String urlApi = "http://localhost:9527/api/recuperar-contrasena";
            Log.ficheroLog("Conectando a la API en: " + urlApi);

            HttpURLConnection conex = prepararConexion(urlApi, json);

            int codigoRespuesta = conex.getResponseCode();
            Log.ficheroLog("Código de respuesta de la API: " + codigoRespuesta);

            String respuesta = leerRespuesta(conex, codigoRespuesta);
            Log.ficheroLog("Respuesta de la API: " + respuesta);

            boolean exito = codigoRespuesta == HttpURLConnection.HTTP_OK;
            Log.ficheroLog("Resultado final de enviarEnlaceRecuperacion: " + exito);
            return exito;

        } catch (Exception e) {
            Log.ficheroLog("Excepción en enviarEnlaceRecuperacion: " + e.getMessage());
            return false;
        }
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

    /**
     * Lee la respuesta del servidor, ya sea de éxito o error.
     *
     * @param conex Conexión HTTP establecida.
     * @param codigoRespuesta Código HTTP recibido.
     * @return Respuesta como String.
     * @throws Exception en caso de error leyendo la respuesta.
     */
    private String leerRespuesta(HttpURLConnection conex, int codigoRespuesta) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                (codigoRespuesta < 400) ? conex.getInputStream() : conex.getErrorStream(), "utf-8"))) {
            StringBuilder respuesta = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                respuesta.append(linea.trim());
            }
            return respuesta.toString();
        }
    }
}
