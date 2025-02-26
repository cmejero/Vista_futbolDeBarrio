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

public class UsuarioServicio {

	public void guardarUsuario(UsuarioDto usuario) {

		try {

			// Tras pasarle un dto construyo el json en base al objeto usuario

			JSONObject json = new JSONObject();

			json.put("nombreCompletoUsuario", usuario.getNombreCompletoUsuario());
			json.put("aliasUsuario", usuario.getAliasUsuario());
			json.put("fechaNacimientoUsuario", usuario.getFechaNacimientoUsuario());
			json.put("emailUsuario", usuario.getEmailUsuario());
			json.put("telefonoUsuario", usuario.getTelefonoUsuario());
			json.put("passwordUsuario", usuario.getPasswordUsuario());
			json.put("rolUsuario", usuario.getRolUsuario().name()); 
			json.put("descripcionUsuario", usuario.getDescripcionUsuario());
			json.put("imagenUsuario", usuario.getImagenUsuario());
			json.put("estadoUsuario", usuario.getEstadoUsuario());

			String urlApi = "http://localhost:9527/api/guardarUsuario";

			URL url = new URL(urlApi);
			// Habro la conexion a la api

			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("POST");
			conex.setRequestProperty("Content-Type", "application/json");
			conex.setDoOutput(true);

			// Meto el JSON en la solicitud

			try (OutputStream os = conex.getOutputStream()) {
				byte[] input = json.toString().getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.out.println("Usuario guardado correctamente.");
			} else {
				System.out.println("Error al guardar usuario: " + responseCode);
			}

		} catch (Exception e) {

			System.out.println("ERROR- [ServiciosUsuario]" + e);

		}

	}

	public ArrayList<UsuarioDto> listausuario() {
	    ArrayList<UsuarioDto> lista = new ArrayList<UsuarioDto>();
	    StringBuilder response = new StringBuilder(); // Declaración fuera del bloque try-catch

	    try {
	        String urlApi = "http://localhost:9527/api/mostrarUsuarios";
	        URL url = new URL(urlApi);

	        HttpURLConnection conex = (HttpURLConnection) url.openConnection();
	        conex.setRequestMethod("GET");
	        conex.setRequestProperty("Accept", "application/json");

	        int responseCode = conex.getResponseCode();

	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            // Leer la respuesta
	            BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
	            String inputLine;

	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);  // Continuamos agregando a la respuesta
	            }
	            in.close();

	            // Procesar la respuesta JSON
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
	                        System.out.println("El campo 'imagenUsuario' no es un String válido.");
	                        usuario.setImagenUsuario(null);
	                    }
	                }

	                // Procesar el estado del usuario
	                String estadoUsuarioString = jsonUsuario.getString("estadoUsuario");
	                Estado estadoUsuario = Estado.valueOf(estadoUsuarioString);
	                usuario.setEstadoUsuario(estadoUsuario);

	                lista.add(usuario);
	                System.out.println(response.toString());
	            }
	        } else {
	            System.out.println("Error al obtener usuarios: " + responseCode);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("ERROR- ServiciosUsuarios-ListaUsuario: " + e.getMessage());
	    }

	    System.out.println(lista);
	    return lista;
	}




	public boolean modificarUsuario(String idUsuario, UsuarioDto usuario) {
		try {
			// Crear JSON del objeto UsuarioDtos
			JSONObject json = new JSONObject();
			json.put("idUsuario", usuario.getIdUsuario());
			json.put("nombreCompletoUsuario", usuario.getNombreCompletoUsuario());
			json.put("aliasUsuario", usuario.getAliasUsuario());
			json.put("fechaNacimientoUsuario", usuario.getFechaNacimientoUsuario());
			json.put("emailUsuario", usuario.getEmailUsuario());
			json.put("telefonoUsuario", usuario.getTelefonoUsuario());
			json.put("passwordUsuario", usuario.getPasswordUsuario());
			json.put("rolUsuario", usuario.getRolUsuario().name());
			json.put("descripcionUsuario", usuario.getDescripcionUsuario());
			json.put("imagenUsuario", usuario.getImagenUsuario());
			json.put("estadoUsuario", usuario.getEstadoUsuario());

			// URL para hacer la solicitud PUT. Aquí estamos usando el idUsuario en la URL
			String urlApi = "http://localhost:9527/api/modificarUsuario/" + idUsuario;
			URL url = new URL(urlApi);

			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("PUT");
			conex.setRequestProperty("Content-Type", "application/json");
			conex.setDoOutput(true);

			// Enviar JSON en la solicitud PUT
			try (OutputStream os = conex.getOutputStream()) {
				byte[] input = json.toString().getBytes();
				os.write(input, 0, input.length);
			}

			// Verificar la respuesta
			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				return true; // Usuario actualizado correctamente
			} else {
				return false; // Error al actualizar
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Error
		}
	}
	
	public boolean eliminarUsuario(Long idUsuario) {
	    try {
	        // Construir la URL para la solicitud DELETE, incluyendo el ID del usuario
	        String urlApi = "http://localhost:9527/api/eliminarUsuario/" + idUsuario;
	        URL url = new URL(urlApi);

	        // Abrir la conexión HTTP
	        HttpURLConnection conex = (HttpURLConnection) url.openConnection();
	        conex.setRequestMethod("DELETE");
	        conex.setRequestProperty("Accept", "application/json");

	        // Obtener el código de respuesta
	        int responseCode = conex.getResponseCode();

	        // Verificar si la eliminación fue exitosa
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            System.out.println("Usuario eliminado correctamente.");
	            return true;
	        } else {
	            System.out.println("Error al eliminar usuario: " + responseCode);
	            return false;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("ERROR- [UsuarioServicio.eliminarUsuario]: " + e.getMessage());
	        return false;
	    }
	}


}
