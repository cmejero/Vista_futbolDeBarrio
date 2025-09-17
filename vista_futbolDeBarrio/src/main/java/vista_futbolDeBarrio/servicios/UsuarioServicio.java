package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.ServletContext;
import vista_futbolDeBarrio.dtos.UsuarioDto;
import vista_futbolDeBarrio.enums.Estado;
import vista_futbolDeBarrio.enums.RolUsuario;

/**
 * Clase que se encarga de la logica de los metodos CRUD usuario
 */
public class UsuarioServicio {

	 /**
     * Obtiene la lista de todos los usuarios.
     * 
     * @return Lista de usuarios.
     */
	public ArrayList<UsuarioDto> listausuario() {
		ArrayList<UsuarioDto> lista = new ArrayList<UsuarioDto>();
		StringBuilder response = new StringBuilder(); 
		try {
			String urlApi = "http://localhost:9527/api/mostrarUsuarios";
			URL url = new URL(urlApi);
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");
			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine); 
				}
				in.close();
				JSONArray jsonlista = new JSONArray(response.toString());
				for (int i = 0; i < jsonlista.length(); i++) {
					JSONObject jsonUsuario = jsonlista.getJSONObject(i);
					UsuarioDto usuario = new UsuarioDto();
					usuario.setIdUsuario(jsonUsuario.getLong("idUsuario"));
					usuario.setNombreCompletoUsuario(jsonUsuario.getString("nombreCompletoUsuario"));
					usuario.setAliasUsuario(jsonUsuario.getString("aliasUsuario"));
					usuario.setFechaNacimientoUsuario(jsonUsuario.getString("fechaNacimientoUsuario"));
					usuario.setEmailUsuario(jsonUsuario.getString("emailUsuario"));
					usuario.setTelefonoUsuario(jsonUsuario.getString("telefonoUsuario"));
					usuario.setPasswordUsuario(jsonUsuario.getString("passwordUsuario"));
					String rol = jsonUsuario.getString("rolUsuario");
					RolUsuario rolUsuario = RolUsuario.valueOf(rol);
					usuario.setRolUsuario(rolUsuario);
					usuario.setDescripcionUsuario(jsonUsuario.getString("descripcionUsuario"));
					String imagenBase64 = null;
					if (jsonUsuario.has("imagenUsuario") && !jsonUsuario.isNull("imagenUsuario")) {
						if (jsonUsuario.get("imagenUsuario") instanceof String) {
							imagenBase64 = jsonUsuario.getString("imagenUsuario");

							if (imagenBase64 != null && !imagenBase64.isEmpty()) {
								byte[] imageBytes = Base64.getDecoder().decode(imagenBase64);
								usuario.setImagenUsuario(imageBytes);
							}
						} else {
							// System.out.println("El campo 'imagenUsuario' no es un String válido.");
							usuario.setImagenUsuario(null);
						}
					}
					String estadoUsuarioString = jsonUsuario.getString("estadoUsuario");
					Estado estadoUsuario = Estado.valueOf(estadoUsuarioString);
					usuario.setEstadoUsuario(estadoUsuario);
					lista.add(usuario);
					// System.out.println(response.toString());
				}
			} else {
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("ERROR- ServiciosUsuarios-ListaUsuario: " + e.getMessage());
		}
		return lista;
	}

	 /**
     * Guarda un nuevo usuario en el sistema.
     * 
     * @param usuario El usuario a guardar.
     */
	public void guardarUsuario(UsuarioDto usuario) {
		try {
			JSONObject json = new JSONObject();
			json.put("nombreCompletoUsuario", usuario.getNombreCompletoUsuario());
			json.put("aliasUsuario", usuario.getAliasUsuario());
			json.put("fechaNacimientoUsuario", usuario.getFechaNacimientoUsuario());
			json.put("emailUsuario", usuario.getEmailUsuario());
			json.put("telefonoUsuario", usuario.getTelefonoUsuario());
			json.put("passwordUsuario", usuario.getPasswordUsuario());
			json.put("rolUsuario", usuario.getRolUsuario().name());
			json.put("descripcionUsuario", usuario.getDescripcionUsuario());
			byte[] imagenBytes = usuario.getImagenUsuario();
			if (imagenBytes != null && imagenBytes.length > 0) {
				String imagenBase64 = Base64.getEncoder().encodeToString(imagenBytes);
				json.put("imagenUsuario", imagenBase64);
			} else {
				json.put("imagenUsuario", JSONObject.NULL); 
			}
			json.put("estadoUsuario", usuario.getEstadoUsuario());

			String urlApi = "http://localhost:9527/api/guardarUsuario";
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
				// System.out.println("Usuario guardado correctamente.");
			} else {
				// System.out.println("Error al guardar usuario: " + responseCode);
			}

		} catch (Exception e) {
			// System.out.println("ERROR- [ServiciosUsuario]" + e);
		}
	}

	  /**
     * Modifica un usuario existente en el sistema.
     * 
     * @param idUsuario El ID del usuario a modificar.
     * @param usuario El objeto con los nuevos datos del usuario.
     * @return true si la modificación fue exitosa, false en caso contrario.
     */
	public boolean modificarUsuario(String idUsuario, UsuarioDto usuario) {
		try {
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

			byte[] imagenBytes = usuario.getImagenUsuario();

			if (imagenBytes != null && imagenBytes.length > 0) {

				String imagenBase64 = Base64.getEncoder().encodeToString(imagenBytes);
				json.put("imagenUsuario", imagenBase64);
			} else {

				json.put("imagenUsuario", JSONObject.NULL); 
			}

			json.put("estadoUsuario", usuario.getEstadoUsuario());

			// System.out.println("Datos enviados a la API:");
			// System.out.println(json.toString());
			String urlApi = "http://localhost:9527/api/modificarUsuario/" + idUsuario;
			URL url = new URL(urlApi);

			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("PUT");
			conex.setRequestProperty("Content-Type", "application/json");
			conex.setDoOutput(true);

			try (OutputStream os = conex.getOutputStream()) {
				byte[] input = json.toString().getBytes();
				os.write(input, 0, input.length);
			}

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				return true; 
			} else {
		
				// System.out.println("Error al modificar usuario, código de respuesta: " + responseCode);
				return false; 
			}
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
	public boolean eliminarUsuario(Long idUsuario) {
		try {
			String urlApi = "http://localhost:9527/api/eliminarUsuario/" + idUsuario;
			URL url = new URL(urlApi);

			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("DELETE");
			conex.setRequestProperty("Accept", "application/json");

			int responseCode = conex.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				// System.out.println("Usuario eliminado correctamente.");
				return true;
			} else {
				// System.out.println("Error al eliminar usuario: " + responseCode);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("ERROR- [UsuarioServicio.eliminarUsuario]: " + e.getMessage());
			return false;
		}
	}

	/**
     * Obtiene la imagen por defecto del usuario.
     * 
     * @param context El contexto del servlet.
     * @return Los bytes de la imagen por defecto o null si no existe.
     */
	public byte[] obtenerImagenPorDefecto(ServletContext context) {
		try {

			String rutaImagen = context.getRealPath("/Imagenes/usuarioPorDefecto.jpg");

			File archivoImagen = new File(rutaImagen);

			if (archivoImagen.exists()) {
				return Files.readAllBytes(archivoImagen.toPath());
			} else {
				// System.out.println("El archivo de imagen por defecto no se encuentra: " + rutaImagen);
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
