package vista_futbolDeBarrio.controlador;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.JugadorEstadisticaGlobalDto;
import vista_futbolDeBarrio.servicios.JugadorEstadisticaGlobalServicio;

@WebServlet("/jugadorEstadisticaGlobal")
@MultipartConfig
/**
 * Clase controlador que se encarga de los metodos CRUD de la tabla jugador estadisticas global
 */
public class JugadorEstadisticaGlobalControlador extends HttpServlet{
	private static final long serialVersionUID = 1L;
    private JugadorEstadisticaGlobalServicio jugadorEstadisticaGlobalServicio;

    @Override
    public void init() throws ServletException {
        this.jugadorEstadisticaGlobalServicio = new JugadorEstadisticaGlobalServicio();
    }
  /*  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Object resultado;
            ObjectMapper objectMapper = new ObjectMapper();
            String idParam = request.getParameter("id");

            if (idParam != null) {
                // Mostrar estadísticas de un jugador concreto
                Long jugadorId = Long.parseLong(idParam);
                resultado = jugadorEstadisticaGlobalServicio.obtenerJugadorEstadisticasGlobal(jugadorId);
            } else {
                // Mostrar TODOS los jugadores
                String jsonLista = jugadorEstadisticaGlobalServicio.obtenerTodosJugadorEstadisticasGlobal();
                resultado = objectMapper.readValue(jsonLista, JugadorEstadisticaGlobalDto[].class);
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(resultado));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("text/plain; charset=UTF-8");
            response.getWriter().write("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
*/



}
