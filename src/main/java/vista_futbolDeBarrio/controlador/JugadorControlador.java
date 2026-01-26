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
import vista_futbolDeBarrio.servicios.JugadorEstadisticaGlobalServicio;
import vista_futbolDeBarrio.servicios.UsuarioServicio;

@WebServlet("/jugador")
public class JugadorControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UsuarioServicio usuarioServicio = new UsuarioServicio();
    private JugadorEstadisticaGlobalServicio estadisticaServicio =
            new JugadorEstadisticaGlobalServicio();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession(false);

        // 🔐 Seguridad
        if (session == null || session.getAttribute("token") == null) {
            response.sendRedirect("login?error=accesoDenegado");
            return;
        }

        if (!"jugador".equals(session.getAttribute("tipoUsuario"))) {
            response.sendRedirect("login?error=accesoDenegado");
            return;
        }

        Long usuarioId = (Long) session.getAttribute("usuarioId");
        if (usuarioId == null) {
            response.sendRedirect("login?error=accesoDenegado");
            return;
        }

        // 👉 Diferenciar comportamiento
        String accion = request.getParameter("accion");

        // =========================
        // 📦 PETICIÓN DE DATOS (AJAX)
        // =========================
        if ("datos".equals(accion)) {

            UsuarioDto usuario = usuarioServicio.obtenerUsuario(request, usuarioId);
            JugadorEstadisticaGlobalDto estadisticas =
                    estadisticaServicio.obtenerJugadorEstadisticasGlobal(usuarioId);

            if (usuario == null || estadisticas == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }

            Map<String, Object> resultado = new HashMap<>();
            resultado.put("usuario", usuario);
            resultado.put("estadisticas", estadisticas);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            mapper.writeValue(response.getWriter(), resultado);
            return;
        }

  
        request.getRequestDispatcher("/WEB-INF/Vistas/Jugador.jsp")
               .forward(request, response);
    }
}
