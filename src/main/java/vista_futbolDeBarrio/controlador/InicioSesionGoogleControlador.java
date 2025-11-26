package vista_futbolDeBarrio.controlador;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Properties;

import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.LoginGoogleDto;
import vista_futbolDeBarrio.servicios.InicioSesionGoogleServicio;

@WebServlet("/loginGoogle")
@MultipartConfig
public class InicioSesionGoogleControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Leer propiedades desde config.properties
    private static final Properties CONFIG = new Properties();
    static {
        try (InputStream input = InicioSesionGoogleControlador.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input != null) {
                CONFIG.load(input);
            } else {
                throw new RuntimeException("No se encontró config.properties");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String CLIENT_ID = CONFIG.getProperty("google.clientId");
    private static final String CLIENT_SECRET = CONFIG.getProperty("google.clientSecret");
    private static final String REDIRECT_URI = CONFIG.getProperty("google.redirectUri");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        System.out.println("===== InicioSesionGoogleControlador =====");

        // 0. Mostrar todos los parámetros que llegan
        request.getParameterMap().forEach((k,v) -> System.out.println("Param: " + k + " = " + String.join(",", v)));

        String code = request.getParameter("code");
        if (code == null) {
            System.out.println("No se recibió code de Google, redirigiendo a InicioSesion.jsp");
            response.sendRedirect("InicioSesion.jsp?error=google");
            return;
        }
        System.out.println("Code recibido: " + code);

        // 1. Intercambiar code por token de Google
        String tokenResponse = getTokenFromCode(code);
        System.out.println("Respuesta token de Google: " + tokenResponse);

        // 2. Obtener email y nombre del usuario desde el token
        String email = getEmailFromToken(tokenResponse);
        String nombreCompleto = getNombreFromToken(tokenResponse);

        // 3. Obtener el tipo de usuario
        String tipoUsuario = request.getParameter("state");
        if(tipoUsuario == null || tipoUsuario.isEmpty()){
            System.out.println("No se recibió tipoUsuario, redirigiendo a ElegirTipoUsuario.jsp");
            response.sendRedirect("ElegirTipoUsuario.jsp?email=" + email);
            return;
        }
        System.out.println("Tipo de usuario recibido: " + tipoUsuario);

        // 4. Llamar al servicio para autenticar en tu API y obtener token y datos de usuario
        InicioSesionGoogleServicio servicio = new InicioSesionGoogleServicio();
        LoginGoogleDto respuesta = servicio.loginConGoogle(email, tipoUsuario, nombreCompleto, request.getServletContext());

        if (respuesta != null) {
            System.out.println("Login con Google exitoso. Tipo de usuario: " + respuesta.getTipoUsuario());
            System.out.println("Token generado: " + respuesta.getToken());

            // 5. Guardar datos en sesión
            HttpSession session = request.getSession();

            // Guardar ID según el tipo de usuario
            switch (respuesta.getTipoUsuario()) {
                case "jugador":
                    session.setAttribute("usuarioId", respuesta.getIdTipoUsuario());
                    session.setAttribute("esPremium", respuesta.isEsPremium());
                    session.setAttribute("nombreUsuario", respuesta.getNombreCompleto());
                    break;
                case "club":
                    session.setAttribute("usuarioId", respuesta.getIdTipoUsuario());
                    session.setAttribute("esPremium", respuesta.isEsPremium());
                    session.setAttribute("nombreUsuario", respuesta.getNombreCompleto());
                    break;
                case "administrador":
                    session.setAttribute("usuarioId", respuesta.getIdTipoUsuario());
                    // los administradores quizá no tienen esPremium
                    break;
                case "instalacion":
                    session.setAttribute("instalacionId", respuesta.getIdTipoUsuario());
                    // instalacion no tiene esPremium
                    break;
            }

            session.setAttribute("token", respuesta.getToken());
            session.setAttribute("tipoUsuario", respuesta.getTipoUsuario());
            session.setAttribute("nombreCompleto", respuesta.getNombreCompleto());

            // 6. Redirigir según tipo de usuario
            switch (respuesta.getTipoUsuario()) {
                case "jugador":
                    response.sendRedirect("Jugador.jsp");
                    break;
                case "administrador":
                    response.sendRedirect("Administrador.jsp");
                    break;
                case "club":
                    response.sendRedirect("Club.jsp");
                    break;
                case "instalacion":
                    response.sendRedirect("Instalacion.jsp");
                    break;
                default:
                    response.sendRedirect("InicioSesion.jsp");
            }
        } else {
            System.out.println("Login fallido en la API, redirigiendo a InicioSesion.jsp");
            response.sendRedirect("InicioSesion.jsp?error=googleAPI");
        }
    }



    private String getTokenFromCode(String code) throws java.io.IOException {
        // Preparar la URL
        URL url = new URL("https://oauth2.googleapis.com/token");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        // Indicar que los parámetros se envían como form-urlencoded
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Preparar los parámetros codificados
        String data = "code=" + URLEncoder.encode(code, "UTF-8") +
                      "&client_id=" + URLEncoder.encode(CLIENT_ID, "UTF-8") +
                      "&client_secret=" + URLEncoder.encode(CLIENT_SECRET, "UTF-8") +
                      "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8") +
                      "&grant_type=authorization_code";

        try (OutputStream os = conn.getOutputStream()) {
            os.write(data.getBytes("UTF-8"));
        }

        // Leer la respuesta de Google
        BufferedReader reader;
        if (conn.getResponseCode() >= 400) {
            // Si hay error, leer del error stream para ver el mensaje
            reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }

        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();

        return response.toString();
    }


    private String getEmailFromToken(String tokenResponse) {
        JSONObject json = new JSONObject(tokenResponse);
        String idToken = json.getString("id_token");
        String[] parts = idToken.split("\\.");
        String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
        JSONObject payloadJson = new JSONObject(payload);
        return payloadJson.getString("email");
        
    }
    private String getNombreFromToken(String tokenResponse) {
        JSONObject json = new JSONObject(tokenResponse);
        String idToken = json.getString("id_token");
        String[] parts = idToken.split("\\.");
        String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
        JSONObject payloadJson = new JSONObject(payload);
        return payloadJson.getString("name"); // <- Aquí está el nombre completo
    }

}
