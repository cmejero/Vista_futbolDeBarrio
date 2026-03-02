package vista_futbolDeBarrio.servicios;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;

import org.json.JSONObject;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import vista_futbolDeBarrio.dtos.LoginGoogleDto;

/**
 * Servicio encargado de manejar el login de usuarios a través de Google.
 * Proporciona métodos para obtener tokens, extraer información de usuario y
 * autenticar al usuario en la API interna.
 */
public class InicioSesionGoogleServicio {

	private static final String API_LOGIN_URL = "http://localhost:9527/api/loginGoogle";
	private static final String CLIENT_ID = System.getenv("GOOGLE_CLIENT_ID");
	private static final String CLIENT_SECRET = System.getenv("GOOGLE_CLIENT_SECRET");

	/**
	 * Realiza el login de un usuario usando Google.
	 *
	 * @param codeGoogle  Código recibido desde Google para obtener el token.
	 * @param tipoUsuario Tipo de usuario que intenta iniciar sesión.
	 * @param context     Contexto del servlet.
	 * @param request     HttpServletRequest para construir el redirect_uri
	 *                    dinámicamente
	 * @return Objeto LoginGoogleDto con la información del usuario, o null si falla
	 *         el login.
	 * @throws IOException Si ocurre un error durante la comunicación con la API o
	 *                     Google.
	 */
	public LoginGoogleDto loginConGoogle(String codeGoogle, String tipoUsuario, ServletContext context,
			HttpServletRequest request) throws IOException {

		String redirectUri;
		if ("localhost".equals(request.getServerName())) {
			redirectUri = request.getScheme() + "://" + request.getServerName()
					+ (request.getServerPort() != 80 && request.getServerPort() != 443 ? ":" + request.getServerPort()
							: "")
					+ "/vista_futbolDeBarrio/login";
		} else {
		    redirectUri = "https://" + request.getServerName() + "/login"; 
		}

		String respuestaToken = obtenerTokenDesdeCodigo(codeGoogle, redirectUri);

		if (respuestaToken == null)
			return null;

		String email = extraerEmailDesdeToken(respuestaToken);
		String nombreCompleto = extraerNombreDesdeToken(respuestaToken);

		if (tipoUsuario == null || tipoUsuario.isEmpty())
			return null;
		return loginUsuarioAPI(email, tipoUsuario, nombreCompleto, context);
	}

	// ----------------- MÉTODOS AUXILIARES -----------------
	/**
	 * Obtiene el token de acceso de Google usando el código recibido.
	 *
	 * @param code        Código de autorización proporcionado por Google.
	 * @param redirectUri URI de redirección configurado en Google API.
	 * @return Token de acceso como String, o null si falla.
	 * @throws IOException Si ocurre un error durante la solicitud HTTP.
	 */
	private String obtenerTokenDesdeCodigo(String codigo, String redirectUri) {
		try {
			URL url = new URL("https://oauth2.googleapis.com/token");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			String datos = "code=" + URLEncoder.encode(codigo, "UTF-8") + "&client_id="
					+ URLEncoder.encode(CLIENT_ID, "UTF-8") + "&client_secret="
					+ URLEncoder.encode(CLIENT_SECRET, "UTF-8") + "&redirect_uri="
					+ URLEncoder.encode(redirectUri, "UTF-8") + "&grant_type=authorization_code";

			try (OutputStream os = conn.getOutputStream()) {
				os.write(datos.getBytes("UTF-8"));
			}

			BufferedReader reader = (conn.getResponseCode() >= 400)
					? new BufferedReader(new InputStreamReader(conn.getErrorStream()))
					: new BufferedReader(new InputStreamReader(conn.getInputStream()));

			StringBuilder respuesta = new StringBuilder();
			String linea;
			while ((linea = reader.readLine()) != null)
				respuesta.append(linea);
			reader.close();

			return respuesta.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Extrae el email del usuario a partir del token de Google.
	 *
	 * @param token Token de acceso obtenido de Google.
	 * @return Email del usuario como String.
	 */
	private String extraerEmailDesdeToken(String respuestaToken) {
		JSONObject json = new JSONObject(respuestaToken);
		String idToken = json.getString("id_token");
		String payload = new String(Base64.getUrlDecoder().decode(idToken.split("\\.")[1]));
		return new JSONObject(payload).getString("email");
	}

	/**
	 * Extrae el nombre completo del usuario a partir del token de Google.
	 *
	 * @param token Token de acceso obtenido de Google.
	 * @return Nombre completo del usuario como String.
	 */
	private String extraerNombreDesdeToken(String respuestaToken) {
		JSONObject json = new JSONObject(respuestaToken);
		String idToken = json.getString("id_token");
		String payload = new String(Base64.getUrlDecoder().decode(idToken.split("\\.")[1]));
		return new JSONObject(payload).optString("name", "Desconocido");
	}

	/**
	 * Realiza el login del usuario en la API interna usando la información
	 * obtenida.
	 *
	 * @param email          Email del usuario.
	 * @param tipoUsuario    Tipo de usuario.
	 * @param nombreCompleto Nombre completo del usuario.
	 * @param context        Contexto del servlet.
	 * @return Objeto LoginGoogleDto con la información del usuario, o null si
	 *         falla.
	 */
	private LoginGoogleDto loginUsuarioAPI(String email, String tipoUsuario, String nombreCompleto,
			ServletContext context) {
		try {
			JSONObject json = new JSONObject();
			json.put("email", email);
			json.put("tipoUsuario", tipoUsuario);
			json.put("nombreCompleto", nombreCompleto != null ? nombreCompleto : "Desconocido");

			try (InputStream is = context.getResourceAsStream("/Imagenes/usuarioPorDefecto.jpg")) {
				if (is != null) {
					byte[] bytesImagen = is.readAllBytes();
					json.put("imagenUsuario", Base64.getEncoder().encodeToString(bytesImagen));
				} else {
					json.put("imagenUsuario", JSONObject.NULL);
				}
			}

			URL url = new URL(API_LOGIN_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);

			try (OutputStream os = conn.getOutputStream()) {
				os.write(json.toString().getBytes("utf-8"));
			}

			if (conn.getResponseCode() != 200)
				return null;

			StringBuilder response = new StringBuilder();
			try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
				String line;
				while ((line = in.readLine()) != null)
					response.append(line);
			}

			JSONObject jsonResponse = new JSONObject(response.toString());
			if (!jsonResponse.has("login") || !jsonResponse.has("token"))
				return null;

			JSONObject loginJson = jsonResponse.getJSONObject("login");
			String token = jsonResponse.getString("token");

			LoginGoogleDto loginDto = new LoginGoogleDto();
			loginDto.setEmail(loginJson.optString("email", ""));
			loginDto.setNombreCompleto(loginJson.optString("nombreCompleto", ""));
			loginDto.setTipoUsuario(loginJson.optString("tipoUsuario", ""));
			loginDto.setToken(token);
			loginDto.setIdTipoUsuario(loginJson.optLong("idTipoUsuario", 0));
			loginDto.setEsPremium(loginJson.optBoolean("esPremium", false));

			if (loginJson.has("imagenUsuario") && !loginJson.isNull("imagenUsuario")) {
				loginDto.setImagenUsuario(Base64.getDecoder().decode(loginJson.optString("imagenUsuario")));
			}

			return loginDto;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}