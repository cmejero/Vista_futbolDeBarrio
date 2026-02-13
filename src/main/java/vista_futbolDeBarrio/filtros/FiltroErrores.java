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

/**
 * Filtro global de manejo de errores.
 * <p>
 * - Captura excepciones no controladas de los servlets y controladores.
 * - Evita que errores inesperados rompan la aplicación.
 * - Redirige al servlet /login con mensaje de error solo para errores de servidor.
 * - No afecta las redirecciones de seguridad (acceso denegado).
 */
@WebFilter("/*") // Aplica a todas las rutas
public class FiltroErrores implements Filter {

    @Override
    /**
     * Intercepta las solicitudes y redirige a la página de login si ocurre una excepción.
     *
     * @param request Objeto de la solicitud HTTP.
     * @param response Objeto de la respuesta HTTP.
     * @param chain Cadena de filtros para continuar la ejecución.
     * @throws IOException Si ocurre un error de E/S.
     * @throws ServletException Si ocurre un error en el servlet.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            // Continua la cadena de filtros / ejecución del servlet
            chain.doFilter(request, response);

        } catch (Exception e) {
       
            e.printStackTrace();

            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;


            if (resp.isCommitted()) {
                return;
            }

            String mensaje = "servidor"; 
            String loginUrl = req.getContextPath() + "/login?error=" + mensaje;

            resp.sendRedirect(loginUrl);
        }
    }

    /**
     * Inicializa el filtro.
     *
     * @param filterConfig Configuración del filtro
     */
    @Override
    public void init(FilterConfig filterConfig) {}

    /**
     * Destruye el filtro, liberando recursos si fuera necesario.
     */
    @Override
    public void destroy() {}
}
