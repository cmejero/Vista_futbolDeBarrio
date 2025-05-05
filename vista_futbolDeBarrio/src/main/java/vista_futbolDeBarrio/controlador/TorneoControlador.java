package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.TorneoDto;
import vista_futbolDeBarrio.enums.Modalidad;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.TorneoServicio;
import com.google.gson.Gson;


@WebServlet("/torneo")
@MultipartConfig
/**
 * Clase controlador que se encarga de los metodos CRUD de la tabla torneo
 */
public class TorneoControlador extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private TorneoServicio servicio;

	@Override
	public void init() throws ServletException {
		this.servicio = new TorneoServicio();
	}

	@Override
	/**
	 * Metodo POST que se encarga de guardar un nuevo torneo
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        String accion = request.getParameter("accion");

	        if ("aniadir".equals(accion)) {
	            String nombreTorneo = request.getParameter("nombreTorneo");
	            String descripcionTorneo = request.getParameter("descripcionTorneo");
	            String modalidadStr = request.getParameter("modalidad");
	            Long instalacionId = (Long) request.getSession().getAttribute("instalacionId");

	            if (instalacionId == null) {
	                request.setAttribute("mensajeError", "Instalación no encontrada en sesión.");
	                request.getRequestDispatcher("/EventoInstalacion.jsp").forward(request, response);
	                return;
	            }

	            String fechaInicioTorneo = request.getParameter("fechaInicioTorneo");
	            String fechaFinTorneo = "9999-01-01";

	            try {
	                Modalidad modalidad = Modalidad.valueOf(modalidadStr.trim());

	                TorneoDto nuevoTorneo = new TorneoDto();
	                nuevoTorneo.setNombreTorneo(nombreTorneo);
	                nuevoTorneo.setFechaInicioTorneo(fechaInicioTorneo);
	                nuevoTorneo.setFechaFinTorneo(fechaFinTorneo);
	                nuevoTorneo.setDescripcionTorneo(descripcionTorneo);
	                nuevoTorneo.setModalidad(modalidad);
	                nuevoTorneo.setInstalacionId(instalacionId);

	                servicio.guardarTorneo(nuevoTorneo);

	                Log.ficheroLog("Torneo creado: " + nombreTorneo + " | Modalidad: " + modalidad + " | Instalación ID: " + instalacionId);

	                request.setAttribute("mensajeExito", "Torneo creado correctamente.");
	            } catch (IllegalArgumentException e) {
	                request.setAttribute("mensajeError", "Modalidad no válida.");
	            }
	        } else if ("modificar".equals(accion)) {
	            request.setAttribute("mensajeInfo", "Funcionalidad de modificación aún no implementada.");
	        } else {
	            request.setAttribute("mensajeError", "Acción no válida.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("mensajeError", "Error en el servidor: " + e.getMessage());
	        Log.ficheroLog("Error en POST /torneo: " + e.getMessage());
	    }

	    // Siempre volver al formulario con mensaje
	    request.getRequestDispatcher("/EventoInstalacion.jsp").forward(request, response);
	}


	@Override
	/**
	 * Metodo GET que se encarga de mostrar una lista de todos los torneos
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String idParam = request.getParameter("instalacionId");
            List<TorneoDto> torneos;

            if (idParam != null && !idParam.isEmpty()) {
                Long instalacionId = Long.parseLong(idParam);
                torneos = servicio.obtenerTorneosPorInstalacion(instalacionId);
            } else {
                torneos = servicio.obtenerTodosLosTorneos();
            }

            String json = new Gson().toJson(torneos);
            response.getWriter().write(json);

        } catch (Exception e) {
            Log.ficheroLog("Error en TorneoControlador: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error al obtener torneos\"}");
        }
    }
	
	@Override
    /**
     * Metodo DELETE que se encarga de eliminar un usuario
     */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        // Obtener la sesión y comprobar el tipo de usuario
	        HttpSession session = request.getSession(false);
	        String tipoInstalacion = (session != null) ? (String) session.getAttribute("tipoInstalacion") : null;

	       

	        // Obtener el ID del torneo desde los parámetros de la URL
	        String idTorneoParam = request.getParameter("idTorneo");
	        if (idTorneoParam == null || idTorneoParam.isEmpty()) {
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	            response.getWriter().write("ID de torneo no proporcionado.");
	            Log.ficheroLog("Eliminación fallida: ID no proporcionado");
	            return;
	        }

	        Long idTorneo = Long.parseLong(idTorneoParam);  // Convertir a Long
	        boolean eliminado = servicio.eliminarTorneo(idTorneo);  // Llamada al servicio para eliminar el torneo

	        if (eliminado) {
	            Log.ficheroLog("Torneo eliminado: id=" + idTorneo);
	            response.setStatus(HttpServletResponse.SC_OK);
	            response.getWriter().write("Torneo eliminado correctamente.");
	        } else {
	            Log.ficheroLog("Error al eliminar torneo: id=" + idTorneo);
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            response.getWriter().write("Error al eliminar el torneo.");
	        }
	    } catch (NumberFormatException e) {
	        Log.ficheroLog("Error de formato en ID de torneo para eliminar: " + e.getMessage());
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        response.getWriter().write("ID de torneo no válido.");
	    } catch (Exception e) {
	        Log.ficheroLog("Error en DELETE /torneo: " + e.getMessage());
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().write("Error en el servidor: " + e.getMessage());
	    }
	}


}
