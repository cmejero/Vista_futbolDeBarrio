package vista_futbolDeBarrio.controlador;

import java.io.IOException;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.TorneoServicio;

@WebServlet("/inicio")
/**
 * Controlador de la página de inicio.
 * Gestiona la redirección según tipo de usuario y la petición AJAX para torneos.
 * Incluye trazabilidad mediante logs.
 */
public class InicioControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private TorneoServicio torneoServicio;

    @Override
    /**
     * Inicializa el servicio de torneos.
     *
     * @throws ServletException Si ocurre un error durante la inicialización.
     */
    public void init() throws ServletException {
        torneoServicio = new TorneoServicio();
    }

    @Override
    /**
     * Maneja solicitudes GET:
     * - Si es AJAX con acción "torneos", devuelve torneos en JSON.
     * - Redirige según tipo de usuario o muestra la JSP de inicio.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String accion = request.getParameter("accion");

            if ("torneos".equals(accion)) {

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                var torneos = torneoServicio.obtenerTodosLosTorneos();

                for (var t : torneos) {
                    String progreso = torneoServicio.progresoEquipos(t.getIdTorneo());
                    t.setClubesInscritos(progreso);
                }

                String json = new Gson().toJson(torneos);
                response.getWriter().write(json);

                Log.ficheroLog("InicioControlador: Enviados " + torneos.size() + " torneos en JSON");
                return; 
            }


            HttpSession session = request.getSession(false);
            String tipoUsuario = (session != null)
                    ? (String) session.getAttribute("tipoUsuario")
                    : null;

            if (tipoUsuario == null) {
                request.getRequestDispatcher("/WEB-INF/Vistas/Index.jsp")
                       .forward(request, response);
                Log.ficheroLog("InicioControlador: Usuario no logueado, cargada Index.jsp");
                return;
            }

            switch (tipoUsuario) {
                case "jugador":
                    Log.ficheroLog("InicioControlador: Redirigiendo jugador ID=" + session.getAttribute("idUsuario"));
                    response.sendRedirect("jugador");
                    break;
                case "club":
                    Log.ficheroLog("InicioControlador: Redirigiendo club ID=" + session.getAttribute("clubId"));
                    response.sendRedirect("club");
                    break;
                case "instalacion":
                    Log.ficheroLog("InicioControlador: Redirigiendo instalacion ID=" + session.getAttribute("idInstalacion"));
                    response.sendRedirect("instalacion");
                    break;
                case "administrador":
                    Log.ficheroLog("InicioControlador: Redirigiendo administrador ID=" + session.getAttribute("idUsuario"));
                    response.sendRedirect("admin");
                    break;
                default:
                    request.getRequestDispatcher("/WEB-INF/Vistas/Index.jsp")
                           .forward(request, response);
                    Log.ficheroLog("InicioControlador: Tipo de usuario desconocido, cargada Index.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("InicioControlador - Error: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error interno del servidor\"}");
        }
    }
}
