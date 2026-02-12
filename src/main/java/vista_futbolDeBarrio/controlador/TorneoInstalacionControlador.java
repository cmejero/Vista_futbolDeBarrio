package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.ClubEstadisticaTorneoDto;
import vista_futbolDeBarrio.dtos.JugadorEstadisticaTorneoDto;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.ClubEstadisticaTorneoServicio;
import vista_futbolDeBarrio.servicios.JugadorEstadisticaTorneoServicio;

/**
 * Controlador para la visualización de torneos en la instalación.
 * Permite mostrar la página JSP del torneo y enviar estadísticas de
 * jugadores y clubes vía JSON.
 */
@WebServlet("/instalacion/torneo")
public class TorneoInstalacionControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private JugadorEstadisticaTorneoServicio jugadorServicio;
    private ClubEstadisticaTorneoServicio clubServicio;

    @Override
    public void init() throws ServletException {
        jugadorServicio = new JugadorEstadisticaTorneoServicio();
        clubServicio = new ClubEstadisticaTorneoServicio();
    }

    /**
     * Maneja la carga de la página JSP y/o la respuesta JSON con estadísticas.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	 HttpSession session = request.getSession(false);

         // 🔐 Seguridad básica
         if (session == null || session.getAttribute("token") == null) {
             Log.ficheroLog("EventoJugadorControlador: intento de acceso sin sesión o token inválido");
             response.sendRedirect(request.getContextPath() + "/login?error=accesoDenegado");
             return;
         }

        try {
            Long torneoId = (Long) session.getAttribute("torneoIdSeleccionado");

            if (torneoId == null) {
                Log.ficheroLog("[WARN] No hay torneo seleccionado → redirigiendo a login");
                response.sendRedirect(request.getContextPath() + "/login?error=torneoNoSeleccionado");
                return;
            }

            // Petición JSON (fetch)
            String jsonParam = request.getParameter("json");
            if ("true".equalsIgnoreCase(jsonParam)) {
                try {
                    ArrayList<JugadorEstadisticaTorneoDto> jugadores =
                            jugadorServicio.obtenerJugadoresPorTorneo(torneoId);

                    ArrayList<ClubEstadisticaTorneoDto> clubes =
                            clubServicio.obtenerClubesPorTorneo(torneoId);

                    Map<String, Object> resp = new HashMap<>();
                    resp.put("jugadores", jugadores);
                    resp.put("clubes", clubes);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(response.getWriter(), resp);

                    Log.ficheroLog("[INFO] JSON de estadísticas enviado para torneoId=" + torneoId);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.ficheroLog("[ERROR] Error al generar JSON para torneoId=" + torneoId + " → " + e.getMessage());
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("{\"error\":\"Error interno del servidor\"}");
                }
                return; // Importante
            }

            // Forward normal al JSP
            request.setAttribute("torneoId", torneoId);
            request.getRequestDispatcher("/WEB-INF/Vistas/TorneoInstalacion.jsp")
                   .forward(request, response);

            Log.ficheroLog("[INFO] JSP TorneoInstalacion.jsp cargado para torneoId=" + torneoId);

        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("[ERROR] Excepción en TorneoInstalacionControlador doGet: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/login?error=error_servidor");
        }
    }

    /**
     * Guarda el torneo seleccionado en sesión desde el formulario.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String torneoIdParam = request.getParameter("torneoId");

        if (torneoIdParam == null || torneoIdParam.isEmpty()) {
            Log.ficheroLog("[WARN] Torneo no proporcionado en POST → redirigiendo a login");
            response.sendRedirect(request.getContextPath() + "/login?error=torneoNoSeleccionado");
            return;
        }

        try {
            Long torneoId = Long.parseLong(torneoIdParam);
            request.getSession().setAttribute("torneoIdSeleccionado", torneoId);

            Log.ficheroLog("[INFO] TorneoId guardado en sesión: " + torneoId);

            response.sendRedirect(request.getContextPath() + "/instalacion/torneo");

        } catch (NumberFormatException e) {
            Log.ficheroLog("[WARN] TorneoId inválido en POST: " + torneoIdParam);
            response.sendRedirect(request.getContextPath() + "/login?error=torneoIdInvalido");
        }
    }
}
