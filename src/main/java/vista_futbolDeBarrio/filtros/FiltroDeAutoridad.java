package vista_futbolDeBarrio.filtros;

import java.io.IOException;

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
import vista_futbolDeBarrio.servicios.LoginServicio;
import vista_futbolDeBarrio.utilidades.Utilidades;

/**
 * Filtro de autoridad para controlar el acceso a endpoints según tipo de usuario.
 *
 * <p>
 * - Rutas públicas: login, registro, información, CSS/Imágenes, etc.
 * - Rutas privadas: accesibles solo según tipo de usuario (jugador, club, instalación, administrador).
 * - En caso de acceso denegado: hace forward al JSP de login con mensaje.
 * - Previene que los usuarios escriban manualmente la URL de JSPs privadas.
 */
@WebFilter("/*")
public class FiltroDeAutoridad implements Filter {

    /**
     * Método principal del filtro que se ejecuta antes de cualquier servlet.
     * Controla:
     * 1️⃣ Auto-login desde cookies si no hay sesión.
     * 2️⃣ Redirección a raíz según tipo de usuario.
     * 3️⃣ Permitir rutas públicas sin sesión.
     * 4️⃣ Control de permisos por tipo de usuario.
     * 5️⃣ Forward a login JSP si acceso denegado.
     */
    @Override
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

        // =========================
        // 1️⃣ Auto-login desde cookies
        // =========================
        if (tipoUsuario == null) {
            Cookie[] cookies = http.getCookies();
            String token = null;
            String tipo = null;

            if (cookies != null) {
                for (Cookie c : cookies) {
                    if ("tokenUsuario".equals(c.getName())) token = c.getValue();
                    if ("tipoUsuario".equals(c.getName())) tipo = c.getValue();
                }
            }

            if (token != null && tipo != null) {
                try {
                    session = http.getSession(true);
                    session.setAttribute("token", token);
                    session.setAttribute("tipoUsuario", tipo);

                    Object datosUsuario = new LoginServicio().obtenerDatosUsuario(token, tipo);
                    session.setAttribute("datosUsuario", datosUsuario);

                    tipoUsuario = tipo;

                    // Redirigir a la página principal según tipo de usuario si entra a "/"
                    if (path.equals("/") || path.equals("")) {
                        res.sendRedirect(ctx + "/" + servletSegunTipoUsuario(tipoUsuario));
                        return;
                    }

                } catch (Exception e) {
                    // Limpiar cookies y enviar a login con mensaje de acceso denegado
                    Utilidades.borrarCookies(res);
                    res.sendRedirect(ctx + "/login?error=accesoDenegado");
                    return;
                }
            }
        }

        // =========================
        // 2️⃣ Redirigir raíz "/" según sesión existente
        // =========================
        if ((path.equals("/") || path.equals("")) && tipoUsuario != null) {
            res.sendRedirect(ctx + "/" + servletSegunTipoUsuario(tipoUsuario));
            return;
        }

        // =========================
        // 3️⃣ Rutas públicas (accesibles sin sesión)
        // =========================
        if (esRutaPublica(path, metodo)) {
            chain.doFilter(request, response);
            return;
        }

        // =========================
        // 4️⃣ Control de permisos según tipoUsuario
        // =========================
        if (!tienePermisoSegunTipoUsuario(path, tipoUsuario)) {
            // ⚠️ Acceso denegado: redirige al login con parámetro de error
            res.sendRedirect(ctx + "/login?error=accesoDenegado");
            return;
        }

        // =========================
        // 5️⃣ Continuar con la cadena de filtros si todo es correcto
        // =========================
        chain.doFilter(request, response);

    }

    /**
     * Determina si la ruta es pública (accesible sin sesión)
     *
     * @param uri    Ruta solicitada
     * @param metodo Método HTTP
     * @return true si es pública, false si requiere sesión
     */
    private boolean esRutaPublica(String uri, String metodo) {
        // POST públicos para crear usuario, club o instalación
        if ("POST".equalsIgnoreCase(metodo) &&
            (uri.equals("/usuario") || uri.equals("/club") || uri.equals("/instalacion"))) {
            return true;
        }

        // GET públicos
        if ("GET".equalsIgnoreCase(metodo) && uri.startsWith("/torneo")) return true;

        // Otras rutas públicas de información
        return uri.equals("/") ||
               uri.contains("/quienesSomos") ||
               uri.contains("/inicio") ||
               uri.contains("/registrar") ||
               uri.contains("/marcadores") ||
               uri.contains("/eventos") ||
               uri.contains("/recuperarCuenta") ||
               uri.contains("/restablecerCuenta") ||
               uri.contains("/login") ||


               uri.contains("/Css") ||
               uri.contains("/Imagenes") ||
               uri.contains("/detalleTorneo");
    }

    /**
     * Determina si el tipo de usuario tiene permiso para acceder al endpoint privado
     *
     * @param uri         Ruta solicitada
     * @param tipoUsuario Tipo de usuario de la sesión
     * @return true si tiene permiso, false si no
     */
    private boolean tienePermisoSegunTipoUsuario(String uri, String tipoUsuario) {
        if (tipoUsuario == null) return false;

        switch (tipoUsuario) {
            case "jugador":
                return uri.contains("/jugador") ||
                       uri.contains("/jugador/eventos") ||
                       uri.contains("/jugador/misClubes") ||
                       uri.contains("/jugador/marcadores") ||
                       uri.contains("/logout") ||
                       uri.contains("/pagoPremium");

            case "club":
                return uri.contains("/club") ||
                       uri.contains("/club/eventos") ||
                       uri.contains("/club/plantilla") ||
                       uri.contains("/logout") ||
                       uri.contains("/pagoPremium");

            case "instalacion":
                return uri.contains("/instalacion") ||
                       uri.contains("/instalacion/eventos") ||
                       uri.contains("/instalacion/torneo") ||
                       uri.contains("/instalacion/actaPartido") ||
                       uri.contains("/logout") ||
                       uri.contains("/torneo/bracket");

            case "administrador":
                return uri.contains("/administrador");

            default:
                return false;
        }
    }

    /**
     * Devuelve el endpoint de inicio según tipo de usuario para redirección
     *
     * @param tipoUsuario Tipo de usuario
     * @return Servlet de inicio correspondiente
     */
    private String servletSegunTipoUsuario(String tipoUsuario) {
        switch (tipoUsuario) {
            case "administrador": return "administrador";
            case "jugador":       return "jugador";
            case "club":          return "club";
            case "instalacion":   return "instalacion";
            default:              return "login";
        }
    }

    @Override
    public void init(FilterConfig fConfig) {}

    @Override
    public void destroy() {}
}
