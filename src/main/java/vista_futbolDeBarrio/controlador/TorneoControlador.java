package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.EquipoTorneoDto;
import vista_futbolDeBarrio.dtos.TorneoDto;
import vista_futbolDeBarrio.enums.Modalidad;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.EquipoTorneoServicio;
import vista_futbolDeBarrio.servicios.PartidoTorneoServicio;
import vista_futbolDeBarrio.servicios.TorneoServicio;

@WebServlet("/torneo")
@MultipartConfig
/**
 * Clase controlador que se encarga de los metodos CRUD de la tabla torneo
 */
public class TorneoControlador extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private TorneoServicio torneoServicio;
	private PartidoTorneoServicio partidoTorneoServicio;
	private EquipoTorneoServicio equipoTorneoServicio;

	@Override
	public void init() throws ServletException {
		this.torneoServicio = new TorneoServicio();
		this.partidoTorneoServicio = new PartidoTorneoServicio();
		this.equipoTorneoServicio = new EquipoTorneoServicio();
	}

	/**
	 * Método POST que guarda un nuevo torneo.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene los parámetros de la
	 *                 solicitud.
	 * @param response Objeto HttpServletResponse para enviar la respuesta.
	 * @throws ServletException En caso de error en la solicitud.
	 * @throws IOException      En caso de error en la respuesta.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try {
			String accion = request.getParameter("accion");

			// ---------------------------------------------------------
			// 1️⃣ AÑADIR TORNEO
			// ---------------------------------------------------------
			if ("aniadir".equals(accion)) {

				String nombreTorneo = request.getParameter("nombreTorneo");
				String descripcion = request.getParameter("descripcionTorneo");
				String modalidadStr = request.getParameter("modalidad");
				Long instalacionId = (Long) request.getSession().getAttribute("idInstalacion");
				String fechaInicio = request.getParameter("fechaInicioTorneo");
				String fechaFin = request.getParameter("fechaFinTorneo");

				
				if (fechaFin == null || fechaFin.isEmpty())
					fechaFin = "9999-01-01";

				Modalidad modalidad = Modalidad.valueOf(modalidadStr.trim());


				TorneoDto torneo = new TorneoDto();
				torneo.setNombreTorneo(nombreTorneo);
				torneo.setDescripcionTorneo(descripcion);
				torneo.setModalidad(modalidad);
				torneo.setEstaActivo(false);
				torneo.setInstalacionId(instalacionId);
				torneo.setFechaInicioTorneo(fechaInicio);
				torneo.setFechaFinTorneo(fechaFin);


				torneoServicio.guardarTorneo(torneo, request);

				response.getWriter().write("{\"mensaje\":\"Torneo creado correctamente\"}");
				Log.ficheroLog("Torneo creado: " + nombreTorneo);
			}

			// ---------------------------------------------------------
			// 2️⃣ MODIFICAR TORNEO
			// ---------------------------------------------------------
			else if ("modificar".equals(accion)) {

				Long idTorneo = Long.parseLong(request.getParameter("idTorneo"));
				String nombreTorneo = request.getParameter("nombreTorneo");
				String descripcion = request.getParameter("descripcionTorneo");
				String modalidadStr = request.getParameter("modalidad");
			    String fechaInicio = request.getParameter("fechaInicioTorneo");
			    String fechaFin = request.getParameter("fechaFinTorneo");
			    String estaActivoStr = request.getParameter("estaActivo");

			    
				Modalidad modalidad = Modalidad.valueOf(modalidadStr.trim());
			    boolean estaActivo = "true".equalsIgnoreCase(estaActivoStr); 


				TorneoDto torneo = new TorneoDto();
				torneo.setNombreTorneo(nombreTorneo);
				torneo.setDescripcionTorneo(descripcion);
				torneo.setModalidad(modalidad);
				torneo.setFechaInicioTorneo(fechaInicio);  
			    torneo.setFechaFinTorneo(fechaFin);
			    torneo.setEstaActivo(estaActivo);

				boolean exito = torneoServicio.modificarTorneo(idTorneo, torneo, request);

				if (exito) {
					response.getWriter().write("{\"mensaje\":\"Torneo modificado correctamente\"}");
					Log.ficheroLog("Torneo modificado: id=" + idTorneo);
				} else {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					response.getWriter().write("{\"error\":\"Error al modificar torneo\"}");
				}
			}

			// ---------------------------------------------------------
			// 3️⃣ GENERAR BRACKET (desde AJAX en admin)
			// ---------------------------------------------------------
			else if ("generarBracket".equals(accion)) {

				Long torneoId = Long.parseLong(request.getParameter("torneoId"));

				torneoServicio.generarBracket(torneoId, torneoServicio, equipoTorneoServicio, partidoTorneoServicio, request);

				response.getWriter().write("{\"mensaje\":\"Bracket generado correctamente\"}");
				Log.ficheroLog("Bracket generado para torneo id=" + torneoId);
			}

			// ---------------------------------------------------------
			// 4️⃣ ACTIVAR TORNEO (genera automáticamente los OCTAVOS)
			// ---------------------------------------------------------
			else if ("activar".equals(accion)) {

				Long torneoId = Long.parseLong(request.getParameter("idTorneo"));

				// Actualizar clubes inscritos
				torneoServicio.actualizarClubesInscritos(torneoId, request);

				// Validar que haya 16 equipos reales
				int inscritos = equipoTorneoServicio.contarEquiposPorTorneo(torneoId);
				if (inscritos != 16) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					response.getWriter().write("{\"error\":\"No hay 16 equipos inscritos\"}");
					return;
				}

				// Obtener torneo
				TorneoDto torneo = torneoServicio.obtenerTodosLosTorneos().stream()
						.filter(t -> t.getIdTorneo() == torneoId).findFirst().orElse(null);

				if (torneo == null) {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					response.getWriter().write("{\"error\":\"Torneo no encontrado\"}");
					return;
				}

				try {
					// 👉 ❗ SOLO ESTE método crea octavos
					torneoServicio.generarBracket(torneoId, torneoServicio, equipoTorneoServicio,
							partidoTorneoServicio, request);

					torneo.setEstaActivo(true);
					boolean exito = torneoServicio.modificarTorneo(torneoId, torneo, request);

					if (exito) {
						response.setStatus(HttpServletResponse.SC_OK);
						response.getWriter()
								.write("{\"mensaje\":\"Torneo activado y bracket generado correctamente\"}");
						Log.ficheroLog("Torneo activado: id=" + torneoId);
					} else {
						response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						response.getWriter().write("{\"error\":\"No se pudo activar el torneo\"}");
					}

				} catch (Exception e) {
					e.printStackTrace();
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					response.getWriter().write("{\"error\":\"Error al generar el bracket: " + e.getMessage() + "\"}");
					Log.ficheroLog("Error generando bracket: " + e.getMessage());
				}
			}

			// ---------------------------------------------------------
			// 5️⃣ ACCIÓN DESCONOCIDA
			// ---------------------------------------------------------
			else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("{\"error\":\"Acción no válida\"}");
			}

		} catch (Exception e) {
			Log.ficheroLog("Error en POST /torneo: " + e.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"error\":\"Error en el servidor: " + e.getMessage() + "\"}");
		}
	}

	@Override
	/**
	 * Método GET que obtiene la lista de todos los torneos o los torneos de una
	 * instalación específica.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene los parámetros de la
	 *                 solicitud.
	 * @param response Objeto HttpServletResponse para enviar la respuesta.
	 * @throws ServletException En caso de error en la solicitud.
	 * @throws IOException      En caso de error en la respuesta.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try {
			String idParam = request.getParameter("instalacionId");
			List<TorneoDto> torneos;

			if (idParam != null && !idParam.isEmpty()) {
				Long instalacionId = Long.parseLong(idParam);
				torneos = torneoServicio.obtenerTorneosPorInstalacion(instalacionId);
			} else {
				torneos = torneoServicio.obtenerTodosLosTorneos();
			}

			// Creamos un array de objetos JSON para añadir el progreso dinámicamente
			List<JSONObject> torneosJson = new ArrayList<>();
			for (TorneoDto torneo : torneos) {
				// Actualizamos clubesInscritos con el número real
				String progreso = torneoServicio.progresoEquipos(torneo.getIdTorneo());
				torneo.setClubesInscritos(progreso);

				JSONObject obj = new JSONObject(new Gson().toJson(torneo));
				obj.put("progresoEquipos", progreso); // opcional si quieres un campo extra
				torneosJson.add(obj);
			}

			response.getWriter().write(torneosJson.toString());

		} catch (Exception e) {
			Log.ficheroLog("Error en TorneoControlador: " + e.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"error\": \"Error al obtener torneos\"}");
		}
	}

	@Override
	/**
	 * Método DELETE que elimina un torneo.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene los parámetros de la
	 *                 solicitud.
	 * @param response Objeto HttpServletResponse para enviar la respuesta.
	 * @throws ServletException En caso de error en la solicitud.
	 * @throws IOException      En caso de error en la respuesta.
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			String tipoInstalacion = (session != null) ? (String) session.getAttribute("tipoInstalacion") : null;

			String idTorneoParam = request.getParameter("idTorneo");
			if (idTorneoParam == null || idTorneoParam.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("ID de torneo no proporcionado.");
				Log.ficheroLog("Eliminación fallida: ID no proporcionado");
				return;
			}

			Long idTorneo = Long.parseLong(idTorneoParam);
			boolean eliminado = torneoServicio.eliminarTorneo(idTorneo, request);
			if (eliminado) {
				Log.ficheroLog("Torneo eliminado: id=" + idTorneo);
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("Torneo eliminado correctamente.");
			} else {
				Log.ficheroLog("Error al eliminar torneo: id=" + idTorneo);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().write("Error al eliminar el torneo.");
			}
		} catch (NumberFormatException e) {
			Log.ficheroLog("Error de formato en ID de torneo para eliminar: " + e.getMessage());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("ID de torneo no válido.");
		} catch (Exception e) {
			Log.ficheroLog("Error en DELETE /torneo: " + e.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("Error en el servidor: " + e.getMessage());
		}
	}

}
