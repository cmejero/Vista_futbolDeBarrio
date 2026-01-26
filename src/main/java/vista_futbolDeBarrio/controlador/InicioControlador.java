package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/inicio")
public class InicioControlador extends HttpServlet {
	
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String tipoUsuario = (session != null)
                ? (String) session.getAttribute("tipoUsuario")
                : null;

        if (tipoUsuario == null) {
            // 🔓 Usuario NO logueado → Index
            request.getRequestDispatcher("/WEB-INF/Vistas/Index.jsp")
                   .forward(request, response);
            return;
        }

        // 🔐 Usuario logueado → redirigir según rol
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
