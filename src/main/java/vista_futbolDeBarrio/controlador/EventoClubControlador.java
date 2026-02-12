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
/**
 * Controlador para la gestión de eventos y torneos desde la perspectiva del club.
 * Permite listar torneos, consultar estado de inscripción y realizar inscripciones.
 * Incluye trazabilidad completa mediante logs.
 */
public class EventoClubControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private EquipoTorneoServicio servicio;
    private TorneoServicio torneoServicio;

    @Override
    /**
     * Inicializa los servicios de torneos y equipos inscritos.
     *
     * @throws ServletException Si ocurre un error durante la inicialización del servlet.
     */
    public void init() throws ServletException {
        this.servicio = new EquipoTorneoServicio();
        this.torneoServicio = new TorneoServicio(); 
    }

    @Override
    /**
     * Maneja solicitudes GET para listar torneos y mostrar la vista JSP.
     * Diferencia entre peticiones AJAX y navegación normal.
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
         

            String ajaxHeader = request.getHeader("X-Requested-With");

            if ("XMLHttpRequest".equals(ajaxHeader)) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                Long clubId = (Long) session.getAttribute("clubId");
                List<TorneoDto> torneos = torneoServicio.obtenerTodosLosTorneos();
                List<JSONObject> torneosJson = new ArrayList<>();

                for (TorneoDto torneo : torneos) {
                    String progreso = torneoServicio.progresoEquipos(torneo.getIdTorneo());
                    torneo.setClubesInscritos(progreso);

                    JSONObject obj = new JSONObject(new Gson().toJson(torneo));

                    boolean inscrito = servicio.estaInscrito(torneo.getIdTorneo(), clubId);
                    obj.put("inscrito", inscrito);

                    torneosJson.add(obj);
                }

                response.getWriter().write(torneosJson.toString());
                Log.ficheroLog("EventoClubControlador: listado de torneos enviado al clubId=" + clubId);
                return;
            }

            // 🚀 Carga de la JSP normal
            request.getRequestDispatcher("/WEB-INF/Vistas/EventoClub.jsp")
                   .forward(request, response);
            Log.ficheroLog("EventoClubControlador: JSP EventoClub.jsp cargada correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("Error GET EventoClubControlador: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error al cargar torneos\"}");
        }
    }

    @Override
    /**
     * Maneja solicitudes POST para inscribirse en un torneo.
     * Valida permisos, existencia previa de inscripción y cupos del torneo.
     *
     * @param request La solicitud HTTP con los parámetros del torneo.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);

            if (session == null) {
                Log.ficheroLog("EventoClubControlador: intento de inscripción sin sesión");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("No tienes permiso para inscribirte en el torneo.");
                return;
            }

            Long clubId = (Long) session.getAttribute("clubId");
            if (clubId == null) {
                Log.ficheroLog("EventoClubControlador: clubId nulo en sesión");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("No tienes permiso para inscribirte en el torneo.");
                return;
            }

            Long torneoId = Long.parseLong(request.getParameter("torneoId"));

            if (servicio.estaInscrito(torneoId, clubId)) {
                Log.ficheroLog("EventoClubControlador: clubId=" + clubId + " ya inscrito en torneoId=" + torneoId);
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("Ya estás inscrito en este torneo.");
                return;
            }

            if (!servicio.puedeInscribirse(torneoId)) {
                Log.ficheroLog("EventoClubControlador: torneoId=" + torneoId + " lleno, clubId=" + clubId);
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

            Log.ficheroLog("EventoClubControlador: clubId=" + clubId + " inscrito correctamente en torneoId=" + torneoId);
            response.getWriter().write("Inscripción realizada correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("Error POST EventoClubControlador: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al inscribirse en el torneo: " + e.getMessage());
        }
    }
}
