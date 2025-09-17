package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
	 * Método POST que maneja la creación de un miembro en el club.
	 * @param request Objeto HttpServletRequest que contiene los parámetros de la solicitud.
	 * @param response Objeto HttpServletResponse para enviar la respuesta.
	 * @throws ServletException En caso de error en la solicitud.
	 * @throws IOException En caso de error en la respuesta.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String accion = request.getParameter("accion");

			if ("aniadir".equals(accion)) {
				String fechaAltaUsuarioForm = LocalDate.now().toString();
				String fechaBajaUsuarioForm = ("9999-01-01");
				long clubIdForm = Long.parseLong(request.getParameter("idClub"));
				long usuarioIdForm = Long.parseLong(request.getParameter("usuarioId"));
				MiembroClubDto nuevoMiembro = new MiembroClubDto();
				nuevoMiembro.setFechaAltaUsuario(fechaAltaUsuarioForm);
				nuevoMiembro.setFechaBajaUsuario(fechaBajaUsuarioForm);

				nuevoMiembro.setIdClub(clubIdForm);
				nuevoMiembro.setUsuarioId(usuarioIdForm);
				servicio.guardarMiembroClub(nuevoMiembro);
				Log.ficheroLog("Miembro del club añadido: Usuario ID " + usuarioIdForm + ", Club ID " + clubIdForm);

				response.getWriter().write("Miembro del club creado correctamente.");
			} else if ("modificar".equals(accion)) {
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
	 * Método GET que obtiene una lista de clubes con sus jugadores.
	 * @param request Objeto HttpServletRequest que contiene los parámetros de la solicitud.
	 * @param response Objeto HttpServletResponse para enviar la respuesta.
	 * @throws ServletException En caso de error en la solicitud.
	 * @throws IOException En caso de error en la respuesta.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        HttpSession session = request.getSession(false);
	        if (session == null || session.getAttribute("clubId") == null) {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            response.getWriter().write("Usuario no logueado como club.");
	            return;
	        }

	        Long clubId = (Long) session.getAttribute("clubId");
	        Long usuarioId = Long.parseLong(request.getParameter("usuarioId"));
	        boolean esMiembro = servicio.esMiembroDelClub(clubId, usuarioId);
	        ArrayList<MiembroClubDto> listaMiembros = servicio.listarMiembrosClub(clubId);
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");

	        JSONArray jsonArray = new JSONArray();
	        for (MiembroClubDto miembro : listaMiembros) {
	            JSONObject json = new JSONObject();
	            json.put("idMiembroClub", miembro.getIdMiembroClub());
	            json.put("fechaAltaUsuario", miembro.getFechaAltaUsuario());
	            json.put("fechaBajaUsuario", miembro.getFechaBajaUsuario());
	            json.put("clubId", miembro.getIdClub());
	            json.put("usuarioId", miembro.getUsuarioId());

	            if (miembro.getUsuario() != null) {
	                JSONObject jsonUsuario = new JSONObject();
	                jsonUsuario.put("idUsuario", miembro.getUsuario().getIdUsuario());
	                jsonUsuario.put("nombreCompletoUsuario", miembro.getUsuario().getNombreCompletoUsuario());
	                jsonUsuario.put("aliasUsuario", miembro.getUsuario().getAliasUsuario());
	                json.put("usuario", jsonUsuario);
	            }
	            jsonArray.put(json);
	        }
	        JSONObject jsonResponse = new JSONObject();
	        jsonResponse.put("esMiembro", esMiembro);  
	        jsonResponse.put("miembros", jsonArray);
	        response.getWriter().write(jsonResponse.toString());

	    } catch (Exception e) {
	        e.printStackTrace();
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().write("Error al recuperar la lista de miembros.");
	    }
	}

	
	
	


}
