package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.ClubDto;
import vista_futbolDeBarrio.dtos.ClubEstadisticaGlobalDto;
import vista_futbolDeBarrio.servicios.ClubEstadisticaGlobalServicio;
import vista_futbolDeBarrio.servicios.ClubServicio;
import vista_futbolDeBarrio.log.Log;

@WebServlet("/club")
/**
 * Controlador para la gestión de la vista de Club.
 * Permite obtener información del club y sus estadísticas globales.
 * Incluye trazabilidad de acciones mediante logs.
 */
public class ClubControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ClubServicio clubServicio = new ClubServicio();
    private ClubEstadisticaGlobalServicio estadisticaServicio = new ClubEstadisticaGlobalServicio();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    /**
     * Maneja solicitudes GET para la vista del club.
     * Puede devolver datos JSON para peticiones AJAX o cargar la vista JSP.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws IOException Si ocurre un error de entrada/salida.
     * @throws ServletException Si ocurre un error en el servlet.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        try {
        	 HttpSession session = request.getSession(false);

             // 🔐 Seguridad básica
             if (session == null || session.getAttribute("token") == null) {
                 Log.ficheroLog("EventoJugadorControlador: intento de acceso sin sesión o token inválido");
                 response.sendRedirect(request.getContextPath() + "/login?error=accesoDenegado");
                 return;
             }

            if (!"club".equals(session.getAttribute("tipoUsuario"))) {
                Log.ficheroLog("ClubControlador: intento de acceso de usuario no club, tipo=" + session.getAttribute("tipoUsuario"));
                response.sendRedirect("login?error=accesoDenegado");
                return;
            }

            Long clubId = (Long) session.getAttribute("clubId");
            if (clubId == null) {
                Log.ficheroLog("ClubControlador: clubId nulo en sesión");
                response.sendRedirect("login?error=accesoDenegado");
                return;
            }

            String accion = request.getParameter("accion");

            // 📦 PETICIÓN DE DATOS (AJAX)
            if ("datos".equals(accion)) {
                ClubDto club = clubServicio.obtenerClubPorId(clubId, request);
                ClubEstadisticaGlobalDto estadisticas = estadisticaServicio.obtenerClubEstadisticasGlobal(clubId);

                if (club == null || estadisticas == null) {
                    Log.ficheroLog("ClubControlador: error al obtener club o estadísticas, clubId=" + clubId);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    return;
                }

                Map<String, Object> resultado = new HashMap<>();
                resultado.put("club", club);
                resultado.put("estadisticas", estadisticas);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                mapper.writeValue(response.getWriter(), resultado);

                Log.ficheroLog("ClubControlador: datos del club enviados correctamente, clubId=" + clubId);
                return;
            }

            // 🚀 Cargar vista JSP
            request.getRequestDispatcher("/WEB-INF/Vistas/Club.jsp")
                   .forward(request, response);

            Log.ficheroLog("ClubControlador: vista Club.jsp cargada, clubId=" + clubId);

        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("ClubControlador: error en doGet clubId=" + 
                            (request.getSession(false) != null ? request.getSession(false).getAttribute("clubId") : "null") + 
                            ", mensaje=" + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error del servidor");
        }
    }
}
