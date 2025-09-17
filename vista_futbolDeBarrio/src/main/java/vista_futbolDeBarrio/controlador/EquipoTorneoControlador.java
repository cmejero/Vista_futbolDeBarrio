package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vista_futbolDeBarrio.dtos.EquipoTorneoDto;
import vista_futbolDeBarrio.enums.EstadoParticipacion;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.EquipoTorneoServicio;
import vista_futbolDeBarrio.servicios.TorneoServicio;

@WebServlet("/equipoTorneo")
/**
 * Clase controlador que se encarga de los metodos CRUD de la tabla equipoTorneo
 */
public class EquipoTorneoControlador extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private EquipoTorneoServicio servicio;
	private TorneoServicio torneoServicio;
	
	

	@Override
	public void init() throws ServletException {
		this.servicio = new EquipoTorneoServicio();
		this.torneoServicio = new TorneoServicio();
	}

	  /**
     * Metodo POST que se encarga de guardar un equipo en un torneo
     * @param request La solicitud HTTP que contiene los datos del formulario.
     * @param response La respuesta HTTP que se enviará al cliente.
     * @throws ServletException Si ocurre un error durante la manipulación de la solicitud o respuesta.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String accion = request.getParameter("accion");

            Log.ficheroLog("Acción recibida desde el formulario: " + accion );

            if ("aniadir".equals(accion)) {
               
                long torneoId = Long.parseLong(request.getParameter("torneoId"));
                long clubId = Long.parseLong(request.getParameter("clubId"));
                
                if (!servicio.puedeInscribirse(torneoId)) {
                    response.getWriter().write("El torneo ya alcanzó el máximo de 16 equipos.");
                    Log.ficheroLog("Intento de inscripción fallido. Torneo ID " + torneoId + " lleno.");
                    return;
                }
                if (servicio.estaInscrito(torneoId, clubId)) {
                    response.getWriter().write("El club ya está inscrito en este torneo.");
                    Log.ficheroLog("Intento de inscripción duplicada. Torneo ID " + torneoId + ", Club ID: " + clubId);
                    return;
                }

                String fechaInicioParticipacion = LocalDate.now().toString();
                EstadoParticipacion estadoParticipacion = EstadoParticipacion.Activo; 

                
                EquipoTorneoDto nuevoEquipoTorneo = new EquipoTorneoDto();
                nuevoEquipoTorneo.setTorneoId(torneoId);
                nuevoEquipoTorneo.setClubId(clubId);
                nuevoEquipoTorneo.setFechaInicioParticipacion(fechaInicioParticipacion.toString());
                nuevoEquipoTorneo.setFechaFinParticipacion("9999-01-01");
                nuevoEquipoTorneo.setEstadoParticipacion(estadoParticipacion);

                
                servicio.guardarEquipoTorneo(nuevoEquipoTorneo);
                Log.ficheroLog("Equipo-Torneo creado correctamente. Torneo ID: " + torneoId + ", Club ID: " + clubId );

                // Actualizar el progreso de inscripción
                torneoServicio.actualizarClubesInscritos(torneoId);

                response.getWriter().write("Equipo-Torneo creado correctamente.");
            } else if ("modificar".equals(accion)) {
               
                Log.ficheroLog("Modificar equipo-torneo. Acción no implementada aún." );
            } else {
                response.getWriter().write("Acción no válida.");
                Log.ficheroLog("Acción no válida recibida: " + accion);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Se ha producido un error en el servidor. Por favor, inténtelo más tarde." + e.getMessage());
            Log.ficheroLog("Error en el procesamiento de la acción: " + e.getMessage());
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
		try {

			ArrayList<EquipoTorneoDto> listaEquiposTorneo = servicio.listaEquiposTorneo();

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(listaEquiposTorneo);
			Log.ficheroLog("Lista de equipos-torneo solicitada. Número de equipos: " + listaEquiposTorneo.size());
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("Se ha producido un error al obtener los equipos-torneo. " + e.getMessage());
			Log.ficheroLog("Error al obtener lista de equipos-torneo: " + e.getMessage());
		}
	}
}
