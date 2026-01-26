package vista_futbolDeBarrio.controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.servicios.MiembroClubServicio;

@WebServlet("/jugador/misClubes")
public class MiClubJugadorControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private MiembroClubServicio servicio;

    @Override
    public void init() throws ServletException {
        this.servicio = new MiembroClubServicio();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("token") == null) {
            response.sendRedirect(request.getContextPath() + "/login?error=accesoDenegado");
            return;
        }

        if (!"jugador".equals(session.getAttribute("tipoUsuario"))) {
            response.sendRedirect(request.getContextPath() + "/login?error=accesoDenegado");
            return;
        }

        Long usuarioId = (Long) session.getAttribute("usuarioId");
        Long clubId = request.getParameter("clubId") != null ? Long.parseLong(request.getParameter("clubId")) : null;

        String tipo = request.getParameter("tipo");

        // 1️⃣ tipo null -> JSP
        if (tipo == null) {
            request.getRequestDispatcher("/WEB-INF/Vistas/MiClubJugador.jsp")
                   .forward(request, response);
            return;
        }

        // 2️⃣ tipo=jugadores -> lista jugadores
        if ("jugadores".equalsIgnoreCase(tipo)) {
            String json = servicio.obtenerJugadoresDelClubEnJson(clubId);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            return;
        }

        // 3️⃣ tipo=json -> mis clubes
        if ("json".equalsIgnoreCase(tipo)) {
            String json = servicio.obtenerMisClubesEnJson(usuarioId, clubId);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            return;
        }

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("token") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            Long idMiembroClub = Long.parseLong(request.getParameter("idMiembroClub"));
            Long usuarioId = Long.parseLong(request.getParameter("usuarioId"));

            boolean ok = servicio.eliminarMiembroClubPorUsuario(request, idMiembroClub, usuarioId);

            if (ok) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
