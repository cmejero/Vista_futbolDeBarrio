package vista_futbolDeBarrio.controlador;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Map;
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

private static final Properties CONFIG = new Properties();
static {
    try (InputStream entrada = InicioSesionGoogleControlador.class.getClassLoader()
            .getResourceAsStream("config.properties")) {
        if (entrada != null) {
            CONFIG.load(entrada);
        } else {
            throw new RuntimeException("No se encontró config.properties");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private static final String CLIENT_ID = System.getenv("GOOGLE_CLIENT_ID");
private static final String CLIENT_SECRET = System.getenv("GOOGLE_CLIENT_SECRET");
private static final String REDIRECT_URI = CONFIG.getProperty("google.redirectUri");

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, java.io.IOException {

    String codigo = request.getParameter("code");
    if (codigo == null) {
        response.sendRedirect("InicioSesion.jsp?error=google");
        return;
    }

    String respuestaToken = obtenerTokenDesdeCodigo(codigo);
    String email = obtenerEmailDesdeToken(respuestaToken);
    String nombreCompleto = obtenerNombreDesdeToken(respuestaToken);

    String tipoUsuario = request.getParameter("state");
    if (tipoUsuario == null || tipoUsuario.isEmpty()) {
        response.sendRedirect("ElegirTipoUsuario.jsp?email=" + email);
        return;
    }

    InicioSesionGoogleServicio servicio = new InicioSesionGoogleServicio();
    Map<String, Object> respuestaMap = servicio.loginConGoogle(email, tipoUsuario, nombreCompleto,
            request.getServletContext());

    if (respuestaMap != null && respuestaMap.containsKey("login")) {
        LoginGoogleDto loginDto = (LoginGoogleDto) respuestaMap.get("login");

        HttpSession sesion = request.getSession();
        sesion.setAttribute("token", loginDto.getToken());
        sesion.setAttribute("tipoUsuario", loginDto.getTipoUsuario());
        sesion.setAttribute("nombreCompleto", loginDto.getNombreCompleto());
        sesion.setAttribute("esPremium", loginDto.isEsPremium());

        // Guardar ID según tipo
        switch (loginDto.getTipoUsuario().toLowerCase()) {
        case "jugador":
            sesion.setAttribute("usuarioId", loginDto.getIdTipoUsuario());
            sesion.setAttribute("nombreUsuario", loginDto.getNombreCompleto());
            break;
        case "club":
            sesion.setAttribute("usuarioId", loginDto.getIdTipoUsuario());
            sesion.setAttribute("clubId", loginDto.getIdTipoUsuario());  
            sesion.setAttribute("nombreClub", loginDto.getNombreCompleto()); 
            break;
        case "instalacion":
            sesion.setAttribute("instalacionId", loginDto.getIdTipoUsuario());
            sesion.setAttribute("nombreInstalacion", loginDto.getNombreCompleto());
            break;
    }

        // Redirigir según tipo de usuario
        switch (loginDto.getTipoUsuario().toLowerCase()) {
            case "jugador":
                response.sendRedirect("Jugador.jsp");
                break;
            case "club":
                response.sendRedirect("Club.jsp");
                break;
            case "instalacion":
                response.sendRedirect("Instalacion.jsp");
                break;
            case "administrador":
                response.sendRedirect("Administrador.jsp");
                break;
            default:
                response.sendRedirect("InicioSesion.jsp");
        }

    } else {
        response.sendRedirect("InicioSesion.jsp?error=googleAPI");
    }
}

private String obtenerTokenDesdeCodigo(String codigo) throws java.io.IOException {
    URL url = new URL("https://oauth2.googleapis.com/token");
    HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
    conexion.setRequestMethod("POST");
    conexion.setDoOutput(true);
    conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

    String datos = "code=" + URLEncoder.encode(codigo, "UTF-8") +
            "&client_id=" + URLEncoder.encode(CLIENT_ID, "UTF-8") +
            "&client_secret=" + URLEncoder.encode(CLIENT_SECRET, "UTF-8") +
            "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8") +
            "&grant_type=authorization_code";

    try (OutputStream salida = conexion.getOutputStream()) {
        salida.write(datos.getBytes("UTF-8"));
    }

    BufferedReader lector;
    if (conexion.getResponseCode() >= 400) {
        lector = new BufferedReader(new InputStreamReader(conexion.getErrorStream()));
    } else {
        lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
    }

    StringBuilder respuesta = new StringBuilder();
    String linea;
    while ((linea = lector.readLine()) != null) {
        respuesta.append(linea);
    }
    lector.close();
    return respuesta.toString();
}

private String obtenerEmailDesdeToken(String respuestaToken) {
    JSONObject json = new JSONObject(respuestaToken);
    String idToken = json.getString("id_token");
    String[] partes = idToken.split("\\.");
    String payload = new String(Base64.getUrlDecoder().decode(partes[1]));
    JSONObject payloadJson = new JSONObject(payload);
    return payloadJson.getString("email");
}

private String obtenerNombreDesdeToken(String respuestaToken) {
    JSONObject json = new JSONObject(respuestaToken);
    String idToken = json.getString("id_token");
    String[] partes = idToken.split("\\.");
    String payload = new String(Base64.getUrlDecoder().decode(partes[1]));
    JSONObject payloadJson = new JSONObject(payload);
    return payloadJson.getString("name");
}


}
