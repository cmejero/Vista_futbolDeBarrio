		package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

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
	public UsuarioDto obtenerUsuario(Long idUsuario) {
		if (idUsuario == null) return null; // O lanzar excepción si quieres

		
		try {
		    String urlApi = "http://localhost:9527/api/usuarios/" + idUsuario;
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
		        if (imagenBase64 != null) usuario.setImagenUsuario(Base64.getDecoder().decode(imagenBase64));
		        String estadoStr = Utilidades.getValorSeguro(jsonUsuario, "estadoUsuario");
		        if (estadoStr != null) usuario.setEstadoUsuario(Estado.valueOf(estadoStr));
		        usuario.setEsPremium(jsonUsuario.optBoolean("esPremium", false));

		        return usuario;
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		    System.err.println("Error al obtener usuario por ID: " + e.getMessage());
		}
		return null;
		

		}

		public ArrayList<UsuarioDto> obtenerUsuarios() {
		ArrayList<UsuarioDto> lista = new ArrayList<>();
		try {
		String urlApi = "http://localhost:9527/api/mostrarUsuarios";
		URL url = new URL(urlApi);
		HttpURLConnection conex = (HttpURLConnection) url.openConnection();
		conex.setRequestMethod("GET");
		conex.setRequestProperty("Accept", "application/json");

		
		    int responseCode = conex.getResponseCode();
		    if (responseCode == HttpURLConnection.HTTP_OK) {
		        BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
		        StringBuilder response = new StringBuilder();
		        String inputLine;
		        while ((inputLine = in.readLine()) != null) response.append(inputLine);
		        in.close();

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
		            if (imagenBase64 != null) usuario.setImagenUsuario(Base64.getDecoder().decode(imagenBase64));
		            String estadoStr = Utilidades.getValorSeguro(jsonUsuario, "estadoUsuario");
		            if (estadoStr != null) usuario.setEstadoUsuario(Estado.valueOf(estadoStr));
		            usuario.setEsPremium(jsonUsuario.optBoolean("esPremium", false));

		            lista.add(usuario);
		        }
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		    System.err.println("Error al obtener lista de usuarios: " + e.getMessage());
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
			json.put("esPremium", usuario.isEsPremium());

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
			json.put("esPremium", usuario.isEsPremium());

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
	 * Marca al usuario como Premium (esPremium = true).
	 *
	 * @param idUsuario El ID del usuario.
	 * @return true si se actualizó correctamente, false en caso contrario.
	 */
	public boolean marcarPremium(Long idUsuario) {
		try {
			String urlApi = "http://localhost:9527/api/modificarPremiumUsuario/" + idUsuario;
			URL url = new URL(urlApi);

			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("PUT");
			conex.setRequestProperty("Content-Type", "application/json");
			conex.setDoOutput(true);

			JSONObject json = new JSONObject();
			json.put("esPremium", true);

			try (OutputStream os = conex.getOutputStream()) {
				os.write(json.toString().getBytes("utf-8"));
			}

			int responseCode = conex.getResponseCode();
			return responseCode == HttpURLConnection.HTTP_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


}
