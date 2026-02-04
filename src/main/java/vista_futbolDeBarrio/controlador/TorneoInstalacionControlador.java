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
import vista_futbolDeBarrio.servicios.ClubEstadisticaTorneoServicio;
import vista_futbolDeBarrio.servicios.JugadorEstadisticaTorneoServicio;

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // 1️⃣ Torneo desde sesión
        Long torneoId = (Long) session.getAttribute("torneoIdSeleccionado");

        if (torneoId == null) {
            response.sendRedirect(request.getContextPath() + "/login?error=torneoNoSeleccionado");
            return;
        }

        // 2️⃣ Petición JSON (fetch)
        String jsonParam = request.getParameter("json");
        if ("true".equalsIgnoreCase(jsonParam)) {
            try {
                // 🔥 USAMOS LOS MÉTODOS NUEVOS
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
                response.getWriter().write(mapper.writeValueAsString(resp));

            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"Error interno del servidor\"}");
            }
            return; // ⚠️ CLAVE
        }

        // 3️⃣ Forward normal al JSP
        request.setAttribute("torneoId", torneoId);
        request.getRequestDispatcher("/WEB-INF/Vistas/TorneoInstalacion.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recibir torneoId desde el formulario
        String torneoIdParam = request.getParameter("torneoId");
        if (torneoIdParam == null || torneoIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login?error=torneoNoSeleccionado");
            return;
        }

        try {
            Long torneoId = Long.parseLong(torneoIdParam);
            // Guardar en sesión
            request.getSession().setAttribute("torneoIdSeleccionado", torneoId);

            // Redirigir al GET sin parámetros
            response.sendRedirect(request.getContextPath() + "/instalacion/torneo");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/login?error=torneoIdInvalido");
        }
    }
}
