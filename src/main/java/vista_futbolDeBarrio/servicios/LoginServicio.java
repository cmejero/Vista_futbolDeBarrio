package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.ClubDto;
import vista_futbolDeBarrio.dtos.InstalacionDto;
import vista_futbolDeBarrio.dtos.LoginGoogleDto;
import vista_futbolDeBarrio.dtos.RespuestaLoginDto;
import vista_futbolDeBarrio.dtos.UsuarioDto;
import vista_futbolDeBarrio.enums.Estado;
import vista_futbolDeBarrio.log.Log;

public class LoginServicio {

    private static final String URL_API = "http://localhost:9527/api";

    /**
     * Realiza login enviando email, contraseña y tipo de usuario a la API.
     *
     * @param email Correo del usuario.
     * @param password Contraseña del usuario.
     * @param tipoUsuario Tipo de usuario (jugador, club, instalacion).
     * @return RespuestaLoginDto con token y datos del usuario si es correcto, null si falla.
     */
    public RespuestaLoginDto login(String email, String password, String tipoUsuario) {
        try {
            JSONObject json = new JSONObject();
            json.put("email", email);
            json.put("password", password);
            json.put("tipoUsuario", tipoUsuario);

            HttpURLConnection con = (HttpURLConnection) new URL(URL_API + "/login").openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                os.write(json.toString().getBytes("utf-8"));
            }

            if (con.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) sb.append(line);
                return construirRespuestaLogin(new JSONObject(sb.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Genera un token persistente para mantener sesión del usuario.
     *
     * @param datosUsuario DTO del usuario.
     * @param tipoUsuario Tipo de usuario (jugador, club, instalacion).
     * @param jwt JWT actual del usuario.
     * @return Token persistente como String o null si falla.
     */
    public String generarTokenPersistente(Object datosUsuario, String tipoUsuario, String jwt) {
        Long idUsuario = extraerId(datosUsuario, tipoUsuario);
        if (idUsuario == null) return null;

        try {
            JSONObject json = new JSONObject();
            json.put("idUsuario", idUsuario);
            json.put("tipoUsuario", tipoUsuario);

            URL url = new URL(URL_API + "/recordar");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + jwt);
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                os.write(json.toString().getBytes("utf-8"));
            }

            int status = con.getResponseCode();
            if (status == 200) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) sb.append(line);
                    JSONObject resp = new JSONObject(sb.toString());
                    return resp.getString("token");
                }
            } else {
                System.err.println("Error al generar token persistente. HTTP: " + status);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Valida un token persistente mediante la API.
     *
     * @param token Token persistente a validar.
     * @return Mapa con JWT, tipo de usuario y datos del usuario, o null si es inválido.
     */
    public Map<String,Object> validarTokenPersistente(String token) {
        try {
            URL url = new URL(URL_API + "/validarToken");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("token", token);

            try (OutputStream os = con.getOutputStream()) {
                os.write(json.toString().getBytes("utf-8"));
            }

            if (con.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) sb.append(line);

                JSONObject resp = new JSONObject(sb.toString());
                String tipo = resp.getString("tipoUsuario");
                JSONObject datosUsuarioJson = resp.getJSONObject("datosUsuario");
                Object usuario = mapearUsuario(tipo, datosUsuarioJson);

                Map<String,Object> resultado = new HashMap<>();
                resultado.put("jwt", resp.getString("jwt"));
                resultado.put("tipoUsuario", tipo);
                resultado.put("datosUsuario", usuario);

                Log.ficheroLog("validarTokenPersistente -> tipo: " + tipo + ", usuario: " + usuario);
                return resultado;
            } else {
                Log.ficheroLog("Token inválido o expirado: HTTP " + con.getResponseCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    
    /**
     * Obtiene el ID del usuario según su tipo.
     *
     * @param datosUsuario DTO del usuario.
     * @param tipoUsuario Tipo de usuario ("jugador", "club", "instalacion", "administrador").
     * @return ID del usuario, o null si el tipo no es válido.
     */
    private Long extraerId(Object datosUsuario, String tipoUsuario) {
        switch (tipoUsuario.toLowerCase()) {
            case "jugador":
            case "administrador": return ((UsuarioDto) datosUsuario).getIdUsuario();
            case "club": return ((ClubDto) datosUsuario).getIdClub();
            case "instalacion": return ((InstalacionDto) datosUsuario).getIdInstalacion();
        }
        return null;
    }

    /**
     * Convierte un JSONObject en el DTO correspondiente según el tipo de usuario.
     *
     * @param tipoUsuario Tipo de usuario ("jugador", "club", "instalacion", "administrador").
     * @param datos JSONObject con los datos del usuario.
     * @return DTO del usuario mapeado, o null si ocurre un error.
     */
    private Object mapearUsuario(String tipoUsuario, JSONObject datos) {
        try {
            switch (tipoUsuario.toLowerCase()) {
                case "jugador":
                case "administrador":
                    UsuarioDto u = new UsuarioDto();
                    u.setIdUsuario(datos.getLong("idUsuario"));
                    u.setNombreCompletoUsuario(datos.getString("nombreCompletoUsuario"));
                    u.setAliasUsuario(datos.optString("aliasUsuario", "Invitado"));
                    String estadoStr = datos.optString("estadoUsuario", "Activo");
                    try { u.setEstadoUsuario(Estado.valueOf(estadoStr)); }
                    catch (IllegalArgumentException e) { u.setEstadoUsuario(Estado.Activo); }
                    u.setEmailUsuario(datos.getString("emailUsuario"));
                    String img = datos.optString("imagenUsuario", null);
                    if(img != null) u.setImagenUsuario(Base64.getDecoder().decode(img));
                    u.setEsPremium(datos.optBoolean("esPremium", false));
                    return u;

                case "club":
                    ClubDto c = new ClubDto();
                    c.setIdClub(datos.getLong("idClub"));
                    c.setNombreClub(datos.getString("nombreClub"));
                    c.setEmailClub(datos.getString("emailClub"));
                    c.setEsPremium(datos.optBoolean("esPremium", false));
                    return c;

                case "instalacion":
                    InstalacionDto i = new InstalacionDto();
                    i.setIdInstalacion(datos.getLong("idInstalacion"));
                    i.setNombreInstalacion(datos.getString("nombreInstalacion"));
                    return i;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Construye un DTO de respuesta de login a partir del JSON recibido de la API.
     *
     * @param json JSONObject con token, tipoUsuario y datosUsuario.
     * @return RespuestaLoginDto con la información mapeada del usuario.
     */
    private RespuestaLoginDto construirRespuestaLogin(JSONObject json) {
        RespuestaLoginDto r = new RespuestaLoginDto();
        r.setToken(json.getString("token"));
        r.setTipoUsuario(json.getString("tipoUsuario"));
        r.setDatosUsuario(mapearUsuario(r.getTipoUsuario(), json.getJSONObject("datosUsuario")));
        return r;
    }
    
    
    /**
     * Crea una nueva sesión HTTP, invalidando la anterior si existe.
     *
     * @param request Objeto de la solicitud HTTP.
     * @return Nueva HttpSession activa.
     */
    public HttpSession crearSesion(HttpServletRequest request) {
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) oldSession.invalidate();
        return request.getSession(true);
    }

    
    /**
     * Asigna atributos del usuario a la sesión según su tipo.
     *
     * @param session Sesión HTTP donde se guardarán los datos.
     * @param tipoUsuario Tipo de usuario ("jugador", "club", "instalacion").
     * @param datosUsuario DTO del usuario con la información a guardar.
     */
    public void asignarDatosUsuarioASesion(HttpSession session, String tipoUsuario, Object datosUsuario) {
        switch (tipoUsuario.toLowerCase()) {
            case "jugador" -> {
                UsuarioDto u = (UsuarioDto) datosUsuario;
                session.setAttribute("usuarioId", u.getIdUsuario());
                session.setAttribute("nombreUsuario", u.getNombreCompletoUsuario());
                session.setAttribute("esPremium", u.isEsPremium());
            }
            case "club" -> {
                ClubDto c = (ClubDto) datosUsuario;
                session.setAttribute("clubId", c.getIdClub());
                session.setAttribute("nombreClub", c.getNombreClub());
                session.setAttribute("esPremium", c.isEsPremium());
            }
            case "instalacion" -> {
                InstalacionDto i = (InstalacionDto) datosUsuario;
                session.setAttribute("idInstalacion", i.getIdInstalacion());
                session.setAttribute("nombreInstalacion", i.getNombreInstalacion());
            }
            default -> session.setAttribute("esPremium", false);
        }
    }
    
    
    public Object construirDtoSegunTipo(LoginGoogleDto loginDto) {

        switch (loginDto.getTipoUsuario().toLowerCase()) {

            case "jugador" -> {
                UsuarioDto u = new UsuarioDto();
                u.setIdUsuario(loginDto.getIdTipoUsuario());
                u.setNombreCompletoUsuario(loginDto.getNombreCompleto());
                u.setEmailUsuario(loginDto.getEmail());
                u.setEsPremium(loginDto.isEsPremium());
                return u;
            }

            case "club" -> {
                ClubDto c = new ClubDto();
                c.setIdClub(loginDto.getIdTipoUsuario());
                c.setNombreClub(loginDto.getNombreCompleto());
                c.setEmailClub(loginDto.getEmail());
                c.setEsPremium(loginDto.isEsPremium());
                return c;
            }

            case "instalacion" -> {
                InstalacionDto i = new InstalacionDto();
                i.setIdInstalacion(loginDto.getIdTipoUsuario());
                i.setNombreInstalacion(loginDto.getNombreCompleto());
                i.setEmailInstalacion(loginDto.getEmail());
                return i;
            }

            default -> throw new IllegalArgumentException("Tipo de usuario no válido");
        }
    }


    
    /**
     * Agrega cookies persistentes con el token y tipo de usuario.
     *
     * @param response Objeto de respuesta HTTP donde se añaden las cookies.
     * @param token Token persistente del usuario.
     * @param tipoUsuario Tipo de usuario ("jugador", "club", "instalacion", etc.).
     * @param contextPath Contexto de la aplicación para asignar a las cookies.
     */
    public void agregarTokenYCookies(HttpServletResponse response, String token, String tipoUsuario, String contextPath) {
        Cookie cookieToken = new Cookie("tokenUsuario", token);
        cookieToken.setMaxAge(60 * 60 * 24 * 30);
        cookieToken.setPath(contextPath);
        response.addCookie(cookieToken);

        Cookie cookieTipo = new Cookie("tipoUsuario", tipoUsuario);
        cookieTipo.setMaxAge(60 * 60 * 24 * 30);
        cookieTipo.setPath(contextPath);
        response.addCookie(cookieTipo);
    }

    
    
    /**
     * Elimina las cookies de sesión persistente del usuario.
     *
     * @param response Objeto de respuesta HTTP donde se eliminan las cookies.
     * @param contextPath Contexto de la aplicación para asignar a las cookies.
     */
    public void borrarCookies(HttpServletResponse response, String contextPath) {
        Cookie cookieToken = new Cookie("tokenUsuario", "");
        cookieToken.setMaxAge(0); cookieToken.setPath(contextPath);
        response.addCookie(cookieToken);

        Cookie cookieTipo = new Cookie("tipoUsuario", "");
        cookieTipo.setMaxAge(0); cookieTipo.setPath(contextPath);
        response.addCookie(cookieTipo);
    }

    
    /**
     * Redirige al usuario según su tipo tras el login.
     *
     * @param response Objeto de respuesta HTTP para enviar la redirección.
     * @param tipoUsuario Tipo de usuario (administrador, jugador, club, instalacion).
     * @throws IOException Si ocurre un error al enviar la redirección.
     */
    public void redirigirPorTipoUsuario(HttpServletResponse response, String tipoUsuario) throws IOException {
        switch (tipoUsuario.toLowerCase()) {
            case "administrador" -> response.sendRedirect("administrador");
            case "jugador" -> response.sendRedirect("jugador");
            case "club" -> response.sendRedirect("club");
            case "instalacion" -> response.sendRedirect("instalacion");
            default -> response.sendRedirect("login?error=tipoDesconocido");
        }
    }

    
    /**
     * Crea y configura la sesión del usuario tras el login.
     *
     * @param request Objeto de solicitud HTTP.
     * @param response Objeto de respuesta HTTP para redirección.
     * @param datosUsuario DTO del usuario autenticado.
     * @param token JWT o token de sesión.
     * @param tipoUsuario Tipo de usuario (jugador, club, instalacion, administrador).
     * @throws IOException Si ocurre un error al redirigir.
     */
    public void manejarSesion(HttpServletRequest request, HttpServletResponse response, Object datosUsuario, String token, String tipoUsuario) throws IOException {
        HttpSession session = crearSesion(request);
        session.setAttribute("token", token);
        session.setAttribute("tipoUsuario", tipoUsuario);
        session.setAttribute("datosUsuario", datosUsuario);
        asignarDatosUsuarioASesion(session, tipoUsuario, datosUsuario);
        redirigirPorTipoUsuario(response, tipoUsuario);
    }
}
