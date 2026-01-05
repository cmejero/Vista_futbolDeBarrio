package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.EquipoTorneoDto;
import vista_futbolDeBarrio.dtos.TorneoDto;
import vista_futbolDeBarrio.enums.EstadoParticipacion;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.EquipoTorneoServicio;

@WebServlet("/equipoTorneo")
/**
 * Clase controlador que se encarga de los metodos CRUD de la tabla equipoTorneo
 */
public class EquipoTorneoControlador extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private EquipoTorneoServicio servicio;

	
	

	@Override
	public void init() throws ServletException {
		this.servicio = new EquipoTorneoServicio();
	
	}

	  @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        try {
	            // Obtenemos el ID del club desde la sesión (usuario logueado)
	            HttpSession session = request.getSession();
	            Long clubId = (Long) session.getAttribute("clubId");

	            if (clubId == null) {
	                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	                response.getWriter().write("No tienes permiso para inscribirte en el torneo.");
	                return;
	            }

	            // Obtenemos parámetros del formulario o JSON enviado
	            Long torneoId = Long.parseLong(request.getParameter("torneoId"));

	            // Validaciones
	            if (servicio.estaInscrito(torneoId, clubId)) {
	                response.setStatus(HttpServletResponse.SC_CONFLICT);
	                response.getWriter().write("Ya estás inscrito en este torneo.");
	                return;
	            }

	            if (!servicio.puedeInscribirse(torneoId)) {
	                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	                response.getWriter().write("El torneo está lleno.");
	                return;
	            }

	            // Creamos objeto EquipoTorneoDto
	            EquipoTorneoDto equipoTorneo = new EquipoTorneoDto();
	            equipoTorneo.setClubId(clubId);
	            equipoTorneo.setTorneoId(torneoId);
	            equipoTorneo.setFechaInicioParticipacion(LocalDate.now().toString());
	            equipoTorneo.setFechaFinParticipacion("9999-01-01");
	            equipoTorneo.setEstadoParticipacion(EstadoParticipacion.Activo);

	            // Guardamos usando el servicio
	            servicio.guardarEquipoTorneo(equipoTorneo, request);

	            Log.ficheroLog("Club " + clubId + " inscrito correctamente en torneo " + torneoId);
	            response.getWriter().write("Inscripción realizada correctamente.");

	        } catch (Exception e) {
	            e.printStackTrace();
	            Log.ficheroLog("Error al inscribirse en torneo: " + e.getMessage());
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            response.getWriter().write("Error al inscribirse en el torneo: " + e.getMessage());
	        }
	    }
	
	
	@Override
	 /**
     * Metodo GET que se encarga de mostrar listas de los torneos y equipos que participan
     * @param request La solicitud HTTP que contiene los parámetros de la consulta.
     * @param response La respuesta HTTP que se enviará al cliente.
     * @throws ServletException Si ocurre un error durante la manipulación de la solicitud o respuesta.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");

    	List<TorneoDto> torneos = new ArrayList<>();

	    try {
	        // Leemos el parámetro usuarioId de la URL: /equipoTorneo?usuarioId=123
	        String usuarioIdParam = request.getParameter("usuarioId");

	        if (usuarioIdParam != null) {
	            Long usuarioId = Long.parseLong(usuarioIdParam);

	            // Llamamos al nuevo método que obtiene todos los torneos de todos los clubes del usuario
	            torneos = servicio.obtenerTorneosPorUsuario(usuarioId);

	        } else {
	        	torneos = servicio.listaEquiposTorneo()
	                    .stream()
	                    .map(e -> {
	                        TorneoDto t = new TorneoDto();
	                        t.setIdTorneo(e.getTorneoId());
	                        return t;
	                    }).toList();
	        }

	        // Convertimos a JSON
	        ObjectMapper objectMapper = new ObjectMapper();
	        String json = objectMapper.writeValueAsString(torneos);
	        response.getWriter().write(json);

	        Log.ficheroLog("Lista de torneos para usuario solicitada. Total: " + torneos.size());

	    } catch (NumberFormatException nfe) {
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        response.getWriter().write("ID de usuario inválido.");
	        Log.ficheroLog("Error: ID de usuario inválido. " + nfe.getMessage());
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().write("Error al obtener torneos: " + e.getMessage());
	        Log.ficheroLog("Error al obtener lista de torneos: " + e.getMessage());
	    }
	}

}
