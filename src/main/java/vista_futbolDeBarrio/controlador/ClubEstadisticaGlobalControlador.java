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
import vista_futbolDeBarrio.dtos.ClubEstadisticaGlobalDto;
import vista_futbolDeBarrio.servicios.ClubEstadisticaGlobalServicio;

@WebServlet("/clubEstadisticaGlobal")
@MultipartConfig
/**
 * Clase controlador que se encarga de los metodos CRUD de la tabla club estadisticas global
 */
public class ClubEstadisticaGlobalControlador extends HttpServlet{
	private static final long serialVersionUID = 1L;
    private ClubEstadisticaGlobalServicio clubEstadisticaGlobalServicio;

    @Override
    public void init() throws ServletException {
        this.clubEstadisticaGlobalServicio = new ClubEstadisticaGlobalServicio();
    }
     
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String idParam = request.getParameter("id");

        // Validación: debe existir sesión y debe tener usuarioId o clubId
        if (session == null ||
            (session.getAttribute("usuarioId") == null && session.getAttribute("clubId") == null)) {

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("text/plain; charset=UTF-8");
            response.getWriter().write("Acceso denegado. Debe iniciar sesión.");
            return;
        }

        try {
            Object resultado;
            ObjectMapper objectMapper = new ObjectMapper();

            if (idParam != null) {
               
                Long clubId = Long.parseLong(idParam);
                String json = clubEstadisticaGlobalServicio.obtenerClubEstadisticasGlobal(clubId);
                resultado = objectMapper.readValue(json, ClubEstadisticaGlobalDto.class);

            } else {
                // Mostrar TODOS los clubes SIEMPRE
                String jsonLista = clubEstadisticaGlobalServicio.obtenerTodasClubEstadisticasGlobal();
                resultado = objectMapper.readValue(jsonLista, ClubEstadisticaGlobalDto[].class);
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

}
