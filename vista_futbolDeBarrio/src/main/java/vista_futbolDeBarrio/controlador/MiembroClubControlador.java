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
				long clubIdForm = Long.parseLong(request.getParameter("idClub"));
				long usuarioIdForm = Long.parseLong(request.getParameter("usuarioId"));

				// Crear el objeto MiembroClubDto
				MiembroClubDto nuevoMiembro = new MiembroClubDto();
				nuevoMiembro.setFechaAltaUsuario(fechaAltaUsuarioForm);
				nuevoMiembro.setFechaBajaUsuario(fechaBajaUsuarioForm);

				nuevoMiembro.setIdClub(clubIdForm);
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
	        HttpSession session = request.getSession(false);
	        if (session == null || session.getAttribute("clubId") == null) {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            response.getWriter().write("Usuario no logueado como club.");
	            return;
	        }

	        Long clubId = (Long) session.getAttribute("clubId");
	        Long usuarioId = Long.parseLong(request.getParameter("usuarioId"));  // Obtener usuarioId desde la solicitud

	        // Verificar si el usuario ya es miembro del club
	        boolean esMiembro = servicio.esMiembroDelClub(clubId, usuarioId);

	        // Lista de miembros del club
	        ArrayList<MiembroClubDto> listaMiembros = servicio.listarMiembrosClub(clubId);

	        // Preparar la respuesta JSON
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");

	        JSONArray jsonArray = new JSONArray();
	        
	        // Iterar sobre la lista de miembros y agregar cada uno al JSON
	        for (MiembroClubDto miembro : listaMiembros) {
	            JSONObject json = new JSONObject();
	            json.put("idMiembroClub", miembro.getIdMiembroClub());
	            json.put("fechaAltaUsuario", miembro.getFechaAltaUsuario());
	            json.put("fechaBajaUsuario", miembro.getFechaBajaUsuario());
	            json.put("clubId", miembro.getIdClub());
	            json.put("usuarioId", miembro.getUsuarioId());

	            // Incluir detalles del usuario si están disponibles
	            if (miembro.getUsuario() != null) {
	                JSONObject jsonUsuario = new JSONObject();
	                jsonUsuario.put("idUsuario", miembro.getUsuario().getIdUsuario());
	                jsonUsuario.put("nombreCompletoUsuario", miembro.getUsuario().getNombreCompletoUsuario());
	                jsonUsuario.put("aliasUsuario", miembro.getUsuario().getAliasUsuario());
	                json.put("usuario", jsonUsuario);
	            }

	            jsonArray.put(json);
	        }

	        // Agregar la información sobre si el usuario ya es miembro
	        JSONObject jsonResponse = new JSONObject();
	        jsonResponse.put("esMiembro", esMiembro);  // Si el usuario ya es miembro, se indica en la respuesta
	        jsonResponse.put("miembros", jsonArray);   // Agregar la lista de miembros del club

	        // Enviar la respuesta al cliente
	        response.getWriter().write(jsonResponse.toString());

	    } catch (Exception e) {
	        e.printStackTrace();
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().write("Error al recuperar la lista de miembros.");
	    }
	}

	
	
	


}
