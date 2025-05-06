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
            requestedURI.contains("PlantillaClub.jsp") ||
            requestedURI.contains("/usuario") ||
            requestedURI.contains("/club") ||
            requestedURI.contains("/torneo") ||
            requestedURI.contains("/instalacion") ||
            requestedURI.contains("/Css") ||
            requestedURI.contains("/Imagenes/") ||
            requestedURI.contains("/login")) {

            chain.doFilter(request, response);
            return;
        }

     

        // Permitir solicitudes DELETE a /torneo solo si es una instalación
        if (httpRequest.getMethod().equals("DELETE") && requestedURI.contains("/torneo")) {
            if ("instalacion".equals(tipoUsuario)) {
                chain.doFilter(request, response);
                return;
            } else {
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpResponse.getWriter().write("Solo las instalaciones pueden eliminar torneos.");
                return;
            }
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
        } else if (requestedURI.contains("EventoClub.jsp")) {
            if ("club".equals(tipoUsuario) || "administrador".equals(tipoUsuario)) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendRedirect("InicioSesion.jsp");
            }
        
        } else if (requestedURI.contains("Instalacion.jsp")) {
            if ("instalacion".equals(tipoUsuario)) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendRedirect("InicioSesion.jsp");
            }

            if (requestedURI.contains("EventoInstalacion.jsp")) {
                if ("instalacion".equals(tipoUsuario)) {
                    chain.doFilter(request, response);
                } else {
                    httpResponse.sendRedirect("InicioSesion.jsp");
                }
            }
        } else {
            // Otros recursos
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {}

    public void destroy() {}
}
