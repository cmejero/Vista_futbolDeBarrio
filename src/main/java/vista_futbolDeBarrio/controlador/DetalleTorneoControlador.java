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
public class DetalleTorneoControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private JugadorEstadisticaTorneoServicio jugadorServicio;
    private ClubEstadisticaTorneoServicio clubServicio;

    @Override
    public void init() throws ServletException {
        jugadorServicio = new JugadorEstadisticaTorneoServicio();
        clubServicio = new ClubEstadisticaTorneoServicio();
    }

    // ---------------- GET ----------------
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        // ====== CARGA DE JSP ======
        if (accion == null || accion.isEmpty()) {
            HttpSession session = request.getSession(false);
            Long torneoId = null;
            if (session != null) {
                torneoId = (Long) session.getAttribute("torneoIdSeleccionado");
            }

            if (torneoId == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No se seleccionó ningún torneo");
                return;
            }

            request.setAttribute("torneoId", torneoId);
            request.getRequestDispatcher("/WEB-INF/Vistas/DetallesTorneo.jsp").forward(request, response);
            return;
        }

        // ====== RESPUESTA JSON ======
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String torneoIdParam = request.getParameter("torneoId");
            if (torneoIdParam == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Falta torneoId\"}");
                return;
            }
            Long torneoId = Long.valueOf(torneoIdParam);

            if ("jugadores".equals(accion)) {
                List<JugadorEstadisticaTorneoDto> lista = jugadorServicio.obtenerJugadoresPorTorneo(torneoId);
                response.getWriter().write(new Gson().toJson(lista));
                return;
            }

            if ("clubes".equals(accion)) {
                List<ClubEstadisticaTorneoDto> lista = clubServicio.obtenerClubesPorTorneo(torneoId);
                response.getWriter().write(new Gson().toJson(lista));
                return;
            }

            // Acción no válida
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Acción no válida\"}");

        } catch (Exception e) {
            Log.ficheroLog("Error DetalleTorneoControlador: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error interno\"}");
        }
    }

    // ---------------- POST ----------------
    @Override
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
                return;
            }

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Datos inválidos");

        } catch (Exception e) {
            Log.ficheroLog("Error POST DetalleTorneoControlador: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno");
        }
    }
}
