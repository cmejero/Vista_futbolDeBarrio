package vista_futbolDeBarrio.controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.ClubDto;
import vista_futbolDeBarrio.dtos.InstalacionDto;
import vista_futbolDeBarrio.dtos.RespuestaLoginDto;
import vista_futbolDeBarrio.dtos.UsuarioDto;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.LoginServicio;
import vista_futbolDeBarrio.utilidades.Utilidades;

@WebServlet("/login")
@MultipartConfig
/**
 * Clase que se encarga de el inicio de sesion 
 */
public class LoginControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private LoginServicio servicioLogin = new LoginServicio();
    
    
    
    
    /**
     * Método GET para mostrar login o auto-login si existen cookies válidas
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Revisar cookies
        Cookie[] cookies = request.getCookies();
        String token = null;
        String tipoUsuario = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("tokenUsuario".equals(cookie.getName())) token = cookie.getValue();
                if ("tipoUsuario".equals(cookie.getName())) tipoUsuario = cookie.getValue();
            }
        }

        if (token != null && tipoUsuario != null) {
            try {
                // Crear sesión nueva sin invalidar la actual
                HttpSession session = request.getSession(true);
                session.setAttribute("token", token);
                session.setAttribute("tipoUsuario", tipoUsuario);

                Object datosUsuario = servicioLogin.obtenerDatosUsuario(token, tipoUsuario);
                session.setAttribute("datosUsuario", datosUsuario);
                asignarIdUsuarioASesion(session, tipoUsuario, datosUsuario);

                // Redirigir automáticamente al JSP del usuario
                redirigirPorTipoUsuario(response, tipoUsuario);
                return;

            } catch (Exception e) {
                Log.ficheroLog("Error al auto-logear con cookie: " + e.getMessage());
                Utilidades.borrarCookies(response);
                response.sendRedirect("InicioSesion.jsp");
                return;
            }
        }

        // Mostrar página de login
        request.getRequestDispatcher("InicioSesion.jsp").forward(request, response);
    }

    

    @Override
    /**
     * Método POST que maneja el inicio de sesión de un usuario.
     *
     * @param request  Objeto HttpServletRequest que contiene los parámetros del formulario.
     * @param response Objeto HttpServletResponse para enviar la respuesta.
     * @throws ServletException En caso de error en la solicitud.
     * @throws IOException      En caso de error en la respuesta.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String tipoUsuario = request.getParameter("tipoUsuario");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String recordarSesion = request.getParameter("recordarSesion"); // "on" si marcado

            Log.ficheroLog("Intento de login. Email: " + email);

            RespuestaLoginDto respuestaLogin = servicioLogin.login(email, password, tipoUsuario);

            if (respuestaLogin != null && respuestaLogin.getToken() != null) {
                procesarLoginExitoso(request, response, email, respuestaLogin, "on".equals(recordarSesion));
            } else {
                Log.ficheroLog("Credenciales incorrectas para email: " + email);
                response.sendRedirect("InicioSesion.jsp?error=credenciales");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("Error en el login: " + e.getMessage());
            response.sendRedirect("InicioSesion.jsp?error=servidor");
        }
    }
    /**
     * Procesa el inicio de sesión exitoso, guardando los datos en sesión y redirigiendo según el tipo de usuario.
     *
     * @param request         La solicitud HTTP.
     * @param response        La respuesta HTTP.
     * @param email           El correo electrónico del usuario.
     * @param respuestaLogin  Objeto que contiene el token, tipo de usuario y datos del usuario.
     * @throws IOException Si ocurre un error al redirigir.
     */
    private void procesarLoginExitoso(HttpServletRequest request, HttpServletResponse response, String email,
            RespuestaLoginDto respuestaLogin, boolean recordarSesion) throws IOException {

        String token = respuestaLogin.getToken();
        String tipoUsuario = respuestaLogin.getTipoUsuario();
        Object datosUsuario = respuestaLogin.getDatosUsuario();

        Log.ficheroLog("Login exitoso para: " + email + ", Tipo: " + tipoUsuario);

        // 1️⃣ Invalidar sesión vieja
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) oldSession.invalidate();

        // 2️⃣ Crear nueva sesión
        HttpSession session = request.getSession(true);
        session.setAttribute("token", token);
        session.setAttribute("tipoUsuario", tipoUsuario);
        session.setAttribute("datosUsuario", datosUsuario);

        asignarIdUsuarioASesion(session, tipoUsuario, datosUsuario);

        // 3️⃣ Crear cookies solo si se quiere recordar sesión
        crearCookies(response, token, tipoUsuario, recordarSesion);

        // 4️⃣ Redirigir al JSP correspondiente
        redirigirPorTipoUsuario(response, tipoUsuario);
    }


    /**
     * Asigna a la sesión el ID correspondiente del usuario según su tipo.
     *
     * @param session      La sesión HTTP del usuario.
     * @param tipoUsuario  El tipo de usuario (jugador, club, instalacion).
     * @param datosUsuario El objeto con los datos del usuario.
     */
    public void asignarIdUsuarioASesion(HttpSession session, String tipoUsuario, Object datosUsuario) {
        switch (tipoUsuario) {
            case "instalacion":
                if (datosUsuario instanceof InstalacionDto) {
                    session.setAttribute("idInstalacion", ((InstalacionDto) datosUsuario).getIdInstalacion());
                    session.setAttribute("nombreInstalacion", ((InstalacionDto) datosUsuario).getNombreInstalacion());

                }
                break;
            case "club":
                if (datosUsuario instanceof ClubDto) {
                    session.setAttribute("clubId", ((ClubDto) datosUsuario).getIdClub());
                    session.setAttribute("esPremium", false); 
                    session.setAttribute("nombreClub", ((ClubDto) datosUsuario).getNombreClub()); 
                    session.setAttribute("esPremium", ((ClubDto) datosUsuario).isEsPremium()); 
                }
                break;
            case "jugador":
                if (datosUsuario instanceof UsuarioDto) {
                    UsuarioDto usuario = (UsuarioDto) datosUsuario;
                    session.setAttribute("usuarioId", usuario.getIdUsuario());
                    session.setAttribute("esPremium", usuario.isEsPremium()); 
                    session.setAttribute("nombreUsuario", usuario.getNombreCompletoUsuario()); 
                }
                break;
            default:
                session.setAttribute("esPremium", false);
                break;
        }
    }


    /**
     * Redirige al usuario a la página correspondiente según su tipo.
     *
     * @param response     La respuesta HTTP.
     * @param tipoUsuario  El tipo de usuario autenticado.
     * @throws IOException Si ocurre un error al redirigir.
     */
    private void redirigirPorTipoUsuario(HttpServletResponse response, String tipoUsuario) throws IOException {
        switch (tipoUsuario) {
            case "administrador":
                response.sendRedirect("Administrador.jsp");
                break;
            case "jugador":
                response.sendRedirect("Jugador.jsp");
                break;
            case "club":
                response.sendRedirect("Club.jsp");
                break;
            case "instalacion":
                response.sendRedirect("Instalacion.jsp");
                break;
            default:
                Log.ficheroLog("Tipo de usuario desconocido: " + tipoUsuario);
                response.sendRedirect("InicioSesion.jsp?error=tipoDesconocido");
        }
    }
    
    /**
     * Método para cerrar sesión y eliminar cookies
     */
    public static void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Borrar cookies
        Cookie cookieToken = new Cookie("tokenUsuario", "");
        cookieToken.setMaxAge(0);
        cookieToken.setPath("/");
        response.addCookie(cookieToken);

        Cookie cookieTipo = new Cookie("tipoUsuario", "");
        cookieTipo.setMaxAge(0);
        cookieTipo.setPath("/");
        response.addCookie(cookieTipo);

        // Invalidar sesión
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();

        response.sendRedirect("InicioSesion.jsp");
    }




    
    private void crearCookies(HttpServletResponse response, String token, String tipoUsuario, boolean recordarSesion) {
        int maxAge = recordarSesion ? 7 * 24 * 60 * 60 : -1; // 7 días o sesión temporal

        // Cookie para token
        Cookie cookieToken = new Cookie("tokenUsuario", token);
        cookieToken.setMaxAge(maxAge);
        cookieToken.setHttpOnly(true);
        cookieToken.setSecure(true); // solo HTTPS
        cookieToken.setPath("/");
        response.addCookie(cookieToken);

        // Cookie para tipo de usuario
        Cookie cookieTipo = new Cookie("tipoUsuario", tipoUsuario);
        cookieTipo.setMaxAge(maxAge);
        cookieTipo.setHttpOnly(true);
        cookieTipo.setSecure(true);
        cookieTipo.setPath("/");
        response.addCookie(cookieTipo);
    }



}
