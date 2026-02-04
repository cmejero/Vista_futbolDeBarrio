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

	                    // Redirigir automáticamente si entramos a la raíz "/"
	                    if (path.equals("/") || path.equals("")) {
	                        res.sendRedirect(ctx + "/" + jspSegunTipoUsuario(tipoUsuario));
	                        return;
	                    }

	                } catch (Exception e) {
	                    Utilidades.borrarCookies(res);
	                    res.sendRedirect(ctx + "/InicioSesion.jsp?error=accesoDenegado");
	                    return;
	                }
	            }
	        }

	        // =========================
	        // 2️⃣ Redirigir raíz "/" según sesión existente
	        // =========================
	        if ((path.equals("/") || path.equals("")) && tipoUsuario != null) {
	            res.sendRedirect(ctx + "/" + jspSegunTipoUsuario(tipoUsuario));
	            return;
	        }

	        // =========================
	        // 3️⃣ Rutas públicas
	        // =========================
	        if (esRutaPublica(path, metodo) || path.equals("/login") || path.equals("/logout")) {
	            chain.doFilter(request, response);
	            return;
	        }

	        // =========================
	        // 4️⃣ Control de permisos según tipoUsuario
	        // =========================
	        if (!tienePermisoSegunTipoUsuario(path, metodo, tipoUsuario)) {
	            // ❌ NO cerramos sesión, solo denegamos el acceso
	            res.sendRedirect(ctx + "/InicioSesion.jsp?error=accesoDenegado");
	            return;
	        }

	        // =========================
	        // 5️⃣ Continuar con la cadena de filtros
	        // =========================
	        chain.doFilter(request, response);
	    }

    
    
    // =======================
    // RUTAS PÚBLICAS
    // =======================
    private boolean esRutaPublica(String uri, String metodo) {
        // POST /usuario, /club, /instalacion son públicos
        if ("POST".equalsIgnoreCase(metodo) && (uri.equals("/usuario") || uri.equals("/club") || uri.equals("/instalacion"))) {
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
               uri.contains("/RecuperarCuenta.jsp") ||
               uri.contains("/recuperarPassword") ||
               uri.contains("/RestablecerPassword.jsp") ||
               uri.contains("/restablecerCuenta") ||
               uri.contains("/Index.jsp") ||
               uri.contains("/Eventos.jsp") ||
               uri.contains("/MarcadoresClub.jsp") ||
               uri.contains("/QuienesSomos.jsp") ||
               uri.contains("/clubEstadisticaGlobal") ||
               uri.contains("/clubEstadisticaTorneo") ||
               uri.contains("/jugadorEstadisticaGlobal") ||
               uri.contains("/jugadorEstadisticaTorneo") ||
               uri.contains("/Inicio") ||
               uri.contains("/registrar") ||
               uri.contains("/marcadores") ||
               uri.contains("/eventos") ||
               uri.contains("/recuperarCuenta") ||
               uri.contains("/jugador") ||
               uri.contains("/jugador/eventos") ||
               uri.contains("/jugador/misClubes") ||
               uri.contains("/jugador/marcadores") ||
               uri.contains("/detalleTorneo") ||

               uri.contains("/club") ||
               uri.contains("/club/eventos") ||
               uri.contains("/club/plantilla") ||

               uri.contains("/instalacion") ||
               uri.contains("/instalacion/eventos") ||
               uri.contains("/instalacion/torneo") ||
               uri.contains("/instalacion/actaPartido") ||





               
               
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
        // ✅ Evitar NPE si tipoUsuario es null
        if (tipoUsuario == null) {
            return false;
        }

        if (uri.equals("/pagoPremium")) {
            return "jugador".equals(tipoUsuario) || "club".equals(tipoUsuario);
        }
        if (uri.contains("/detalleTorneo")) {
            return true;
        }

        // ------------------- /torneo -------------------
        if (uri.startsWith("/torneo")) {
            if (!"GET".equalsIgnoreCase(metodo)) {
                return "instalacion".equals(tipoUsuario);
            }
        }

        // ------------------- /usuario -------------------
        if (uri.equals("/usuario")) {
            if ("POST".equalsIgnoreCase(metodo)) return true; // público
            if ("GET".equalsIgnoreCase(metodo)) return "administrador".equals(tipoUsuario) || "jugador".equals(tipoUsuario);
            return "administrador".equals(tipoUsuario);
        }

        // ------------------- /club -------------------
        if (uri.equals("/club")) {
            if ("POST".equalsIgnoreCase(metodo)) return true; // público
            if ("GET".equalsIgnoreCase(metodo)) return "administrador".equals(tipoUsuario) || "club".equals(tipoUsuario);
            return "administrador".equals(tipoUsuario);
        }

        // ------------------- /instalacion -------------------
        if (uri.equals("/instalacion")) {
            if ("POST".equalsIgnoreCase(metodo)) return true; // todavía público si se usa para registrar instalaciones
            if ("GET".equalsIgnoreCase(metodo)) return "administrador".equals(tipoUsuario) || "instalacion".equals(tipoUsuario);
            return "administrador".equals(tipoUsuario);
        }

        // ------------------- /instalacion/eventos -------------------
        if (uri.startsWith("/instalacion/eventos")) {
            if ("POST".equalsIgnoreCase(metodo) || "DELETE".equalsIgnoreCase(metodo)) {
                return "instalacion".equals(tipoUsuario); // Solo instalaciones pueden crear/eliminar torneos
            }
            if ("GET".equalsIgnoreCase(metodo)) {
                return "instalacion".equals(tipoUsuario) || "administrador".equals(tipoUsuario); // GET también para admin
            }
        }

        // ------------------- Resto de rutas -------------------
        switch (tipoUsuario) {
            case "jugador":
                if (uri.contains("Jugador.jsp") || uri.contains("MiClubJugador.jsp") || uri.contains("MarcadoresJugador.jsp")) return true;
                if (uri.contains("Club.jsp") || uri.contains("EventoClub.jsp") || uri.contains("PlantillaClub.jsp") ||
                    uri.contains("Instalacion.jsp") || uri.contains("EventoInstalacion.jsp") || uri.contains("TorneoInstalacion.jsp")) return false;
                break;
            case "club":
                if (uri.contains("Club.jsp") || uri.contains("EventoClub.jsp") || uri.contains("PlantillaClub.jsp")) return true;
                if (uri.contains("Jugador.jsp") || uri.contains("MiClubJugador.jsp") || uri.contains("MarcadoresJugador.jsp") ||
                    uri.contains("Instalacion.jsp") || uri.contains("EventoInstalacion.jsp") || uri.contains("TorneoInstalacion.jsp")) return false;
                break;
            case "instalacion":
                if (uri.contains("Instalacion.jsp") || uri.contains("EventoInstalacion.jsp") || uri.contains("TorneoInstalacion.jsp")) return true;
                if (uri.contains("Jugador.jsp") || uri.contains("MiClubJugador.jsp") || uri.contains("MarcadoresJugador.jsp") ||
                    uri.contains("Club.jsp") || uri.contains("EventoClub.jsp") || uri.contains("PlantillaClub.jsp")) return false;
                break;
            case "administrador":
                return true; // administrador puede todo
        }

        // Resto de rutas “generales” accesibles por todos
        return true;
    }

    
    private String jspSegunTipoUsuario(String tipoUsuario) {
        switch (tipoUsuario) {
            case "administrador": return "Administrador.jsp";
            case "jugador":       return "Jugador.jsp";
            case "club":          return "Club.jsp";
            case "instalacion":   return "Instalacion.jsp";
            default:              return "InicioSesion.jsp";
        }
    }
    
    

    @Override
    public void init(FilterConfig fConfig) {}
    @Override
    public void destroy() {}
}
