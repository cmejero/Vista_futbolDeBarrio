package vista_futbolDeBarrio.filtros;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.ClubDto;
import vista_futbolDeBarrio.dtos.InstalacionDto;
import vista_futbolDeBarrio.dtos.UsuarioDto;
import vista_futbolDeBarrio.servicios.LoginServicio;
import vista_futbolDeBarrio.utilidades.Utilidades;

@WebFilter("/*")
/**
 * Filtro que reconstruye la sesión desde la cookie "tokenUsuario" si existe.
 * Redirige al login si el token es inválido o expirado.
 */

public class FiltroRecordarSesion implements Filter {

    private LoginServicio loginServicio = new LoginServicio();

    @Override
    /**
     * Intercepta solicitudes para reconstruir la sesión desde cookie persistente.
     *
     * - Ignora rutas de login/logout para no interferir.
     * - Si no hay sesión activa, valida la cookie "tokenUsuario".
     * - Reconstruye la sesión con JWT y atributos según tipo de usuario.
     * - Borra cookies y redirige al login si el token es inválido o expirado.
     *
     * @param request Objeto de la solicitud HTTP.
     * @param response Objeto de la respuesta HTTP.
     * @param chain Cadena de filtros para continuar la ejecución.
     * @throws IOException Si ocurre un error de E/S.
     * @throws ServletException Si ocurre un error en el servlet.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest http = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = http.getSession(false);
        String path = http.getRequestURI().substring(http.getContextPath().length());

        try {
            // 🔹 Ignorar login y logout para evitar reconstrucción de sesión
            String logoutParam = http.getParameter("logout");
            if (path.equals("/login") || path.equals("/logout") || "true".equals(logoutParam)) {
                chain.doFilter(request, response);
                return;
            }

            // 🔹 Si no hay sesión, intentar reconstruir desde cookies
            if (session == null || session.getAttribute("tipoUsuario") == null) {
                Cookie[] cookies = http.getCookies();
                String token = null;

                if (cookies != null) {
                    for (Cookie c : cookies) {
                        if ("tokenUsuario".equals(c.getName())) token = c.getValue();
                    }
                }

                if (token != null) {
                    // 🔹 Usar nuevo método que devuelve Map con JWT, tipo y DTO
                    Map<String, Object> datos = loginServicio.validarTokenPersistente(token);

                    if (datos != null) {
                        String tipo = (String) datos.get("tipoUsuario");
                        Object usuario = datos.get("datosUsuario");
                        String jwt = (String) datos.get("jwt");

                        session = http.getSession(true);
                        session.setAttribute("tipoUsuario", tipo);
                        session.setAttribute("datosUsuario", usuario);
                        session.setAttribute("token", jwt); // 🔑 Guardar JWT en sesión

                        // Asignar atributos específicos según tipo
                        switch (tipo.toLowerCase()) {
                            case "jugador" -> {
                                UsuarioDto u = (UsuarioDto) usuario;
                                session.setAttribute("usuarioId", u.getIdUsuario());
                                session.setAttribute("nombreUsuario", u.getNombreCompletoUsuario());
                                session.setAttribute("esPremium", u.getEsPremium());
                            }
                            case "club" -> {
                                ClubDto c = (ClubDto) usuario;
                                session.setAttribute("clubId", c.getIdClub());
                                session.setAttribute("nombreClub", c.getNombreClub());
                                session.setAttribute("esPremium", c.getEsPremium());
                            }
                            case "instalacion" -> {
                                InstalacionDto i = (InstalacionDto) usuario;
                                session.setAttribute("idInstalacion", i.getIdInstalacion());
                                session.setAttribute("nombreInstalacion", i.getNombreInstalacion());
                            }
                            case "administrador" -> {
                                // no hay atributos adicionales
                            }
                        }
                        // ✅ No hacer redirect, dejar que el servlet maneje la navegación
                    } else {
                        // Token inválido o expirado
                        Utilidades.borrarCookies(res, http.getContextPath());
                        res.sendRedirect("https://" + http.getServerName() + http.getContextPath() + "/login?mensaje=sessionExpirada");
                        return;
                    }
                }
            }

            // 🔹 Si hay sesión o no se reconstruye, continuar
            chain.doFilter(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            Utilidades.borrarCookies(res, http.getContextPath());
            res.sendRedirect("https://" + http.getServerName() + http.getContextPath() + "/login?error=servidor");
        }
    }

    @Override
    /**
     * Inicializa el filtro.
     *
     * @param filterConfig Configuración del filtro
     */
    public void init(FilterConfig filterConfig) { }

    @Override
    /**
     * Destruye el filtro, liberando recursos si fuera necesario.
     */
    public void destroy() { }
}
