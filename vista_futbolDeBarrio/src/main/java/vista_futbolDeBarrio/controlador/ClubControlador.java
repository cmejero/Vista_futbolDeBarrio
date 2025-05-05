package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import vista_futbolDeBarrio.dtos.ClubDto;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.ClubServicio;

@WebServlet("/club")
@MultipartConfig
/**
 * Clase que se encarga de los metodos CRUD de la tabla de club
 */
public class ClubControlador extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ClubServicio servicio;

	@Override
	public void init() throws ServletException {
		this.servicio = new ClubServicio();
	}

	@Override
	/**
	 * Metodo POST que se encarga de guardar o modificar un club
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String accion = request.getParameter("accion"); // Acción para determinar si es crear o modificar
			Log.ficheroLog("Acción recibida desde el formulario: " + accion);

			if ("aniadir".equals(accion)) {
				// Obtener los datos del formulario
				String nombreClubForm = request.getParameter("nombreClub");
				String abreviaturaClubForm = request.getParameter("abreviaturaClub");
				String descripcionClubForm = request.getParameter("descripcionClub");
				
				// Generar la fecha de creación actual
				LocalDateTime fechaCreacion = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");  // Formato de fecha y hora
				String fechaCreacionClubForm = fechaCreacion.format(formatter); // Convertir a String en el formato deseado
				
				
				String localidadClubForm = request.getParameter("localidadClub");
				String paisClubForm = request.getParameter("paisClub");
				String emailClubForm = request.getParameter("emailClub");
				String passwordClubForm = request.getParameter("passwordClub");
				String telefonoClubForm = request.getParameter("telefonoClub");

				Part imagenPart = request.getPart("logoClub");
				byte[] logoClubForm = null;
				if (imagenPart != null && imagenPart.getSize() > 0) {
					logoClubForm = new byte[(int) imagenPart.getSize()];
					InputStream inputStream = imagenPart.getInputStream();
					inputStream.read(logoClubForm);
				}

				// Crear un nuevo ClubDto
				ClubDto nuevoClub = new ClubDto();
				nuevoClub.setNombreClub(nombreClubForm);
				nuevoClub.setAbreviaturaClub(abreviaturaClubForm);
				nuevoClub.setDescripcionClub(descripcionClubForm);
				nuevoClub.setFechaCreacionClub(fechaCreacionClubForm);  // Asignar la fecha de creación como String
				nuevoClub.setFechaFundacionClub(""); // Asignar la fecha de fundación como String
				nuevoClub.setLocalidadClub(localidadClubForm);
				nuevoClub.setPaisClub(paisClubForm);
				nuevoClub.setEmailClub(emailClubForm);
				nuevoClub.setPasswordClub(passwordClubForm);
				nuevoClub.setTelefonoClub(telefonoClubForm);

				// Guardar el club
				servicio.guardarClub(nuevoClub);
				Log.ficheroLog("Club creado correctamente: " + nombreClubForm + ", " + abreviaturaClubForm);
				response.getWriter().write("Club creado correctamente.");

			} else if ("modificar".equals(accion)) {

				Log.ficheroLog("Modificar club. Acción no implementada aún.");
			} else {
				response.getWriter().write("Acción no válida.");
				Log.ficheroLog("Acción no válida recibida: " + accion);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter()
					.write("Se ha producido un error en el servidor. Por favor, inténtelo más tarde." + e.getMessage());
			Log.ficheroLog("Error en el procesamiento de la acción: " + e.getMessage());
		}
	}

	@Override
	/**
	 * Metodo GET que se encarga de mostrar la lista de clubes
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			String tipoUsuario = (session != null) ? (String) session.getAttribute("tipoUsuario") : null;

			// Verificar si hay sesión activa y si el usuario tiene algún tipo definido
			if (tipoUsuario == null || tipoUsuario.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.getWriter().write("Acceso denegado. Debe iniciar sesión para acceder.");
				Log.ficheroLog("Intento de acceso no autorizado sin sesión a GET /club");
				return;
			}

			// Puedes restringir más si lo necesitas, por ejemplo:
			// if (!"administrador".equals(tipoUsuario)) { ... }

			ArrayList<ClubDto> listaClub = servicio.listaClub();

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			ObjectMapper objectMapper = new ObjectMapper();
			
			String json = objectMapper.writeValueAsString(listaClub);
			Log.ficheroLog("Lista de clubes solicitada. Número de clubes: " + listaClub.size());
			response.getWriter().write(json);
		} catch (Exception e) {
			Log.ficheroLog("Error al obtener lista de clubes: " + e.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("Se ha producido un error al obtener los clubes. " + e.getMessage());
		}
	}

}
