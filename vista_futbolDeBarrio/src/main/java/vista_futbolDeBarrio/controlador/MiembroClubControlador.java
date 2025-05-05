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
import vista_futbolDeBarrio.dtos.MiembroClubDto;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.MiembroClubServicio;

@WebServlet("/miembroClub")
/**
 * Clase controlador que se encarga de los metodos CRUD de la tabla miembroClub
 */
public class MiembroClubControlador extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private MiembroClubServicio servicio;

	@Override
	public void init() throws ServletException {
		this.servicio = new MiembroClubServicio();
	}

	@Override
	/**
	 * Metodo POST que se necarga de guardar un jugador en un club
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String accion = request.getParameter("accion");

			if ("aniadir".equals(accion)) {
				// Obtener los datos del formulario
				String fechaAltaUsuarioForm = LocalDate.now().toString();
				String fechaBajaUsuarioForm = ("9999-01-01");
				long clubIdForm = Long.parseLong(request.getParameter("clubId"));
				long usuarioIdForm = Long.parseLong(request.getParameter("usuarioId"));

				// Crear el objeto MiembroClubDto
				MiembroClubDto nuevoMiembro = new MiembroClubDto();
				nuevoMiembro.setFechaAltaUsuario(fechaAltaUsuarioForm);
				nuevoMiembro.setFechaBajaUsuario(fechaBajaUsuarioForm);

				nuevoMiembro.setClubId(clubIdForm);
				nuevoMiembro.setUsuarioId(usuarioIdForm);

				// Guardar el miembro en el servicio
				servicio.guardarMiembroClub(nuevoMiembro);

				// Log de creación
				Log.ficheroLog("Miembro del club añadido: Usuario ID " + usuarioIdForm + ", Club ID " + clubIdForm);

				response.getWriter().write("Miembro del club creado correctamente.");
			} else if ("modificar".equals(accion)) {
				// Lógica para modificar el miembro del club
				response.getWriter().write("Funcionalidad de modificación aún no implementada.");
				Log.ficheroLog("Intento de modificar miembro del club, pero funcionalidad aún no implementada.");
			} else {
				response.getWriter().write("Acción no válida.");
				Log.ficheroLog("Acción no válida recibida: " + accion);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter()
					.write("Se ha producido un error en el servidor. Por favor, inténtelo más tarde." + e.getMessage());
			Log.ficheroLog("Error en el procesamiento de la acción POST en /miembroClub: " + e.getMessage());
		}
	}

	@Override
	/**
	 * Metodo GET que se encarga de mostrar una lista de clubes con sus jugadores
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ArrayList<MiembroClubDto> listaMiembros = servicio.listarMiembrosClub();

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			// Convertir la lista a JSON
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(listaMiembros);

			// Escribir la respuesta JSON
			response.getWriter().write(json);

			// Log de acceso
			Log.ficheroLog("Lista de miembros del club consultada correctamente. Total: " + listaMiembros.size());
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("Error al recuperar la lista de miembros.");
			Log.ficheroLog("Error al obtener lista de miembros del club: " + e.getMessage());
		}
	}
}
