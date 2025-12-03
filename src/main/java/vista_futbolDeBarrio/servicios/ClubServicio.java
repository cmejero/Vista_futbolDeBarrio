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

import com.fasterxml.jackson.databind.ObjectMapper;

import vista_futbolDeBarrio.dtos.ClubDto;
import vista_futbolDeBarrio.utilidades.Utilidades;

/**
 * Clase que se encarga de la logica de los metodos CRUD de club
 */
public class ClubServicio {

	  /**
     * Guarda un nuevo club en el sistema.
     * 
     * @param club El objeto ClubDto que contiene los datos del club a guardar.
     */
    public void guardarClub(ClubDto club) {
        try {
            JSONObject json = new JSONObject();
            json.put("nombreClub", club.getNombreClub());
            json.put("abreviaturaClub", club.getAbreviaturaClub());
            json.put("descripcionClub", club.getDescripcionClub());
            json.put("fechaCreacionClub", club.getFechaCreacionClub());
            json.put("fechaFundacionClub", club.getFechaFundacionClub());
            json.put("localidadClub", club.getLocalidadClub());
            json.put("paisClub", club.getPaisClub());
            json.put("emailClub", club.getEmailClub());
            json.put("passwordClub", club.getPasswordClub());
            json.put("telefonoClub", club.getTelefonoClub());
        	byte[] imagenBytes = club.getLogoClub();
			if (imagenBytes != null && imagenBytes.length > 0) {
				String imagenBase64 = Base64.getEncoder().encodeToString(imagenBytes);
				json.put("logoClub", imagenBase64);
			} else {
				json.put("logoClub", JSONObject.NULL); 
			}
            json.put("esPremium", club.isEsPremium());

            String urlApi = "http://localhost:9527/api/guardarClub";
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
                // System.out.println("Club guardado correctamente.");
            } else {
                // System.out.println("Error al guardar el club: " + responseCode);
            }
        } catch (Exception e) {
            // System.out.println("ERROR- [ServiciosClub]" + e);
        }
    }

    /**
     * Obtiene una lista de clubes desde el servicio web.
     * 
     * @return Una lista de objetos ClubDto con los datos de los clubes.
     */
    public ArrayList<ClubDto> listaClub() {
        ArrayList<ClubDto> lista = new ArrayList<>();
        StringBuilder response = new StringBuilder();

        try {
            // URL de la API de clubes
            String urlApi = "http://localhost:9527/api/mostrarClubes";
            URL url = new URL(urlApi);

            HttpURLConnection conex = (HttpURLConnection) url.openConnection();
            conex.setRequestMethod("GET");
            conex.setRequestProperty("Accept", "application/json");

            int responseCode = conex.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer la respuesta JSON
                BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Convertir a JSONArray
                JSONArray jsonLista = new JSONArray(response.toString());
                for (int i = 0; i < jsonLista.length(); i++) {
                    JSONObject jsonClub = jsonLista.getJSONObject(i);
                    ClubDto club = new ClubDto();

                    // Campos obligatorios
                    club.setIdClub(jsonClub.optLong("idClub"));
                    club.setNombreClub(Utilidades.getValorSeguro(jsonClub, "nombreClub"));
                    club.setAbreviaturaClub(Utilidades.getValorSeguro(jsonClub, "abreviaturaClub"));
                    club.setDescripcionClub(Utilidades.getValorSeguro(jsonClub, "descripcionClub"));
                    club.setFechaFundacionClub(Utilidades.getValorSeguro(jsonClub, "fechaFundacionClub"));
                    club.setLocalidadClub(Utilidades.getValorSeguro(jsonClub, "localidadClub"));
                    club.setPaisClub(Utilidades.getValorSeguro(jsonClub, "paisClub"));
                    club.setEmailClub(Utilidades.getValorSeguro(jsonClub, "emailClub"));

                    // Contraseña no se devuelve
                    club.setPasswordClub(null);

                    // Campos opcionales
                    club.setTelefonoClub(Utilidades.getValorSeguro(jsonClub, "telefonoClub"));
                    club.setFechaCreacionClub(Utilidades.getValorSeguro(jsonClub, "fechaCreacionClub"));

                    // Logo (opcional, base64)
                    String imagenBase64 = Utilidades.getValorSeguro(jsonClub, "logoClub");
                    if (imagenBase64 != null && !imagenBase64.isEmpty()) {
                        byte[] imageBytes = Base64.getDecoder().decode(imagenBase64);
                        club.setLogoClub(imageBytes);
                    } else {
                        club.setLogoClub(null);
                    }

                    // Premium
                    club.setEsPremium(jsonClub.optBoolean("esPremium", false));

                    // Agregar a la lista
                    lista.add(club);
                }
            } else {
                System.err.println("Error al obtener clubes. Código HTTP: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR - ServiciosClub - listaClub: " + e.getMessage());
        }

        return lista;
    }
    
    
    public ClubDto obtenerClubPorId(long idClub) {
    	try {
    	String urlApi = "http://localhost:9527/api/club/" + idClub;
    	URL url = new URL(urlApi);
    	HttpURLConnection conex = (HttpURLConnection) url.openConnection();
    	conex.setRequestMethod("GET");
    	conex.setRequestProperty("Accept", "application/json");

    	
    	    int responseCode = conex.getResponseCode();
    	    if (responseCode == HttpURLConnection.HTTP_OK) {
    	        BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
    	        StringBuilder response = new StringBuilder();
    	        String line;
    	        while ((line = in.readLine()) != null) {
    	            response.append(line);
    	        }
    	        in.close();

    	        // Convertir JSON a ClubDto
    	        ObjectMapper mapper = new ObjectMapper();
    	        ClubDto club = mapper.readValue(response.toString(), ClubDto.class);

    	        // Convertir logoClub de Base64 a byte[] para el DTO
    	        // Primero parseamos el JSON para leer el campo logoClub en Base64
    	        JSONObject jsonClub = new JSONObject(response.toString());
    	        String imagenBase64 = Utilidades.getValorSeguro(jsonClub, "logoClub");
    	        if (imagenBase64 != null && !imagenBase64.isEmpty()) {
    	            byte[] imageBytes = Base64.getDecoder().decode(imagenBase64);
    	            club.setLogoClub(imageBytes);
    	        } else {
    	            club.setLogoClub(null);
    	        }

    	        return club;
    	    } else {
    	        System.err.println("No se encontró el club. Código HTTP: " + responseCode);
    	    }
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    	return null;
    	

    	}




    /**
     * Modifica los datos de un club existente.
     * 
     * @param idClub El identificador del club que se va a modificar.
     * @param club El objeto ClubDto con los nuevos datos del club.
     * @return true si la modificación fue exitosa, false en caso contrario.
     */
    public boolean modificarClub(String idClub, ClubDto club) {
        try {
            JSONObject json = new JSONObject();
            json.put("nombreClub", club.getNombreClub());
            json.put("abreviaturaClub", club.getAbreviaturaClub());
            json.put("descripcionClub", club.getDescripcionClub());
            json.put("fechaCreacionClub", club.getFechaCreacionClub());
            json.put("fechaFundacionClub", club.getFechaFundacionClub());
            json.put("localidadClub", club.getLocalidadClub());
            json.put("paisClub", club.getPaisClub());
            json.put("emailClub", club.getEmailClub());
            json.put("passwordClub", club.getPasswordClub());
            json.put("telefonoClub", club.getTelefonoClub());
            byte[] imagenBytes = club.getLogoClub();

			if (imagenBytes != null && imagenBytes.length > 0) {

				String imagenBase64 = Base64.getEncoder().encodeToString(imagenBytes);
				json.put("logoClub", imagenBase64);
			} else {

				json.put("logoClub", JSONObject.NULL); 
			}
            json.put("esPremium", club.isEsPremium());

            String urlApi = "http://localhost:9527/api/modificarClub/" + idClub;
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
                return false; 
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean marcarPremium(Long idClub) {
        try {
            String urlApi = "http://localhost:9527/api/modificarPremiumClub/" + idClub;
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
