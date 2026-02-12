package vista_futbolDeBarrio.controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vista_futbolDeBarrio.log.Log;

/**
 * Controlador para la página "Quiénes Somos".
 * Simplemente carga la vista QuienesSomos.jsp.
 */
@WebServlet("/quienesSomos")
public class QuienesSomosControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Maneja solicitudes GET.
     * Carga la vista QuienesSomos.jsp y registra la acción en el log.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Log.ficheroLog("QuienesSomosControlador: GET /quienesSomos → mostrar vista QuienesSomos.jsp");

            request.getRequestDispatcher("/WEB-INF/Vistas/QuienesSomos.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("QuienesSomosControlador - Error en doGet: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al cargar la página QuienesSomos");
        }
    }
}
