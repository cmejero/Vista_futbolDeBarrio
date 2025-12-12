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
import vista_futbolDeBarrio.dtos.ClubEstadisticaTorneoDto;
import vista_futbolDeBarrio.servicios.ClubEstadisticaTorneoServicio;

@WebServlet("/clubEstadisticaTorneo")
@MultipartConfig
/**
 * Clase controlador que se encarga de los metodos CRUD de la tabla club estadistica torneo
 */
public class ClubEstadisticaTorneoControlador  extends HttpServlet{

	 private static final long serialVersionUID = 1L;
	    private ClubEstadisticaTorneoServicio clubEstadisticaTorneoServicio;

	    @Override
	    public void init() throws ServletException {
	        this.clubEstadisticaTorneoServicio = new ClubEstadisticaTorneoServicio();
	    }
	    
	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        try {
	            String idParam = request.getParameter("id"); // id opcional del club
	            ArrayList<ClubEstadisticaTorneoDto> listaEstadisticas;

	            if (idParam != null) {
	                // Si se pasa id, obtener estadísticas solo de ese club
	                Long clubId = Long.parseLong(idParam);
	                listaEstadisticas = clubEstadisticaTorneoServicio.obtenerClubEstadisticasTorneoPorClubId(clubId);
	            } else {
	                // Si no hay id, obtener todas las estadísticas de todos los clubes
	                listaEstadisticas = clubEstadisticaTorneoServicio.obtenerTodasClubEstadisticasTorneo();
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
