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
import vista_futbolDeBarrio.servicios.MiembroClubServicio;

@WebServlet("/club/plantilla")
public class PlantillaClubControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final MiembroClubServicio servicio = new MiembroClubServicio();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("clubId") == null) {
            response.sendRedirect("login?error=accesoDenegado");
            return;
        }

        Long clubId = (Long) session.getAttribute("clubId");
        String tipo = request.getParameter("tipo"); // "jugadores" para AJAX

        if ("jugadores".equalsIgnoreCase(tipo)) {
            try {
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


                    // ✅ Añadir idMiembroClub si existe
                    MiembroClubDto miembro = mapaMiembros.get(j.getJugadorGlobalId());
                    jugadorJson.put("idMiembroClub", miembro != null ? miembro.getIdMiembroClub() : null);
                    jugadorJson.put("miembroClub", miembro != null ? miembro : null);

                    resultado.add(jugadorJson);
                }

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                mapper.writeValue(response.getWriter(), resultado);

            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"Error al cargar jugadores\"}");
            }
            return;
        }

        // Caso normal: cargar JSP
        request.getRequestDispatcher("/WEB-INF/Vistas/PlantillaClub.jsp")
               .forward(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("clubId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Usuario no logueado.");
            return;
        }

        Long clubId = (Long) session.getAttribute("clubId");
        String idMiembroParam = request.getParameter("idMiembroClub");

        if (idMiembroParam == null || idMiembroParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("ID de miembro no proporcionado.");
            return;
        }

        try {
            Long idMiembro = Long.parseLong(idMiembroParam);

            boolean eliminado = servicio.eliminarMiembroClubPorClub(request, idMiembro, clubId);

            if (eliminado) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Miembro eliminado correctamente.");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("No se pudo eliminar el miembro.");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("ID de miembro inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al eliminar el miembro: " + e.getMessage());
        }
    }
}
