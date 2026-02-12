package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.JugadorEstadisticaGlobalDto;
import vista_futbolDeBarrio.dtos.MiembroClubDto;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.MiembroClubServicio;

/**
 * Controlador para la gestión de la plantilla de un club.
 * Permite cargar la vista JSP, listar jugadores con estadísticas y eliminar miembros.
 */
@WebServlet("/club/plantilla")
public class PlantillaClubControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final MiembroClubServicio servicio = new MiembroClubServicio();
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Maneja solicitudes GET.
     * Carga la vista de plantilla o devuelve la lista de jugadores en JSON.
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
        	 HttpSession session = request.getSession(false);

             // 🔐 Seguridad básica
             if (session == null || session.getAttribute("token") == null) {
                 Log.ficheroLog("EventoJugadorControlador: intento de acceso sin sesión o token inválido");
                 response.sendRedirect(request.getContextPath() + "/login?error=accesoDenegado");
                 return;
             }
            if (session == null || session.getAttribute("clubId") == null) {
                response.sendRedirect("login?error=accesoDenegado");
                Log.ficheroLog("PlantillaClubControlador: Acceso denegado (sin sesión o clubId)");
                return;
            }

            Long clubId = (Long) session.getAttribute("clubId");
            String tipo = request.getParameter("tipo"); // "jugadores" para AJAX

            if ("jugadores".equalsIgnoreCase(tipo)) {
                // 1️⃣ Obtener jugadores y miembros
                List<JugadorEstadisticaGlobalDto> jugadores = servicio.listarJugadoresPorClub(clubId);
                List<MiembroClubDto> miembros = servicio.listarMiembrosClub(clubId);

                Map<Long, MiembroClubDto> mapaMiembros = miembros.stream()
                        .collect(Collectors.toMap(MiembroClubDto::getUsuarioId, m -> m));

                // 2️⃣ Construir JSON para cada jugador
                List<Map<String, Object>> resultado = new ArrayList<>();
                for (JugadorEstadisticaGlobalDto j : jugadores) {
                    Map<String, Object> jugadorJson = new HashMap<>();
                    jugadorJson.put("nombreJugador", j.getNombreJugador());
                    jugadorJson.put("aliasJugador", j.getAliasJugador());
                    jugadorJson.put("partidosJugadosGlobal", j.getPartidosJugadosGlobal());
                    jugadorJson.put("partidosGanadosGlobal", j.getPartidosGanadosGlobal());
                    jugadorJson.put("partidosPerdidosGlobal", j.getPartidosPerdidosGlobal());
                    jugadorJson.put("golesGlobal", j.getGolesGlobal());
                    jugadorJson.put("asistenciasGlobal", j.getAsistenciasGlobal());
                    jugadorJson.put("amarillasGlobal", j.getAmarillasGlobal());
                    jugadorJson.put("rojasGlobal", j.getRojasGlobal());
                    jugadorJson.put("minutosJugadosGlobal", j.getMinutosJugadosGlobal());
                    jugadorJson.put("usuarioId", j.getJugadorGlobalId());
                    jugadorJson.put("jugadorGlobalId", j.getJugadorGlobalId());

                    MiembroClubDto miembro = mapaMiembros.get(j.getJugadorGlobalId());
                    jugadorJson.put("idMiembroClub", miembro != null ? miembro.getIdMiembroClub() : null);
                    jugadorJson.put("miembroClub", miembro != null ? miembro : null);

                    resultado.add(jugadorJson);
                }

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                mapper.writeValue(response.getWriter(), resultado);
                Log.ficheroLog("PlantillaClubControlador: Lista de jugadores enviada, clubId=" + clubId);
                return;
            }

            // Caso normal: cargar JSP
            request.getRequestDispatcher("/WEB-INF/Vistas/PlantillaClub.jsp").forward(request, response);
            Log.ficheroLog("PlantillaClubControlador: Cargada vista PlantillaClub.jsp, clubId=" + clubId);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error al cargar jugadores\"}");
            Log.ficheroLog("PlantillaClubControlador - Error en doGet: " + e.getMessage());
        }
    }

    /**
     * Maneja solicitudes DELETE.
     * Permite eliminar un miembro de la plantilla del club.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("clubId") == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Usuario no logueado.");
                Log.ficheroLog("PlantillaClubControlador: DELETE denegado (sin sesión o clubId)");
                return;
            }

            Long clubId = (Long) session.getAttribute("clubId");
            String idMiembroParam = request.getParameter("idMiembroClub");

            if (idMiembroParam == null || idMiembroParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("ID de miembro no proporcionado.");
                Log.ficheroLog("PlantillaClubControlador: DELETE rechazado (idMiembroClub nulo)");
                return;
            }

            Long idMiembro = Long.parseLong(idMiembroParam);
            boolean eliminado = servicio.eliminarMiembroClubPorClub(request, idMiembro, clubId);

            if (eliminado) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Miembro eliminado correctamente.");
                Log.ficheroLog("PlantillaClubControlador: Miembro eliminado, idMiembro=" + idMiembro + ", clubId=" + clubId);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("No se pudo eliminar el miembro.");
                Log.ficheroLog("PlantillaClubControlador: Falló eliminación de miembro, idMiembro=" + idMiembro);
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("ID de miembro inválido.");
            Log.ficheroLog("PlantillaClubControlador: ID de miembro inválido - " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al eliminar el miembro: " + e.getMessage());
            Log.ficheroLog("PlantillaClubControlador - Error en doDelete: " + e.getMessage());
        }
    }
}
