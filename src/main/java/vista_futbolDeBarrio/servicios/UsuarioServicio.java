package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import vista_futbolDeBarrio.dtos.UsuarioDto;
import vista_futbolDeBarrio.enums.Estado;
import vista_futbolDeBarrio.enums.RolUsuario;
import vista_futbolDeBarrio.utilidades.Utilidades;

/**
 * Clase que se encarga de la logica de los metodos CRUD usuario
 */
public class UsuarioServicio {

	/**
	 * Obtiene la lista de todos los usuarios.
	 * 
	 * @return Lista de usuarios.
	 */
	public UsuarioDto obtenerUsuario(HttpServletRequest request, Long idUsuario) {
		if (idUsuario == null)
			return null;

		try {
			// 1️⃣ Obtener el token de la sesión
			HttpSession session = request.getSession(false);
			if (session == null) {
				System.err.println("No hay sesión activa");
				return null;
			}

			String token = (String) session.getAttribute("token");
			if (token == null || token.isEmpty()) {
				System.err.println("No se encontró token JWT en sesión");
				return null;
			}
			System.out.println("Token recibido: " + token);

			// 2️⃣ Preparar la conexión a la API
			String urlApi = "http://localhost:9527/api/usuarios/" + idUsuario;
			URL url = new URL(urlApi);
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");

			// 3️⃣ Incluir el token JWT en la cabecera Authorization
			conex.setRequestProperty("Authorization", "Bearer " + token);

			// 4️⃣ Leer la respuesta
			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
				StringBuilder response = new StringBuilder();
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				JSONObject jsonUsuario = new JSONObject(response.toString());
				UsuarioDto usuario = new UsuarioDto();
				usuario.setIdUsuario(jsonUsuario.optLong("idUsuario"));
				usuario.setNombreCompletoUsuario(Utilidades.getValorSeguro(jsonUsuario, "nombreCompletoUsuario"));
				usuario.setAliasUsuario(Utilidades.getValorSeguro(jsonUsuario, "aliasUsuario"));
				usuario.setFechaNacimientoUsuario(Utilidades.getValorSeguro(jsonUsuario, "fechaNacimientoUsuario"));
				usuario.setEmailUsuario(Utilidades.getValorSeguro(jsonUsuario, "emailUsuario"));
				usuario.setTelefonoUsuario(Utilidades.getValorSeguro(jsonUsuario, "telefonoUsuario"));
				usuario.setRolUsuario(RolUsuario.valueOf(Utilidades.getValorSeguro(jsonUsuario, "rolUsuario")));
				usuario.setDescripcionUsuario(Utilidades.getValorSeguro(jsonUsuario, "descripcionUsuario"));
				String imagenBase64 = Utilidades.getValorSeguro(jsonUsuario, "imagenUsuario");
				if (imagenBase64 != null)
					usuario.setImagenUsuario(Base64.getDecoder().decode(imagenBase64));
				String estadoStr = Utilidades.getValorSeguro(jsonUsuario, "estadoUsuario");
				if (estadoStr != null)
					usuario.setEstadoUsuario(Estado.valueOf(estadoStr));
				usuario.setEsPremium(jsonUsuario.optBoolean("esPremium", false));

				return usuario;
			} else {
				System.err.println("Error al llamar API. Código: " + responseCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error al obtener usuario por ID: " + e.getMessage());
		}

		return null;
	}

	public ArrayList<UsuarioDto> obtenerUsuarios(HttpServletRequest request) {
		ArrayList<UsuarioDto> lista = new ArrayList<>();

		try {
			// 1️⃣ Obtener token JWT de la sesión
			HttpSession session = request.getSession(false);
			if (session == null)
				throw new IllegalStateException("No hay sesión activa");
			String token = (String) session.getAttribute("token");
			if (token == null || token.isEmpty())
				throw new IllegalStateException("No se encontró token JWT");

			// 2️⃣ Preparar conexión a la API
			String urlApi = "http://localhost:9527/api/mostrarUsuarios";
			URL url = new URL(urlApi);
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");
			conex.setRequestProperty("Authorization", "Bearer " + token); // 🔑 enviar token

			// 3️⃣ Leer respuesta
			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
				StringBuilder response = new StringBuilder();
				String inputLine;
				while ((inputLine = in.readLine()) != null)
					response.append(inputLine);
				in.close();

				// 4️⃣ Parsear JSON a lista de DTOs
				JSONArray jsonLista = new JSONArray(response.toString());
				for (int i = 0; i < jsonLista.length(); i++) {
					JSONObject jsonUsuario = jsonLista.getJSONObject(i);
					UsuarioDto usuario = new UsuarioDto();
					usuario.setIdUsuario(jsonUsuario.optLong("idUsuario"));
					usuario.setNombreCompletoUsuario(Utilidades.getValorSeguro(jsonUsuario, "nombreCompletoUsuario"));
					usuario.setAliasUsuario(Utilidades.getValorSeguro(jsonUsuario, "aliasUsuario"));
					usuario.setFechaNacimientoUsuario(Utilidades.getValorSeguro(jsonUsuario, "fechaNacimientoUsuario"));
					usuario.setEmailUsuario(Utilidades.getValorSeguro(jsonUsuario, "emailUsuario"));
					usuario.setTelefonoUsuario(Utilidades.getValorSeguro(jsonUsuario, "telefonoUsuario"));
					usuario.setRolUsuario(RolUsuario.valueOf(Utilidades.getValorSeguro(jsonUsuario, "rolUsuario")));
					usuario.setDescripcionUsuario(Utilidades.getValorSeguro(jsonUsuario, "descripcionUsuario"));

					String imagenBase64 = Utilidades.getValorSeguro(jsonUsuario, "imagenUsuario");
					if (imagenBase64 != null)
						usuario.setImagenUsuario(Base64.getDecoder().decode(imagenBase64));

					String estadoStr = Utilidades.getValorSeguro(jsonUsuario, "estadoUsuario");
					if (estadoStr != null)
						usuario.setEstadoUsuario(Estado.valueOf(estadoStr));

					usuario.setEsPremium(jsonUsuario.optBoolean("esPremium", false));

					lista.add(usuario);
				}
			} else {
				throw new IllegalStateException("Error al obtener usuarios. Código HTTP: " + responseCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error al obtener lista de usuarios: " + e.getMessage());
		}

		return lista;
	}

	public String crearUsuarioDesdeFormulario(HttpServletRequest request) {

	    try {
	        String nombre = request.getParameter("nombreCompletoUsuario");
	        String alias = request.getParameter("aliasUsuario");
	        String fechaNac = request.getParameter("fechaNacimientoUsuario");
	        String email = request.getParameter("emailUsuario");
	        String telefono = request.getParameter("telefonoUsuario");
	        String password = request.getParameter("passwordUsuario");
	        String password2 = request.getParameter("passwordUsuario2");
	        String rolString = request.getParameter("rolUsuario");
	        String descripcion = request.getParameter("descripcionUsuario");

	        if (!password.equals(password2)) {
	            return "password_no_coincide";
	        }

	        UsuarioDto usuario = new UsuarioDto();
	        usuario.setNombreCompletoUsuario(nombre);
	        usuario.setAliasUsuario(alias);
	        usuario.setFechaNacimientoUsuario(fechaNac);
	        usuario.setEmailUsuario(email);
	        usuario.setTelefonoUsuario(telefono);
	        usuario.setPasswordUsuario(password);
	        usuario.setRolUsuario(RolUsuario.valueOf(rolString));
	        usuario.setDescripcionUsuario(descripcion);

	        Part imagenPart = request.getPart("imagenUsuario");
	        if (imagenPart != null && imagenPart.getSize() > 0) {
	            InputStream is = imagenPart.getInputStream();
	            byte[] imagenBytes = is.readAllBytes();
	            usuario.setImagenUsuario(imagenBytes);
	        }

	        return guardarUsuario(usuario);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return "error_servidor";
	    }
	}


	/**
	 * Guarda un nuevo usuario en el sistema.
	 * 
	 * @param usuario El usuario a guardar.
	 */
	private String guardarUsuario(UsuarioDto usuario) {

		try {
			JSONObject json = new JSONObject();
			json.put("nombreCompletoUsuario", usuario.getNombreCompletoUsuario());
			json.put("aliasUsuario", usuario.getAliasUsuario());
			json.put("fechaNacimientoUsuario", usuario.getFechaNacimientoUsuario());
			json.put("emailUsuario", usuario.getEmailUsuario());
			json.put("telefonoUsuario", usuario.getTelefonoUsuario());
			json.put("passwordUsuario", usuario.getPasswordUsuario());
			json.put("rolUsuario", usuario.getRolUsuario().name());
			if (usuario.getImagenUsuario() != null) {
			    String imagenBase64 = Base64.getEncoder().encodeToString(usuario.getImagenUsuario());
			    json.put("imagenUsuario", imagenBase64);
			} else {
			    json.put("imagenUsuario", JSONObject.NULL);
			}

			String urlApi = "http://localhost:9527/api/guardarUsuario";
			HttpURLConnection conex = (HttpURLConnection) new URL(urlApi).openConnection();
			conex.setRequestMethod("POST");
			conex.setRequestProperty("Content-Type", "application/json");
			conex.setDoOutput(true);

			try (OutputStream os = conex.getOutputStream()) {
				os.write(json.toString().getBytes("utf-8"));
			}

			int responseCode = conex.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				return "ok";
			}

			if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
				try (BufferedReader br = new BufferedReader(new InputStreamReader(conex.getErrorStream(), "utf-8"))) {

					String error = br.lines().reduce("", String::concat);

					if (error.contains("ya está en uso")) {
						return "usuario_existente";
					}
					if (error.contains("email")) {
						return "email_invalido";
					}
				}
			}

			return "error_servidor";

		} catch (Exception e) {
			e.printStackTrace();
			return "error_servidor";
		}
	}

	/**
	 * Modifica un usuario existente en el sistema.
	 * 
	 * @param idUsuario El ID del usuario a modificar.
	 * @param usuario   El objeto con los nuevos datos del usuario.
	 * @return true si la modificación fue exitosa, false en caso contrario.
	 */
	public boolean modificarUsuario(HttpServletRequest request, String idUsuario, UsuarioDto usuario) {
		try {
			// 1️⃣ Obtener el token de la sesión
			HttpSession session = request.getSession(false);
			if (session == null)
				throw new IllegalStateException("No hay sesión activa");
			String token = (String) session.getAttribute("token");
			if (token == null || token.isEmpty())
				throw new IllegalStateException("No se encontró token JWT");

			// 2️⃣ Construir JSON con los datos del usuario
			JSONObject json = new JSONObject();
			json.put("idUsuario", usuario.getIdUsuario());
			json.put("nombreCompletoUsuario", usuario.getNombreCompletoUsuario());
			json.put("aliasUsuario", usuario.getAliasUsuario());
			json.put("fechaNacimientoUsuario", usuario.getFechaNacimientoUsuario());
			json.put("emailUsuario", usuario.getEmailUsuario());
			json.put("telefonoUsuario", usuario.getTelefonoUsuario());
			json.put("passwordUsuario", usuario.getPasswordUsuario());
			json.put("rolUsuario", usuario.getRolUsuario());
			json.put("descripcionUsuario", usuario.getDescripcionUsuario());

			if (usuario.getImagenUsuario() != null) {
				String imagenBase64 = Base64.getEncoder().encodeToString(usuario.getImagenUsuario());
				json.put("imagenUsuario", imagenBase64);
			} else {
				json.put("imagenUsuario", JSONObject.NULL);
			}

			json.put("estadoUsuario", usuario.getEstadoUsuario());
			json.put("esPremium", usuario.isEsPremium());

			// 3️⃣ Llamar a la API con token en Authorization
			String urlApi = "http://localhost:9527/api/modificarUsuario/" + idUsuario;
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
	 * Elimina un usuario por su ID.
	 * 
	 * @param idUsuario El ID del usuario a eliminar.
	 * @return true si la eliminación fue exitosa, false en caso contrario.
	 */
	public boolean eliminarUsuario(Long idUsuario, HttpServletRequest request) {
		try {
			// 1️⃣ Obtener el token JWT de la sesión
			HttpSession session = request.getSession(false);
			if (session == null)
				throw new IllegalStateException("No hay sesión activa");
			String token = (String) session.getAttribute("token");
			if (token == null || token.isEmpty())
				throw new IllegalStateException("No se encontró token JWT");

			// 2️⃣ Preparar la conexión a la API
			String urlApi = "http://localhost:9527/api/eliminarUsuario/" + idUsuario;
			URL url = new URL(urlApi);
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("DELETE");
			conex.setRequestProperty("Accept", "application/json");
			conex.setRequestProperty("Authorization", "Bearer " + token); // 🔑 enviar token

			// 3️⃣ Leer el código de respuesta
			int responseCode = conex.getResponseCode();
			return responseCode == HttpURLConnection.HTTP_OK;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Marca al usuario como Premium (esPremium = true).
	 *
	 * @param idUsuario El ID del usuario.
	 * @return true si se actualizó correctamente, false en caso contrario.
	 */
	  public boolean marcarPremium(Long idUsuario, HttpServletRequest request) {

	        try {
	            // 1️⃣ Sesión y token
	            HttpSession session = request.getSession(false);
	            if (session == null)
	                throw new IllegalStateException("No hay sesión");

	            String token = (String) session.getAttribute("token");
	            if (token == null || token.isEmpty())
	                throw new IllegalStateException("No hay token JWT");

	            // 2️⃣ Llamada a la API
	            String urlApi = "http://localhost:9527/api/modificarPremiumUsuario/" + idUsuario;
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
