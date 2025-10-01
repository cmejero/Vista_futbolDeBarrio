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

	
	

	@Override
	public void init() throws ServletException {
		this.servicio = new EquipoTorneoServicio();
	
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
