package vista_futbolDeBarrio.filtros;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class FiltroDeAutoridad implements Filter {

    @Override
    /**
     * Filtra las solicitudes HTTP para controlar acceso según tipo de usuario.
     *
     * - Permite rutas públicas sin sesión.
     * - Redirige "/" a la página correspondiente según tipo de usuario.
     * - Bloquea accesos no autorizados y redirige a login con error.
     *
     * @param request Solicitud HTTP entrante.
     * @param response Respuesta HTTP para redirección.
     * @param chain Cadena de filtros para continuar procesamiento.
     * @throws IOException Error de E/S al redirigir.
     * @throws ServletException Error del servlet en el filtro.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest http = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = http.getSession(false);

        String tipoUsuario = (session != null) ? (String) session.getAttribute("tipoUsuario") : null;
        String uri = http.getRequestURI();
        String ctx = http.getContextPath();
        String path = uri.substring(ctx.length());
        String metodo = http.getMethod();

        // 🔹 Rutas públicas
        if (esRutaPublica(path, metodo)) {
            chain.doFilter(request, response);
            return;
        }

        // 🔹 Redirigir raíz "/" según sesión válida
        if ((path.equals("/") || path.equals("")) && tipoUsuario != null) {
            res.sendRedirect(ctx + "/" + servletSegunTipoUsuario(tipoUsuario));
            return;
        }

        // 🔹 Control de permisos
        if (!tienePermisoSegunTipoUsuario(path, tipoUsuario)) {
            if (!path.startsWith("/login") && !path.startsWith("/logout")) {
                res.sendRedirect(ctx + "/login?error=accesoDenegado");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    
    /**
     * Determina si una ruta es accesible sin autenticación.
     *
     * Considera rutas de registro, login, recuperación de cuenta,
     * páginas públicas y recursos estáticos como CSS/imagenes.
     *
     * @param path Ruta solicitada.
     * @param metodo Método HTTP de la solicitud.
     * @return true si la ruta es pública; false si requiere sesión.
     */
    private boolean esRutaPublica(String path, String metodo) {
        // Incluye logout como ruta pública
        if ("/logout".equals(path)) return true;

        if ("POST".equalsIgnoreCase(metodo)
                && (path.equals("/usuario") || path.equals("/club") || path.equals("/instalacion"))) return true;

        if ("GET".equalsIgnoreCase(metodo) && path.startsWith("/torneo")) return true;

        return path.equals("/") || path.startsWith("/quienesSomos") || path.startsWith("/inicio")
                || path.startsWith("/registrar") || path.startsWith("/marcadores")
                || path.startsWith("/eventos") || path.startsWith("/recuperarCuenta")
                || path.startsWith("/restablecerCuenta") || path.startsWith("/login")
                || path.startsWith("/Css") || path.startsWith("/Imagenes")
                || path.startsWith("/detalleTorneo");
    }

    
    /**
     * Verifica si un usuario tiene acceso a una ruta según su tipo.
     *
     * @param path Ruta solicitada.
     * @param tipoUsuario Tipo de usuario en sesión (jugador, club, instalación, administrador).
     * @return true si el usuario puede acceder; false si no tiene permisos.
     */
    private boolean tienePermisoSegunTipoUsuario(String path, String tipoUsuario) {
        if (tipoUsuario == null) return false;

        return switch (tipoUsuario) {
            case "jugador" -> path.startsWith("/jugador") || path.startsWith("/logout") || path.startsWith("/pagoPremium");
            case "club" -> path.startsWith("/club") || path.startsWith("/logout") || path.startsWith("/pagoPremium");
            case "instalacion" -> path.startsWith("/instalacion") || path.startsWith("/logout");
            case "administrador" -> path.startsWith("/administrador") || path.startsWith("/logout");
            default -> false;
        };
    }

    
    /**
     * Devuelve la ruta base a redirigir según el tipo de usuario.
     *
     * @param tipoUsuario Tipo de usuario en sesión.
     * @return Nombre del servlet o "login" si el tipo no es válido.
     */
    private String servletSegunTipoUsuario(String tipoUsuario) {
        return switch (tipoUsuario) {
            case "administrador" -> "administrador";
            case "jugador" -> "jugador";
            case "club" -> "club";
            case "instalacion" -> "instalacion";
            default -> "login";
        };
    }

    @Override
    /**
     * Inicializa el filtro. No se requiere configuración adicional en este caso.
     */
    public void init(FilterConfig fConfig) { }

    @Override
    /**
     * Limpieza del filtro al ser destruido. No se realiza ninguna acción aquí.
     */

    public void destroy() { }
}
