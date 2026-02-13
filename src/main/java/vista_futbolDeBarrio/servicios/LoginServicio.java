package vista_futbolDeBarrio.servicios;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import vista_futbolDeBarrio.dtos.*;

/**
 * Servicio encargado de la lógica de autenticación.
 */
public class LoginServicio {

	/**
	 * Realiza el login de un usuario en la API interna.
	 *
	 * @param email       Correo del usuario.
	 * @param password    Contraseña del usuario.
	 * @param tipoUsuario Tipo de usuario que intenta iniciar sesión.
	 * @return Objeto RespuestaLoginDto con los datos del login, o null si falla la
	 *         operación.
	 */
	public RespuestaLoginDto login(String email, String password, String tipoUsuario) {
		try {
			JSONObject json = new JSONObject();
			json.put("email", email);
			json.put("password", password);
			json.put("tipoUsuario", tipoUsuario);

			HttpURLConnection conex = (HttpURLConnection) new URL("http://localhost:9527/api/login").openConnection();

			conex.setRequestMethod("POST");
			conex.setRequestProperty("Content-Type", "application/json");
			conex.setDoOutput(true);

			try (OutputStream os = conex.getOutputStream()) {
				os.write(json.toString().getBytes("utf-8"));
			}

			if (conex.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conex.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String linea;
				while ((linea = br.readLine()) != null)
					sb.append(linea);

				return construirRespuestaLogin(new JSONObject(sb.toString()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Obtiene los datos de un usuario a partir de su token de sesión.
	 *
	 * @param token       Token JWT del usuario.
	 * @param tipoUsuario Tipo de usuario.
	 * @return Objeto con los datos del usuario, o null si ocurre un error.
	 */
	public Object obtenerDatosUsuario(String token, String tipoUsuario) {
		try {
			HttpURLConnection conex = (HttpURLConnection) new URL("http://localhost:9527/api/usuario/datos")
					.openConnection();

			conex.setRequestMethod("GET");
			conex.setRequestProperty("Authorization", "Bearer " + token);

			if (conex.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conex.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String linea;
				while ((linea = br.readLine()) != null)
					sb.append(linea);

				JSONObject datos = new JSONObject(sb.toString()).getJSONObject("datosUsuario");
				return mapearUsuario(tipoUsuario, datos);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Mapea un JSONObject con los datos del usuario al DTO correspondiente según su
	 * tipo.
	 *
	 * @param tipoUsuario Tipo de usuario ("jugador", "administrador", "club" o
	 *                    "instalacion").
	 * @param datos       JSONObject con la información del usuario.
	 * @return Objeto DTO correspondiente al tipo de usuario, o null si el tipo no
	 *         coincide.
	 */
	private Object mapearUsuario(String tipoUsuario, JSONObject datos) {
		switch (tipoUsuario) {
		case "jugador":
		case "administrador":
			UsuarioDto u = new UsuarioDto();
			u.setIdUsuario(datos.getLong("idUsuario"));
			u.setNombreCompletoUsuario(datos.getString("nombreCompletoUsuario"));
			u.setEmailUsuario(datos.getString("emailUsuario"));
			u.setEsPremium(datos.getBoolean("esPremium"));
			return u;
		case "club":
			ClubDto c = new ClubDto();
			c.setIdClub(datos.getLong("idClub"));
			c.setNombreClub(datos.getString("nombreClub"));
			c.setEmailClub(datos.getString("emailClub"));
			c.setEsPremium(datos.getBoolean("esPremium"));
			return c;
		case "instalacion":
			InstalacionDto i = new InstalacionDto();
			i.setIdInstalacion(datos.getLong("idInstalacion"));
			i.setNombreInstalacion(datos.getString("nombreInstalacion"));
			return i;
		}
		return null;
	}

	
	/**
	 * Construye un objeto RespuestaLoginDto a partir de la respuesta JSON de login.
	 *
	 * @param json JSONObject con los datos de la respuesta de login.
	 * @return Objeto RespuestaLoginDto con el token, tipo de usuario y datos del usuario.
	 */
	private RespuestaLoginDto construirRespuestaLogin(JSONObject json) {
		RespuestaLoginDto r = new RespuestaLoginDto();
		r.setToken(json.getString("token"));
		r.setTipoUsuario(json.getString("tipoUsuario"));
		r.setDatosUsuario(mapearUsuario(r.getTipoUsuario(), json.getJSONObject("datosUsuario")));
		return r;
	}
}
