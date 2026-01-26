package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vista_futbolDeBarrio.dtos.MiembroClubDto;
import vista_futbolDeBarrio.servicios.ClubEstadisticaGlobalServicio;
import vista_futbolDeBarrio.servicios.JugadorEstadisticaGlobalServicio;
import vista_futbolDeBarrio.servicios.JugadorEstadisticaTorneoServicio;
import vista_futbolDeBarrio.servicios.MiembroClubServicio;

@WebServlet("/jugador/marcadores")
public class MarcadoresJugadorControlador extends HttpServlet {
    private static final long serialVersionUID = 1L;


    private final ObjectMapper mapper = new ObjectMapper();

    private final JugadorEstadisticaGlobalServicio jugadorGlobalServicio = new JugadorEstadisticaGlobalServicio();
    private final ClubEstadisticaGlobalServicio clubGlobalServicio = new ClubEstadisticaGlobalServicio();
    private final MiembroClubServicio miembroClubServicio = new MiembroClubServicio();
    private final JugadorEstadisticaTorneoServicio jugadorTorneoServicio = new JugadorEstadisticaTorneoServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1) Si no llega "tipo", se carga la página JSP
        String tipo = request.getParameter("tipo");

        if (tipo == null || tipo.isEmpty()) {
            request.getRequestDispatcher("/WEB-INF/Vistas/MarcadoresJugador.jsp")
                    .forward(request, response);
            return;
        }

        // 2) Si llega tipo, devolvemos JSON según el valor
        switch (tipo) {

            case "jugadorEstadisticaGlobal":
                String id = request.getParameter("id");
                if (id != null && !id.isEmpty()) {
                    Long jugadorId = Long.parseLong(id);
                    var dto = jugadorGlobalServicio.obtenerJugadorEstadisticasGlobal(jugadorId);
                    response.setContentType("application/json");
                    response.getWriter().write(mapper.writeValueAsString(dto));
                } else {
                    List<?> lista = jugadorGlobalServicio.obtenerTodosJugadorEstadisticasGlobal();
                    response.setContentType("application/json");
                    response.getWriter().write(mapper.writeValueAsString(lista));
                }
                break;

            case "clubEstadisticaGlobal":
                List<?> clubs = clubGlobalServicio.obtenerTodasClubEstadisticasGlobal();
                response.setContentType("application/json");
                response.getWriter().write(mapper.writeValueAsString(clubs));
                break;

            case "miembroClub":
                if ("json".equals(request.getParameter("tipoJson"))) {
                    Long usuarioId = Long.parseLong(request.getParameter("usuarioId"));
                    List<?> listaMiembros = miembroClubServicio.listarMisClubesPorUsuario(usuarioId);
                    response.setContentType("application/json");
                    response.getWriter().write(mapper.writeValueAsString(listaMiembros));
                }
                break;

            case "jugadorEstadisticaTorneo":
                Long jugadorIdTorneo = Long.parseLong(request.getParameter("id"));
                var torneos = jugadorTorneoServicio.obtenerJugadorEstadisticasTorneoPorJugadorId(jugadorIdTorneo);
                response.setContentType("application/json");
                response.getWriter().write(mapper.writeValueAsString(torneos));
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("Te has unido al club exitosamente.");
                } catch (IllegalStateException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write(e.getMessage());
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("Error del servidor.");
                }
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("tipo inválido");
        }
    }

}
