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

import vista_futbolDeBarrio.dtos.ClubDto;

public class ClubServicio {

    // Guardar un nuevo club
    public void guardarClub(ClubDto club) {
        try {
            // Crear el JSON del objeto ClubDto
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
            json.put("instalacionId", club.getInstalacionId());
            json.put("logoClub", club.getLogoClub());

            String urlApi = "http://localhost:9527/api/guardarClub"; // Cambia esta URL según tu configuración
            URL url = new URL(urlApi);
            
            HttpURLConnection conex = (HttpURLConnection) url.openConnection();
            conex.setRequestMethod("POST");
            conex.setRequestProperty("Content-Type", "application/json");
            conex.setDoOutput(true);

            // Enviar el JSON en la solicitud POST
            try (OutputStream os = conex.getOutputStream()) {
                byte[] input = json.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conex.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Club guardado correctamente.");
            } else {
                System.out.println("Error al guardar el club: " + responseCode);
            }
        } catch (Exception e) {
            System.out.println("ERROR- [ServiciosClub]" + e);
        }
    }

    // Obtener la lista de clubes
    public ArrayList<ClubDto> listaClub() {
        ArrayList<ClubDto> lista = new ArrayList<ClubDto>();

        try {
            String urlApi = "http://localhost:9527/api/club"; // Cambia esta URL según tu configuración
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
                    JSONObject jsonClub = jsonlista.getJSONObject(i);
                    ClubDto club = new ClubDto();

                    club.setIdClub(jsonClub.getLong("idClub"));
                    club.setNombreClub(jsonClub.getString("nombreClub"));
                    club.setAbreviaturaClub(jsonClub.getString("abreviaturaClub"));
                    club.setDescripcionClub(jsonClub.getString("descripcionClub"));
                    club.setFechaCreacionClub(jsonClub.getString("fechaCreacionClub"));
                    club.setFechaFundacionClub(jsonClub.getString("fechaFundacionClub"));
                    club.setLocalidadClub(jsonClub.getString("localidadClub"));
                    club.setPaisClub(jsonClub.getString("paisClub"));
                    club.setEmailClub(jsonClub.getString("emailClub"));
                    club.setPasswordClub(jsonClub.getString("passwordClub"));
                    club.setTelefonoClub(jsonClub.getString("telefonoClub"));
                    club.setInstalacionId(jsonClub.getLong("instalacionId"));

                    // Manejar la imagen del logo
                    String logoBase64 = jsonClub.getString("logoClub");
                    if (logoBase64 != null && !logoBase64.isEmpty()) {
                        String fileName = "club_" + club.getIdClub() + ".jpg"; // Nombre del archivo
                        byte[] imageBytes = Base64.getDecoder().decode(logoBase64);
                        try (FileOutputStream fos = new FileOutputStream(fileName)) {
                            fos.write(imageBytes);
                            System.out.println("Logo guardado como: " + fileName);
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Error al guardar la imagen: " + e.getMessage());
                        }
                    }

                    lista.add(club);
                }
            } else {
                System.out.println("Error al obtener clubes: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR- ServiciosClub-ListaClub: " + e.getMessage());
        }

        // Imprimir la lista de clubes
        System.out.println(lista);
        return lista;
    }

    // Modificar un club
    public boolean modificarClub(String idClub, ClubDto club) {
        try {
            // Crear JSON del objeto ClubDto
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
            json.put("instalacionId", club.getInstalacionId());
            json.put("logoClub", club.getLogoClub());

            // URL para hacer la solicitud PUT. Usamos el idClub en la URL
            String urlApi = "http://localhost:9527/api/modificarClub/" + idClub;
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
                return true; // Club actualizado correctamente
            } else {
                return false; // Error al actualizar
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Error
        }
    }
}
