package vista_futbolDeBarrio.controlador;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.ClubEstadisticaGlobalServicio;
import vista_futbolDeBarrio.servicios.ClubEstadisticaTorneoServicio;
import vista_futbolDeBarrio.servicios.JugadorEstadisticaGlobalServicio;

/**
 * Controlador que maneja las peticiones de marcadores y estadísticas globales
 * de jugadores y clubes. Devuelve JSON para peticiones AJAX o muestra la vista
 * de Marcadores de Club.
 */
@WebServlet("/marcadores")
public class MarcadoresControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Servicios de estadísticas
    private ClubEstadisticaGlobalServicio clubGlobalServicio = new ClubEstadisticaGlobalServicio();
    private ClubEstadisticaTorneoServicio clubTorneoServicio = new ClubEstadisticaTorneoServicio();
    private JugadorEstadisticaGlobalServicio jugadorGlobalServicio = new JugadorEstadisticaGlobalServicio();

    /**
     * GET: Devuelve datos de estadísticas o muestra la vista Marcadores
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String tipo = request.getParameter("tipo");
            String idParam = request.getParameter("id");

            // Si no viene tipo -> mostrar JSP
            if (tipo == null) {
                request.getRequestDispatcher("/WEB-INF/Vistas/MarcadoresClub.jsp").forward(request, response);
                return;
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            ObjectMapper mapper = new ObjectMapper();

            switch (tipo) {
                case "jugadoresGlobal":
                    Log.ficheroLog("Obteniendo estadísticas globales de jugadores");
                    response.getWriter().write(
                        mapper.writeValueAsString(jugadorGlobalServicio.obtenerTodosJugadorEstadisticasGlobal())
                    );
                    break;

                case "clubesGlobal":
                    Log.ficheroLog("Obteniendo estadísticas globales de todos los clubes");
                    response.getWriter().write(
                        mapper.writeValueAsString(clubGlobalServicio.obtenerTodasClubEstadisticasGlobal())
                    );
                    break;

                case "clubGlobal":
                    Long clubIdGlobal = Long.parseLong(idParam);
                    Log.ficheroLog("Obteniendo estadísticas globales del club con ID: " + clubIdGlobal);
                    response.getWriter().write(
                        mapper.writeValueAsString(clubGlobalServicio.obtenerClubEstadisticasGlobal(clubIdGlobal))
                    );
                    break;

                case "clubTorneo":
                    Long clubIdTorneo = Long.parseLong(idParam);
                    Log.ficheroLog("Obteniendo estadísticas de torneo del club con ID: " + clubIdTorneo);
                    response.getWriter().write(
                        mapper.writeValueAsString(clubTorneoServicio.obtenerClubEstadisticasTorneo(clubIdTorneo))
                    );
                    break;

                default:
                    Log.ficheroLog("Tipo de marcador no válido recibido: " + tipo);
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\":\"tipo no válido\"}");
                    break;
            }

        } catch (NumberFormatException e) {
            Log.ficheroLog("ID inválido para obtener estadísticas: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"ID inválido\"}");

        } catch (Exception e) {
            Log.ficheroLog("Error al obtener estadísticas en MarcadoresControlador: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error interno del servidor\"}");
        }
    }
}
