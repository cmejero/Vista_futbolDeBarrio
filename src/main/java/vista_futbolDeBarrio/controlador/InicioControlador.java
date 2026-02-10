package vista_futbolDeBarrio.controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.servicios.TorneoServicio;

@WebServlet("/inicio")
public class InicioControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private TorneoServicio torneoServicio;

    @Override
    public void init() throws ServletException {
        torneoServicio = new TorneoServicio();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        // =====================================================
        // 🔥 PETICIÓN AJAX → DEVOLVER TORNEOS EN JSON
        // =====================================================
        if ("torneos".equals(accion)) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            try {
                var torneos = torneoServicio.obtenerTodosLosTorneos();

                // actualizar progreso real
                for (var t : torneos) {
                    String progreso = torneoServicio.progresoEquipos(t.getIdTorneo());
                    t.setClubesInscritos(progreso);
                }

                response.getWriter().write(new com.google.gson.Gson().toJson(torneos));

            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("[]");
            }

            return; // ⛔ IMPORTANTE: no continuar al JSP
        }

        // =====================================================
        // 🧭 COMPORTAMIENTO NORMAL DE INICIO
        // =====================================================
        HttpSession session = request.getSession(false);
        String tipoUsuario = (session != null)
                ? (String) session.getAttribute("tipoUsuario")
                : null;

        if (tipoUsuario == null) {
            request.getRequestDispatcher("/WEB-INF/Vistas/Index.jsp")
                   .forward(request, response);
            return;
        }

        switch (tipoUsuario) {
            case "jugador":
                response.sendRedirect("jugador");
                break;
            case "club":
                response.sendRedirect("club");
                break;
            case "instalacion":
                response.sendRedirect("instalacion");
                break;
            case "administrador":
                response.sendRedirect("admin");
                break;
            default:
                request.getRequestDispatcher("/WEB-INF/Vistas/Index.jsp")
                       .forward(request, response);
        }
    }
}
