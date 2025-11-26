package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import vista_futbolDeBarrio.dtos.ClubDto;
import vista_futbolDeBarrio.dtos.InstalacionDto;
import vista_futbolDeBarrio.dtos.RespuestaLoginDto;
import vista_futbolDeBarrio.dtos.UsuarioDto;

/**
 * Clase que se encarga de la logica del login
 */
public class LoginServicio {

	/**
     * Realiza el proceso de login enviando las credenciales del usuario y obteniendo la respuesta del servidor.
     * 
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @return Un objeto RespuestaLoginDto con los datos de la respuesta del login, incluyendo un token y los detalles del usuario.
     */
	public RespuestaLoginDto login(String email, String password, String tipoUsuario) {
	    try {
	    
	    	JSONObject json = new JSONObject();
	    	json.put("email", email);
	    	json.put("password", password);
	    	json.put("tipoUsuario", tipoUsuario); 


	        // Establecer conexión con la API
	        String urlApi = "http://localhost:9527/api/login";
	        HttpURLConnection conex = (HttpURLConnection) new URL(urlApi).openConnection();
	        conex.setRequestMethod("POST");
	        conex.setRequestProperty("Content-Type", "application/json");
	        conex.setDoOutput(true);

	        // Enviar el JSON en el cuerpo de la solicitud
	        try (OutputStream os = conex.getOutputStream()) {
	            byte[] input = json.toString().getBytes("utf-8");
	            os.write(input, 0, input.length);
	        }

	        int codigoRespuesta = conex.getResponseCode();

	        if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
	            // Leer la respuesta del servidor
	            StringBuilder respuesta = new StringBuilder();
	            try (BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()))) {
	                String linea;
	                while ((linea = in.readLine()) != null) {
	                    respuesta.append(linea);
	                }
	            }

	            // Parsear respuesta JSON y construir DTO de login
	            return construirRespuestaLogin(new JSONObject(respuesta.toString()));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	/**
	 * Construye y retorna un objeto RespuestaLoginDto a partir del JSON recibido.
	 *
	 * @param jsonRespuesta Objeto JSON recibido del servidor tras el login.
	 * @return Objeto RespuestaLoginDto con los datos del usuario autenticado.
	 */
	private RespuestaLoginDto construirRespuestaLogin(JSONObject jsonRespuesta) {
	    RespuestaLoginDto respuestaLogin = new RespuestaLoginDto();
	    respuestaLogin.setToken(jsonRespuesta.getString("token"));
	    respuestaLogin.setTipoUsuario(jsonRespuesta.getString("tipoUsuario"));
	    

	    String tipoUsuario = respuestaLogin.getTipoUsuario();
	    JSONObject datos = jsonRespuesta.getJSONObject("datosUsuario");

	    switch (tipoUsuario) {
	        case "administrador":
	        case "jugador":
	            UsuarioDto usuario = new UsuarioDto();
	            usuario.setIdUsuario(datos.getLong("idUsuario"));
	            usuario.setNombreCompletoUsuario(datos.getString("nombreCompletoUsuario"));
	            usuario.setEmailUsuario(datos.getString("emailUsuario"));
	            usuario.setEsPremium(datos.getBoolean("esPremium"));
	            respuestaLogin.setDatosUsuario(usuario);
	            break;
	        case "club":
	            ClubDto club = new ClubDto();
	            club.setIdClub(datos.getLong("idClub"));
	            club.setNombreClub(datos.getString("nombreClub"));
	            club.setEmailClub(datos.getString("emailClub"));
	            club.setEsPremium(datos.getBoolean("esPremium"));
	            respuestaLogin.setDatosUsuario(club);
	            break;
	        case "instalacion":
	            InstalacionDto instalacion = new InstalacionDto();
	            instalacion.setIdInstalacion(datos.getLong("idInstalacion"));
	            instalacion.setNombreInstalacion(datos.getString("nombreInstalacion"));
	            respuestaLogin.setDatosUsuario(instalacion);
	            break;
	    }

	    return respuestaLogin;
	}

}
