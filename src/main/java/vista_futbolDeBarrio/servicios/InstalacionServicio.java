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
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import vista_futbolDeBarrio.dtos.InstalacionDto;
import vista_futbolDeBarrio.enums.Estado;
import vista_futbolDeBarrio.enums.Modalidad;
import vista_futbolDeBarrio.utilidades.Utilidades;

/**
 * Clase que se encarga de la logica de los metodos CRUD de equipo torneo
 */
public class InstalacionServicio {
	
	  public String crearInstalacionDesdeFormulario(HttpServletRequest request)
	            throws IOException, ServletException {

	        ServletContext context = request.getServletContext();

	        InstalacionDto instalacion = new InstalacionDto();
	        instalacion.setNombreInstalacion(request.getParameter("nombreInstalacion"));
	        instalacion.setDireccionInstalacion(request.getParameter("direccionInstalacion"));
	        instalacion.setTelefonoInstalacion(request.getParameter("telefonoInstalacion"));
	        instalacion.setEmailInstalacion(request.getParameter("emailInstalacion"));
	        instalacion.setPasswordInstalacion(request.getParameter("passwordInstalacion"));

	        // 🔐 Validación de contraseña
	        String repassword = request.getParameter("repasswordInstalacion");
	        if (!instalacion.getPasswordInstalacion().equals(repassword)) {
	            return "error_password";
	        }

	        instalacion.setServiciosInstalacion(request.getParameter("serviciosInstalacion"));

	        // 🏟 Modalidades de las pistas
	        String tipoCampo1Form = request.getParameter("tipoCampo1");
	        String tipoCampo2Form = request.getParameter("tipoCampo2");
	        String tipoCampo3Form = request.getParameter("tipoCampo3");

	        if (tipoCampo1Form != null && !tipoCampo1Form.isEmpty()) {
	            instalacion.setTipoCampo1(Modalidad.valueOf(tipoCampo1Form));
	        }

	        if (tipoCampo2Form != null && !tipoCampo2Form.isEmpty()) {
	            instalacion.setTipoCampo2(Modalidad.valueOf(tipoCampo2Form));
	        } else {
	            instalacion.setTipoCampo2(instalacion.getTipoCampo1());
	        }

	        if (tipoCampo3Form != null && !tipoCampo3Form.isEmpty()) {
	            instalacion.setTipoCampo3(Modalidad.valueOf(tipoCampo3Form));
	        } else {
	            instalacion.setTipoCampo3(instalacion.getTipoCampo1());
	        }

	        // 📌 Estado de la instalación
	        String estadoInstalacionForm = request.getParameter("estadoInstalacion");
	        if (estadoInstalacionForm != null && !estadoInstalacionForm.isEmpty()) {
	            instalacion.setEstadoInstalacion(Estado.valueOf(estadoInstalacionForm));
	        }

	        // 🖼 Imagen
	        Part imagenPart = request.getPart("imagenInstalacion");
	        byte[] imagenBytes;

	        if (imagenPart != null && imagenPart.getSize() > 0) {
	            imagenBytes = new byte[(int) imagenPart.getSize()];
	            try (InputStream inputStream = imagenPart.getInputStream()) {
	                inputStream.read(imagenBytes);
	            }
	        } else {
	            imagenBytes = Utilidades.obtenerImagenPorDefecto(context);
	        }

	        instalacion.setImagenInstalacion(imagenBytes);

	        // 🆔 Torneos (vacío por defecto)
	        instalacion.setTorneoIds(new ArrayList<>());

	        // 📡 Llamada a la API
	        return guardarInstalacion(instalacion);
	    }


	/**
	 * Guarda una nueva instalación en el sistema.
	 * 
	 * @param instalacion El objeto InstalacionDto que contiene los datos de la
	 *                    instalación a guardar.
	 */
	public String guardarInstalacion(InstalacionDto instalacion) {
	    try {
	        JSONObject json = new JSONObject();
	        json.put("nombreInstalacion", instalacion.getNombreInstalacion());
	        json.put("direccionInstalacion", instalacion.getDireccionInstalacion());
	        json.put("telefonoInstalacion", instalacion.getTelefonoInstalacion());
	        json.put("emailInstalacion", instalacion.getEmailInstalacion());
	        if (instalacion.getTipoCampo1() != null) json.put("tipoCampo1", instalacion.getTipoCampo1().name());
	        if (instalacion.getTipoCampo2() != null) json.put("tipoCampo2", instalacion.getTipoCampo2().name());
	        if (instalacion.getTipoCampo3() != null) json.put("tipoCampo3", instalacion.getTipoCampo3().name());
	        json.put("serviciosInstalacion", instalacion.getServiciosInstalacion());
	        json.put("estadoInstalacion", instalacion.getEstadoInstalacion().name());
	        json.put("passwordInstalacion", instalacion.getPasswordInstalacion());

	        byte[] imagenBytes = instalacion.getImagenInstalacion();
	        if (imagenBytes != null && imagenBytes.length > 0) {
	            json.put("imagenInstalacion", Base64.getEncoder().encodeToString(imagenBytes));
	        } else {
	            json.put("imagenInstalacion", JSONObject.NULL);
	        }

	        json.put("torneoId", instalacion.getTorneoIds());

	        String urlApi = "http://localhost:9527/api/guardarInstalacion";
	        URL url = new URL(urlApi);
	        HttpURLConnection conex = (HttpURLConnection) url.openConnection();
	        conex.setRequestMethod("POST");
	        conex.setRequestProperty("Content-Type", "application/json");
	        conex.setDoOutput(true);

	        try (OutputStream os = conex.getOutputStream()) {
	            os.write(json.toString().getBytes("utf-8"));
	        }

	        int responseCode = conex.getResponseCode();
	        String responseMessage = "";
	        try (BufferedReader in = new BufferedReader(new InputStreamReader(
	                responseCode >= 400 ? conex.getErrorStream() : conex.getInputStream(), "utf-8"))) {
	            StringBuilder resp = new StringBuilder();
	            String line;
	            while ((line = in.readLine()) != null) {
	                resp.append(line);
	            }
	            responseMessage = resp.toString();
	        }

	        // ✅ Evaluar resultado para mostrar mensajes
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            return "ok";
	        } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
	            if (responseMessage.contains("ya está en uso")) {
	                return "instalacion_existente";
	            } else if (responseMessage.toLowerCase().contains("email")) {
	                return "email_invalido";
	            } else {
	                return "error_servidor";
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
	 * Obtiene la lista de todas las instalaciones.
	 * 
	 * @return Una lista de objetos InstalacionDto con los datos de las
	 *         instalaciones.
	 */
	public List<InstalacionDto> listaInstalaciones(HttpServletRequest request) {
		List<InstalacionDto> lista = new ArrayList<>();
		StringBuilder response = new StringBuilder();

		try {
			// 1️⃣ Obtener token JWT de la sesión
			HttpSession session = request.getSession(false);
			if (session == null)
				throw new IllegalStateException("No hay sesión activa");
			String token = (String) session.getAttribute("token");
			if (token == null || token.isEmpty())
				throw new IllegalStateException("No se encontró token JWT");

			// 2️⃣ Conexión a la API
			String urlApi = "http://localhost:9527/api/mostrarInstalaciones";
			URL url = new URL(urlApi);
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");
			conex.setRequestProperty("Authorization", "Bearer " + token); // 🔑 Enviar token

			// 3️⃣ Leer respuesta
			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream(), "utf-8"));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// 4️⃣ Parsear JSON a InstalacionDto
				JSONArray jsonLista = new JSONArray(response.toString());
				for (int i = 0; i < jsonLista.length(); i++) {
					JSONObject jsonInstalacion = jsonLista.getJSONObject(i);
					InstalacionDto instalacion = new InstalacionDto();

					instalacion.setIdInstalacion(jsonInstalacion.getLong("idInstalacion"));
					instalacion.setNombreInstalacion(Utilidades.getValorSeguro(jsonInstalacion, "nombreInstalacion"));
					instalacion.setDireccionInstalacion(
							Utilidades.getValorSeguro(jsonInstalacion, "direccionInstalacion"));
					instalacion
							.setTelefonoInstalacion(Utilidades.getValorSeguro(jsonInstalacion, "telefonoInstalacion"));
					instalacion.setEmailInstalacion(Utilidades.getValorSeguro(jsonInstalacion, "emailInstalacion"));
					instalacion.setServiciosInstalacion(
							Utilidades.getValorSeguro(jsonInstalacion, "serviciosInstalacion"));

					String tipo1 = Utilidades.getValorSeguro(jsonInstalacion, "tipoCampo1");
					instalacion.setTipoCampo1(tipo1 != null && !tipo1.isEmpty() ? Modalidad.valueOf(tipo1) : null);

					String tipo2 = Utilidades.getValorSeguro(jsonInstalacion, "tipoCampo2");
					instalacion.setTipoCampo2(tipo2 != null && !tipo2.isEmpty() ? Modalidad.valueOf(tipo2) : null);

					String tipo3 = Utilidades.getValorSeguro(jsonInstalacion, "tipoCampo3");
					instalacion.setTipoCampo3(tipo3 != null && !tipo3.isEmpty() ? Modalidad.valueOf(tipo3) : null);

					String estado = Utilidades.getValorSeguro(jsonInstalacion, "estadoInstalacion");
					instalacion
							.setEstadoInstalacion(estado != null && !estado.isEmpty() ? Estado.valueOf(estado) : null);

					instalacion.setPasswordInstalacion(null);

					String imagenBase64 = Utilidades.getValorSeguro(jsonInstalacion, "imagenInstalacion");
					if (imagenBase64 != null && !imagenBase64.isEmpty()) {
						byte[] imageBytes = Base64.getDecoder().decode(imagenBase64);
						instalacion.setImagenInstalacion(imageBytes);
					} else {
						instalacion.setImagenInstalacion(null);
					}

					JSONArray torneosArray = jsonInstalacion.isNull("torneoIds") ? new JSONArray()
							: jsonInstalacion.getJSONArray("torneoIds");
					List<Long> torneoIds = new ArrayList<>();
					for (int j = 0; j < torneosArray.length(); j++) {
						torneoIds.add(torneosArray.getLong(j));
					}
					instalacion.setTorneoIds(torneoIds);

					lista.add(instalacion);
				}
			} else {
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	public InstalacionDto obtenerInstalacionPorId(Long idInstalacion, HttpServletRequest request) {
		try {
			// 1️⃣ Obtener token de sesión
			HttpSession session = request.getSession(false);
			if (session == null)
				throw new IllegalStateException("No hay sesión activa");

			String token = (String) session.getAttribute("token");
			if (token == null || token.isEmpty())
				throw new IllegalStateException("No se encontró token JWT");

			// 2️⃣ Conexión a la API
			String urlApi = "http://localhost:9527/api/instalacion/" + idInstalacion;
			URL url = new URL(urlApi);
			HttpURLConnection conex = (HttpURLConnection) url.openConnection();
			conex.setRequestMethod("GET");
			conex.setRequestProperty("Accept", "application/json");
			conex.setRequestProperty("Authorization", "Bearer " + token);

			int responseCode = conex.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
				StringBuilder response = new StringBuilder();
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				JSONObject jsonInstalacion = new JSONObject(response.toString());
				InstalacionDto instalacion = new InstalacionDto();

				instalacion.setIdInstalacion(jsonInstalacion.getLong("idInstalacion"));
				instalacion.setNombreInstalacion(Utilidades.getValorSeguro(jsonInstalacion, "nombreInstalacion"));
				instalacion.setDireccionInstalacion(Utilidades.getValorSeguro(jsonInstalacion, "direccionInstalacion"));
				instalacion.setTelefonoInstalacion(Utilidades.getValorSeguro(jsonInstalacion, "telefonoInstalacion"));
				instalacion.setEmailInstalacion(Utilidades.getValorSeguro(jsonInstalacion, "emailInstalacion"));
				instalacion.setServiciosInstalacion(Utilidades.getValorSeguro(jsonInstalacion, "serviciosInstalacion"));

				String tipo1 = Utilidades.getValorSeguro(jsonInstalacion, "tipoCampo1");
				instalacion.setTipoCampo1(tipo1 != null && !tipo1.isEmpty() ? Modalidad.valueOf(tipo1) : null);

				String tipo2 = Utilidades.getValorSeguro(jsonInstalacion, "tipoCampo2");
				instalacion.setTipoCampo2(tipo2 != null && !tipo2.isEmpty() ? Modalidad.valueOf(tipo2) : null);

				String tipo3 = Utilidades.getValorSeguro(jsonInstalacion, "tipoCampo3");
				instalacion.setTipoCampo3(tipo3 != null && !tipo3.isEmpty() ? Modalidad.valueOf(tipo3) : null);

				instalacion.setEstadoInstalacion(Utilidades.getValorSeguro(jsonInstalacion, "estadoInstalacion") != null
						? Estado.valueOf(Utilidades.getValorSeguro(jsonInstalacion, "estadoInstalacion"))
						: null);

				String imagenBase64 = jsonInstalacion.optString("imagenInstalacion", "");
				if (!imagenBase64.isEmpty()) {
					instalacion.setImagenInstalacion(Base64.getDecoder().decode(imagenBase64));
				}

				JSONArray torneosArray = jsonInstalacion.optJSONArray("torneoIds");
				List<Long> torneoIds = new ArrayList<>();
				if (torneosArray != null) {
					for (int j = 0; j < torneosArray.length(); j++) {
						torneoIds.add(torneosArray.getLong(j));
					}
				}
				instalacion.setTorneoIds(torneoIds);

				return instalacion;
			} else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
				return null;
			} else {
				throw new RuntimeException("Error al obtener instalación. Código: " + responseCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Modifica una instalación existente.
	 * 
	 * @param idInstalacion El ID de la instalación a modificar.
	 * @param instalacion   El objeto InstalacionDto con los nuevos datos de la
	 *                      instalación.
	 * @return true si la modificación fue exitosa, false en caso contrario.
	 */
	public boolean modificarInstalacion(String idInstalacion, InstalacionDto instalacion, HttpServletRequest request) {
	    try {
	        // 1️⃣ Obtener token JWT de la sesión
	        HttpSession session = request.getSession(false);
	        if (session == null) throw new IllegalStateException("No hay sesión activa");
	        String token = (String) session.getAttribute("token");
	        if (token == null || token.isEmpty()) throw new IllegalStateException("No se encontró token JWT");

	        // 2️⃣ Preparar JSON
	        JSONObject json = new JSONObject();
	        json.put("nombreInstalacion", instalacion.getNombreInstalacion());
	        json.put("direccionInstalacion", instalacion.getDireccionInstalacion());
	        json.put("telefonoInstalacion", instalacion.getTelefonoInstalacion());
	        json.put("emailInstalacion", instalacion.getEmailInstalacion());
	        json.put("tipoCampo1", instalacion.getTipoCampo1() != null ? instalacion.getTipoCampo1().name() : JSONObject.NULL);
	        json.put("tipoCampo2", instalacion.getTipoCampo2() != null ? instalacion.getTipoCampo2().name() : JSONObject.NULL);
	        json.put("tipoCampo3", instalacion.getTipoCampo3() != null ? instalacion.getTipoCampo3().name() : JSONObject.NULL);
	        json.put("serviciosInstalacion", instalacion.getServiciosInstalacion());
	        json.put("estadoInstalacion", instalacion.getEstadoInstalacion() != null ? instalacion.getEstadoInstalacion().name() : JSONObject.NULL);
	        json.put("passwordInstalacion", instalacion.getPasswordInstalacion());

	        byte[] imagenBytes = instalacion.getImagenInstalacion();
	        if (imagenBytes != null && imagenBytes.length > 0) {
	            String imagenBase64 = Base64.getEncoder().encodeToString(imagenBytes);
	            json.put("imagenInstalacion", imagenBase64);
	        } else {
	            json.put("imagenInstalacion", JSONObject.NULL);
	        }

	        json.put("torneoId", instalacion.getTorneoIds());

	        // 3️⃣ Conexión HTTP
	        String urlApi = "http://localhost:9527/api/modificarInstalacion/" + idInstalacion;
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
	 * Elimina una instalación por su ID mediante la API.
	 * 
	 * @param idInstalacion El ID de la instalación a eliminar.
	 * @return true si la eliminación fue exitosa, false en caso contrario.
	 */
	public boolean eliminarInstalacion(Long idInstalacion, HttpServletRequest request) {
	    try {
	        // 1️⃣ Obtener token JWT de la sesión
	        HttpSession session = request.getSession(false);
	        if (session == null) throw new IllegalStateException("No hay sesión activa");
	        String token = (String) session.getAttribute("token");
	        if (token == null || token.isEmpty()) throw new IllegalStateException("No se encontró token JWT");

	        // 2️⃣ Preparar conexión a la API
	        String urlApi = "http://localhost:9527/api/eliminarInstalacion/" + idInstalacion;
	        URL url = new URL(urlApi);
	        HttpURLConnection conex = (HttpURLConnection) url.openConnection();
	        conex.setRequestMethod("DELETE");
	        conex.setRequestProperty("Accept", "application/json");
	        conex.setRequestProperty("Authorization", "Bearer " + token); // 🔑 Enviar token

	        // 3️⃣ Leer respuesta
	        int responseCode = conex.getResponseCode();
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            System.out.println("Instalación eliminada correctamente: id=" + idInstalacion);
	            return true;
	        } else {
	            System.out.println("Error al eliminar instalación: " + responseCode);
	            return false;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("ERROR- [InstalacionServicio.eliminarInstalacion]: " + e.getMessage());
	        return false;
	    }
	}

}
