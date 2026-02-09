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
import vista_futbolDeBarrio.dtos.LoginGoogleDto;
import vista_futbolDeBarrio.dtos.RespuestaLoginDto;
import vista_futbolDeBarrio.dtos.UsuarioDto;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.InicioSesionGoogleServicio;
import vista_futbolDeBarrio.servicios.LoginServicio;

@WebServlet("/login")
@MultipartConfig
public class LoginControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private LoginServicio servicioLogin = new LoginServicio();
    private InicioSesionGoogleServicio servicioGoogle = new InicioSesionGoogleServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mensaje = request.getParameter("mensaje");
        if (mensaje != null) request.setAttribute("mensaje", mensaje);

        request.getRequestDispatcher("/WEB-INF/Vistas/InicioSesion.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String codeGoogle = request.getParameter("code");
            String tipoUsuario = request.getParameter("tipoUsuario");

            if (codeGoogle != null && !codeGoogle.isEmpty()) {
                // 🔹 LOGIN CON GOOGLE
                LoginGoogleDto loginDto = servicioGoogle.loginConGoogle(codeGoogle, tipoUsuario, request.getServletContext());
                if (loginDto != null) {
                    manejarSesion(request, response, loginDto);
                } else {
                	request.setAttribute("error", "googleAPI");
                	request.getRequestDispatcher("/WEB-INF/Vistas/InicioSesion.jsp").forward(request, response);
                }
                return;
            }

            // 🔹 LOGIN NORMAL
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            boolean recordarSesion = "on".equals(request.getParameter("recordarSesion"));

            Log.ficheroLog("Intento de login normal. Email: " + email);

            RespuestaLoginDto respuestaLogin = servicioLogin.login(email, password, tipoUsuario);

            if (respuestaLogin != null && respuestaLogin.getToken() != null) {
                manejarSesion(request, response, respuestaLogin);

                if (recordarSesion) {
                    Cookie cookieToken = new Cookie("tokenUsuario", respuestaLogin.getToken());
                    cookieToken.setMaxAge(60 * 60 * 24 * 30);
                    cookieToken.setPath(request.getContextPath());
                    response.addCookie(cookieToken);

                    Cookie cookieTipo = new Cookie("tipoUsuario", respuestaLogin.getTipoUsuario());
                    cookieTipo.setMaxAge(60 * 60 * 24 * 30);
                    cookieTipo.setPath(request.getContextPath());
                    response.addCookie(cookieTipo);
                }

            } else {
                Log.ficheroLog("Credenciales incorrectas para el email: " + email);
                response.sendRedirect("InicioSesion.jsp?error=credenciales");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("Error en el proceso de login: " + e.getMessage());
            response.sendRedirect("InicioSesion.jsp?error=servidor");
        }
    }

    // ----------------- MÉTODOS AUXILIARES -----------------

    /**
     * Maneja la sesión y redirección para LoginGoogleDto
     */
    private void manejarSesion(HttpServletRequest request, HttpServletResponse response, LoginGoogleDto loginDto) throws IOException {
        HttpSession sesion = crearSesion(request);
        sesion.setAttribute("token", loginDto.getToken());
        sesion.setAttribute("tipoUsuario", loginDto.getTipoUsuario());
        sesion.setAttribute("esPremium", loginDto.isEsPremium());

        asignarIdYNombreSesion(sesion, loginDto.getTipoUsuario(), loginDto.getIdTipoUsuario(), loginDto.getNombreCompleto());

        redirigirPorTipoUsuario(response, loginDto.getTipoUsuario());
    }

    /**
     * Maneja la sesión y redirección para RespuestaLoginDto
     */
    private void manejarSesion(HttpServletRequest request, HttpServletResponse response, RespuestaLoginDto respuestaLogin) throws IOException {
        HttpSession sesion = crearSesion(request);
        sesion.setAttribute("token", respuestaLogin.getToken());
        sesion.setAttribute("tipoUsuario", respuestaLogin.getTipoUsuario());
        sesion.setAttribute("datosUsuario", respuestaLogin.getDatosUsuario());

        asignarIdUsuarioASesion(sesion, respuestaLogin.getTipoUsuario(), respuestaLogin.getDatosUsuario());

        redirigirPorTipoUsuario(response, respuestaLogin.getTipoUsuario());
    }

    /**
     * Invalida la sesión vieja y crea una nueva
     */
    private HttpSession crearSesion(HttpServletRequest request) {
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) oldSession.invalidate();
        return request.getSession(true);
    }

    private void asignarIdYNombreSesion(HttpSession session, String tipoUsuario, long id, String nombreCompleto) {
        switch (tipoUsuario.toLowerCase()) {
            case "jugador":
                session.setAttribute("usuarioId", id);
                session.setAttribute("nombreUsuario", nombreCompleto);
                break;
            case "club":
                session.setAttribute("clubId", id);
                session.setAttribute("nombreClub", nombreCompleto);
                break;
            case "instalacion":
                session.setAttribute("idInstalacion", id);
                session.setAttribute("nombreInstalacion", nombreCompleto);
                break;
            default:
                session.setAttribute("esPremium", false);
        }
    }

    private void asignarIdUsuarioASesion(HttpSession session, String tipoUsuario, Object datosUsuario) {
        switch (tipoUsuario.toLowerCase()) {
            case "instalacion":
                if (datosUsuario instanceof InstalacionDto inst) {
                    session.setAttribute("idInstalacion", inst.getIdInstalacion());
                    session.setAttribute("nombreInstalacion", inst.getNombreInstalacion());
                }
                break;
            case "club":
                if (datosUsuario instanceof ClubDto club) {
                    session.setAttribute("clubId", club.getIdClub());
                    session.setAttribute("nombreClub", club.getNombreClub());
                    session.setAttribute("esPremium", club.isEsPremium());
                }
                break;
            case "jugador":
                if (datosUsuario instanceof UsuarioDto user) {
                    session.setAttribute("usuarioId", user.getIdUsuario());
                    session.setAttribute("nombreUsuario", user.getNombreCompletoUsuario());
                    session.setAttribute("esPremium", user.isEsPremium());
                }
                break;
            default:
                session.setAttribute("esPremium", false);
        }
    }

    private void redirigirPorTipoUsuario(HttpServletResponse response, String tipoUsuario) throws IOException {
        switch (tipoUsuario.toLowerCase()) {
            case "administrador": response.sendRedirect("administrador"); break;
            case "jugador": response.sendRedirect("jugador"); break;
            case "club": response.sendRedirect("club"); break;
            case "instalacion": response.sendRedirect("instalacion"); break;
            default:
                Log.ficheroLog("Tipo de usuario desconocido: " + tipoUsuario);
                response.sendRedirect("login?error=tipoDesconocido");
        }
    }
}
