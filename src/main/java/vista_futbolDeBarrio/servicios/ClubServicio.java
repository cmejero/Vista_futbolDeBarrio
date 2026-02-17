package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import vista_futbolDeBarrio.dtos.ClubDto;
import vista_futbolDeBarrio.utilidades.Utilidades;

/**
 * Clase que se encarga de la logica de los metodos CRUD de club
 */
public class ClubServicio {

	/**
	 * Crea un club a partir de los datos recibidos en el formulario.
	 *
	 * @param request Petición HTTP con la información del club.
	 * @return Resultado de la operación de guardado.
	 * @throws IOException      Si ocurre un error de entrada/salida.
	 * @throws ServletException Si ocurre un error al procesar la solicitud.
	 */
	public String crearClubDesdeFormulario(HttpServletRequest request) throws IOException, ServletException {

		ClubDto club = new ClubDto();
		club.setNombreClub(request.getParameter("nombreClub"));
		club.setAbreviaturaClub(request.getParameter("abreviaturaClub"));
		club.setDescripcionClub(request.getParameter("descripcionClub"));
		club.setEmailClub(request.getParameter("emailClub"));
		club.setLocalidadClub(request.getParameter("localidadClub"));
		club.setPaisClub(request.getParameter("paisClub"));
		club.setPasswordClub(request.getParameter("passwordClub"));
		club.setTelefonoClub(request.getParameter("telefonoClub"));

		// 🖼 Logo del club
		Part imagenPart = request.getPart("logoClub");
		if (imagenPart != null && imagenPart.getSize() > 0) {
			byte[] bytes = new byte[(int) imagenPart.getSize()];
			try (InputStream inputStream = imagenPart.getInputStream()) {
				inputStream.read(bytes);
			}
			club.setLogoClub(bytes);
		}

		// 📡 Llamada a la API
		return guardarClub(club);
	}

	/**
	 * Guarda un nuevo club en el sistema.
	 * 
	 * @param club El objeto ClubDto que contiene los datos del club a guardar.
	 */
	public String guardarClub(ClubDto club) {
		try {
			JSONObject json = new JSONObject();
			json.put("nombreClub", club.getNombreClub());
			json.put("abreviaturaClub", club.getAbreviaturaClub());
			json.put("descripcionClub", club.getDescripcionClub());
			json.put("fechaCreacionClub", club.getFechaCreacionClub());
			json.put("fechaFundacionClub", club.getFechaFundacionClub());
			json.put("localidadClub", club.getLocalidadClub());
			json.put("paisClub", club.getPaisClub());
			json.put("emailClub", club.getEmailClub());
			json.put("passwordClub", club.getPasswordClub());
			json.put("telefonoClub", club.getTelefonoClub());

			byte[] imagenBytes = club.getLogoClub();
			if (imagenBytes != null && imagenBytes.length > 0) {
				String imagenBase64 = Base64.getEncoder().encodeToString(imagenBytes);
				json.put("logoClub", imagenBase64);
			} else {
				json.put("logoClub", JSONObject.NULL);
			}

			json.put("esPremium", club.isEsPremium());

			String urlApi = "http://localhost:9527/api/guardarClub";
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
				return "ok"; // registro correcto
			} else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
				try (BufferedReader br = new BufferedReader(new InputStreamReader(conex.getErrorStream(), "utf-8"))) {
					StringBuilder responseText = new StringBuilder();
					String line;
					while ((line = br.readLine()) != null) {
						responseText.append(line.trim());
					}
					String error = responseText.toString();
					if (error.contains("ya está en uso")) {
						return "usuario_existente";
					} else if (error.toLowerCase().contains("email")) {
						return "email_invalido";
					} else {
						return "error_servidor";
					}
				}
			} else {
				return "error_servidor";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "error_servidor";
		}
	}

	/**
	 * Obtiene una lista de clubes desde el servicio web.
	 * 
	 * @return Una lista de objetos ClubDto con los datos de los clubes.
	 */
	public ArrayList<ClubDto> listaClub(HttpServletRequest request) {
		ArrayList<ClubDto> lista = new ArrayList<>();
		StringBuilder response = new StringBuilder();

		try {
			// 1️⃣ Obtener el token JWT de la sesión
			HttpSession session = request.getSession(false);
			if (session == null)
				throw new IllegalStateException("No hay sesión activa");
			String token = (String) session.getAttribute("token");
			if (token == null || token.isEmpty())
				throw new IllegalStateException("No se encontró token JWT");

			// 2️⃣ URL de la API de clubes
			String urlApi = "http://localhost:9527/api/mostrarClubes";
			URL url = new URL(urlApi);
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");

			// 🔑 Enviar token en header Authorization
			conex.setRequestProperty("Authorization", "Bearer " + token);

			// 3️⃣ Leer respuesta
			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// 4️⃣ Parsear JSON a lista de ClubDto
				JSONArray jsonLista = new JSONArray(response.toString());
				for (int i = 0; i < jsonLista.length(); i++) {
					JSONObject jsonClub = jsonLista.getJSONObject(i);
					ClubDto club = new ClubDto();

					// Campos obligatorios
					club.setIdClub(jsonClub.optLong("idClub"));
					club.setNombreClub(Utilidades.getValorSeguro(jsonClub, "nombreClub"));
					club.setAbreviaturaClub(Utilidades.getValorSeguro(jsonClub, "abreviaturaClub"));
					club.setDescripcionClub(Utilidades.getValorSeguro(jsonClub, "descripcionClub"));
					club.setFechaFundacionClub(Utilidades.getValorSeguro(jsonClub, "fechaFundacionClub"));
					club.setLocalidadClub(Utilidades.getValorSeguro(jsonClub, "localidadClub"));
					club.setPaisClub(Utilidades.getValorSeguro(jsonClub, "paisClub"));
					club.setEmailClub(Utilidades.getValorSeguro(jsonClub, "emailClub"));
					club.setPasswordClub(null); // nunca enviamos contraseña

					// Campos opcionales
					club.setTelefonoClub(Utilidades.getValorSeguro(jsonClub, "telefonoClub"));
					club.setFechaCreacionClub(Utilidades.getValorSeguro(jsonClub, "fechaCreacionClub"));

					// Logo (opcional)
					String imagenBase64 = Utilidades.getValorSeguro(jsonClub, "logoClub");
					if (imagenBase64 != null && !imagenBase64.isEmpty()) {
						byte[] imageBytes = Base64.getDecoder().decode(imagenBase64);
						club.setLogoClub(imageBytes);
					} else {
						club.setLogoClub(null);
					}

					club.setEsPremium(jsonClub.optBoolean("esPremium", false));

					lista.add(club);
				}
			} else {
				System.err.println("Error al obtener clubes. Código HTTP: " + responseCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR - ServiciosClub - listaClub: " + e.getMessage());
		}

		return lista;
	}

	/**
	 * Obtiene la información de un club a partir de su identificador.
	 *
	 * @param idClub  ID del club a consultar.
	 * @param request Petición HTTP desde la que se obtiene el token de sesión.
	 * @return Objeto ClubDto con los datos del club o null si ocurre algún error.
	 */
	public ClubDto obtenerClubPorId(long idClub, HttpServletRequest request) {
		try {
			// 1️⃣ Obtener token JWT de la sesión
			HttpSession session = request.getSession(false);
			if (session == null)
				throw new IllegalStateException("No hay sesión activa");
			String token = (String) session.getAttribute("token");
			if (token == null || token.isEmpty())
				throw new IllegalStateException("No se encontró token JWT");

			// 2️⃣ Construir la URL de la API
			String urlApi = "http://localhost:9527/api/club/" + idClub;
			URL url = new URL(urlApi);

			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");
			conex.setRequestProperty("Authorization", "Bearer " + token); // 🔑 enviar token para validar identidad

			// 3️⃣ Leer la respuesta
			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
				StringBuilder response = new StringBuilder();
				String line;
				while ((line = in.readLine()) != null) {
					response.append(line);
				}
				in.close();

				// 4️⃣ Convertir JSON a ClubDto
				ObjectMapper mapper = new ObjectMapper();
				ClubDto club = mapper.readValue(response.toString(), ClubDto.class);

				// 5️⃣ Convertir logoClub de Base64 a byte[]
				JSONObject jsonClub = new JSONObject(response.toString());
				String imagenBase64 = Utilidades.getValorSeguro(jsonClub, "logoClub");
				if (imagenBase64 != null && !imagenBase64.isEmpty()) {
					club.setLogoClub(Base64.getDecoder().decode(imagenBase64));
				} else {
					club.setLogoClub(null);
				}

				return club;
			} else if (responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
				System.err.println("Acceso no autorizado al club.");
			} else {
				System.err.println("No se encontró el club. Código HTTP: " + responseCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Modifica los datos de un club existente.
	 * 
	 * @param idClub El identificador del club que se va a modificar.
	 * @param club   El objeto ClubDto con los nuevos datos del club.
	 * @return true si la modificación fue exitosa, false en caso contrario.
	 */
	public boolean modificarClub(String idClub, ClubDto club, HttpServletRequest request) {
		try {
			// 1️⃣ Obtener token JWT de la sesión
			HttpSession session = request.getSession(false);
			if (session == null)
				throw new IllegalStateException("No hay sesión activa");
			String token = (String) session.getAttribute("token");
			if (token == null || token.isEmpty())
				throw new IllegalStateException("No se encontró token JWT");

			// 2️⃣ Construir JSON
			JSONObject json = new JSONObject();
			json.put("nombreClub", club.getNombreClub());
			json.put("abreviaturaClub", club.getAbreviaturaClub());
			json.put("descripcionClub", club.getDescripcionClub());
			json.put("fechaCreacionClub", club.getFechaCreacionClub());
			json.put("fechaFundacionClub", club.getFechaFundacionClub());
			json.put("localidadClub", club.getLocalidadClub());
			json.put("paisClub", club.getPaisClub());
			json.put("emailClub", club.getEmailClub());
			json.put("passwordClub", club.getPasswordClub());
			json.put("telefonoClub", club.getTelefonoClub());

			byte[] imagenBytes = club.getLogoClub();
			if (imagenBytes != null && imagenBytes.length > 0) {
				String imagenBase64 = Base64.getEncoder().encodeToString(imagenBytes);
				json.put("logoClub", imagenBase64);
			} else {
				json.put("logoClub", JSONObject.NULL);
			}

			json.put("esPremium", club.isEsPremium());

			// 3️⃣ Conexión a la API
			String urlApi = "http://localhost:9527/api/modificarClub/" + idClub;
			URL url = new URL(urlApi);
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("PUT");
			conex.setRequestProperty("Content-Type", "application/json");
			conex.setRequestProperty("Authorization", "Bearer " + token); // 🔑 Enviar token
			conex.setDoOutput(true);

			try (OutputStream os = conex.getOutputStream()) {
				byte[] input = json.toString().getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			int responseCode = conex.getResponseCode();
			return responseCode == HttpURLConnection.HTTP_OK;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Elimina un club por su ID llamando a la API REST.
	 * 
	 * @param idClub  ID del club a eliminar.
	 * @param request Solicitud HTTP para obtener la sesión y el token JWT.
	 * @return true si el club fue eliminado correctamente, false en caso contrario.
	 */
	public boolean eliminarClub(Long idClub, HttpServletRequest request) {
		try {
			// 1️⃣ Obtener el token JWT de la sesión
			HttpSession session = request.getSession(false);
			if (session == null)
				throw new IllegalStateException("No hay sesión activa");

			String token = (String) session.getAttribute("token");
			if (token == null || token.isEmpty())
				throw new IllegalStateException("No se encontró token JWT");

			// 2️⃣ Preparar la conexión a la API
			String urlApi = "http://localhost:9527/api/eliminarClub/" + idClub;
			URL url = new URL(urlApi);
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("DELETE");
			conex.setRequestProperty("Accept", "application/json");
			conex.setRequestProperty("Authorization", "Bearer " + token); // 🔑 Token JWT

			// 3️⃣ Leer respuesta
			int responseCode = conex.getResponseCode();
			return responseCode == HttpURLConnection.HTTP_OK;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Marca un club como premium.
	 *
	 * @param idClub  ID del club a modificar.
	 * @param request Petición HTTP desde la que se obtiene el token de sesión.
	 * @return true si la operación se realiza correctamente, false en caso
	 *         contrario.
	 */
	public boolean marcarPremium(Long idClub, HttpServletRequest request) {

		try {
			// 1️⃣ Sesión y token
			HttpSession session = request.getSession(false);
			if (session == null)
				throw new IllegalStateException("No hay sesión activa");

			String token = (String) session.getAttribute("token");
			if (token == null || token.isEmpty())
				throw new IllegalStateException("No se encontró token JWT");

			// 2️⃣ Llamada a la API
			String urlApi = "http://localhost:9527/api/modificarPremiumClub/" + idClub;
			URL url = new URL(urlApi);

			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			conexion.setRequestMethod("PUT");
			conexion.setRequestProperty("Content-Type", "application/json");
			conexion.setRequestProperty("Authorization", "Bearer " + token);
			conexion.setDoOutput(true);

			// 3️⃣ JSON
			JSONObject json = new JSONObject();
			json.put("esPremium", true);

			try (OutputStream os = conexion.getOutputStream()) {
				os.write(json.toString().getBytes("utf-8"));
			}

			// 4️⃣ Resultado
			return conexion.getResponseCode() == HttpURLConnection.HTTP_OK;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
