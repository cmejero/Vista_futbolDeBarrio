package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.JugadorEstadisticaGlobalDto;
import vista_futbolDeBarrio.dtos.UsuarioDto;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.JugadorEstadisticaGlobalServicio;
import vista_futbolDeBarrio.servicios.UsuarioServicio;

/**
 * Controlador para la gestión de la vista y datos del jugador.
 * <p>
 * Permite mostrar la página del jugador o enviar sus datos y estadísticas
 * en formato JSON cuando se solicita vía AJAX.
 */
@WebServlet("/jugador")
public class JugadorControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final UsuarioServicio usuarioServicio = new UsuarioServicio();
    private final JugadorEstadisticaGlobalServicio estadisticaServicio =
            new JugadorEstadisticaGlobalServicio();
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Maneja las solicitudes GET para:
     * <ul>
     *     <li>Cargar la vista del jugador si no se envía "accion".</li>
     *     <li>Devolver los datos y estadísticas del jugador en JSON si "accion=datos".</li>
     * </ul>
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession(false);

        // 🔐 Seguridad básica
        if (session == null || session.getAttribute("token") == null) {
            Log.ficheroLog("JugadorControlador: intento de acceso sin sesión o token inválido");
            response.sendRedirect(request.getContextPath() + "/login?error=accesoDenegado");
            return;
        }

        if (!"jugador".equals(session.getAttribute("tipoUsuario"))) {
            Log.ficheroLog("JugadorControlador: Acceso denegado (tipoUsuario no es jugador)");
            response.sendRedirect(request.getContextPath() + "/login?error=accesoDenegado");
            return;
        }

        Long usuarioId = (Long) session.getAttribute("usuarioId");
        if (usuarioId == null) {
            Log.ficheroLog("JugadorControlador: Acceso denegado (usuarioId nulo)");
            response.sendRedirect(request.getContextPath() + "/login?error=accesoDenegado");
            return;
        }

        String accion = request.getParameter("accion");

        try {
            if ("datos".equals(accion)) {
                UsuarioDto usuario = null;
                JugadorEstadisticaGlobalDto estadisticas = null;

                try {
                    usuario = usuarioServicio.obtenerUsuario(request, usuarioId);
                    estadisticas = estadisticaServicio.obtenerJugadorEstadisticasGlobal(usuarioId);
                } catch (Exception ex) {
                    Log.ficheroLog("JugadorControlador: Error obteniendo datos para usuarioId=" + usuarioId + " -> " + ex.getMessage());
                }

                Map<String, Object> resultado = new HashMap<>();
                if (usuario != null) resultado.put("usuario", usuario);
                if (estadisticas != null) resultado.put("estadisticas", estadisticas);

                if (resultado.isEmpty()) {
                    resultado.put("error", "No se pudieron cargar los datos del jugador");
                    response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                }

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                mapper.writeValue(response.getWriter(), resultado);
                Log.ficheroLog("JugadorControlador: Datos enviados para usuarioId=" + usuarioId);
                return;
            }

            // Cargar vista normal si no es AJAX
            request.getRequestDispatcher("/WEB-INF/Vistas/Jugador.jsp").forward(request, response);
            Log.ficheroLog("JugadorControlador: Cargada vista Jugador.jsp para usuarioId=" + usuarioId);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error interno del servidor\"}");
            Log.ficheroLog("JugadorControlador - Error general: " + e.getMessage());
        }
    }

}
