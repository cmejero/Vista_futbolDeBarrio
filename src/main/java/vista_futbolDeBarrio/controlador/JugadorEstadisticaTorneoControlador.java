package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.JugadorEstadisticaTorneoDto;
import vista_futbolDeBarrio.servicios.JugadorEstadisticaTorneoServicio;
@WebServlet("/jugadorEstadisticaTorneo")
@MultipartConfig
/**
 * Clase controlador que se encarga de los metodos CRUD de la tabla jugador estadisticas torneo
 */
public class JugadorEstadisticaTorneoControlador  extends HttpServlet {

	  private static final long serialVersionUID = 1L;
	    private JugadorEstadisticaTorneoServicio jugadorEstadisticaTorneoServicio;

	    @Override
	    public void init() throws ServletException {
	        this.jugadorEstadisticaTorneoServicio = new JugadorEstadisticaTorneoServicio();
	    }
	    
	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        try {
	            String idParam = request.getParameter("id"); // id opcional del jugador
	            ArrayList<JugadorEstadisticaTorneoDto> listaEstadisticas;

	            if (idParam != null) {
	                Long jugadorId = Long.parseLong(idParam);
	                listaEstadisticas = jugadorEstadisticaTorneoServicio.obtenerJugadorEstadisticasTorneoPorJugadorId(jugadorId);
	            } else {
	                listaEstadisticas = jugadorEstadisticaTorneoServicio.obtenerTodasJugadorEstadisticasTorneo();
	            }

	            response.setContentType("application/json");
	            response.setCharacterEncoding("UTF-8");

	            ObjectMapper objectMapper = new ObjectMapper();
	            String json = objectMapper.writeValueAsString(listaEstadisticas);
	            response.getWriter().write(json);

	        } catch (Exception e) {
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            response.setContentType("text/plain; charset=UTF-8");
	            response.getWriter().write("Error en el servidor: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }



}
