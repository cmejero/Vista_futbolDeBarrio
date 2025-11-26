package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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
     * @param miembro El objeto que contiene los datos del miembro del club a guardar.
     */
	public void guardarMiembroClub(MiembroClubDto miembro) {
		try {
			JSONObject json = new JSONObject();
			json.put("fechaAltaUsuario", miembro.getFechaAltaUsuario().toString());
			json.put("fechaBajaUsuario",
					miembro.getFechaBajaUsuario() != null ? miembro.getFechaBajaUsuario().toString() : JSONObject.NULL);
			json.put("idClub", miembro.getIdClub());
			json.put("usuarioId", miembro.getUsuarioId());

			String urlApi = "http://localhost:9527/api/guardarMiembroClub";
			URL url = new URL(urlApi);

			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("POST");
			conex.setRequestProperty("Content-Type", "application/json");
			conex.setDoOutput(true);

			try (OutputStream os = conex.getOutputStream()) {
				byte[] input = json.toString().getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				// System.out.println("Miembro del club guardado correctamente.");
			} else {
				// System.out.println("Error al guardar miembro del club: " + responseCode);
			}

		} catch (Exception e) {
			// System.out.println("ERROR- [MiembroClubServicio] " + e);
		}
	}

	/**
     * Lista todos los miembros de un club.
     * 
     * @param clubId El ID del club cuyos miembros se desean obtener.
     * @return Una lista de objetos `MiembroClubDto` que contienen la información de los miembros.
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
     * @param miembro El objeto que contiene los nuevos datos del miembro.
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
     * @param clubId El ID del club a verificar.
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
	 * Elimina un miembro del club por su propio usuario.
	 * Solo puede eliminar su propia membresía.
	 *
	 * @param idMiembroClub El ID de la relación miembro-club a eliminar.
	 * @param usuarioId El ID del usuario que realiza la eliminación.
	 * @return true si se eliminó correctamente, false en caso contrario.
	 */
	public boolean eliminarMiembroClubPorUsuario(Long idMiembroClub, Long usuarioId) {
	    try {
	        // Construimos la URL del API con control de usuario
	        String urlApi = "http://localhost:9527/api/eliminarMiembroClub/" + idMiembroClub 
	                        + "?usuarioId=" + usuarioId;
	        URL url = new URL(urlApi);

	        HttpURLConnection conex = (HttpURLConnection) url.openConnection();
	        conex.setRequestMethod("DELETE");
	        conex.setRequestProperty("Accept", "application/json");

	        int responseCode = conex.getResponseCode();
	        return responseCode == HttpURLConnection.HTTP_OK;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	/**
	 * Elimina un miembro del club desde la perspectiva del club.
	 * Solo puede eliminar miembros de su propio club.
	 *
	 * @param idMiembroClub El ID de la relación miembro-club a eliminar.
	 * @param clubId El ID del club que realiza la eliminación.
	 * @return true si se eliminó correctamente, false en caso contrario.
	 */
	public boolean eliminarMiembroClubPorClub(Long idMiembroClub, Long clubId) {
	    try {
	        // Construimos la URL del API con control de club
	        String urlApi = "http://localhost:9527/api/eliminarMiembroClub/" + idMiembroClub
	                        + "?clubId=" + clubId;
	        URL url = new URL(urlApi);

	        HttpURLConnection conex = (HttpURLConnection) url.openConnection();
	        conex.setRequestMethod("DELETE");
	        conex.setRequestProperty("Accept", "application/json");

	        int responseCode = conex.getResponseCode();
	        return responseCode == HttpURLConnection.HTTP_OK;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

}
