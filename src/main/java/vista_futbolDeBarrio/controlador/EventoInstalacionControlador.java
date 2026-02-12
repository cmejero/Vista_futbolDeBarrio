package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.TorneoDto;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.TorneoServicio;

@WebServlet("/instalacion/eventos")
/**
 * Controlador para gestionar los torneos desde la perspectiva de la instalación.
 * Permite listar, crear, modificar, activar y eliminar torneos.
 * Incluye trazabilidad mediante logs.
 */
public class EventoInstalacionControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private TorneoServicio torneoServicio;

    @Override
    /**
     * Inicializa el servicio de torneos.
     *
     * @throws ServletException Si ocurre un error durante la inicialización del servlet.
     */
    public void init() throws ServletException {
        torneoServicio = new TorneoServicio();
    }

    @Override
    /**
     * Maneja solicitudes GET para listar torneos o cargar la JSP.
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

            if (session == null || session.getAttribute("idInstalacion") == null) {
                Log.ficheroLog("EventoInstalacionControlador: acceso denegado, sesión nula o idInstalacion nulo");
                response.sendRedirect("login?error=accesoDenegado");
                return;
            }

            String ajaxHeader = request.getHeader("X-Requested-With");
            if ("XMLHttpRequest".equals(ajaxHeader)) {

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                Long idInstalacion = (Long) session.getAttribute("idInstalacion");
                List<TorneoDto> torneos = torneoServicio.obtenerTorneosPorInstalacion(idInstalacion);

                for (TorneoDto torneo : torneos) {
                    String progreso = torneoServicio.progresoEquipos(torneo.getIdTorneo());
                    torneo.setClubesInscritos(progreso);
                }

                String json = new Gson().toJson(torneos);
                response.getWriter().write(json);
                Log.ficheroLog("EventoInstalacionControlador: listado de torneos enviado para instalacionId=" + idInstalacion);
                return;
            }

            request.getRequestDispatcher("/WEB-INF/Vistas/EventoInstalacion.jsp")
                   .forward(request, response);
            Log.ficheroLog("EventoInstalacionControlador: JSP EventoInstalacion.jsp cargada correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("Error GET EventoInstalacionControlador: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error al cargar torneos\"}");
        }
    }

    @Override
    /**
     * Maneja solicitudes POST para crear, modificar o activar torneos.
     *
     * @param request La solicitud HTTP con los parámetros del torneo.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("token") == null) {
                Log.ficheroLog("EventoInstalacionControlador: intento de POST sin sesión activa o token inválido");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("{\"error\":\"No hay sesión activa o token inválido\"}");
                return;
            }

            String accion = request.getParameter("accion");

            if ("crear".equalsIgnoreCase(accion)) {
                String resultado = torneoServicio.crearTorneoDesdeFormulario(request);

                if ("ok".equals(resultado)) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("{\"mensaje\":\"Torneo creado correctamente\"}");
                    Log.ficheroLog("EventoInstalacionControlador: torneo creado correctamente");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\":\"Error creando torneo: " + resultado + "\"}");
                    Log.ficheroLog("EventoInstalacionControlador: error creando torneo: " + resultado);
                }

            } else if ("modificar".equalsIgnoreCase(accion)) {
                boolean exito = torneoServicio.modificarTorneoDesdeFormulario(request);
                if (exito) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("{\"mensaje\":\"Torneo modificado correctamente\"}");
                    Log.ficheroLog("EventoInstalacionControlador: torneo modificado correctamente");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\":\"No se pudo modificar el torneo\"}");
                    Log.ficheroLog("EventoInstalacionControlador: error al modificar torneo");
                }

            } else if ("activar".equalsIgnoreCase(accion)) {
                String resultado = torneoServicio.activarTorneoDesdeServicio(request);
                response.getWriter().write("{\"resultado\":\"" + resultado + "\"}");
                Log.ficheroLog("EventoInstalacionControlador: torneo activado resultado=" + resultado);

            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Acción no válida\"}");
                Log.ficheroLog("EventoInstalacionControlador: acción POST no válida: " + accion);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("Error POST EventoInstalacionControlador: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error en el servidor: " + e.getMessage() + "\"}");
        }
    }

    @Override
    /**
     * Maneja solicitudes DELETE para eliminar un torneo.
     *
     * @param request La solicitud HTTP con el torneo a eliminar.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            boolean eliminado = torneoServicio.eliminarTorneoDesdeServicio(request);
            if (eliminado) {
                response.getWriter().write("{\"mensaje\":\"Torneo eliminado correctamente\"}");
                Log.ficheroLog("EventoInstalacionControlador: torneo eliminado correctamente");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"No se pudo eliminar el torneo\"}");
                Log.ficheroLog("EventoInstalacionControlador: error al eliminar torneo");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("Error DELETE EventoInstalacionControlador: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error en el servidor: " + e.getMessage() + "\"}");
        }
    }
}
