package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import vista_futbolDeBarrio.dtos.ClubDto;

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
            json.put("logoClub", club.getLogoClub());

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
            String urlApi = "http://localhost:9527/api/mostrarClubes";
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
                
                JSONArray jsonLista = new JSONArray(response.toString());
                for (int i = 0; i < jsonLista.length(); i++) {
                    JSONObject jsonClub = jsonLista.getJSONObject(i);
                    ClubDto club = new ClubDto();

                    club.setIdClub(jsonClub.getLong("idClub"));
                    club.setNombreClub(jsonClub.getString("nombreClub"));
                    club.setAbreviaturaClub(jsonClub.getString("abreviaturaClub"));
                    club.setDescripcionClub(jsonClub.getString("descripcionClub"));
                    club.setFechaFundacionClub(jsonClub.getString("fechaFundacionClub"));
                    club.setLocalidadClub(jsonClub.getString("localidadClub"));
                    club.setPaisClub(jsonClub.getString("paisClub"));
                    club.setEmailClub(jsonClub.getString("emailClub"));
                    club.setPasswordClub(jsonClub.getString("passwordClub"));
                    club.setTelefonoClub(jsonClub.getString("telefonoClub"));
                    String fechaCreacionStr = jsonClub.optString("fechaCreacionClub");
                    if (fechaCreacionStr != null && !fechaCreacionStr.isEmpty()) {
                        club.setFechaCreacionClub(fechaCreacionStr);  
                    }
                    if (jsonClub.has("logoClub") && !jsonClub.isNull("logoClub")) {
                        String logoBase64 = jsonClub.getString("logoClub");
                        if (logoBase64 != null && !logoBase64.isEmpty()) {
                            byte[] imageBytes = Base64.getDecoder().decode(logoBase64);
                            club.setLogoClub(imageBytes); 
                        }
                    }
                    lista.add(club);
                }
            } else {
                // System.out.println("Error al obtener clubes: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // System.out.println("ERROR - ServiciosClub - listaClub: " + e.getMessage());
        }
        // System.out.println(lista);
        return lista;
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
            json.put("logoClub", club.getLogoClub());

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
}
