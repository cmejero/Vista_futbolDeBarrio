package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.TorneoDto;
import vista_futbolDeBarrio.servicios.EquipoTorneoServicio;

@WebServlet("/jugador/eventos")
public class EventoJugadorControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private EquipoTorneoServicio servicio;

    @Override
    public void init() throws ServletException {
        this.servicio = new EquipoTorneoServicio();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // 🔐 Seguridad básica
        if (session == null || session.getAttribute("token") == null) {
            response.sendRedirect(request.getContextPath() + "/login?error=accesoDenegado");
            return;
        }

        if (!"jugador".equals(session.getAttribute("tipoUsuario"))) {
            response.sendRedirect(request.getContextPath() + "/login?error=accesoDenegado");
            return;
        }

        // Si no viene usuarioId -> mostrar JSP
        String clubIdParam = request.getParameter("clubId");
        if (clubIdParam == null) {
            request.getRequestDispatcher("/WEB-INF/Vistas/EventoClub.jsp")
                    .forward(request, response);
            return;
        }

        try {
            Long clubId = Long.parseLong(clubIdParam);

            ArrayList<TorneoDto> torneos =
                    servicio.obtenerTorneosPorUsuario(clubId);

            // Convertir a JSON igual que tu controlador antiguo
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(torneos);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("ID de usuario inválido.");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al obtener torneos: " + e.getMessage());
        }
    }
}
