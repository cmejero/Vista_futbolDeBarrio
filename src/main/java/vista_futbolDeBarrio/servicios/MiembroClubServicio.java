package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.ClubDto;
import vista_futbolDeBarrio.dtos.JugadorEstadisticaGlobalDto;
import vista_futbolDeBarrio.dtos.MiembroClubDto;
import vista_futbolDeBarrio.dtos.UsuarioDto;
import vista_futbolDeBarrio.enums.Estado;
import vista_futbolDeBarrio.enums.RolUsuario;
import vista_futbolDeBarrio.utilidades.Utilidades;

/**
 * Clase que se encarga de la logica de los metodos CRUD de miembro club
 */
public class MiembroClubServicio {

	/**
	 * Guarda un nuevo miembro en el club.
	 * 
	 * @param miembro El objeto que contiene los datos del miembro del club a
	 *                guardar.
	 */
	public void guardarMiembroClub(HttpServletRequest request, MiembroClubDto miembro) {
	    try {
	        // 1️⃣ Obtener el token de la sesión
	        HttpSession session = request.getSession(false);
	        if (session == null) throw new IllegalStateException("No hay sesión activa");
	        String token = (String) session.getAttribute("token");
	        if (token == null || token.isEmpty()) throw new IllegalStateException("No se encontró token JWT");

	        // 2️⃣ Extraer el email del token
	        String emailLogueado = Utilidades.extraerEmaildelToken(token);

	        // 3️⃣ Asegurarnos que el UsuarioDto existe y tiene el email correcto
	        if (miembro.getUsuario() == null) miembro.setUsuario(new UsuarioDto());
	        miembro.getUsuario().setEmailUsuario(emailLogueado);
	        miembro.getUsuario().setRolUsuario(RolUsuario.Jugador);

	        // 4️⃣ Validar límite de jugadores en el club
	        List<MiembroClubDto> miembrosActuales = listarMiembrosClub(miembro.getIdClub());
	        long activos = miembrosActuales.stream()
	                .filter(m -> "9999-01-01".equals(m.getFechaBajaUsuario()))
	                .count();

	        if (activos >= 18) {
	            throw new IllegalStateException("Este club ya tiene el máximo de 18 jugadores.");
	        }

	        // 5️⃣ Validar límite de clubes por jugador
	        List<MiembroClubDto> clubesDelJugador = listarMisClubesPorUsuario(miembro.getUsuarioId())
	                .stream()
	                .filter(m -> "9999-01-01".equals(m.getFechaBajaUsuario())) // Solo activos
	                .toList();

	        if (clubesDelJugador.size() >= 3) {
	            throw new IllegalStateException("Has alcanzado el límite de 3 clubes. No puedes unirte a más.");
	        }

	        // 6️⃣ Preparar JSON para enviar a la API
	        JSONObject json = new JSONObject();
	        json.put("fechaAltaUsuario", miembro.getFechaAltaUsuario().toString());
	        json.put("fechaBajaUsuario",
	                miembro.getFechaBajaUsuario() != null ? miembro.getFechaBajaUsuario().toString() : JSONObject.NULL);
	        json.put("idClub", miembro.getIdClub());
	        json.put("usuarioId", miembro.getUsuarioId());

	        JSONObject usuarioJson = new JSONObject();
	        usuarioJson.put("emailUsuario", miembro.getUsuario().getEmailUsuario());
	        json.put("usuario", usuarioJson);

	        // 7️⃣ Conexión a la API
	        String urlApi = "http://localhost:9527/api/guardarMiembroClub";
	        URL url = new URL(urlApi);
	        HttpURLConnection conex = (HttpURLConnection) url.openConnection();
	        conex.setRequestMethod("POST");
	        conex.setRequestProperty("Content-Type", "application/json");
	        conex.setRequestProperty("Authorization", "Bearer " + token);
	        conex.setDoOutput(true);

	        try (OutputStream os = conex.getOutputStream()) {
	            byte[] input = json.toString().getBytes("utf-8");
	            os.write(input, 0, input.length);
	        }

	        // 8️⃣ Leer la respuesta de la API
	        int responseCode = conex.getResponseCode();
	        switch (responseCode) {
	            case HttpURLConnection.HTTP_CREATED:
	                System.out.println("Miembro guardado exitosamente");
	                break;
	            case HttpURLConnection.HTTP_FORBIDDEN:
	                System.err.println("Usuario no autorizado para guardar este miembro");
	                break;
	            case HttpURLConnection.HTTP_CONFLICT:
	                System.err.println("Conflicto al guardar miembro: límite alcanzado");
	                break;
	            default:
	                System.err.println("Error desconocido. Código: " + responseCode);
	        }

	    } catch (IllegalStateException e) {
	        throw e;
	    
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("Error al guardar miembro del club");
	    }
	}

	
	public String obtenerMisClubesEnJson(Long usuarioId, Long clubId) {

	    boolean esMiembro = false;

	    if (clubId != null) {
	        esMiembro = esMiembroDelClub(clubId, usuarioId);
	    }

	    List<MiembroClubDto> misClubes = listarMisClubesPorUsuario(usuarioId);

	    JSONArray jsonArray = new JSONArray();
	    for (MiembroClubDto miembro : misClubes) {
	        jsonArray.put(convertirMiembroClubAJson(miembro));
	    }

	    JSONObject jsonResponse = new JSONObject();
	    jsonResponse.put("esMiembro", esMiembro);
	    jsonResponse.put("misClubes", jsonArray);

	    return jsonResponse.toString();
	}


	
	public String obtenerJugadoresDelClubEnJson(Long clubId) {
	    List<JugadorEstadisticaGlobalDto> jugadores = listarJugadoresPorClub(clubId);
	    List<MiembroClubDto> miembros = listarMiembrosClub(clubId);

	    Map<Long, MiembroClubDto> mapaMiembros = new HashMap<>();
	    for (MiembroClubDto m : miembros) {
	        mapaMiembros.put(m.getUsuarioId(), m);
	    }

	    JSONArray jsonArray = new JSONArray();
	    for (JugadorEstadisticaGlobalDto j : jugadores) {
	        jsonArray.put(convertirJugadorAJson(j, mapaMiembros.get(j.getJugadorGlobalId())));
	    }

	    return jsonArray.toString();
	}

	
	private JSONObject convertirMiembroClubAJson(MiembroClubDto miembro) {
	    JSONObject json = new JSONObject();
	    json.put("idMiembroClub", miembro.getIdMiembroClub());
	    json.put("fechaAltaUsuario", miembro.getFechaAltaUsuario());
	    json.put("fechaBajaUsuario", miembro.getFechaBajaUsuario());
	    json.put("clubId", miembro.getIdClub());
	    json.put("usuarioId", miembro.getUsuarioId());

	    ClubDto club = miembro.getClub();
	    json.put("club", club != null ? convertirClubAJson(club) : JSONObject.NULL);

	    return json;
	}

	private JSONObject convertirClubAJson(ClubDto club) {
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
	    jsonClub.put("logoBase64", club.getLogoClub() != null ? Base64.getEncoder().encodeToString(club.getLogoClub()) : JSONObject.NULL);
	    return jsonClub;
	}

	private JSONObject convertirJugadorAJson(JugadorEstadisticaGlobalDto j, MiembroClubDto miembro) {
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

	    if (miembro != null) {
	        jsonJugador.put("idMiembroClub", miembro.getIdMiembroClub());
	        jsonJugador.put("miembroClub", convertirMiembroClubAJson(miembro));
	    } else {
	        jsonJugador.put("idMiembroClub", JSONObject.NULL);
	        jsonJugador.put("miembroClub", JSONObject.NULL);
	    }

	    return jsonJugador;
	}



	/**
	 * Lista todos los miembros de un club.
	 * 
	 * @param clubId El ID del club cuyos miembros se desean obtener.
	 * @return Una lista de objetos `MiembroClubDto` que contienen la información de
	 *         los miembros.
	 */
	public ArrayList<MiembroClubDto> listarMiembrosClub(Long clubId) {
		ArrayList<MiembroClubDto> lista = new ArrayList<>();

		try {
			String urlApi = "http://localhost:9527/api/miembroClub/porClub/" + clubId;
			URL url = new URL(urlApi);
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
				StringBuilder response = new StringBuilder();
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				JSONArray jsonLista = new JSONArray(response.toString());
				for (int i = 0; i < jsonLista.length(); i++) {
					JSONObject jsonMiembro = jsonLista.getJSONObject(i);
					MiembroClubDto miembro = new MiembroClubDto();

					miembro.setIdMiembroClub(jsonMiembro.getLong("idMiembroClub"));
					miembro.setFechaAltaUsuario(jsonMiembro.optString("fechaAltaUsuario", "N/A"));
					miembro.setFechaBajaUsuario(jsonMiembro.optString("fechaBajaUsuario", "N/A"));
					miembro.setIdClub(jsonMiembro.optLong("idClub", -1));
					miembro.setUsuarioId(jsonMiembro.optLong("usuarioId", -1));

					// Usuario
					if (jsonMiembro.has("usuario") && !jsonMiembro.isNull("usuario")) {
						JSONObject jsonUsuario = jsonMiembro.getJSONObject("usuario");
						UsuarioDto usuario = new UsuarioDto();
						usuario.setIdUsuario(jsonUsuario.optLong("idUsuario", 0));
						usuario.setNombreCompletoUsuario(jsonUsuario.optString("nombreCompletoUsuario", "Desconocido"));
						usuario.setAliasUsuario(jsonUsuario.optString("aliasUsuario", "sin alias"));
						usuario.setFechaNacimientoUsuario(jsonUsuario.optString("fechaNacimientoUsuario", "N/A"));
						usuario.setEmailUsuario(jsonUsuario.optString("emailUsuario", "no-email@dominio.com"));
						usuario.setTelefonoUsuario(jsonUsuario.optString("telefonoUsuario", "Sin teléfono"));
						usuario.setRolUsuario(RolUsuario.valueOf(jsonUsuario.optString("rolUsuario", "Jugador")));
						usuario.setDescripcionUsuario(jsonUsuario.optString("descripcionUsuario", "Sin descripción"));
						usuario.setImagenUsuario(jsonUsuario.optString("imagenUsuario", "default.jpg").getBytes());
						usuario.setEstadoUsuario(Estado.valueOf(jsonUsuario.optString("estadoUsuario", "Inactivo")));
						miembro.setUsuario(usuario);
					}

					// Club
					if (jsonMiembro.has("club") && !jsonMiembro.isNull("club")) {
						JSONObject jsonClub = jsonMiembro.getJSONObject("club");
						ClubDto club = new ClubDto();
						club.setIdClub(jsonClub.optLong("idClub", -1));
						club.setNombreClub(jsonClub.optString("nombreClub", "Sin nombre"));
						club.setAbreviaturaClub(jsonClub.optString("abreviaturaClub", ""));
						club.setDescripcionClub(jsonClub.optString("descripcionClub", ""));
						club.setLocalidadClub(jsonClub.optString("localidadClub", ""));
						club.setPaisClub(jsonClub.optString("paisClub", ""));
						club.setEmailClub(jsonClub.optString("emailClub", ""));
						club.setTelefonoClub(jsonClub.optString("telefonoClub", ""));
						club.setEsPremium(jsonClub.optBoolean("esPremium", false));

						String imagenBase64 = Utilidades.getValorSeguro(jsonClub, "logoClub");
						if (imagenBase64 != null && !imagenBase64.isEmpty()) {
							byte[] imageBytes = Base64.getDecoder().decode(imagenBase64);
							club.setLogoClub(imageBytes);
						} else {
							club.setLogoClub(null);
						}

						miembro.setClub(club);
					}

					lista.add(miembro);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	/**
	 * Modifica un miembro existente del club.
	 * 
	 * @param idMiembroClub El ID del miembro que se desea modificar.
	 * @param miembro       El objeto que contiene los nuevos datos del miembro.
	 * @return `true` si la modificación fue exitosa, `false` en caso contrario.
	 */
	public boolean modificarMiembroClub(long idMiembroClub, MiembroClubDto miembro) {
		try {
			JSONObject json = new JSONObject();
			json.put("fechaAltaUsuario", miembro.getFechaAltaUsuario().toString());
			json.put("fechaBajaUsuario",
					miembro.getFechaBajaUsuario() != null ? miembro.getFechaBajaUsuario().toString() : JSONObject.NULL);
			json.put("clubId", miembro.getIdClub());
			json.put("usuarioId", miembro.getUsuarioId());

			String urlApi = "http://localhost:9527/api/modificarMiembroClub/" + idMiembroClub;
			URL url = new URL(urlApi);

			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("PUT");
			conex.setRequestProperty("Content-Type", "application/json");
			conex.setDoOutput(true);

			try (OutputStream os = conex.getOutputStream()) {
				byte[] input = json.toString().getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				return true;
			} else {
				// System.out.println("Error al modificar miembro del club: " + responseCode);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Verifica si un usuario es miembro de un club específico.
	 * 
	 * @param clubId    El ID del club a verificar.
	 * @param usuarioId El ID del usuario a verificar.
	 * @return `true` si el usuario es miembro del club, `false` en caso contrario.
	 */
	public boolean esMiembroDelClub(long clubId, long usuarioId) {
		try {
			String urlApi = "http://localhost:9527/api/miembroClub/porUsuarioYClub/" + clubId + "/" + usuarioId;
			URL url = new URL(urlApi);
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
				StringBuilder response = new StringBuilder();
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				return !response.toString().isEmpty();
			} else {
				// System.out.println("Error al verificar si es miembro: " + responseCode);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<MiembroClubDto> listarMisClubesPorUsuario(Long usuarioId) {
		List<MiembroClubDto> lista = new ArrayList<>();

		try {
			String urlApi = "http://localhost:9527/api/miembroClub/porUsuario/" + usuarioId;
			URL url = new URL(urlApi);
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
				StringBuilder response = new StringBuilder();
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				JSONArray jsonLista = new JSONArray(response.toString());

				for (int i = 0; i < jsonLista.length(); i++) {
					JSONObject jsonMiembro = jsonLista.getJSONObject(i);
					MiembroClubDto miembro = new MiembroClubDto();

					// Datos del miembro
					miembro.setIdMiembroClub(jsonMiembro.getLong("idMiembroClub"));
					miembro.setFechaAltaUsuario(jsonMiembro.optString("fechaAltaUsuario", "N/A"));
					miembro.setFechaBajaUsuario(jsonMiembro.optString("fechaBajaUsuario", "N/A"));
					miembro.setIdClub(jsonMiembro.optLong("idClub", -1));
					miembro.setUsuarioId(jsonMiembro.optLong("usuarioId", -1));

					// Usuario
					if (jsonMiembro.has("usuario") && !jsonMiembro.isNull("usuario")) {
						JSONObject jsonUsuario = jsonMiembro.getJSONObject("usuario");
						UsuarioDto usuario = new UsuarioDto();
						usuario.setIdUsuario(jsonUsuario.optLong("idUsuario", 0));
						usuario.setNombreCompletoUsuario(jsonUsuario.optString("nombreCompletoUsuario", "Desconocido"));
						usuario.setAliasUsuario(jsonUsuario.optString("aliasUsuario", "sin alias"));
						usuario.setEmailUsuario(jsonUsuario.optString("emailUsuario", "no-email@dominio.com"));
						usuario.setRolUsuario(RolUsuario.valueOf(jsonUsuario.optString("rolUsuario", "Jugador")));
						usuario.setEstadoUsuario(Estado.valueOf(jsonUsuario.optString("estadoUsuario", "Inactivo")));
						miembro.setUsuario(usuario);
					}

					// Club
					if (jsonMiembro.has("club") && !jsonMiembro.isNull("club")) {
						JSONObject jsonClub = jsonMiembro.getJSONObject("club");
						ClubDto club = new ClubDto();
						club.setIdClub(jsonClub.optLong("idClub", -1));
						club.setNombreClub(jsonClub.optString("nombreClub", "Sin nombre"));
						club.setAbreviaturaClub(jsonClub.optString("abreviaturaClub", ""));
						club.setDescripcionClub(jsonClub.optString("descripcionClub", ""));
						club.setLocalidadClub(jsonClub.optString("localidadClub", ""));
						club.setPaisClub(jsonClub.optString("paisClub", ""));
						club.setEmailClub(jsonClub.optString("emailClub", ""));
						club.setTelefonoClub(jsonClub.optString("telefonoClub", ""));
						club.setEsPremium(jsonClub.optBoolean("esPremium", false));

						// Cargar imagen
						String imagenBase64 = Utilidades.getValorSeguro(jsonClub, "logoClub");
						if (imagenBase64 != null && !imagenBase64.isEmpty()) {
							club.setLogoClub(Base64.getDecoder().decode(imagenBase64));
						}

						miembro.setClub(club);
					}

					lista.add(miembro);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<JugadorEstadisticaGlobalDto> listarJugadoresPorClub(Long clubId) {
		List<JugadorEstadisticaGlobalDto> lista = new ArrayList<>();

		try {
			String urlApi = "http://localhost:9527/api/jugadores/porClub/" + clubId;
			URL url = new URL(urlApi);
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
				StringBuilder response = new StringBuilder();
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				JSONArray jsonLista = new JSONArray(response.toString());
				for (int i = 0; i < jsonLista.length(); i++) {
					JSONObject jsonJugador = jsonLista.getJSONObject(i);
					JugadorEstadisticaGlobalDto jugador = new JugadorEstadisticaGlobalDto();

					jugador.setJugadorGlobalId(jsonJugador.optLong("jugadorGlobalId", -1));
					jugador.setNombreJugador(jsonJugador.optString("nombreJugador", "Desconocido"));
					jugador.setAliasJugador(jsonJugador.optString("aliasJugador", "sin alias"));
					jugador.setGolesGlobal(jsonJugador.optInt("golesGlobal", 0));
					jugador.setAsistenciasGlobal(jsonJugador.optInt("asistenciasGlobal", 0));
					jugador.setAmarillasGlobal(jsonJugador.optInt("amarillasGlobal", 0));
					jugador.setRojasGlobal(jsonJugador.optInt("rojasGlobal", 0));
					jugador.setPartidosJugadosGlobal(jsonJugador.optInt("partidosJugadosGlobal", 0));
					jugador.setPartidosGanadosGlobal(jsonJugador.optInt("partidosGanadosGlobal", 0));
					jugador.setPartidosPerdidosGlobal(jsonJugador.optInt("partidosPerdidosGlobal", 0));
					jugador.setMinutosJugadosGlobal(jsonJugador.optInt("minutosJugadosGlobal", 0));

					lista.add(jugador);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	/**
	 * Elimina un miembro del club por su propio usuario. Solo puede eliminar su
	 * propia membresía.
	 *
	 * @param idMiembroClub El ID de la relación miembro-club a eliminar.
	 * @param usuarioId     El ID del usuario que realiza la eliminación.
	 * @return true si se eliminó correctamente, false en caso contrario.
	 */
	public boolean eliminarMiembroClubPorUsuario(HttpServletRequest request, Long idMiembroClub, Long usuarioId) {
	    try {
	        // 1️⃣ Obtener token de la sesión
	        HttpSession session = request.getSession(false);
	        if (session == null) throw new IllegalStateException("No hay sesión activa");
	        String token = (String) session.getAttribute("token");
	        if (token == null || token.isEmpty()) throw new IllegalStateException("No se encontró token JWT");

	        // 2️⃣ Extraer email del token
	        String emailLogueado = Utilidades.extraerEmaildelToken(token);

	        // 3️⃣ Construir URL del API
	        String urlApi = "http://localhost:9527/api/eliminarMiembroClub/" + idMiembroClub + "?usuarioId=" + usuarioId;
	        URL url = new URL(urlApi);

	        // 4️⃣ Conexión DELETE
	        HttpURLConnection conex = (HttpURLConnection) url.openConnection();
	        conex.setRequestMethod("DELETE");
	        conex.setRequestProperty("Accept", "application/json");

	        // 5️⃣ Enviar token en la cabecera
	        conex.setRequestProperty("Authorization", "Bearer " + token);

	        int responseCode = conex.getResponseCode();
	        return responseCode == HttpURLConnection.HTTP_OK;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	/**
	 * Elimina un miembro del club desde la perspectiva del club. Solo puede
	 * eliminar miembros de su propio club.
	 *
	 * @param idMiembroClub El ID de la relación miembro-club a eliminar.
	 * @param clubId        El ID del club que realiza la eliminación.
	 * @return true si se eliminó correctamente, false en caso contrario.
	 */
	public boolean eliminarMiembroClubPorClub(HttpServletRequest request, Long idMiembroClub, Long clubId) {
	    try {
	        // 1️⃣ Obtener token de la sesión
	        HttpSession session = request.getSession(false);
	        if (session == null) throw new IllegalStateException("No hay sesión activa");
	        String token = (String) session.getAttribute("token");
	        if (token == null || token.isEmpty()) throw new IllegalStateException("No se encontró token JWT");

	        // 2️⃣ Extraer email del token
	        String emailLogueado = Utilidades.extraerEmaildelToken(token);

	        // 3️⃣ Construir URL del API
	        String urlApi = "http://localhost:9527/api/eliminarMiembroClub/" + idMiembroClub + "?clubId=" + clubId;
	        URL url = new URL(urlApi);

	        // 4️⃣ Conexión DELETE
	        HttpURLConnection conex = (HttpURLConnection) url.openConnection();
	        conex.setRequestMethod("DELETE");
	        conex.setRequestProperty("Accept", "application/json");

	        // 5️⃣ Enviar token en la cabecera
	        conex.setRequestProperty("Authorization", "Bearer " + token);

	        int responseCode = conex.getResponseCode();
	        return responseCode == HttpURLConnection.HTTP_OK;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

}
