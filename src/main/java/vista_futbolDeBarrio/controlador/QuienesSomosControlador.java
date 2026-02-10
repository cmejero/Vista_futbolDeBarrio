package vista_futbolDeBarrio.controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vista_futbolDeBarrio.log.Log;

@WebServlet("/quienesSomos")
public class QuienesSomosControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Log.ficheroLog("[INFO] GET /quienes-somos → mostrar vista QuienesSomos.jsp");

        request.getRequestDispatcher("/WEB-INF/Vistas/QuienesSomos.jsp")
               .forward(request, response);
    }
}
