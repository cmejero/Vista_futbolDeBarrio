package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.EquipoTorneoDto;
import vista_futbolDeBarrio.dtos.TorneoDto;
import vista_futbolDeBarrio.enums.EstadoParticipacion;
import vista_futbolDeBarrio.enums.Modalidad;

/**
 * Clase que se encarga de la logica de los metodos CRUD de equipo torneo
 */
public class EquipoTorneoServicio {

	/**
	 * Guarda un nuevo equipo en un torneo.
	 * 
	 * @param equipoTorneo El objeto EquipoTorneoDto que contiene los datos del
	 *                     equipo a guardar.
	 */
	public void guardarEquipoTorneo(EquipoTorneoDto equipoTorneo, HttpServletRequest request) {
	    try {
	        // 1️⃣ Obtener el token JWT de la sesión
	        HttpSession session = request.getSession(false);
	        if (session == null) throw new IllegalStateException("No hay sesión activa");
	        String token = (String) session.getAttribute("token");
	        if (token == null || token.isEmpty()) throw new IllegalStateException("No se encontró token JWT");

	        // 2️⃣ Preparar JSON
	        JSONObject json = new JSONObject();
	        json.put("fechaInicioParticipacion", equipoTorneo.getFechaInicioParticipacion().toString());
	        json.put("fechaFinParticipacion", equipoTorneo.getFechaFinParticipacion().toString());
	        json.put("estadoParticipacion", equipoTorneo.getEstadoParticipacion().name());
	        json.put("torneoId", equipoTorneo.getTorneoId());
	        json.put("clubId", equipoTorneo.getClubId());

	        // 3️⃣ Ejecutar POST con token
	        ejecutarPostConToken("http://localhost:9527/api/guardarEquipoTorneo", json, token);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	/**
	 * Ejecuta una petición POST a la API enviando un JSON con autenticación por token.
	 *
	 * @param urlApi URL del endpoint a invocar.
	 * @param json Datos a enviar en el cuerpo de la petición.
	 * @param token Token JWT para la autorización.
	 * @throws Exception Si ocurre un error durante la comunicación.
	 */
	private void ejecutarPostConToken(String urlApi, JSONObject json, String token) throws Exception {
	    URL url = new URL(urlApi);
	    HttpURLConnection conex = (HttpURLConnection) url.openConnection();
	    conex.setRequestMethod("POST");
	    conex.setRequestProperty("Content-Type", "application/json");
	    conex.setRequestProperty("Authorization", "Bearer " + token); // 🔑 Enviamos token JWT
	    conex.setDoOutput(true);

	    try (OutputStream os = conex.getOutputStream()) {
	        byte[] input = json.toString().getBytes("utf-8");
	        os.write(input, 0, input.length);
	    }

	    int responseCode = conex.getResponseCode();
	    if (responseCode == HttpURLConnection.HTTP_OK) {
	        System.out.println("EquipoTorneo guardado correctamente.");
	    } else {
	        System.out.println("Error al guardar EquipoTorneo: " + responseCode);
	    }
	}


	/**
	 * Obtiene la lista de equipos que están participando en torneos.
	 * 
	 * @return Una lista de objetos EquipoTorneoDto con los datos de los equipos.
	 */
	public ArrayList<EquipoTorneoDto> listaEquiposTorneo() {
		ArrayList<EquipoTorneoDto> lista = new ArrayList<>();

		try {
			String urlApi = "http://localhost:9527/api/mostrarEquipoTorneo";
			URL url = new URL(urlApi);

			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				JSONArray jsonArray = new JSONArray(response.toString());

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonEquipoTorneo = jsonArray.getJSONObject(i);
					EquipoTorneoDto equipoTorneo = new EquipoTorneoDto();

					equipoTorneo.setIdEquipoTorneo(jsonEquipoTorneo.getLong("idEquipoTorneo"));
					equipoTorneo.setFechaInicioParticipacion(jsonEquipoTorneo.getString("fechaInicioParticipacion"));
					equipoTorneo.setFechaFinParticipacion(jsonEquipoTorneo.getString("fechaFinParticipacion"));
					equipoTorneo.setEstadoParticipacion(
							EstadoParticipacion.valueOf(jsonEquipoTorneo.getString("estadoParticipacion")));
					equipoTorneo.setTorneoId(jsonEquipoTorneo.getLong("torneoId"));
					equipoTorneo.setClubId(jsonEquipoTorneo.getLong("clubId"));

					lista.add(equipoTorneo);
				}
			} else {
				// System.out.println("Error al obtener la lista de EquiposTorneo: " +
				// responseCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("ERROR- [EquipoTorneoServicio] listaEquiposTorneo: " +
			// e.getMessage());
		}

		return lista;
	}

	/**
	 * Obtiene la lista de equipos que están participando en un torneo específico.
	 * 
	 * @param torneoId El ID del torneo del que se quieren obtener los equipos.
	 * @return Una lista de objetos EquipoTorneoDto con los datos de los equipos
	 *         inscritos en el torneo.
	 */
	public ArrayList<EquipoTorneoDto> obtenerEquiposPorTorneo(Long torneoId) {
		ArrayList<EquipoTorneoDto> equipos = new ArrayList<>();

		try {
			// Construye la URL para obtener los equipos del torneo
			String urlApi = "http://localhost:9527/api/equipoTorneo/torneo/" + torneoId;
			URL url = new URL(urlApi);

			// Crea la conexión HTTP
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				// Lee la respuesta de la API
				BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
				StringBuilder response = new StringBuilder();
				String line;
				while ((line = in.readLine()) != null) {
					response.append(line);
				}
				in.close();

				// Convierte la respuesta JSON en objetos EquipoTorneoDto
				JSONArray jsonArray = new JSONArray(response.toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonEquipoTorneo = jsonArray.getJSONObject(i);
					EquipoTorneoDto equipoTorneo = new EquipoTorneoDto();

					equipoTorneo.setIdEquipoTorneo(jsonEquipoTorneo.getLong("idEquipoTorneo"));
					equipoTorneo.setFechaInicioParticipacion(jsonEquipoTorneo.getString("fechaInicioParticipacion"));
					equipoTorneo.setFechaFinParticipacion(jsonEquipoTorneo.getString("fechaFinParticipacion"));
					equipoTorneo.setEstadoParticipacion(
							EstadoParticipacion.valueOf(jsonEquipoTorneo.getString("estadoParticipacion")));
					equipoTorneo.setTorneoId(jsonEquipoTorneo.getLong("torneoId"));
					equipoTorneo.setClubId(jsonEquipoTorneo.getLong("clubId"));

					equipos.add(equipoTorneo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return equipos;
	}
	
	
	/**
	 * Obtiene la lista de torneos asociados a un usuario.
	 *
	 * @param usuarioId ID del usuario a consultar.
	 * @return Lista de torneos sin duplicados. Si ocurre un error, se devuelve la lista obtenida hasta el momento.
	 */
	public ArrayList<TorneoDto> obtenerTorneosPorUsuario(Long usuarioId) {
	    ArrayList<TorneoDto> torneosTotales = new ArrayList<>();
	    
	    try {
	        String urlApi = "http://localhost:9527/api/equipoTorneo/usuario/" + usuarioId;
	        URL url = new URL(urlApi);

	        HttpURLConnection conex = (HttpURLConnection) url.openConnection();
	        conex.setRequestMethod("GET");
	        conex.setRequestProperty("Accept", "application/json");

	        int responseCode = conex.getResponseCode();
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
	            StringBuilder response = new StringBuilder();
	            String line;
	            while ((line = in.readLine()) != null) {
	                response.append(line);
	            }
	            in.close();

	            JSONArray jsonArray = new JSONArray(response.toString());
	            for (int i = 0; i < jsonArray.length(); i++) {
	                JSONObject jsonTorneo = jsonArray.getJSONObject(i);
	                TorneoDto torneo = new TorneoDto();

	                torneo.setIdTorneo(jsonTorneo.getLong("idTorneo"));
	                torneo.setNombreTorneo(jsonTorneo.getString("nombreTorneo"));
	                torneo.setFechaInicioTorneo(jsonTorneo.getString("fechaInicioTorneo"));
	                torneo.setFechaFinTorneo(jsonTorneo.getString("fechaFinTorneo"));
	                torneo.setDescripcionTorneo(jsonTorneo.optString("descripcionTorneo", null));
	                torneo.setClubesInscritos(jsonTorneo.optString("clubesInscritos", ""));
	                torneo.setModalidad(Modalidad.valueOf(jsonTorneo.getString("modalidad")));
	                torneo.setEstaActivo(jsonTorneo.getBoolean("estaActivo"));
	                torneo.setInstalacionId(jsonTorneo.getLong("instalacionId"));
	                torneo.setNombreInstalacion(jsonTorneo.optString("nombreInstalacion", null));
	                torneo.setDireccionInstalacion(jsonTorneo.optString("direccionInstalacion", null));



	                torneosTotales.add(torneo);
	            }

	            // Filtrar duplicados por idTorneo
	            ArrayList<TorneoDto> torneosUnicos = new ArrayList<>();
	            HashSet<Long> ids = new HashSet<>();
	            for (TorneoDto t : torneosTotales) {
	                if (!ids.contains(t.getIdTorneo())) {
	                    torneosUnicos.add(t);
	                    ids.add(t.getIdTorneo());
	                }
	            }

	            return torneosUnicos;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return torneosTotales;
	}



	/**
	 * Modifica los detalles de un equipo que participa en un torneo.
	 * 
	 * @param idEquipoTorneo El ID del equipo en el torneo que se va a modificar.
	 * @param equipoTorneo   El objeto EquipoTorneoDto que contiene los nuevos datos
	 *                       del equipo.
	 * @return true si la modificación fue exitosa, false en caso contrario.
	 */
	public boolean modificarEquipoTorneo(long idEquipoTorneo, EquipoTorneoDto equipoTorneo) {
		try {

			JSONObject json = new JSONObject();
			json.put("fechaInicioParticipacion", equipoTorneo.getFechaInicioParticipacion().toString());
			json.put("fechaFinParticipacion", equipoTorneo.getFechaFinParticipacion().toString());
			json.put("estadoParticipacion", equipoTorneo.getEstadoParticipacion().name());
			json.put("torneoId", equipoTorneo.getTorneoId());
			json.put("clubId", equipoTorneo.getClubId());

			String urlApi = "http://localhost:9527/api/modificarEquipoTorneo/" + idEquipoTorneo;
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
			return responseCode == HttpURLConnection.HTTP_OK;

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("ERROR- [EquipoTorneoServicio] modificarEquipoTorneo: " +
			// e.getMessage());
			return false;
		}
	}

	
	/**
	 * Cuenta la cantidad de equipos inscritos en un torneo.
	 *
	 * @param torneoId ID del torneo.
	 * @return Número de equipos asociados al torneo.
	 */
	public int contarEquiposPorTorneo(long torneoId) {
		int contador = 0;
		for (EquipoTorneoDto equipo : listaEquiposTorneo()) {
			if (equipo.getTorneoId() == torneoId) {
				contador++;
			}
		}
		return contador;
	}

	
	/**
	 * Indica si aún es posible inscribirse en un torneo según el límite de equipos.
	 *
	 * @param torneoId ID del torneo.
	 * @return true si quedan plazas disponibles, false en caso contrario.
	 */
	public boolean puedeInscribirse(Long torneoId) {
		return contarEquiposPorTorneo(torneoId) < 16;
	}

	
	/**
	 * Comprueba si un club ya está inscrito en un torneo.
	 *
	 * @param torneoId ID del torneo.
	 * @param clubId ID del club.
	 * @return true si el club está inscrito, false en caso contrario.
	 */
	public boolean estaInscrito(Long torneoId, long clubId) {
	    for (EquipoTorneoDto equipo : obtenerEquiposPorTorneo(torneoId)) {
	        if (equipo.getClubId() == clubId) { 
	            return true; 
	        }
	    }
	    return false;
	}

}
