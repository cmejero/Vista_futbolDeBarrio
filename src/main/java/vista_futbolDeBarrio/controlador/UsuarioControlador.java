package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import vista_futbolDeBarrio.dtos.UsuarioDto;
import vista_futbolDeBarrio.enums.Estado;
import vista_futbolDeBarrio.enums.RolUsuario;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.UsuarioServicio;
import vista_futbolDeBarrio.utilidades.Utilidades;

@WebServlet("/usuario")
@MultipartConfig
/**
 * Clase controlador que se encarga de los metodos CRUD de la tabla usuario
 */
public class UsuarioControlador extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UsuarioServicio servicio;

	@Override
	public void init() throws ServletException {
		this.servicio = new UsuarioServicio();
	}

	@Override
	/**
	 * Maneja la creación o modificación de un usuario.
	 * 
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws ServletException Si ocurre un error durante la ejecución del servlet.
	 * @throws IOException      Si ocurre un error al leer o escribir datos.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			String tipoUsuario = (session != null) ? (String) session.getAttribute("tipoUsuario") : null;

			String accion = request.getParameter("accion");
			if ("activarPremium".equals(accion)) {
				activarPremiumUsuario(request, response);
				return;
			}

			if (tipoUsuario == null) {
				crearUsuario(request, response);
			} else if ("administrador".equals(tipoUsuario)) {
				modificarUsuario(request, response);
			} else {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "No tienes permiso para esta acción.");
				Log.ficheroLog("Intento de acceso no autorizado a POST /usuario");
			}

		} catch (Exception e) {
			Log.ficheroLog("Error en doPost /usuario: " + e.getMessage());
			e.printStackTrace();
			response.getWriter().write("Se ha producido un error en el servidor. Por favor, inténtelo más tarde.");
		}
	}

	/**
	 * Crea un nuevo usuario.
	 * 
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws IOException      Si ocurre un error al escribir en la respuesta.
	 * @throws ServletException Si ocurre un error durante la ejecución del servlet.
	 */
	private void crearUsuario(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ServletContext context = request.getServletContext();

		String nombreCompleto = request.getParameter("nombreCompletoUsuario");
		String alias = request.getParameter("aliasUsuario");
		String fechaNac = request.getParameter("fechaNacimientoUsuario");
		String email = request.getParameter("emailUsuario");
		String telefono = request.getParameter("telefonoUsuario");
		String password = request.getParameter("passwordUsuario");
		String password2 = request.getParameter("passwordUsuario2");
		String rolString = request.getParameter("rolUsuario");

		if (!password.equals(password2)) {
			response.sendRedirect("InicioSesion.jsp?mensaje=error_password");
			return;
		}

		RolUsuario rol = RolUsuario.valueOf(rolString);

		Part imagenPart = request.getPart("imagenUsuario");
		byte[] imagenBytes = null;
		if (imagenPart != null && imagenPart.getSize() > 0) {
			imagenBytes = new byte[(int) imagenPart.getSize()];
			try (InputStream inputStream = imagenPart.getInputStream()) {
				inputStream.read(imagenBytes);
			}
		} else {
			imagenBytes = Utilidades.obtenerImagenPorDefecto(context);
		}

		UsuarioDto usuario = new UsuarioDto();
		usuario.setNombreCompletoUsuario(nombreCompleto);
		usuario.setAliasUsuario(alias);
		usuario.setFechaNacimientoUsuario(fechaNac);
		usuario.setEmailUsuario(email);
		usuario.setTelefonoUsuario(telefono);
		usuario.setPasswordUsuario(password);
		usuario.setRolUsuario(rol);
		usuario.setImagenUsuario(imagenBytes);
		usuario.setDescripcionUsuario(request.getParameter("descripcionUsuario"));
		usuario.setEstadoUsuario(Estado.Activo);

		// ✅ Guardar usuario en API y capturar resultado
		String resultado = servicio.guardarUsuario(usuario);

		switch (resultado) {
		    case "ok":
		        response.sendRedirect("InicioSesion.jsp?mensajeAlta=registro_exitoso"); 
		        break;
		    case "usuario_existente":
		        response.sendRedirect("InicioSesion.jsp?mensajeAlta=usuario_existente");
		        break;
		    case "email_invalido":
		        response.sendRedirect("InicioSesion.jsp?mensajeAlta=email_invalido");
		        break;
		    default:
		        response.sendRedirect("InicioSesion.jsp?mensajeAlta=error_servidor");
		        break;
		}


	}

	/**
	 * Modifica un usuario existente.
	 * 
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws IOException      Si ocurre un error al escribir en la respuesta.
	 * @throws ServletException Si ocurre un error durante la ejecución del servlet.
	 */
	private void modificarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String idUsuarioForm = request.getParameter("idUsuario");

		if (idUsuarioForm == null || idUsuarioForm.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.getWriter().write("Los administradores solo pueden modificar usuarios existentes.");
			Log.ficheroLog("Intento de modificación sin ID de usuario");
			return;
		}

		String nombre = request.getParameter("nombreCompletoUsuarioEditar");
		String alias = request.getParameter("aliasUsuarioEditar");
		String fechaNac = request.getParameter("fechaNacimientoUsuarioEditar");
		String email = request.getParameter("emailUsuarioEditar");
		String telefono = request.getParameter("telefonoUsuarioEditar");
		String password = request.getParameter("passwordUsuarioEditar");
		String rolString = request.getParameter("rolUsuarioEditar");
		String descripcion = request.getParameter("descripcionUsuarioEditar");
		String estadoStr = request.getParameter("estadoUsuarioEditar");

		RolUsuario rol = RolUsuario.valueOf(rolString);
		System.out.println(rol);
		Estado estado = Estado.valueOf(estadoStr);

		Part imagenPart = request.getPart("imagenUsuarioEditar");
		byte[] imagenBytes = null;

		if (imagenPart != null && imagenPart.getSize() > 0) {
			imagenBytes = new byte[(int) imagenPart.getSize()];
			try (InputStream inputStream = imagenPart.getInputStream()) {
				inputStream.read(imagenBytes);
			}
		}

		UsuarioDto usuarioModificado = new UsuarioDto();
		usuarioModificado.setIdUsuario(Long.parseLong(idUsuarioForm));
		usuarioModificado.setNombreCompletoUsuario(nombre);
		usuarioModificado.setAliasUsuario(alias);
		usuarioModificado.setFechaNacimientoUsuario(fechaNac);
		usuarioModificado.setEmailUsuario(email);
		usuarioModificado.setTelefonoUsuario(telefono);
		usuarioModificado.setPasswordUsuario(password);
		usuarioModificado.setRolUsuario(rol);
		usuarioModificado.setDescripcionUsuario(descripcion);
		usuarioModificado.setEstadoUsuario(estado);
		usuarioModificado.setImagenUsuario(imagenBytes);

		boolean actualizado = servicio.modificarUsuario(request, idUsuarioForm, usuarioModificado);

		if (actualizado) {
			Log.ficheroLog("Usuario modificado: id=" + idUsuarioForm + ", alias=" + alias);
			response.getWriter().write("Usuario modificado correctamente.");
		} else {
			Log.ficheroLog("Error al modificar usuario: id=" + idUsuarioForm);
			response.getWriter().write("No se pudo modificar el usuario.");
		}
	}

	@Override
	/**
	 * Recupera una lista de todos los usuarios en formato JSON.
	 * 
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws ServletException Si ocurre un error durante la ejecución del servlet.
	 * @throws IOException      Si ocurre un error al leer o escribir datos.
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			String tipoUsuario = (session != null) ? (String) session.getAttribute("tipoUsuario") : null;

			if (tipoUsuario == null || tipoUsuario.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.setContentType("text/plain; charset=UTF-8");
				response.getWriter().write("Acceso denegado. Debe iniciar sesión para acceder.");
				Log.ficheroLog("Intento de acceso no autorizado sin sesión a GET /usuario");
				return;
			}

			// Verificar si llega un parámetro idUsuario
			String idParam = request.getParameter("idUsuario");
			ObjectMapper objectMapper = new ObjectMapper();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			if (idParam != null && !idParam.isEmpty()) {
				Long idUsuario = Long.parseLong(idParam);
				UsuarioDto usuario = servicio.obtenerUsuario(request, idUsuario);
				if (usuario != null) {
					response.getWriter().write(objectMapper.writeValueAsString(usuario));
				} else {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					response.getWriter().write("{\"mensaje\":\"Usuario no encontrado.\"}");
					Log.ficheroLog("Usuario no encontrado: id=" + idUsuario);
				}
			} else {
				ArrayList<UsuarioDto> listaUsuario = servicio.obtenerUsuarios(request);
				response.getWriter().write(objectMapper.writeValueAsString(listaUsuario));
			}

		} catch (NumberFormatException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("{\"mensaje\":\"ID de usuario no válido.\"}");
			Log.ficheroLog("Error de formato en ID de usuario: " + e.getMessage());
		} catch (Exception e) {
			Log.ficheroLog("Error en GET /usuario: " + e.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain; charset=UTF-8");
			response.getWriter().write("Error en el servidor: " + e.getMessage());
		}

	}

	@Override
	/**
	 * Elimina un usuario por su ID.
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

			if (!"administrador".equals(tipoUsuario)) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.getWriter().write("Acceso denegado. Solo los administradores pueden eliminar usuarios.");
				Log.ficheroLog("Intento de eliminación no autorizado");
				return;
			}

			String idUsuarioParam = request.getParameter("idUsuario");
			if (idUsuarioParam == null || idUsuarioParam.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("ID de usuario no proporcionado.");
				Log.ficheroLog("Eliminación fallida: ID no proporcionado");
				return;
			}

			Long idUsuario = Long.parseLong(idUsuarioParam);
			boolean eliminado = servicio.eliminarUsuario(idUsuario, request);

			if (eliminado) {
				Log.ficheroLog("Usuario eliminado: id=" + idUsuario);
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("Usuario eliminado correctamente.");
			} else {
				Log.ficheroLog("Error al eliminar usuario: id=" + idUsuario);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().write("Error al eliminar el usuario.");
			}
		} catch (NumberFormatException e) {
			Log.ficheroLog("Error de formato en ID de usuario para eliminar: " + e.getMessage());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("ID de usuario no válido.");
		} catch (Exception e) {
			Log.ficheroLog("Error en DELETE /usuario: " + e.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("Error en el servidor: " + e.getMessage());
		}
	}

	private void activarPremiumUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("usuarioId") == null) {
			response.sendRedirect("InicioSesion.jsp");
			return;
		}

		Long jugadorId = (Long) session.getAttribute("usuarioId");
		boolean actualizado = servicio.marcarPremium(jugadorId, request);

		if (actualizado) {
			session.setAttribute("esPremium", true);
			response.sendRedirect("PagoPremium.jsp?mensajePago=exitoso");
		} else {
			response.sendRedirect("PagoPremium.jsp?mensajePago=error");
		}
	}

}
