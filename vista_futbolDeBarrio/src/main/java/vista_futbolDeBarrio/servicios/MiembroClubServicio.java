package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import vista_futbolDeBarrio.dtos.MiembroClubDto;
import vista_futbolDeBarrio.dtos.UsuarioDto;
import vista_futbolDeBarrio.enums.Estado;
import vista_futbolDeBarrio.enums.RolUsuario;

public class MiembroClubServicio {

	// Método para guardar un nuevo miembro del club
	public void guardarMiembroClub(MiembroClubDto miembro) {
		try {
			// Crear el objeto JSON a partir del DTO
			JSONObject json = new JSONObject();
			json.put("fechaAltaUsuario", miembro.getFechaAltaUsuario().toString());
			json.put("fechaBajaUsuario",
					miembro.getFechaBajaUsuario() != null ? miembro.getFechaBajaUsuario().toString() : JSONObject.NULL);
			json.put("idClub", miembro.getIdClub());
			json.put("usuarioId", miembro.getUsuarioId());

			// Definir la URL de la API para guardar el miembro
			String urlApi = "http://localhost:9527/api/guardarMiembroClub";
			URL url = new URL(urlApi);

			// Establecer la conexión HTTP
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("POST");
			conex.setRequestProperty("Content-Type", "application/json");
			conex.setDoOutput(true);

			// Enviar el JSON en la solicitud
			try (OutputStream os = conex.getOutputStream()) {
				byte[] input = json.toString().getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			// Verificar la respuesta del servidor
			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.out.println("Miembro del club guardado correctamente.");
			} else {
				System.out.println("Error al guardar miembro del club: " + responseCode);
			}

		} catch (Exception e) {
			System.out.println("ERROR- [MiembroClubServicio] " + e);
		}
	}

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

	            // Convertir la respuesta JSON a una lista de objetos MiembroClubDto
	            JSONArray jsonLista = new JSONArray(response.toString());
	            for (int i = 0; i < jsonLista.length(); i++) {
	                JSONObject jsonMiembro = jsonLista.getJSONObject(i);
	                MiembroClubDto miembro = new MiembroClubDto();

	                miembro.setIdMiembroClub(jsonMiembro.getLong("idMiembroClub"));
	                miembro.setFechaAltaUsuario(jsonMiembro.optString("fechaAltaUsuario", "N/A"));  // Asignar valor predeterminado
	                miembro.setFechaBajaUsuario(jsonMiembro.optString("fechaBajaUsuario", "N/A"));
	                miembro.setIdClub(jsonMiembro.optLong("idClub", -1));  // Valor predeterminado si no existe
	                miembro.setUsuarioId(jsonMiembro.optLong("usuarioId", -1));  // Valor predeterminado si no existe

	                // Si el objeto usuario tiene valores nulos, se asignan valores predeterminados
	                if (jsonMiembro.has("usuario") && !jsonMiembro.isNull("usuario")) {
	                    JSONObject jsonUsuario = jsonMiembro.getJSONObject("usuario");
	                    UsuarioDto usuario = new UsuarioDto();
	                    
	                    // Aseguramos que cada campo de UsuarioDto tiene un valor predeterminado si es null
	                    usuario.setIdUsuario(jsonUsuario.optLong("idUsuario", 0));  // 0 como valor por defecto
	                    usuario.setNombreCompletoUsuario(jsonUsuario.optString("nombreCompletoUsuario", "Desconocido"));
	                    usuario.setAliasUsuario(jsonUsuario.optString("aliasUsuario", "sin alias"));
	                    usuario.setFechaNacimientoUsuario(jsonUsuario.optString("fechaNacimientoUsuario", "N/A"));
	                    usuario.setEmailUsuario(jsonUsuario.optString("emailUsuario", "no-email@dominio.com"));
	                    usuario.setTelefonoUsuario(jsonUsuario.optString("telefonoUsuario", "Sin teléfono"));
	                    usuario.setRolUsuario(RolUsuario.valueOf(jsonUsuario.optString("rolUsuario", "USUARIO")));  // Valor predeterminado si es null
	                    usuario.setDescripcionUsuario(jsonUsuario.optString("descripcionUsuario", "Sin descripción"));
	                    
	                    // Convierte la URL de la imagen (o el nombre del archivo) en un array de bytes
	                    usuario.setImagenUsuario(jsonUsuario.optString("imagenUsuario", "default.jpg").getBytes());

	                    usuario.setEstadoUsuario(Estado.valueOf(jsonUsuario.optString("estadoUsuario", "INACTIVO")));  // Valor predeterminado si es null

	                    miembro.setUsuario(usuario);
	                }

	                lista.add(miembro);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return lista;
	}






	// Método para modificar un miembro del club existente
	public boolean modificarMiembroClub(long idMiembroClub, MiembroClubDto miembro) {
		try {
			// Crear el objeto JSON a partir del DTO
			JSONObject json = new JSONObject();
			json.put("fechaAltaUsuario", miembro.getFechaAltaUsuario().toString());
			json.put("fechaBajaUsuario",
					miembro.getFechaBajaUsuario() != null ? miembro.getFechaBajaUsuario().toString() : JSONObject.NULL);
			json.put("clubId", miembro.getIdClub());
			json.put("usuarioId", miembro.getUsuarioId());

			// Definir la URL de la API para modificar el miembro
			String urlApi = "http://localhost:9527/api/modificarMiembroClub/" + idMiembroClub;
			URL url = new URL(urlApi);

			// Establecer la conexión HTTP
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("PUT");
			conex.setRequestProperty("Content-Type", "application/json");
			conex.setDoOutput(true);

			// Enviar el JSON en la solicitud
			try (OutputStream os = conex.getOutputStream()) {
				byte[] input = json.toString().getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			// Verificar la respuesta del servidor
			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				return true; // Miembro actualizado correctamente
			} else {
				System.out.println("Error al modificar miembro del club: " + responseCode);
				return false; // Error al actualizar
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Error
		}
	}
	
	
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

	            // Si la respuesta no está vacía, el usuario ya es miembro
	            return !response.toString().isEmpty();
	        } else {
	            System.out.println("Error al verificar si es miembro: " + responseCode);
	            return false;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

}
