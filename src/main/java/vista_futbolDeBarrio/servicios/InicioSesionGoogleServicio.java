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
     * Método principal para login con Google.
     */
    public LoginGoogleDto loginConGoogle(String codeGoogle, String tipoUsuario, ServletContext context) throws IOException {

        // 1️⃣ Obtener token desde Google
        String respuestaToken = obtenerTokenDesdeCodigo(codeGoogle);

        if (respuestaToken == null) return null;

        // 2️⃣ Extraer email y nombre del token
        String email = extraerEmailDesdeToken(respuestaToken);
        String nombreCompleto = extraerNombreDesdeToken(respuestaToken);

        if (tipoUsuario == null || tipoUsuario.isEmpty()) {
            // Si no se seleccionó tipo de usuario, retornar null
            return null;
        }

        // 3️⃣ Llamar a API interna para registrar/login del usuario
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

    private String extraerEmailDesdeToken(String respuestaToken) {
        JSONObject json = new JSONObject(respuestaToken);
        String idToken = json.getString("id_token");
        String payload = new String(Base64.getUrlDecoder().decode(idToken.split("\\.")[1]));
        return new JSONObject(payload).getString("email");
    }

    private String extraerNombreDesdeToken(String respuestaToken) {
        JSONObject json = new JSONObject(respuestaToken);
        String idToken = json.getString("id_token");
        String payload = new String(Base64.getUrlDecoder().decode(idToken.split("\\.")[1]));
        return new JSONObject(payload).optString("name", "Desconocido");
    }

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
