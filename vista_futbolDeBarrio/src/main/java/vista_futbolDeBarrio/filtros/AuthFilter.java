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
public class AuthFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();

        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        String token = (String) session.getAttribute("token");

        String requestedURI = httpRequest.getRequestURI();
        System.out.println("Ruta solicitada: " + requestedURI);
        System.out.println("Token: " + token);
        System.out.println("TipoUsuario: " + tipoUsuario);

        // Rutas públicas que no pasan por el filtro
        if (requestedURI.contains("InicioSesion.jsp") ||
            requestedURI.contains("Registrar.jsp") ||
            requestedURI.contains("Index.jsp") ||
            requestedURI.contains("/usuario") ||
            requestedURI.contains("/club") ||
            requestedURI.contains("/instalacion") ||
            requestedURI.contains("/Css") ||
            requestedURI.contains("/Imagenes/") ||
            requestedURI.contains("/login")) {

            chain.doFilter(request, response);
            return;
        }

        // Usuario no autenticado
        if (token == null || token.isEmpty()) {
            httpResponse.sendRedirect("InicioSesion.jsp");
            return;
        }

        // Autorización según tipo de usuario
        if (requestedURI.contains("Administrador.jsp")) {
            if ("administrador".equals(tipoUsuario)) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendRedirect("InicioSesion.jsp");
            }
        } else if (requestedURI.contains("Jugador.jsp")) {
            if ("jugador".equals(tipoUsuario) || "administrador".equals(tipoUsuario)) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendRedirect("InicioSesion.jsp");
            }
        } else if (requestedURI.contains("Club.jsp")) {
            if ("club".equals(tipoUsuario) || "administrador".equals(tipoUsuario)) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendRedirect("InicioSesion.jsp");
            }
        } else if (requestedURI.contains("Instalacion.jsp")) {
            if ("instalacion".equals(tipoUsuario) || "administrador".equals(tipoUsuario)) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendRedirect("InicioSesion.jsp");
            }
        } else {
            // Otros recursos
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {}

    public void destroy() {}
}
