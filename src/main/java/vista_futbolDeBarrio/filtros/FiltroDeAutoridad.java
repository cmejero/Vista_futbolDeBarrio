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

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest http = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = http.getSession();

        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        String uri = http.getRequestURI();
        String ctx = http.getContextPath();
        String path = uri.substring(ctx.length());

        // =======================
        // 1. RUTAS TOTALMENTE PÚBLICAS
        // =======================
        if (esRutaPublica(path)) {
            chain.doFilter(request, response);
            return;
        }

        // =======================
        // 2. SI NO HAY SESIÓN → REDIRIGE A LOGIN
        // =======================
        if (tipoUsuario == null) {
            res.sendRedirect(ctx + "/InicioSesion.jsp");
            return;
        }

        // =======================
        // 3. CONTROL DE ROLES
        // =======================
        if (!tienePermisoSegunTipoUsuario(path, tipoUsuario)) {
            res.sendRedirect(ctx + "/InicioSesion.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    // =======================
    // RUTAS PÚBLICAS PERMITIDAS
    // =======================
    private boolean esRutaPublica(String uri) {

        return uri.equals("/") ||
               uri.contains("/InicioSesion.jsp") ||
               uri.contains("/Registrar.jsp") ||
               uri.contains("/PedirEmail.jsp") ||
               uri.contains("/RestablecerPassword.jsp") ||
               uri.contains("/Index.jsp") ||
               uri.contains("/Eventos.jsp") ||
               uri.contains("/MarcadoresClub.jsp") ||
               uri.contains("/clubEstadisticaGlobal") ||
               uri.contains("/clubEstadisticaTorneo") ||
               uri.contains("/jugadorEstadisticaGlobal") ||
               uri.contains("/jugadorEstadisticaTorneo") ||
               uri.contains("/torneo") ||





               // Permitir Servlets de login / registro
               uri.contains("/login") ||

               // Recursos estáticos
               uri.contains("/Css") ||
               uri.contains("/Imagenes") ||

               // Vista pública del torneo
               uri.contains("/DetallesTorneo.jsp") ||
               uri.contains("/torneo/bracket");
    }

    // =======================
    // PERMISOS POR TIPO DE USUARIO
    // =======================
    private boolean tienePermisoSegunTipoUsuario(String uri, String tipoUsuario) {
        // Rutas exclusivas por tipo de usuario
        if (tipoUsuario.equals("jugador")) {
            if (uri.contains("Jugador.jsp") || uri.contains("MiClubJugador.jsp") || uri.contains("MarcadoresJugador.jsp")) {
                return true;
            }
            if (uri.contains("Club.jsp") || uri.contains("EventoClub.jsp") || uri.contains("PlantillaClub.jsp") ||
                uri.contains("Instalacion.jsp") || uri.contains("EventoInstalacion.jsp") || uri.contains("TorneoInstalacion.jsp")) {
                return false;
            }
        }

        if (tipoUsuario.equals("club")) {
            if (uri.contains("Club.jsp") || uri.contains("EventoClub.jsp") || uri.contains("PlantillaClub.jsp")) {
                return true;
            }
            if (uri.contains("Jugador.jsp") || uri.contains("MiClubJugador.jsp") || uri.contains("MarcadoresJugador.jsp") ||
                uri.contains("Instalacion.jsp") || uri.contains("EventoInstalacion.jsp") || uri.contains("TorneoInstalacion.jsp")) {
                return false;
            }
        }

        if (tipoUsuario.equals("instalacion")) {
            if (uri.contains("Instalacion.jsp") || uri.contains("EventoInstalacion.jsp") || uri.contains("TorneoInstalacion.jsp")) {
                return true;
            }
            if (uri.contains("Jugador.jsp") || uri.contains("MiClubJugador.jsp") || uri.contains("MarcadoresJugador.jsp") ||
                uri.contains("Club.jsp") || uri.contains("EventoClub.jsp") || uri.contains("PlantillaClub.jsp")) {
                return false;
            }
        }

        // Administrador puede todo
        if (tipoUsuario.equals("administrador")) {
            return true;
        }

        // Rutas “generales” accesibles por todos
        return true;
    }

    public void init(FilterConfig fConfig) {}

    public void destroy() {}
}
