package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Map;

import org.json.JSONObject;

import jakarta.servlet.ServletContext;
import vista_futbolDeBarrio.dtos.LoginGoogleDto;

public class InicioSesionGoogleServicio {

private static final String API_LOGIN_URL = "http://localhost:9527/api/loginGoogle";

public Map<String, Object> loginConGoogle(String email, String tipoUsuario, String nombreCompleto, ServletContext context) {
try {
if (email == null || tipoUsuario == null) {
System.out.println("Faltan datos para loginGoogle: email=" + email + ", tipoUsuario=" + tipoUsuario);
return null;
}

    // Construir DTO de envío
    JSONObject json = new JSONObject();
    json.put("email", email);
    json.put("tipoUsuario", tipoUsuario);
    json.put("nombreCompleto", nombreCompleto != null ? nombreCompleto : "Desconocido");

    // Imagen por defecto
    try (InputStream is = context.getResourceAsStream("/Imagenes/usuarioPorDefecto.jpg")) {
        if (is != null) {
            byte[] bytesImagen = is.readAllBytes();
            String imagenBase64 = Base64.getEncoder().encodeToString(bytesImagen);
            json.put("imagenUsuario", imagenBase64);
        } else {
            json.put("imagenUsuario", JSONObject.NULL);
        }
    }

    // Conexión con la API
    URL url = new URL(API_LOGIN_URL);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    try (OutputStream os = conn.getOutputStream()) {
        os.write(json.toString().getBytes("utf-8"));
    }

    int responseCode = conn.getResponseCode();
    if (responseCode != 200) {
        System.out.println("Error en la API, código de respuesta: " + responseCode);
        return null;
    }

    // Leer respuesta
    StringBuilder response = new StringBuilder();
    try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
    }

    JSONObject jsonResponse = new JSONObject(response.toString());

    // Obtener el objeto "login" correctamente
    if (!jsonResponse.has("login")) {
        System.out.println("La API no devolvió el objeto 'login'");
        return null;
    }

    JSONObject loginJson = jsonResponse.getJSONObject("login");

    LoginGoogleDto loginBase = new LoginGoogleDto();
    loginBase.setEmail(loginJson.optString("email", ""));
    loginBase.setNombreCompleto(loginJson.optString("nombreCompleto", ""));
    loginBase.setTipoUsuario(loginJson.optString("tipoUsuario", ""));
    loginBase.setToken(loginJson.optString("token", ""));
    loginBase.setIdTipoUsuario(loginJson.optLong("idTipoUsuario", 0));
    loginBase.setEsPremium(loginJson.optBoolean("esPremium", false));

    // Mapear imagen si existe
    
    // Mapear imagen si existe
    if (loginJson.has("imagenUsuario") && !loginJson.isNull("imagenUsuario")) {
        loginBase.setImagenUsuario(Base64.getDecoder().decode(loginJson.optString("imagenUsuario", "")));
    } else if (jsonResponse.has("club") && !jsonResponse.isNull("club")) {
        JSONObject clubJson = jsonResponse.getJSONObject("club");
        String logo = clubJson.optString("logoClub", null);
        if (logo != null) {
            loginBase.setImagenUsuario(Base64.getDecoder().decode(logo));
        }
    } else if (jsonResponse.has("instalacion") && !jsonResponse.isNull("instalacion")) {
        JSONObject instJson = jsonResponse.getJSONObject("instalacion");
        String imgInst = instJson.optString("imagenInstalacion", null);
        if (imgInst != null) {
            loginBase.setImagenUsuario(Base64.getDecoder().decode(imgInst));
        }
    }


    // Devolver el mapa completo
    return Map.of(
            "login", loginBase,
            "respuesta", jsonResponse.toMap()
    );

} catch (Exception e) {
    e.printStackTrace();
    return null;
}

}




}
