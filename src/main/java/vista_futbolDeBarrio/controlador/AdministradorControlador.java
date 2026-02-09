package vista_futbolDeBarrio.controlador;

import java.io.IOException;

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
import vista_futbolDeBarrio.dtos.InstalacionDto;
import vista_futbolDeBarrio.dtos.UsuarioDto;
import vista_futbolDeBarrio.enums.Estado;
import vista_futbolDeBarrio.enums.Modalidad;
import vista_futbolDeBarrio.enums.RolUsuario;
import vista_futbolDeBarrio.servicios.ClubServicio;
import vista_futbolDeBarrio.servicios.InstalacionServicio;
import vista_futbolDeBarrio.servicios.TorneoServicio;
import vista_futbolDeBarrio.servicios.UsuarioServicio;

@WebServlet("/administrador")
@MultipartConfig
public class AdministradorControlador extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private UsuarioServicio usuarioServicio;
	private ClubServicio clubServicio;
	private InstalacionServicio instalacionServicio;
	private TorneoServicio torneoServicio;

	@Override
	public void init() throws ServletException {
		usuarioServicio = new UsuarioServicio();
		clubServicio = new ClubServicio();
		instalacionServicio = new InstalacionServicio();
		torneoServicio = new TorneoServicio();
	}

	// ======================================================
	// GET
	// ======================================================
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String ajaxHeader = request.getHeader("X-Requested-With");

		// 👉 Navegación normal
		if (!"XMLHttpRequest".equals(ajaxHeader)) {
			request.getRequestDispatcher("/WEB-INF/Vistas/Administrador.jsp").forward(request, response);
			return;
		}

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String entidad = request.getParameter("entidad");
		ObjectMapper mapper = new ObjectMapper();

		try {
			switch (entidad) {

			case "usuario":
				response.getWriter().write(mapper.writeValueAsString(usuarioServicio.obtenerUsuarios(request)));
				break;

			case "club":
				response.getWriter().write(mapper.writeValueAsString(clubServicio.listaClub(request)));
				break;

			case "instalacion":
				response.getWriter().write(mapper.writeValueAsString(instalacionServicio.listaInstalaciones(request)));
				break;

			case "torneo":
				response.getWriter().write(mapper.writeValueAsString(torneoServicio.obtenerTodosLosTorneos()));
				break;

			default:
				response.getWriter().write("[]");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(500);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String entidad = request.getParameter("entidad");
		String accion = request.getParameter("accion");

		try {

			// ==================================================
			// USUARIO
			// ==================================================
			if ("usuario".equals(entidad) && "modificar".equals(accion)) {

			    String id = request.getParameter("idUsuario");

			    UsuarioDto usuario = new UsuarioDto();
			    usuario.setIdUsuario(Long.parseLong(id));
			    usuario.setNombreCompletoUsuario(request.getParameter("nombreCompletoUsuarioEditar"));
			    usuario.setAliasUsuario(request.getParameter("aliasUsuarioEditar"));
			    usuario.setEmailUsuario(request.getParameter("emailUsuarioEditar"));
			    usuario.setTelefonoUsuario(request.getParameter("telefonoUsuarioEditar"));
			    String estado = request.getParameter("estadoUsuarioEditar");
			    if (estado != null ) usuario.setEstadoUsuario(Estado.valueOf(estado));
			    usuario.setDescripcionUsuario(request.getParameter("descripcionUsuarioEditar"));
			    String rol = request.getParameter("rolUsuarioEditar");
			    if (rol != null) usuario.setRolUsuario(RolUsuario.valueOf(rol));

			    Part imagenPart = request.getPart("imagenUsuarioEditar");
			    if (imagenPart != null && imagenPart.getSize() > 0) {
			        usuario.setImagenUsuario(imagenPart.getInputStream().readAllBytes());
			    }

			    boolean ok = usuarioServicio.modificarUsuario(request, id, usuario);

			    response.getWriter().write(
			        ok ? "Usuario modificado correctamente." : "No se pudo modificar el usuario."
			    );
			}


			// ==================================================
			// CLUB
			// ==================================================
			if ("club".equals(entidad) && "modificar".equals(accion)) {

			    String id = request.getParameter("idClubEditar"); 

			    ClubDto club = new ClubDto();
			    club.setIdClub(Long.parseLong(id));
			    club.setNombreClub(request.getParameter("nombreClubEditar"));
			    club.setAbreviaturaClub(request.getParameter("abreviaturaClubEditar"));
			    club.setDescripcionClub(request.getParameter("descripcionClubEditar"));
			    club.setEmailClub(request.getParameter("emailClubEditar"));
			    club.setTelefonoClub(request.getParameter("telefonoClubEditar"));
			    club.setLocalidadClub(request.getParameter("localidadClubEditar"));
			    club.setPaisClub(request.getParameter("paisClubEditar"));
			    club.setFechaCreacionClub(request.getParameter("fechaCreacionClubEditar"));
			    club.setFechaFundacionClub(request.getParameter("fechaFundacionClubEditar"));

			    Part logoPart = request.getPart("logoClubEditar");
			    if (logoPart != null && logoPart.getSize() > 0) {
			        club.setLogoClub(logoPart.getInputStream().readAllBytes());
			    }

			    boolean ok = clubServicio.modificarClub(id, club, request);

			    response.getWriter().write(ok ? "Club modificado correctamente." : "No se pudo modificar el club.");
			    return;
			}

			// ==================================================
			// INSTALACION
			// ==================================================
			if ("instalacion".equals(entidad) && "modificar".equals(accion)) {

			    String id = request.getParameter("idInstalacion");
			    if (id == null || id.isEmpty()) {
			        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			        response.getWriter().write("ID de instalación no proporcionado.");
			        return;
			    }

			    InstalacionDto inst = new InstalacionDto();
			    inst.setIdInstalacion(Long.parseLong(id));
			    inst.setNombreInstalacion(request.getParameter("nombreInstalacionEditar"));
			    inst.setDireccionInstalacion(request.getParameter("direccionInstalacionEditar"));
			    inst.setTelefonoInstalacion(request.getParameter("telefonoInstalacionEditar"));
			    inst.setEmailInstalacion(request.getParameter("emailInstalacionEditar"));
			    inst.setServiciosInstalacion(request.getParameter("serviciosInstalacionEditar"));

			    // Tipos de campo
			    String tipoCampo1 = request.getParameter("tipoCampo1Editar");
			    if (tipoCampo1 != null && !tipoCampo1.isEmpty()) inst.setTipoCampo1(Modalidad.valueOf(tipoCampo1));

			    String tipoCampo2 = request.getParameter("tipoCampo2Editar");
			    if (tipoCampo2 != null && !tipoCampo2.isEmpty()) inst.setTipoCampo2(Modalidad.valueOf(tipoCampo2));

			    String tipoCampo3 = request.getParameter("tipoCampo3Editar");
			    if (tipoCampo3 != null && !tipoCampo3.isEmpty()) inst.setTipoCampo3(Modalidad.valueOf(tipoCampo3));

			    // Estado
			    String estado = request.getParameter("estadoInstalacionEditar");
			    if (estado != null && !estado.isEmpty()) inst.setEstadoInstalacion(Estado.valueOf(estado));

			    
			    // Imagen
			    Part imagenPart = request.getPart("imagenInstalacionEditar");
			    if (imagenPart != null && imagenPart.getSize() > 0) {
			        inst.setImagenInstalacion(imagenPart.getInputStream().readAllBytes());
			    }

			    boolean ok = instalacionServicio.modificarInstalacion(id, inst, request);

			    if (ok) {
			        response.getWriter().write("Instalación modificada correctamente.");
			    } else {
			        response.getWriter().write("No se pudo modificar la instalación.");
			    }

			    return;
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("error");
		}
	}

	// ======================================================
	// DELETE
	// ======================================================
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    String entidad = request.getParameter("entidad");

	    try {
	        HttpSession session = request.getSession(false);
	        String tipoUsuario = (session != null) ? (String) session.getAttribute("tipoUsuario") : null;

	        switch (entidad) {
	            case "usuario":
	                // ✅ Solo administradores pueden eliminar usuarios
	                if (!"administrador".equals(tipoUsuario)) {
	                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	                    response.getWriter().write("Acceso denegado. Solo los administradores pueden eliminar usuarios.");
	                    return;
	                }

	                String idUsuarioParam = request.getParameter("idUsuario");
	                if (idUsuarioParam == null || idUsuarioParam.isEmpty()) {
	                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	                    response.getWriter().write("ID de usuario no proporcionado.");
	                    return;
	                }

	                Long idUsuario = Long.parseLong(idUsuarioParam);
	                boolean eliminado = usuarioServicio.eliminarUsuario(idUsuario, request);

	                if (eliminado) {
	                    response.setStatus(HttpServletResponse.SC_OK);
	                    response.getWriter().write("Usuario eliminado correctamente.");
	                } else {
	                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	                    response.getWriter().write("Error al eliminar el usuario.");
	                }
	                break;

	            case "club":
	                String idClubParam = request.getParameter("idClub");
	                if (idClubParam == null || idClubParam.isEmpty()) {
	                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	                    response.getWriter().write("ID de club no proporcionado.");
	                    return;
	                }

	                Long idClub = Long.parseLong(idClubParam);
	                boolean eliminadoClub = clubServicio.eliminarClub(idClub, request);

	                if (eliminadoClub) {
	                    response.setStatus(HttpServletResponse.SC_OK);
	                    response.getWriter().write("Club eliminado correctamente.");
	                } else {
	                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	                    response.getWriter().write("Error al eliminar el club.");
	                }
	                break;

	            case "instalacion":
	                String idInstParam = request.getParameter("idInstalacion");
	                if (idInstParam == null || idInstParam.isEmpty()) {
	                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	                    response.getWriter().write("ID de instalación no proporcionado.");
	                    return;
	                }

	                Long idInst = Long.parseLong(idInstParam);
	                boolean eliminadoInst = instalacionServicio.eliminarInstalacion(idInst, request);

	                if (eliminadoInst) {
	                    response.setStatus(HttpServletResponse.SC_OK);
	                    response.getWriter().write("Instalación eliminada correctamente.");
	                } else {
	                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	                    response.getWriter().write("Error al eliminar la instalación.");
	                }
	                break;

	            default:
	                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	                response.getWriter().write("Entidad no válida para eliminación.");
	        }

	    } catch (NumberFormatException e) {
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        response.getWriter().write("ID no válido.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().write("Error en el servidor: " + e.getMessage());
	    }
	}

}
