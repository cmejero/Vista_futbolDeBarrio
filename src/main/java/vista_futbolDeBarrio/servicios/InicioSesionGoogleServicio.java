package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;

import org.json.JSONObject;

import jakarta.servlet.ServletContext;
import vista_futbolDeBarrio.dtos.LoginGoogleDto;

public class InicioSesionGoogleServicio {

    private static final String API_LOGIN_URL = "http://localhost:9527/api/loginGoogle";
    private static final String CLIENT_ID = System.getenv("GOOGLE_CLIENT_ID");
    private static final String CLIENT_SECRET = System.getenv("GOOGLE_CLIENT_SECRET");
    private static final String REDIRECT_URI = "http://localhost:8080/vista_futbolDeBarrio/login";

    /**
     * Realiza el login de un usuario usando Google.
     *
     * @param codeGoogle Código recibido desde Google para obtener el token.
     * @param tipoUsuario Tipo de usuario que intenta iniciar sesión.
     * @param context Contexto del servlet.
     * @return Objeto LoginGoogleDto con la información del usuario, o null si falla el login.
     * @throws IOException Si ocurre un error durante la comunicación con la API o Google.
     */
    public LoginGoogleDto loginConGoogle(String codeGoogle, String tipoUsuario, ServletContext context) throws IOException {

        String respuestaToken = obtenerTokenDesdeCodigo(codeGoogle);

        if (respuestaToken == null) return null;

        String email = extraerEmailDesdeToken(respuestaToken);
        String nombreCompleto = extraerNombreDesdeToken(respuestaToken);

        if (tipoUsuario == null || tipoUsuario.isEmpty()) {
            return null;
        }
        return loginUsuarioAPI(email, tipoUsuario, nombreCompleto, context);
    }
    
    

    // ----------------- MÉTODOS AUXILIARES -----------------

    private String obtenerTokenDesdeCodigo(String codigo) {
        try {
            URL url = new URL("https://oauth2.googleapis.com/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String datos = "code=" + URLEncoder.encode(codigo, "UTF-8") +
                    "&client_id=" + URLEncoder.encode(CLIENT_ID, "UTF-8") +
                    "&client_secret=" + URLEncoder.encode(CLIENT_SECRET, "UTF-8") +
                    "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8") +
                    "&grant_type=authorization_code";

            try (OutputStream os = conn.getOutputStream()) {
                os.write(datos.getBytes("UTF-8"));
            }

            BufferedReader reader = (conn.getResponseCode() >= 400)
                    ? new BufferedReader(new InputStreamReader(conn.getErrorStream()))
                    : new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder respuesta = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) respuesta.append(linea);
            reader.close();

            return respuesta.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
    /**
     * Obtiene el token de acceso de Google a partir de un código de autorización.
     *
     * @param codigo Código de autorización recibido desde Google.
     * @return Token de acceso en formato JSON, o null si ocurre un error.
     */
    private String extraerEmailDesdeToken(String respuestaToken) {
        JSONObject json = new JSONObject(respuestaToken);
        String idToken = json.getString("id_token");
        String payload = new String(Base64.getUrlDecoder().decode(idToken.split("\\.")[1]));
        return new JSONObject(payload).getString("email");
    }

    
    /**
     * Extrae el nombre completo del usuario a partir del token de Google.
     *
     * @param respuestaToken Token recibido desde Google en formato JSON.
     * @return Nombre del usuario o "Desconocido" si no se encuentra.
     */
    private String extraerNombreDesdeToken(String respuestaToken) {
        JSONObject json = new JSONObject(respuestaToken);
        String idToken = json.getString("id_token");
        String payload = new String(Base64.getUrlDecoder().decode(idToken.split("\\.")[1]));
        return new JSONObject(payload).optString("name", "Desconocido");
    }

    
    /**
     * Realiza el login o registro de un usuario en la API interna usando sus datos de Google.
     *
     * @param email Correo del usuario.
     * @param tipoUsuario Tipo de usuario.
     * @param nombreCompleto Nombre completo del usuario.
     * @param context Contexto del servlet para obtener recursos como la imagen por defecto.
     * @return Objeto LoginGoogleDto con los datos del usuario y token, o null si falla la operación.
     */
    private LoginGoogleDto loginUsuarioAPI(String email, String tipoUsuario, String nombreCompleto, ServletContext context) {
        try {
            JSONObject json = new JSONObject();
            json.put("email", email);
            json.put("tipoUsuario", tipoUsuario);
            json.put("nombreCompleto", nombreCompleto != null ? nombreCompleto : "Desconocido");

            // Imagen por defecto
            try (InputStream is = context.getResourceAsStream("/Imagenes/usuarioPorDefecto.jpg")) {
                if (is != null) {
                    byte[] bytesImagen = is.readAllBytes();
                    json.put("imagenUsuario", Base64.getEncoder().encodeToString(bytesImagen));
                } else {
                    json.put("imagenUsuario", JSONObject.NULL);
                }
            }

            URL url = new URL(API_LOGIN_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.toString().getBytes("utf-8"));
            }

            if (conn.getResponseCode() != 200) return null;

            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) response.append(line);
            }

            JSONObject jsonResponse = new JSONObject(response.toString());
            if (!jsonResponse.has("login") || !jsonResponse.has("token")) return null;

            JSONObject loginJson = jsonResponse.getJSONObject("login");
            String token = jsonResponse.getString("token");

            LoginGoogleDto loginDto = new LoginGoogleDto();
            loginDto.setEmail(loginJson.optString("email", ""));
            loginDto.setNombreCompleto(loginJson.optString("nombreCompleto", ""));
            loginDto.setTipoUsuario(loginJson.optString("tipoUsuario", ""));
            loginDto.setToken(token);  // <- Ahora usamos el token que viene de la API
            loginDto.setIdTipoUsuario(loginJson.optLong("idTipoUsuario", 0));
            loginDto.setEsPremium(loginJson.optBoolean("esPremium", false));

            // Mapear imagen si existe
            if (loginJson.has("imagenUsuario") && !loginJson.isNull("imagenUsuario")) {
                loginDto.setImagenUsuario(Base64.getDecoder().decode(loginJson.optString("imagenUsuario")));
            }

            return loginDto;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
