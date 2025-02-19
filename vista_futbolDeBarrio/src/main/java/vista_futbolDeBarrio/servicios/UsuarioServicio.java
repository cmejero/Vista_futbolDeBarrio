package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
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

			json.put("nombreUsuario", usuario.getNombreCompletoUsuario());
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

		try {
			String urlApi = "http://localhost:9527/api/usuario";
			URL url = new URL(urlApi);

			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");

			int responseCode = conex.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				// Leer la respuesta
				BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// Procesar la respuesta JSON
				JSONArray jsonlista = new JSONArray(response.toString());

				System.out.println(jsonlista);

				for (int i = 0; i < jsonlista.length(); i++) {
					JSONObject jsonUsuario = jsonlista.getJSONObject(i);
					UsuarioDto usuario = new UsuarioDto();

					usuario.setIdUsuario(jsonUsuario.getLong("id_usuario"));
					usuario.setNombreCompletoUsuario(jsonUsuario.getString("nombreCompletoUsuario"));
					usuario.setAliasUsuario(jsonUsuario.getString("aliasUsuario"));
					usuario.setFechaNacimientoUsuario(jsonUsuario.getString("fechaNacimientoUsuario"));
					usuario.setEmailUsuario(jsonUsuario.getString("emailUsuario"));
					usuario.setTelefonoUsuario(jsonUsuario.getString("telefonoUsuario")); // Cambio en la "U"
					usuario.setPasswordUsuario(jsonUsuario.getString("passwordUsuario"));
					String rol = jsonUsuario.getString("rolUsuario").toUpperCase(); 
					RolUsuario rolUsuario = RolUsuario.valueOf(rol);
					usuario.setRolUsuario(rolUsuario);
					usuario.setDescripcionUsuario(jsonUsuario.getString("descripcionUsuario"));
                    String imagenBase64 = jsonUsuario.getString("imagenUsuario");
                    if (imagenBase64 != null && !imagenBase64.isEmpty()) {
                        // Si la imagen viene como Base64, puedes guardarla en un archivo
                        String fileName = "usuario_" + usuario.getIdUsuario() + ".jpg"; // Nombre del archivo
                        byte[] imageBytes = Base64.getDecoder().decode(imagenBase64);
                        try (FileOutputStream fos = new FileOutputStream(fileName)) {
                            fos.write(imageBytes);
                            System.out.println("Imagen guardada como: " + fileName);
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Error al guardar la imagen: " + e.getMessage());
                        }
                    }
                	String estadoUsuarioString = jsonUsuario.getString("estadoUsuario").toUpperCase(); 
					Estado estadoUsuario = Estado.valueOf(estadoUsuarioString);
					usuario.setEstadoUsuario(estadoUsuario);


					lista.add(usuario);
				}
			} else {
				System.out.println("Error al obtener usuarios: " + responseCode);
			}
		} catch (Exception e) {
			// Manejo de la excepción con detalles completos
			e.printStackTrace();
			System.out.println("ERROR- ServiciosUsuarios-ListaUsuario: " + e.getMessage());
		}

		// Imprimir la lista de usuarios
		System.out.println(lista);
		return lista;
	}

	public boolean modificarUsuario(String idUsuario, UsuarioDto usuario) {
		try {
			// Crear JSON del objeto UsuarioDtos
			JSONObject json = new JSONObject();
			json.put("nombreCompletoUsuario", usuario.getNombreCompletoUsuario());
			json.put("aliasUsuario", usuario.getAliasUsuario());
			json.put("fechaNacimientoUsuario", usuario.getFechaNacimientoUsuario());
			json.put("emailUsuario", usuario.getEmailUsuario());
			json.put("telefonoUsuario", usuario.getTelefonoUsuario());
			json.put("passwordUsuario", usuario.getPasswordUsuario());
			json.put("rolUsuario", usuario.getRolUsuario());
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

}
