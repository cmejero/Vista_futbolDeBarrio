package vista_futbolDeBarrio.servicios;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.json.JSONObject;

import jakarta.servlet.ServletContext;
import vista_futbolDeBarrio.dtos.LoginGoogleDto;

public class InicioSesionGoogleServicio {

    private static final String API_LOGIN_URL = "http://localhost:9527/api/loginGoogle";

    public LoginGoogleDto loginConGoogle(String email, String tipoUsuario, String nombreCompleto, ServletContext context) {
        try {
            if (email == null || tipoUsuario == null) {
                System.out.println("Faltan datos para loginGoogle: email=" + email + ", tipoUsuario=" + tipoUsuario);
                return null;
            }

            JSONObject json = new JSONObject();
            json.put("email", email);
            json.put("tipoUsuario", tipoUsuario);
            json.put("nombreCompleto", nombreCompleto != null ? nombreCompleto : "Desconocido");

            // ---- Aquí agregamos la imagen por defecto ----
            try (InputStream is = context.getResourceAsStream("/Imagenes/usuarioPorDefecto.jpg")) {
                if (is != null) {
                    byte[] bytesImagen = is.readAllBytes();
                    String imagenBase64 = Base64.getEncoder().encodeToString(bytesImagen);
                    json.put("imagenUsuario", imagenBase64);
                } else {
                    json.put("imagenUsuario", JSONObject.NULL);
                }
            }

            URL url = new URL(API_LOGIN_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            System.out.println("DTO que se va a enviar a la API: " + json.toString());
            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.toString().getBytes("utf-8"));
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                StringBuilder response = new StringBuilder();
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                }

                JSONObject jsonResponse = new JSONObject(response.toString());
                LoginGoogleDto dto = new LoginGoogleDto();
                dto.setToken(jsonResponse.getString("token"));
                dto.setTipoUsuario(jsonResponse.getString("tipoUsuario"));
                dto.setEmail(jsonResponse.getString("email"));
                dto.setNombreCompleto(jsonResponse.getString("nombreCompleto"));

                if (jsonResponse.has("idTipoUsuario") && !jsonResponse.isNull("idTipoUsuario")) {
                    dto.setIdTipoUsuario(jsonResponse.getLong("idTipoUsuario"));
                }
                if (jsonResponse.has("esPremium") && !jsonResponse.isNull("esPremium")) {
                    dto.setEsPremium(jsonResponse.getBoolean("esPremium"));
                }
                if (jsonResponse.has("imagenUsuario") && !jsonResponse.isNull("imagenUsuario")) {
                    String imagenBase64 = jsonResponse.getString("imagenUsuario");
                    byte[] imagenBytes = Base64.getDecoder().decode(imagenBase64);
                    dto.setImagenUsuario(imagenBytes);
                }


                return dto;
            } else {
                System.out.println("Error en la API, código de respuesta: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
