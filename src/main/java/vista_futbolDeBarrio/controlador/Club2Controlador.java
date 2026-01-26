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

@WebServlet("/club2")
@MultipartConfig
/**
 * Clase que se encarga de los metodos CRUD de la tabla de club
 */
public class Club2Controlador extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ClubServicio servicio;

	@Override
	public void init() throws ServletException {
		this.servicio = new ClubServicio();
	}

	@Override
	/**
	 * Método POST que se encarga de guardar o modificar un club.
	 * 
	 * @param request  HttpServletRequest que contiene los parámetros del
	 *                 formulario.
	 * @param response HttpServletResponse que contiene la respuesta a enviar al
	 *                 cliente.
	 * @throws ServletException Si ocurre un error relacionado con el servlet.
	 * @throws IOException      Si ocurre un error relacionado con la
	 *                          entrada/salida.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			String accion = request.getParameter("accion");
			Log.ficheroLog("Acción recibida desde el formulario: " + accion);

			if ("modificar".equals(accion)) {
				modificarClub(request, response);
			} else if ("activarPremium".equals(accion)) {
				activarPremiumClub(request, response);
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
	 * Método privado que modifica los datos de un club existente.
	 * 
	 * @param request  HttpServletRequest que contiene los parámetros del
	 *                 formulario.
	 * @param response HttpServletResponse que contiene la respuesta a enviar al
	 *                 cliente.
	 * @throws IOException      Si ocurre un error relacionado con la
	 *                          entrada/salida.
	 * @throws ServletException Si ocurre un error relacionado con el servlet.
	 */
	private void modificarClub(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String idClubForm = request.getParameter("idClubEditar");

		if (idClubForm == null || idClubForm.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("ID de club no proporcionado.");
			Log.ficheroLog("Modificación fallida: ID de club no proporcionado");
			return;
		}

		String nombreClubForm = request.getParameter("nombreClubEditar");
		String abreviaturaClubForm = request.getParameter("abreviaturaClubEditar");
		String descripcionClubForm = request.getParameter("descripcionClubEditar");
		String fechaFundacionClubForm = request.getParameter("fechaFundacionClubEditar");
		String fechaCreacionClubForm = request.getParameter("fechaCreacionClubEditar");
		String localidadClubForm = request.getParameter("localidadClubEditar");
		String paisClubForm = request.getParameter("paisClubEditar");
		String emailClubForm = request.getParameter("emailClubEditar");
		String passwordClubForm = request.getParameter("passwordClubEditar");
		String telefonoClubForm = request.getParameter("telefonoClubEditar");

		Part imagenPart = request.getPart("logoClubEditar");
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
		clubModificado.setFechaCreacionClub(fechaCreacionClubForm);
		clubModificado.setLocalidadClub(localidadClubForm);
		clubModificado.setPaisClub(paisClubForm);
		clubModificado.setEmailClub(emailClubForm);
		clubModificado.setPasswordClub(passwordClubForm);
		clubModificado.setTelefonoClub(telefonoClubForm);
		clubModificado.setLogoClub(logoClubForm);

		boolean actualizado = servicio.modificarClub(idClubForm, clubModificado, request);

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
	 * 
	 * @param request  HttpServletRequest que contiene la solicitud del cliente.
	 * @param response HttpServletResponse que contiene la respuesta a enviar al
	 *                 cliente.
	 * @throws ServletException Si ocurre un error relacionado con el servlet.
	 * @throws IOException      Si ocurre un error relacionado con la
	 *                          entrada/salida.
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        HttpSession session = request.getSession(false);
	        String tipoUsuario = (session != null) ? (String) session.getAttribute("tipoUsuario") : null;

	        if (tipoUsuario == null || tipoUsuario.isEmpty()) {
	            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	            response.getWriter().write("Acceso denegado. Debe iniciar sesión para acceder.");
	            return;
	        }

	        String idParam = request.getParameter("idClub");
	        ObjectMapper objectMapper = new ObjectMapper();

	        if (idParam != null && !idParam.isEmpty()) {
	            // Solo devolver el club solicitado
	            long idClub = Long.parseLong(idParam);
	            ClubDto club = servicio.obtenerClubPorId(idClub, request);

	            if (club != null) {
	                response.setContentType("application/json");
	                response.setCharacterEncoding("UTF-8");
	                response.getWriter().write(objectMapper.writeValueAsString(club));
	            } else {
	                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	                response.getWriter().write("Club no encontrado con ID: " + idClub);
	            }

	        } else {
	            // Devolver toda la lista
	            ArrayList<ClubDto> listaClub = servicio.listaClub(request);
	            response.setContentType("application/json");
	            response.setCharacterEncoding("UTF-8");
	            response.getWriter().write(objectMapper.writeValueAsString(listaClub));
	        }

	    } catch (NumberFormatException e) {
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        response.getWriter().write("ID de club inválido.");
	    } catch (Exception e) {
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().write("Se ha producido un error al obtener los clubes. " + e.getMessage());
	    }
	}
	
	
	@Override
	/**
	 * Elimina un club por su ID.
	 * 
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws ServletException Si ocurre un error durante la ejecución del servlet.
	 * @throws IOException      Si ocurre un error al leer o escribir datos.
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    try {
	        HttpSession session = request.getSession(false);
	        String tipoUsuario = (session != null) ? (String) session.getAttribute("tipoUsuario") : null;

	        // 🔐 Solo administrador
	        if (!"administrador".equals(tipoUsuario)) {
	            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	            response.getWriter().write("Acceso denegado. Solo los administradores pueden eliminar clubes.");
	            Log.ficheroLog("Intento de eliminación de club no autorizado");
	            return;
	        }

	        String idClubParam = request.getParameter("idClub");
	        if (idClubParam == null || idClubParam.isEmpty()) {
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	            response.getWriter().write("ID de club no proporcionado.");
	            Log.ficheroLog("Eliminación de club fallida: ID no proporcionado");
	            return;
	        }

	        Long idClub = Long.parseLong(idClubParam);
	        boolean eliminado = servicio.eliminarClub(idClub, request);

	        if (eliminado) {
	            Log.ficheroLog("Club eliminado correctamente: id=" + idClub);
	            response.setStatus(HttpServletResponse.SC_OK);
	            response.getWriter().write("Club eliminado correctamente.");
	        } else {
	            Log.ficheroLog("Error al eliminar club: id=" + idClub);
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            response.getWriter().write("Error al eliminar el club.");
	        }

	    } catch (NumberFormatException e) {
	        Log.ficheroLog("Error de formato en ID de club para eliminar: " + e.getMessage());
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        response.getWriter().write("ID de club no válido.");
	    } catch (Exception e) {
	        Log.ficheroLog("Error en DELETE /club: " + e.getMessage());
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().write("Error en el servidor: " + e.getMessage());
	    }
	}



	private void activarPremiumClub(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("clubId") == null) {
			response.sendRedirect("InicioSesion.jsp");
			return;
		}

		Long clubId = (Long) session.getAttribute("clubId");
		boolean actualizado = servicio.marcarPremium( clubId, request);

		if (actualizado) {
			session.setAttribute("esPremium", true);
			response.sendRedirect("PagoPremium.jsp?mensajePago=exitoso");
		} else {
			response.sendRedirect("PagoPremium.jsp?mensajePago=error");
		}
	}

}
