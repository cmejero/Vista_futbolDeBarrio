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

        // =======================
        // 1️⃣ RUTAS PÚBLICAS
        // =======================
        if (esRutaPublica(path, metodo)) {
            chain.doFilter(request, response);
            return;
        }

        // =======================
        // 2️⃣ SI NO HAY SESIÓN → REDIRIGE A LOGIN
        // =======================
        if (tipoUsuario == null) {
            res.sendRedirect(ctx + "/InicioSesion.jsp");
            return;
        }

        // =======================
        // 3️⃣ CONTROL DE ROLES
        // =======================
        if (!tienePermisoSegunTipoUsuario(path, metodo, tipoUsuario)) {
            res.sendRedirect(ctx + "/InicioSesion.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    // =======================
    // RUTAS PÚBLICAS
    // =======================
    private boolean esRutaPublica(String uri, String metodo) {
        // POST /usuario es público
        if ("POST".equalsIgnoreCase(metodo) && uri.equals("/usuario")) {
            return true;
        }
        if ("POST".equalsIgnoreCase(metodo) && uri.equals("/club")) {
            return true;
        }
        if ("POST".equalsIgnoreCase(metodo) && uri.equals("/instalacion")) {
            return true;
        }

        // GET /torneo es público
        if ("GET".equalsIgnoreCase(metodo) && uri.startsWith("/torneo")) {
            return true;
        }

        // Otras rutas públicas
        return uri.equals("/") ||
               uri.contains("/InicioSesion.jsp") ||
               uri.contains("/Registrar.jsp") ||
               uri.contains("/PedirEmail.jsp") ||
               uri.contains("/recuperarPassword") ||  
               uri.contains("/RestablecerPassword.jsp") ||
               uri.contains("/restablecerPassword") ||
               uri.contains("/Index.jsp") ||
               uri.contains("/Eventos.jsp") ||
               uri.contains("/MarcadoresClub.jsp") ||
               uri.contains("/clubEstadisticaGlobal") ||
               uri.contains("/clubEstadisticaTorneo") ||
               uri.contains("/jugadorEstadisticaGlobal") ||
               uri.contains("/jugadorEstadisticaTorneo") ||
               uri.contains("/DetallesTorneo.jsp") ||
               uri.contains("/torneo/bracket") ||
               uri.contains("/login") ||
               uri.contains("/loginGoogle") ||
               uri.contains("/Css") ||
               uri.contains("/Imagenes");
    }

    // =======================
    // CONTROL DE ROLES POR MÉTODO
    // =======================
    private boolean tienePermisoSegunTipoUsuario(String uri, String metodo, String tipoUsuario) {

        // ------------------- /torneo -------------------
        if (uri.startsWith("/torneo")) {
            // GET ya es público
            if (!"GET".equalsIgnoreCase(metodo)) {
                return "instalacion".equals(tipoUsuario); // POST, PUT, DELETE solo instalacion
            }
        }

        // ------------------- /usuario -------------------
        // POST /usuario es público, ya filtrado en esRutaPublica
        // Modificación solo para admin
        if (uri.equals("/usuario")) {
            if ("POST".equalsIgnoreCase(metodo)) {
                return true; 
            } else if ("GET".equalsIgnoreCase(metodo)) {
                return "administrador".equals(tipoUsuario) || "jugador".equals(tipoUsuario);
            } else {
               
                return "administrador".equals(tipoUsuario);
            }
        }
        
     // ------------------- /club -------------------
        if (uri.equals("/club")) {
            if ("POST".equalsIgnoreCase(metodo)) {
                return true; // Público
            } else if ("GET".equalsIgnoreCase(metodo)) {
                return "administrador".equals(tipoUsuario) || "club".equals(tipoUsuario);
            } else {
                // PUT o DELETE
                return "administrador".equals(tipoUsuario);
            }
        }

        // ------------------- /instalacion -------------------
        if (uri.equals("/instalacion")) {
            if ("POST".equalsIgnoreCase(metodo)) {
                return true; // Público
            } else if ("GET".equalsIgnoreCase(metodo)) {
                return "administrador".equals(tipoUsuario) || "instalacion".equals(tipoUsuario);
            } else {
                // PUT o DELETE
                return "administrador".equals(tipoUsuario);
            }
        }

        // ------------------- Resto de rutas -------------------
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

        // Resto de rutas “generales” accesibles por todos
        return true;
    }

    @Override
    public void init(FilterConfig fConfig) {}
    @Override
    public void destroy() {}
}
