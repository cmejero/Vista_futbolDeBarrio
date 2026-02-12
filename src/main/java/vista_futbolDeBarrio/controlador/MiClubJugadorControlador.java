package vista_futbolDeBarrio.controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.MiembroClubServicio;

/**
 * Controlador para la gestión de los clubes de un jugador.
 * Permite cargar la vista de los clubes del jugador, obtener jugadores de un club
 * en JSON, obtener los clubes del jugador en JSON y eliminar un miembro de un club.
 */
@WebServlet("/jugador/misClubes")
public class MiClubJugadorControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private MiembroClubServicio servicio;

    @Override
    public void init() throws ServletException {
        this.servicio = new MiembroClubServicio();
    }

    /**
     * Maneja las solicitudes GET.
     * Carga la vista JSP si no hay parámetros o devuelve JSON según el tipo.
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

            if (session == null || session.getAttribute("token") == null) {
                response.sendRedirect(request.getContextPath() + "/login?error=accesoDenegado");
                Log.ficheroLog("MiClubJugadorControlador: Acceso denegado (sin sesión o token)");
                return;
            }

            if (!"jugador".equals(session.getAttribute("tipoUsuario"))) {
                response.sendRedirect(request.getContextPath() + "/login?error=accesoDenegado");
                Log.ficheroLog("MiClubJugadorControlador: Acceso denegado (tipoUsuario no es jugador)");
                return;
            }

            Long usuarioId = (Long) session.getAttribute("usuarioId");
            Long clubId = request.getParameter("clubId") != null ? Long.parseLong(request.getParameter("clubId")) : null;
            String tipo = request.getParameter("tipo");

            if (tipo == null) {
                request.getRequestDispatcher("/WEB-INF/Vistas/MiClubJugador.jsp")
                       .forward(request, response);
                Log.ficheroLog("MiClubJugadorControlador: Cargada vista MiClubJugador.jsp para usuarioId=" + usuarioId);
                return;
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            if ("jugadores".equalsIgnoreCase(tipo)) {
                String json = servicio.obtenerJugadoresDelClubEnJson(clubId);
                response.getWriter().write(json);
                Log.ficheroLog("MiClubJugadorControlador: Enviada lista de jugadores para clubId=" + clubId);
                return;
            }

            if ("json".equalsIgnoreCase(tipo)) {
                String json = servicio.obtenerMisClubesEnJson(usuarioId, clubId);
                response.getWriter().write(json);
                Log.ficheroLog("MiClubJugadorControlador: Enviada lista de clubes para usuarioId=" + usuarioId);
                return;
            }

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Log.ficheroLog("MiClubJugadorControlador: Tipo inválido recibido: " + tipo);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Log.ficheroLog("MiClubJugadorControlador - Error en doGet: " + e.getMessage());
        }
    }

    /**
     * Maneja las solicitudes DELETE para eliminar un miembro de club.
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

            if (session == null || session.getAttribute("token") == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                Log.ficheroLog("MiClubJugadorControlador: DELETE denegado (sin sesión o token)");
                return;
            }

            Long idMiembroClub = Long.parseLong(request.getParameter("idMiembroClub"));
            Long usuarioId = Long.parseLong(request.getParameter("usuarioId"));

            boolean ok = servicio.eliminarMiembroClubPorUsuario(request, idMiembroClub, usuarioId);

            if (ok) {
                response.setStatus(HttpServletResponse.SC_OK);
                Log.ficheroLog("MiClubJugadorControlador: Miembro eliminado correctamente, idMiembroClub=" + idMiembroClub);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                Log.ficheroLog("MiClubJugadorControlador: No se pudo eliminar el miembro, idMiembroClub=" + idMiembroClub);
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Log.ficheroLog("MiClubJugadorControlador - Error en doDelete: " + e.getMessage());
        }
    }
}
