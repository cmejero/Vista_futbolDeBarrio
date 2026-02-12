package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

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

@WebServlet("/detalleTorneo")
/**
 * Controlador que maneja la visualización de los detalles de un torneo.
 * Permite obtener estadísticas de jugadores y clubes, y guardar el torneo seleccionado en sesión.
 * Incluye trazabilidad de acciones mediante logs.
 */
public class DetalleTorneoControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private JugadorEstadisticaTorneoServicio jugadorServicio;
    private ClubEstadisticaTorneoServicio clubServicio;

    @Override
    /**
     * Inicializa los servicios de estadísticas de jugadores y clubes.
     *
     * @throws ServletException Si ocurre un error durante la inicialización del servlet.
     */
    public void init() throws ServletException {
        jugadorServicio = new JugadorEstadisticaTorneoServicio();
        clubServicio = new ClubEstadisticaTorneoServicio();
    }

    // ---------------- GET ----------------
    @Override
    /**
     * Gestiona las solicitudes GET para la vista de detalles de torneo.
     * Puede devolver datos JSON (jugadores o clubes) o cargar la JSP de detalles.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String accion = request.getParameter("accion");

            // ====== CARGA DE JSP ======
            if (accion == null || accion.isEmpty()) {
                HttpSession session = request.getSession(false);
                Long torneoId = null;
                if (session != null) {
                    torneoId = (Long) session.getAttribute("torneoIdSeleccionado");
                }

                if (torneoId == null) {
                    Log.ficheroLog("DetalleTorneoControlador: no se seleccionó ningún torneo en sesión");
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No se seleccionó ningún torneo");
                    return;
                }

                request.setAttribute("torneoId", torneoId);
                request.getRequestDispatcher("/WEB-INF/Vistas/DetallesTorneo.jsp").forward(request, response);

                Log.ficheroLog("DetalleTorneoControlador: JSP DetallesTorneo.jsp cargada, torneoId=" + torneoId);
                return;
            }

            // ====== RESPUESTA JSON ======
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String torneoIdParam = request.getParameter("torneoId");
            if (torneoIdParam == null) {
                Log.ficheroLog("DetalleTorneoControlador: falta parametro torneoId para acción=" + accion);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Falta torneoId\"}");
                return;
            }
            Long torneoId = Long.valueOf(torneoIdParam);

            if ("jugadores".equals(accion)) {
                List<JugadorEstadisticaTorneoDto> lista = jugadorServicio.obtenerJugadoresPorTorneo(torneoId);
                response.getWriter().write(new Gson().toJson(lista));
                Log.ficheroLog("DetalleTorneoControlador: lista de jugadores enviada, torneoId=" + torneoId);
                return;
            }

            if ("clubes".equals(accion)) {
                List<ClubEstadisticaTorneoDto> lista = clubServicio.obtenerClubesPorTorneo(torneoId);
                response.getWriter().write(new Gson().toJson(lista));
                Log.ficheroLog("DetalleTorneoControlador: lista de clubes enviada, torneoId=" + torneoId);
                return;
            }

            // Acción no válida
            Log.ficheroLog("DetalleTorneoControlador: acción no válida recibida=" + accion + ", torneoId=" + torneoId);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Acción no válida\"}");

        } catch (Exception e) {
            Log.ficheroLog("Error GET DetalleTorneoControlador: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error interno\"}");
        }
    }

    // ---------------- POST ----------------
    @Override
    /**
     * Guarda el torneo seleccionado en sesión.
     *
     * @param request La solicitud HTTP con el JSON del torneo seleccionado.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> data = mapper.readValue(request.getReader(), Map.class);

            if (data.containsKey("torneoIdSeleccionado")) {
                Long torneoId = Long.valueOf(data.get("torneoIdSeleccionado").toString());
                request.getSession().setAttribute("torneoIdSeleccionado", torneoId);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Torneo guardado en sesión");
                Log.ficheroLog("DetalleTorneoControlador: torneoId=" + torneoId + " guardado en sesión");
                return;
            }

            Log.ficheroLog("DetalleTorneoControlador: POST recibido con datos inválidos: " + data);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Datos inválidos");

        } catch (Exception e) {
            Log.ficheroLog("Error POST DetalleTorneoControlador: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno");
        }
    }
}
