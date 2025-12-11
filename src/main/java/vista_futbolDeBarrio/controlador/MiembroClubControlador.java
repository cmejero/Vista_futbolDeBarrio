package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.ClubDto;
import vista_futbolDeBarrio.dtos.JugadorEstadisticaGlobalDto;
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
	 * 
	 * @param request  Objeto HttpServletRequest que contiene los parámetros de la
	 *                 solicitud.
	 * @param response Objeto HttpServletResponse para enviar la respuesta.
	 * @throws ServletException En caso de error en la solicitud.
	 * @throws IOException      En caso de error en la respuesta.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String accion = request.getParameter("accion");

			if ("aniadir".equals(accion)) {
			    try {
			        String fechaAltaUsuarioForm = LocalDate.now().toString();
			        String fechaBajaUsuarioForm = "9999-01-01";
			        long clubIdForm = Long.parseLong(request.getParameter("idClub"));
			        long usuarioIdForm = Long.parseLong(request.getParameter("usuarioId"));

			        MiembroClubDto nuevoMiembro = new MiembroClubDto();
			        nuevoMiembro.setFechaAltaUsuario(fechaAltaUsuarioForm);
			        nuevoMiembro.setFechaBajaUsuario(fechaBajaUsuarioForm);
			        nuevoMiembro.setIdClub(clubIdForm);
			        nuevoMiembro.setUsuarioId(usuarioIdForm);

			        servicio.guardarMiembroClub(nuevoMiembro);

			        // Guardado exitoso
			        response.setStatus(HttpServletResponse.SC_OK);
			        response.getWriter().write("Te has unido al club exitosamente.");

			    } catch (IllegalStateException e) {
			        // Límite de clubes o de jugadores alcanzado
			        response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
			        response.getWriter().write(e.getMessage());
			    } catch (Exception e) {
			        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
			        response.getWriter().write("Error del servidor: " + e.getMessage());
			    }
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
	 * 
	 * @param request  Objeto HttpServletRequest que contiene los parámetros de la
	 *                 solicitud.
	 * @param response Objeto HttpServletResponse para enviar la respuesta.
	 * @throws ServletException En caso de error en la solicitud.
	 * @throws IOException      En caso de error en la respuesta.
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Usuario no logueado.");
				return;
			}

			Long usuarioId = (Long) session.getAttribute("usuarioId");
			Long clubId = (Long) session.getAttribute("clubId");

			// Si vienen por parámetro GET, los usamos
			if (request.getParameter("clubId") != null) {
				clubId = Long.parseLong(request.getParameter("clubId"));
			}

			String tipoRespuesta = request.getParameter("tipo"); // "j

			boolean esMiembro = false;
			if (clubId != null && usuarioId != null) {
				esMiembro = servicio.esMiembroDelClub(clubId, usuarioId);
			}

			if ("jugadores".equalsIgnoreCase(tipoRespuesta) && clubId != null) {

				List<JugadorEstadisticaGlobalDto> jugadores = servicio.listarJugadoresPorClub(clubId);

				List<MiembroClubDto> miembros = servicio.listarMiembrosClub(clubId);

				Map<Long, MiembroClubDto> mapaMiembrosPorUsuario = new HashMap<>();
				if (miembros != null) {
					for (MiembroClubDto m : miembros) {

						mapaMiembrosPorUsuario.put(m.getUsuarioId(), m);
					}
				}

				JSONArray jsonArray = new JSONArray();

				for (JugadorEstadisticaGlobalDto j : jugadores) {
					JSONObject jsonJugador = new JSONObject();

					jsonJugador.put("nombreJugador", j.getNombreJugador());
					jsonJugador.put("aliasJugador", j.getAliasJugador());
					jsonJugador.put("partidosJugadosGlobal", j.getPartidosJugadosGlobal());
					jsonJugador.put("partidosGanadosGlobal", j.getPartidosGanadosGlobal());
					jsonJugador.put("partidosPerdidosGlobal", j.getPartidosPerdidosGlobal());
					jsonJugador.put("golesGlobal", j.getGolesGlobal());
					jsonJugador.put("asistenciasGlobal", j.getAsistenciasGlobal());
					jsonJugador.put("amarillasGlobal", j.getAmarillasGlobal());
					jsonJugador.put("rojasGlobal", j.getRojasGlobal());
					jsonJugador.put("minutosJugadosGlobal", j.getMinutosJugadosGlobal());
					jsonJugador.put("usuarioId", j.getJugadorGlobalId());
					jsonJugador.put("jugadorGlobalId", j.getJugadorGlobalId());

					MiembroClubDto miembro = mapaMiembrosPorUsuario.get(j.getJugadorGlobalId());

					if (miembro != null) {
						jsonJugador.put("idMiembroClub", miembro.getIdMiembroClub());

						JSONObject miembroJson = new JSONObject();
						miembroJson.put("idMiembroClub", miembro.getIdMiembroClub());
						miembroJson.put("fechaAltaUsuario", miembro.getFechaAltaUsuario() == null ? JSONObject.NULL
								: miembro.getFechaAltaUsuario());
						miembroJson.put("fechaBajaUsuario", miembro.getFechaBajaUsuario() == null ? JSONObject.NULL
								: miembro.getFechaBajaUsuario());
						miembroJson.put("clubId", miembro.getIdClub());
						miembroJson.put("usuarioId", miembro.getUsuarioId());

						
						ClubDto club = miembro.getClub();
						if (club != null) {
							JSONObject jsonClub = new JSONObject();
							jsonClub.put("idClub", club.getIdClub());
							jsonClub.put("nombreClub", club.getNombreClub());
							jsonClub.put("abreviaturaClub", club.getAbreviaturaClub());
							jsonClub.put("descripcionClub", club.getDescripcionClub());
							jsonClub.put("localidadClub", club.getLocalidadClub());
							jsonClub.put("paisClub", club.getPaisClub());
							jsonClub.put("emailClub", club.getEmailClub());
							jsonClub.put("telefonoClub", club.getTelefonoClub());
							jsonClub.put("esPremium", club.isEsPremium());
							if (club.getLogoClub() != null && club.getLogoClub().length > 0) {
								jsonClub.put("logoBase64", Base64.getEncoder().encodeToString(club.getLogoClub()));
							} else {
								jsonClub.put("logoBase64", JSONObject.NULL);
							}
							miembroJson.put("club", jsonClub);
						} else {
							miembroJson.put("club", JSONObject.NULL);
						}

						jsonJugador.put("miembroClub", miembroJson);

					} else {
					
						jsonJugador.put("idMiembroClub", JSONObject.NULL);
						jsonJugador.put("miembroClub", JSONObject.NULL);
					}

					jsonArray.put(jsonJugador);
				}

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jsonArray.toString());
				return;
			}

			if (usuarioId != null) {
				List<MiembroClubDto> misClubes = servicio.listarMisClubesPorUsuario(usuarioId);

				if ("json".equalsIgnoreCase(tipoRespuesta)) {
					JSONArray jsonArray = new JSONArray();
					for (MiembroClubDto miembro : misClubes) {
						JSONObject json = new JSONObject();
						json.put("idMiembroClub", miembro.getIdMiembroClub());
						json.put("fechaAltaUsuario", miembro.getFechaAltaUsuario());
						json.put("fechaBajaUsuario", miembro.getFechaBajaUsuario());
						json.put("clubId", miembro.getIdClub());
						json.put("usuarioId", miembro.getUsuarioId());

						ClubDto club = miembro.getClub();
						if (club != null) {
							JSONObject jsonClub = new JSONObject();
							jsonClub.put("idClub", club.getIdClub());
							jsonClub.put("nombreClub", club.getNombreClub());
							jsonClub.put("abreviaturaClub", club.getAbreviaturaClub());
							jsonClub.put("descripcionClub", club.getDescripcionClub());
							jsonClub.put("localidadClub", club.getLocalidadClub());
							jsonClub.put("paisClub", club.getPaisClub());
							jsonClub.put("emailClub", club.getEmailClub());
							jsonClub.put("telefonoClub", club.getTelefonoClub());
							jsonClub.put("esPremium", club.isEsPremium());
							if (club.getLogoClub() != null && club.getLogoClub().length > 0) {
								jsonClub.put("logoBase64", Base64.getEncoder().encodeToString(club.getLogoClub()));
							} else {
								jsonClub.put("logoBase64", JSONObject.NULL);
							}
							json.put("club", jsonClub);
						}
						jsonArray.put(json);
					}

					JSONObject jsonResponse = new JSONObject();
					jsonResponse.put("esMiembro", esMiembro);
					jsonResponse.put("misClubes", jsonArray);

					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(jsonResponse.toString());
				} else {
					request.setAttribute("misClubes", misClubes);
					request.getRequestDispatcher("/MiClubJugador.jsp").forward(request, response);
				}
			} else if (clubId != null) {
				// caso: usuario tipo club, solo mostrar jugadores
				// ya está manejado arriba, aquí solo para fallback
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("Club identificado, pero no hay usuarioId para mostrar mis clubes.");
			} else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Usuario no logueado o clubId no especificado.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("Error al recuperar la información: " + e.getMessage());
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Usuario no logueado.");
			return;
		}

		Long usuarioId = (Long) session.getAttribute("usuarioId");
		Long clubId = (Long) session.getAttribute("clubId");

		String idMiembroParam = request.getParameter("idMiembroClub");
		if (idMiembroParam == null || idMiembroParam.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("ID de miembro no proporcionado.");
			return;
		}

		Long idMiembro = Long.parseLong(idMiembroParam);
		boolean eliminado = false;

		if (usuarioId != null) {
			// Usuario elimina su propia membresía
			eliminado = servicio.eliminarMiembroClubPorUsuario(idMiembro, usuarioId);
		} else if (clubId != null) {
			// Club elimina a un miembro
			eliminado = servicio.eliminarMiembroClubPorClub(idMiembro, clubId);
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Sesión inválida.");
			return;
		}

		if (eliminado) {
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write("Miembro eliminado correctamente.");
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("No se pudo eliminar el miembro.");
		}
	}

}
