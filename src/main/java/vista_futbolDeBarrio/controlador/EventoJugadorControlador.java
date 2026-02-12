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
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.EquipoTorneoServicio;

@WebServlet("/jugador/eventos")
/**
 * Controlador para gestionar los torneos desde la perspectiva de un jugador.
 * Permite listar los torneos en los que participa un jugador.
 * Incluye trazabilidad mediante logs.
 */
public class EventoJugadorControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private EquipoTorneoServicio servicio;

    @Override
    /**
     * Inicializa el servicio de equipos/torneos.
     *
     * @throws ServletException Si ocurre un error durante la inicialización del servlet.
     */
    public void init() throws ServletException {
        this.servicio = new EquipoTorneoServicio();
    }

    @Override
    /**
     * Maneja solicitudes GET para listar torneos de un jugador o cargar la JSP.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);

            // 🔐 Seguridad básica
            if (session == null || session.getAttribute("token") == null) {
                Log.ficheroLog("EventoJugadorControlador: intento de acceso sin sesión o token inválido");
                response.sendRedirect(request.getContextPath() + "/login?error=accesoDenegado");
                return;
            }

            if (!"jugador".equals(session.getAttribute("tipoUsuario"))) {
                Log.ficheroLog("EventoJugadorControlador: acceso denegado a usuario tipo=" + session.getAttribute("tipoUsuario"));
                response.sendRedirect(request.getContextPath() + "/login?error=accesoDenegado");
                return;
            }

            // Si no viene usuarioId -> mostrar JSP
            String jugadorIdParam = request.getParameter("usuarioId");
            if (jugadorIdParam == null) {
                request.getRequestDispatcher("/WEB-INF/Vistas/EventoJugador.jsp")
                        .forward(request, response);
                Log.ficheroLog("EventoJugadorControlador: JSP EventoJugador.jsp cargada correctamente");
                return;
            }

            Long jugadorId;
            try {
                jugadorId = Long.parseLong(jugadorIdParam);
            } catch (NumberFormatException e) {
                Log.ficheroLog("EventoJugadorControlador: ID de usuario inválido: " + jugadorIdParam);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("ID de usuario inválido.");
                return;
            }

            ArrayList<TorneoDto> torneos = servicio.obtenerTorneosPorUsuario(jugadorId);

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(torneos);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);

            Log.ficheroLog("EventoJugadorControlador: listado de torneos enviado para jugadorId=" + jugadorId);

        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("Error EventoJugadorControlador: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al obtener torneos: " + e.getMessage());
        }
    }
}
