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
/**
 * Clase que maneja la autorización de acceso a las rutas dependiendo del tipo
 * de usuario.
 */
public class FiltroDeAutoridad implements Filter {

	/**
	 * Filtra las solicitudes HTTP y controla el acceso a las rutas según el tipo de
	 * usuario.
	 *
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @param chain    La cadena de filtros para la solicitud.
	 * @throws IOException      Si ocurre un error durante la lectura o escritura de
	 *                          la respuesta.
	 * @throws ServletException Si ocurre un error durante la ejecución del filtro.
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();

		String tipoUsuario = (String) session.getAttribute("tipoUsuario");
		String token = (String) session.getAttribute("token");
		String requestedURI = httpRequest.getRequestURI(); // ej: /vista_futbolDeBarrio/Index.jsp
		String contextPath = httpRequest.getContextPath(); // ej: /vista_futbolDeBarrio
		String path = requestedURI.substring(contextPath.length()); // ej: /Index.jsp o /

		// Permitir acceso a rutas públicas (incluyendo raíz "/")
		if (esRutaPublica(path) || path.equals("/") || path.isEmpty()) {
			chain.doFilter(request, response);
			return;
		}

		if (esPeticionDeleteTorneo(httpRequest, path)) {
			if ("instalacion".equals(tipoUsuario)) {
				chain.doFilter(request, response);
			} else {
				bloquearAcceso(httpResponse, "Solo las instalaciones pueden eliminar torneos.");
			}
			return;
		}

		if (tipoUsuario == null) {
			httpResponse.sendRedirect(contextPath + "/InicioSesion.jsp"); // Añade contexto al redirect
			return;
		}

		if (!tienePermisoSegunTipoUsuario(path, tipoUsuario)) {
			httpResponse.sendRedirect(contextPath + "/InicioSesion.jsp");
			return;
		}

		chain.doFilter(request, response);
	}

	/**
	 * Verifica si la URI solicitada es pública y no requiere autenticación.
	 *
	 * @param uri La URI solicitada.
	 * @return true si es una ruta pública, false en caso contrario.
	 */
	private boolean esRutaPublica(String uri) {
	    return uri.contains("InicioSesion.jsp") || uri.contains("Registrar.jsp") ||
	           uri.contains("PedirEmail.jsp") || uri.contains("RestablecerPassword.jsp") ||
	           uri.contains("Index.jsp") || uri.contains("/usuario") ||
	           uri.contains("/club") || uri.contains("/instalacion") || 
	           uri.contains("/Css") || uri.contains("/Imagenes/") ||
	           uri.contains("/login") || uri.contains("/torneo/bracket") ||
	           uri.contains("/recuperarPassword") || uri.contains("/restablecerPassword") || uri.contains("/DetallesTorneo.jsp");
	}


	
	/**
	 * Verifica si la solicitud es una petición DELETE sobre la entidad torneo.
	 *
	 * @param request La solicitud HTTP.
	 * @param uri     La URI solicitada.
	 * @return true si es una petición DELETE para /torneo, false en caso contrario.
	 */
	private boolean esPeticionDeleteTorneo(HttpServletRequest request, String uri) {
		return "DELETE".equals(request.getMethod()) && uri.contains("/torneo");
	}

	/**
	 * Bloquea el acceso a la ruta estableciendo el código 403 y un mensaje en la
	 * respuesta.
	 *
	 * @param response La respuesta HTTP.
	 * @param mensaje  El mensaje que se enviará al cliente.
	 * @throws IOException Si ocurre un error al escribir la respuesta.
	 */
	private void bloquearAcceso(HttpServletResponse response, String mensaje) throws IOException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.getWriter().write(mensaje);
	}

	/**
	 * Verifica si el tipo de usuario tiene permiso para acceder a la ruta
	 * solicitada.
	 *
	 * @param uri         La URI solicitada.
	 * @param tipoUsuario El tipo de usuario en sesión.
	 * @return true si tiene permiso, false en caso contrario.
	 */
    private boolean tienePermisoSegunTipoUsuario(String uri, String tipoUsuario) {
    	  if (uri.endsWith("MiClubJugador.jsp") && "jugador".equals(tipoUsuario)) {
              return true;
          }
        if (uri.endsWith("Jugador.jsp") || uri.endsWith("MarcadoresJugador.jsp")) {
            return "jugador".equals(tipoUsuario);
        }
        if (uri.endsWith("Club.jsp") || uri.endsWith("EventoClub.jsp") || uri.endsWith("PlantillaClub.jsp") || uri.endsWith("MarcadoresClub.jsp")) {
            return "club".equals(tipoUsuario);
        }
        if (uri.endsWith("PagoPremium.jsp")) {
            return "jugador".equals(tipoUsuario) || "club".equals(tipoUsuario);
        }
        if (uri.endsWith("Administrador.jsp")) {
            return "administrador".equals(tipoUsuario);
        }
        if (uri.endsWith("Instalacion.jsp") || uri.endsWith("EventoInstalacion.jsp")
                || uri.endsWith("TorneoInstalacion.jsp") || uri.endsWith("Acta.jsp")) {
            return "instalacion".equals(tipoUsuario) || "administrador".equals(tipoUsuario);
        }

        return true;
    }
	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void destroy() {
	}
}
