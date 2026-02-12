package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.dtos.MiembroClubDto;
import vista_futbolDeBarrio.servicios.ClubEstadisticaGlobalServicio;
import vista_futbolDeBarrio.servicios.JugadorEstadisticaGlobalServicio;
import vista_futbolDeBarrio.servicios.JugadorEstadisticaTorneoServicio;
import vista_futbolDeBarrio.servicios.MiembroClubServicio;

/**
 * Controlador que maneja las peticiones de marcadores y estadísticas
 * para jugadores. Puede devolver estadísticas globales, estadísticas
 * de torneos, clubes y miembros de clubes. Soporta tanto solicitudes
 * AJAX como acciones POST para unirse a clubes.
 */
@WebServlet("/jugador/marcadores")
public class MarcadoresJugadorControlador extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ObjectMapper mapper = new ObjectMapper();

    private final JugadorEstadisticaGlobalServicio jugadorGlobalServicio = new JugadorEstadisticaGlobalServicio();
    private final ClubEstadisticaGlobalServicio clubGlobalServicio = new ClubEstadisticaGlobalServicio();
    private final MiembroClubServicio miembroClubServicio = new MiembroClubServicio();
    private final JugadorEstadisticaTorneoServicio jugadorTorneoServicio = new JugadorEstadisticaTorneoServicio();

    /**
     * Maneja las solicitudes GET para obtener estadísticas de jugadores,
     * clubes o miembros, o carga la vista JSP si no se especifica tipo.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String tipo = request.getParameter("tipo");
            
            HttpSession session = request.getSession(false);

            // 🔐 Seguridad básica
            if (session == null || session.getAttribute("token") == null) {
                Log.ficheroLog("EventoJugadorControlador: intento de acceso sin sesión o token inválido");
                response.sendRedirect(request.getContextPath() + "/login?error=accesoDenegado");
                return;
            }

            // Si no llega "tipo", se carga la página JSP
            if (tipo == null || tipo.isEmpty()) {
                request.getRequestDispatcher("/WEB-INF/Vistas/MarcadoresJugador.jsp")
                        .forward(request, response);
                return;
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            switch (tipo) {
                case "jugadorEstadisticaGlobal":
                    String id = request.getParameter("id");
                    if (id != null && !id.isEmpty()) {
                        Long jugadorId = Long.parseLong(id);
                        Log.ficheroLog("Obteniendo estadísticas globales del jugador ID: " + jugadorId);
                        var dto = jugadorGlobalServicio.obtenerJugadorEstadisticasGlobal(jugadorId);
                        response.getWriter().write(mapper.writeValueAsString(dto));
                    } else {
                        Log.ficheroLog("Obteniendo estadísticas globales de todos los jugadores");
                        List<?> lista = jugadorGlobalServicio.obtenerTodosJugadorEstadisticasGlobal();
                        response.getWriter().write(mapper.writeValueAsString(lista));
                    }
                    break;

                case "clubEstadisticaGlobal":
                    Log.ficheroLog("Obteniendo estadísticas globales de todos los clubes");
                    List<?> clubs = clubGlobalServicio.obtenerTodasClubEstadisticasGlobal();
                    response.getWriter().write(mapper.writeValueAsString(clubs));
                    break;

                case "miembroClub":
                    if ("json".equals(request.getParameter("tipoJson"))) {
                        Long usuarioId = Long.parseLong(request.getParameter("usuarioId"));
                        Log.ficheroLog("Obteniendo lista de clubes del usuario ID: " + usuarioId);
                        List<?> listaMiembros = miembroClubServicio.listarMisClubesPorUsuario(usuarioId);
                        response.getWriter().write(mapper.writeValueAsString(listaMiembros));
                    }
                    break;

                case "jugadorEstadisticaTorneo":
                    Long jugadorIdTorneo = Long.parseLong(request.getParameter("id"));
                    Log.ficheroLog("Obteniendo estadísticas de torneos para jugador ID: " + jugadorIdTorneo);
                    var torneos = jugadorTorneoServicio.obtenerJugadorEstadisticasTorneoPorJugadorId(jugadorIdTorneo);
                    response.getWriter().write(mapper.writeValueAsString(torneos));
                    break;

                default:
                    Log.ficheroLog("Tipo de marcador no válido: " + tipo);
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }

        } catch (NumberFormatException e) {
            Log.ficheroLog("ID inválido recibido: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"ID inválido\"}");
        } catch (Exception e) {
            Log.ficheroLog("Error en MarcadoresJugadorControlador GET: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error interno del servidor\"}");
        }
    }

    /**
     * Maneja las solicitudes POST para unirse a un club.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String tipo = request.getParameter("tipo");

            if ("miembroClub".equals(tipo)) {
                String accion = request.getParameter("accion");

                if ("aniadir".equals(accion)) {
                    long clubId = Long.parseLong(request.getParameter("idClub"));
                    long usuarioId = Long.parseLong(request.getParameter("usuarioId"));

                    MiembroClubDto miembro = new MiembroClubDto();
                    miembro.setIdClub(clubId);
                    miembro.setUsuarioId(usuarioId);
                    miembro.setFechaAltaUsuario(java.time.LocalDate.now().toString());
                    miembro.setFechaBajaUsuario("9999-01-01");

                    try {
                        miembroClubServicio.guardarMiembroClub(request, miembro);
                        Log.ficheroLog("Usuario ID " + usuarioId + " se unió al club ID " + clubId);
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.getWriter().write("Te has unido al club exitosamente.");
                    } catch (IllegalStateException e) {
                        Log.ficheroLog("Error al unirse al club: " + e.getMessage());
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().write(e.getMessage());
                    } catch (Exception e) {
                        Log.ficheroLog("Error interno al unirse al club: " + e.getMessage());
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.getWriter().write("Error del servidor.");
                    }
                }

            } else {
                Log.ficheroLog("POST recibido con tipo inválido: " + tipo);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("tipo inválido");
            }

        } catch (NumberFormatException e) {
            Log.ficheroLog("ID inválido recibido en POST: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"ID inválido\"}");
        } catch (Exception e) {
            Log.ficheroLog("Error en MarcadoresJugadorControlador POST: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error interno del servidor\"}");
        }
    }
}
