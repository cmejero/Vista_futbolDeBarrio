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
import vista_futbolDeBarrio.dtos.PartidoTorneoDto;
import vista_futbolDeBarrio.servicios.PartidoTorneoServicio;
import vista_futbolDeBarrio.log.Log;

@WebServlet("/partidoTorneo")
public class PartidoTorneoControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private PartidoTorneoServicio partidoServicio;

    @Override
    public void init() throws ServletException {
        this.partidoServicio = new PartidoTorneoServicio();
    }

    // ==============================
    // DO GET: LISTAR PARTIDOS
    // ==============================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idTorneoParam = request.getParameter("idTorneo");
            ArrayList<PartidoTorneoDto> listaPartidos;

            if (idTorneoParam != null && !idTorneoParam.isEmpty()) {
                Long idTorneo = Long.parseLong(idTorneoParam);
                listaPartidos = partidoServicio.listaPartidosPorTorneo(idTorneo);
                Log.ficheroLog("Listado de partidos para torneo id=" + idTorneo);
            } else {
                listaPartidos = partidoServicio.listaPartidos();
                Log.ficheroLog("Listado general de partidos");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(listaPartidos));

        } catch (Exception e) {
            Log.ficheroLog("Error en GET /partidoTorneo: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error en el servidor: " + e.getMessage());
        }
    }

    // ==============================
    // DO POST: GUARDAR NUEVO PARTIDO
    // ==============================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            String tipoUsuario = (session != null) ? (String) session.getAttribute("tipoUsuario") : null;

            if (tipoUsuario == null || tipoUsuario.isEmpty()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Debe iniciar sesión.");
                Log.ficheroLog("Intento de acceso no autorizado a POST /partidoTorneo");
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            PartidoTorneoDto partido = mapper.readValue(request.getInputStream(), PartidoTorneoDto.class);

            if (partido.getIdPartidoTorneo() == null) {
                partidoServicio.guardarPartido(partido);
                Log.ficheroLog("Partido guardado para torneo id=" + partido.getTorneoId());
            } else {
                partidoServicio.modificarPartido(partido.getIdPartidoTorneo(), partido);
                Log.ficheroLog("Partido modificado, id=" + partido.getIdPartidoTorneo());
            }

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Operación realizada correctamente.");

        } catch (Exception e) {
            Log.ficheroLog("Error en POST /partidoTorneo: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error en el servidor. Inténtelo más tarde.");
        }
    }

    // ==============================
    // DO DELETE: ELIMINAR PARTIDO
    // ==============================
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idParam = request.getParameter("idPartidoTorneo");
            if (idParam == null || idParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("ID de partido no proporcionado.");
                Log.ficheroLog("Eliminación fallida: ID no proporcionado");
                return;
            }

            Long idPartido = Long.parseLong(idParam);
            boolean eliminado = partidoServicio.eliminarPartido(idPartido);

            if (eliminado) {
                Log.ficheroLog("Partido eliminado, id=" + idPartido);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Partido eliminado correctamente.");
            } else {
                Log.ficheroLog("Error al eliminar partido, id=" + idPartido);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Error al eliminar el partido.");
            }

        } catch (Exception e) {
            Log.ficheroLog("Error en DELETE /partidoTorneo: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error en el servidor: " + e.getMessage());
        }
    }
}
