package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import vista_futbolDeBarrio.dtos.MiembroClubDto;

public class MiembroClubServicio {

	// Método para guardar un nuevo miembro del club
	public void guardarMiembroClub(MiembroClubDto miembro) {
		try {
			// Crear el objeto JSON a partir del DTO
			JSONObject json = new JSONObject();
			json.put("fechaAltaUsuario", miembro.getFechaAltaUsuario().toString());
			json.put("fechaBajaUsuario",
					miembro.getFechaBajaUsuario() != null ? miembro.getFechaBajaUsuario().toString() : JSONObject.NULL);
			json.put("clubId", miembro.getClubId());
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

	// Método para obtener la lista de miembros del club
	public ArrayList<MiembroClubDto> listarMiembrosClub() {
		ArrayList<MiembroClubDto> lista = new ArrayList<>();

		try {
			// Definir la URL de la API para obtener los miembros
			String urlApi = "http://localhost:9527/api/miembrosClub";
			URL url = new URL(urlApi);

			// Establecer la conexión HTTP
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");

			// Verificar la respuesta del servidor
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
				JSONArray jsonLista = new JSONArray(response.toString());
				for (int i = 0; i < jsonLista.length(); i++) {
					JSONObject jsonMiembro = jsonLista.getJSONObject(i);
					MiembroClubDto miembro = new MiembroClubDto();

					miembro.setIdMiembroClub(jsonMiembro.getLong("idMiembroClub"));
					miembro.setFechaAltaUsuario(jsonMiembro.getString("fechaAltaUsuario"));
					miembro.setFechaBajaUsuario(jsonMiembro.getString("fechaBajaUsuario"));

					miembro.setClubId(jsonMiembro.getLong("clubId"));
					miembro.setUsuarioId(jsonMiembro.getLong("usuarioId"));

					lista.add(miembro);
				}
			} else {
				System.out.println("Error al obtener miembros del club: " + responseCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR- [MiembroClubServicio] " + e.getMessage());
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
			json.put("clubId", miembro.getClubId());
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
}
