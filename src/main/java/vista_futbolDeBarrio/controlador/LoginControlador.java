package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    private final LoginServicio servicioLogin = new LoginServicio();
    private final InicioSesionGoogleServicio servicioGoogle = new InicioSesionGoogleServicio();

    @Override
    /**
     * Maneja GET en la página de login.
     *
     * - Intenta login automático si existe cookie de token persistente.
     * - Si token válido, reconstruye sesión y redirige.
     * - Si token inválido o ausente, muestra la página de login con mensaje opcional.
     *
     * @param request Solicitud HTTP con cookies y parámetros.
     * @param response Respuesta HTTP para forward a JSP o redirección.
     * @throws ServletException Error de servlet al forward.
     * @throws IOException Error de E/S durante forward o redirección.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔹 Intento de login automático desde cookie
        Cookie[] cookies = request.getCookies();
        String tokenCookie = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("tokenUsuario".equals(c.getName())) tokenCookie = c.getValue();
            }
        }

        if (tokenCookie != null) {
            Map<String, Object> datos = servicioLogin.validarTokenPersistente(tokenCookie);
            if (datos != null) {
                // Reconstruir sesión y redirigir automáticamente
                servicioLogin.manejarSesion(
                        request,
                        response,
                        datos.get("datosUsuario"),
                        (String) datos.get("jwt"),
                        (String) datos.get("tipoUsuario")
                );
                return;
            } else {
                // Token inválido o expirado
                servicioLogin.borrarCookies(response, request.getContextPath());
            }
        }

        // Mostrar página de login
        String mensaje = request.getParameter("mensaje");
        if (mensaje != null) request.setAttribute("mensaje", mensaje);
        request.getRequestDispatcher("/WEB-INF/Vistas/InicioSesion.jsp").forward(request, response);
    }

    
    
    @Override
    /**
     * Maneja POST en la página de login.
     *
     * - Soporta login con Google usando código OAuth.
     * - Soporta login normal con email y contraseña.
     * - Permite recordar sesión mediante token persistente y cookies.
     * - Reconstruye sesión y redirige según tipo de usuario.
     * - Redirige a login con mensaje de error si falla.
     *
     * @param request Solicitud HTTP con parámetros de login.
     * @param response Respuesta HTTP para redirección o forward a JSP.
     * @throws ServletException Error de servlet al forward.
     * @throws IOException Error de E/S durante redirección o forward.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String codeGoogle = request.getParameter("code");
            String tipoUsuario = request.getParameter("tipoUsuario");

            // 🔹 LOGIN CON GOOGLE
            if (codeGoogle != null && !codeGoogle.isEmpty()) {
                LoginGoogleDto loginDto = servicioGoogle.loginConGoogle(
                        codeGoogle, tipoUsuario, request.getServletContext());

                if (loginDto != null) {
                    Log.ficheroLog("Login exitoso con Google: " + loginDto.getNombreCompleto());

                    // 🔹 Convertir LoginGoogleDto a UsuarioDto para manejar sesión unificada
                    UsuarioDto usuario = new UsuarioDto();
                    usuario.setIdUsuario(loginDto.getIdTipoUsuario());
                    usuario.setNombreCompletoUsuario(loginDto.getNombreCompleto());
                    usuario.setEsPremium(loginDto.isEsPremium());

                    // 🔹 Manejar sesión usando LoginServicio unificado
                    servicioLogin.manejarSesion(request, response,
                            usuario,
                            loginDto.getToken(),
                            loginDto.getTipoUsuario());
                    return;
                } else {
                    Log.ficheroLog("Error login con Google: respuesta nula");
                    request.setAttribute("error", "googleAPI");
                    request.getRequestDispatcher("/WEB-INF/Vistas/InicioSesion.jsp").forward(request, response);
                    return;
                }
            }

            // 🔹 LOGIN NORMAL
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            boolean recordarSesion = "on".equals(request.getParameter("recordarSesion"));

            Log.ficheroLog("Intento login normal: " + email);

            RespuestaLoginDto respuestaLogin = servicioLogin.login(email, password, tipoUsuario);

            if (respuestaLogin != null && respuestaLogin.getToken() != null) {

                // 🔹 RECORDAR SESIÓN antes de manejar sesión
                if (recordarSesion) {
                    String tokenPersistente = servicioLogin.generarTokenPersistente(
                            respuestaLogin.getDatosUsuario(),
                            respuestaLogin.getTipoUsuario(),
                            respuestaLogin.getToken());
                    if (tokenPersistente != null) {
                        servicioLogin.agregarTokenYCookies(response, tokenPersistente, respuestaLogin.getTipoUsuario(), request.getContextPath());
                        Log.ficheroLog("Cookies persistentes agregadas: " + email);
                    }
                } else {
                    servicioLogin.borrarCookies(response, request.getContextPath());
                }

                // 🔹 Manejar sesión usando LoginServicio unificado
                servicioLogin.manejarSesion(request, response,
                        respuestaLogin.getDatosUsuario(),
                        respuestaLogin.getToken(),
                        respuestaLogin.getTipoUsuario());
                return;

            } else {
                Log.ficheroLog("Credenciales incorrectas: " + email);
                response.sendRedirect("InicioSesion.jsp?error=credenciales");
                return;
            }

        } catch (Exception e) {
            Log.ficheroLog("Error en login POST: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("InicioSesion.jsp?error=servidor");
            return;
        }
    }

}
