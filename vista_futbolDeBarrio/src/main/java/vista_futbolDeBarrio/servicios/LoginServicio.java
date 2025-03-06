package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import vista_futbolDeBarrio.dtos.LoginDto;

public class LoginServicio {

    // URL de la API que manejará la autenticación
    private static final String API_URL = "http://localhost:9527/api/login";

    // Método para autenticar al usuario
    public String autenticarUsuario(LoginDto loginDto) {
        try {
            // Crear la conexión HTTP
            URL url = new URL(API_URL);
            HttpURLConnection conex = (HttpURLConnection) url.openConnection();
            conex.setRequestMethod("POST");
            conex.setRequestProperty("Content-Type", "application/json");
            conex.setDoOutput(true);

            // Crear el cuerpo de la solicitud en formato JSON
            String jsonInputString = String.format("{\"email\":\"%s\",\"password\":\"%s\"}",
                    loginDto.getEmail(), loginDto.getPassword());

            // Enviar la solicitud
            try (OutputStream os = conex.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Leer la respuesta de la API
            int responseCode = conex.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer la respuesta del cuerpo de la respuesta
                BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                // Aquí asumimos que la respuesta es un token (o un JSON que lo contenga)
                return response.toString();  // Suponiendo que la API devuelve el token directamente
            } else {
                // Si la respuesta no es OK, retornamos null
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; // En caso de error, retornamos null
        }
    }
}
