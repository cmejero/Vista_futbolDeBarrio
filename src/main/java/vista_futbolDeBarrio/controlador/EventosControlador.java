package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vista_futbolDeBarrio.dtos.TorneoDto;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.TorneoServicio;

@WebServlet("/eventos")
/**
 * Controlador para gestionar los eventos/torneos públicos.
 * Permite mostrar la vista JSP o devolver los torneos en formato JSON.
 * Incluye trazabilidad mediante logs.
 */
public class EventosControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private TorneoServicio torneoServicio = new TorneoServicio();

    @Override
    /**
     * Maneja solicitudes GET para listar torneos o cargar la JSP.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String tipo = request.getParameter("tipo");

            if (tipo == null) {
                request.getRequestDispatcher("/WEB-INF/Vistas/Eventos.jsp").forward(request, response);
                Log.ficheroLog("EventosControlador: JSP Eventos.jsp cargada correctamente");
                return;
            }

            if ("json".equals(tipo)) {
                List<TorneoDto> torneos = torneoServicio.obtenerTodosLosTorneos();

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                Gson gson = new Gson();
                response.getWriter().write(gson.toJson(torneos));

                Log.ficheroLog("EventosControlador: listado de torneos enviado en JSON, total=" + torneos.size());
                return;
            }

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"tipo no válido\"}");
            Log.ficheroLog("EventosControlador: tipo no válido recibido: " + tipo);

        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("EventosControlador - Error: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error al procesar la solicitud\"}");
        }
    }
}
