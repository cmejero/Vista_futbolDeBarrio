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
     * Método POST que se encarga de guardar o modificar un club.
     * @param request HttpServletRequest que contiene los parámetros del formulario.
     * @param response HttpServletResponse que contiene la respuesta a enviar al cliente.
     * @throws ServletException Si ocurre un error relacionado con el servlet.
     * @throws IOException Si ocurre un error relacionado con la entrada/salida.
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        String accion = request.getParameter("accion");
	        Log.ficheroLog("Acción recibida desde el formulario: " + accion);

	        if ("aniadir".equals(accion)) {
	            crearClub(request, response);
	        } else if ("modificar".equals(accion)) {
	            modificarClub(request, response);
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

	 /**
     * Método privado que crea un nuevo club.
     * @param request HttpServletRequest que contiene los parámetros del formulario.
     * @param response HttpServletResponse que contiene la respuesta a enviar al cliente.
     * @throws IOException Si ocurre un error relacionado con la entrada/salida.
     * @throws ServletException Si ocurre un error relacionado con el servlet.
     */
	private void crearClub(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	    String nombreClubForm = request.getParameter("nombreClub");
	    String abreviaturaClubForm = request.getParameter("abreviaturaClub");
	    String descripcionClubForm = request.getParameter("descripcionClub");

	    LocalDateTime fechaCreacion = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	    String fechaCreacionClubForm = fechaCreacion.format(formatter);

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

	    ClubDto nuevoClub = new ClubDto();
	    nuevoClub.setNombreClub(nombreClubForm);
	    nuevoClub.setAbreviaturaClub(abreviaturaClubForm);
	    nuevoClub.setDescripcionClub(descripcionClubForm);
	    nuevoClub.setFechaCreacionClub(fechaCreacionClubForm);
	    nuevoClub.setFechaFundacionClub("");
	    nuevoClub.setLocalidadClub(localidadClubForm);
	    nuevoClub.setPaisClub(paisClubForm);
	    nuevoClub.setEmailClub(emailClubForm);
	    nuevoClub.setPasswordClub(passwordClubForm);
	    nuevoClub.setTelefonoClub(telefonoClubForm);
	    nuevoClub.setLogoClub(logoClubForm);
	    servicio.guardarClub(nuevoClub);

	    Log.ficheroLog("Club creado correctamente: " + nombreClubForm + ", " + abreviaturaClubForm);
	    
	    response.sendRedirect("InicioSesion.jsp?mensajeAlta=registro_exitoso");
	}

	
	  /**
     * Método privado que modifica los datos de un club existente.
     * @param request HttpServletRequest que contiene los parámetros del formulario.
     * @param response HttpServletResponse que contiene la respuesta a enviar al cliente.
     * @throws IOException Si ocurre un error relacionado con la entrada/salida.
     * @throws ServletException Si ocurre un error relacionado con el servlet.
     */
	private void modificarClub(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	    String idClubForm = request.getParameter("idClub");

	    if (idClubForm == null || idClubForm.isEmpty()) {
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        response.getWriter().write("ID de club no proporcionado.");
	        Log.ficheroLog("Modificación fallida: ID de club no proporcionado");
	        return;
	    }

	    String nombreClubForm = request.getParameter("nombreClub");
	    String abreviaturaClubForm = request.getParameter("abreviaturaClub");
	    String descripcionClubForm = request.getParameter("descripcionClub");
	    String fechaFundacionClubForm = request.getParameter("fechaFundacionClub");
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

	    ClubDto clubModificado = new ClubDto();
	    clubModificado.setIdClub(Long.parseLong(idClubForm));
	    clubModificado.setNombreClub(nombreClubForm);
	    clubModificado.setAbreviaturaClub(abreviaturaClubForm);
	    clubModificado.setDescripcionClub(descripcionClubForm);
	    clubModificado.setFechaFundacionClub(fechaFundacionClubForm);
	    clubModificado.setLocalidadClub(localidadClubForm);
	    clubModificado.setPaisClub(paisClubForm);
	    clubModificado.setEmailClub(emailClubForm);
	    clubModificado.setPasswordClub(passwordClubForm);
	    clubModificado.setTelefonoClub(telefonoClubForm);
	    clubModificado.setLogoClub(logoClubForm); 

	    boolean actualizado = servicio.modificarClub(idClubForm, clubModificado);

	    if (actualizado) {
	        Log.ficheroLog("Club modificado: id=" + idClubForm + ", nombre=" + nombreClubForm);
	        response.getWriter().write("Club modificado correctamente.");
	    } else {
	        Log.ficheroLog("Error al modificar club: id=" + idClubForm);
	        response.getWriter().write("No se pudo modificar el club.");
	    }
	}

	
	
	@Override
    /**
     * Método GET que se encarga de mostrar la lista de clubes.
     * @param request HttpServletRequest que contiene la solicitud del cliente.
     * @param response HttpServletResponse que contiene la respuesta a enviar al cliente.
     * @throws ServletException Si ocurre un error relacionado con el servlet.
     * @throws IOException Si ocurre un error relacionado con la entrada/salida.
     */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			String tipoUsuario = (session != null) ? (String) session.getAttribute("tipoUsuario") : null;

			if (tipoUsuario == null || tipoUsuario.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.getWriter().write("Acceso denegado. Debe iniciar sesión para acceder.");
				Log.ficheroLog("Intento de acceso no autorizado sin sesión a GET /club");
				return;
			}
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
