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
import vista_futbolDeBarrio.dtos.ClubDto;
import vista_futbolDeBarrio.dtos.ClubEstadisticaGlobalDto;
import vista_futbolDeBarrio.servicios.ClubEstadisticaGlobalServicio;
import vista_futbolDeBarrio.servicios.ClubServicio;

@WebServlet("/club")

public class ClubControlador extends HttpServlet {

	 private static final long serialVersionUID = 1L;

	    private ClubServicio clubServicio = new ClubServicio();
	    private ClubEstadisticaGlobalServicio estadisticaServicio =
	            new ClubEstadisticaGlobalServicio();
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

	        if (!"club".equals(session.getAttribute("tipoUsuario"))) {
	            response.sendRedirect("login?error=accesoDenegado");
	            return;
	        }

	        Long clubId = (Long) session.getAttribute("clubId");
	        if (clubId == null) {
	            response.sendRedirect("login?error=accesoDenegado");
	            return;
	        }

	        // 👉 Diferenciar comportamiento
	        String accion = request.getParameter("accion");

	        // =========================
	        // 📦 PETICIÓN DE DATOS (AJAX)
	        // =========================
	        if ("datos".equals(accion)) {

	            ClubDto club = clubServicio.obtenerClubPorId( clubId , request);
	            ClubEstadisticaGlobalDto estadisticas =
	                    estadisticaServicio.obtenerClubEstadisticasGlobal(clubId);

	            if (club == null || estadisticas == null) {
	                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	                return;
	            }

	            Map<String, Object> resultado = new HashMap<>();
	            resultado.put("club", club);
	            resultado.put("estadisticas", estadisticas);

	            response.setContentType("application/json");
	            response.setCharacterEncoding("UTF-8");
	            mapper.writeValue(response.getWriter(), resultado);
	            return;
	        }

	  
	        request.getRequestDispatcher("/WEB-INF/Vistas/Club.jsp")
	               .forward(request, response);
	    }
}
