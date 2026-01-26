package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.EquipoTorneoDto;
import vista_futbolDeBarrio.dtos.TorneoDto;
import vista_futbolDeBarrio.enums.EstadoParticipacion;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.EquipoTorneoServicio;
import vista_futbolDeBarrio.servicios.TorneoServicio;

@WebServlet("/club/eventos")
public class EventoClubControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private EquipoTorneoServicio servicio;
    private TorneoServicio torneoServicio;

    @Override
    public void init() throws ServletException {
        this.servicio = new EquipoTorneoServicio();
        this.torneoServicio = new TorneoServicio(); 

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || !"club".equals(session.getAttribute("tipoUsuario"))) {
            response.sendRedirect("InicioSesion.jsp?error=accesoDenegado");
            return;
        }

        // Si es AJAX
        String ajaxHeader = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(ajaxHeader)) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            try {
                Long clubId = (Long) session.getAttribute("clubId");

                List<TorneoDto> torneos = torneoServicio.obtenerTodosLosTorneos();

                List<JSONObject> torneosJson = new ArrayList<>();

                for (TorneoDto torneo : torneos) {
                    String progreso = torneoServicio.progresoEquipos(torneo.getIdTorneo());
                    torneo.setClubesInscritos(progreso);

                    JSONObject obj = new JSONObject(new Gson().toJson(torneo));

                    // Si quieres, añade un campo extra de "inscrito"
                    obj.put("estaInscrito", servicio.estaInscrito(torneo.getIdTorneo(), clubId));

                    torneosJson.add(obj);
                }

                response.getWriter().write(torneosJson.toString());

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Error al cargar torneos");
            }
            return;
        }

        request.getRequestDispatcher("/WEB-INF/Vistas/EventoClub.jsp")
               .forward(request, response);
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();
            Long clubId = (Long) session.getAttribute("clubId");

            if (clubId == null) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("No tienes permiso para inscribirte en el torneo.");
                return;
            }

            Long torneoId = Long.parseLong(request.getParameter("torneoId"));

            if (servicio.estaInscrito(torneoId, clubId)) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("Ya estás inscrito en este torneo.");
                return;
            }

            if (!servicio.puedeInscribirse(torneoId)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("El torneo está lleno.");
                return;
            }

            EquipoTorneoDto equipoTorneo = new EquipoTorneoDto();
            equipoTorneo.setClubId(clubId);
            equipoTorneo.setTorneoId(torneoId);
            equipoTorneo.setFechaInicioParticipacion(java.time.LocalDate.now().toString());
            equipoTorneo.setFechaFinParticipacion("9999-01-01");
            equipoTorneo.setEstadoParticipacion(EstadoParticipacion.Activo);

            servicio.guardarEquipoTorneo(equipoTorneo, request);

            Log.ficheroLog("Club " + clubId + " inscrito correctamente en torneo " + torneoId);
            response.getWriter().write("Inscripción realizada correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("Error al inscribirse en torneo: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al inscribirse en el torneo: " + e.getMessage());
        }
    }
}
