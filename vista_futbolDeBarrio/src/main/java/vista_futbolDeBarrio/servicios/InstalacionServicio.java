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
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import vista_futbolDeBarrio.dtos.InstalacionDto;
import vista_futbolDeBarrio.enums.Estado;
import vista_futbolDeBarrio.enums.Modalidad;

/**
 * Clase que se encarga de la logica de los metodos CRUD de equipo torneo
 */
public class InstalacionServicio {

	 /**
     * Guarda una nueva instalación en el sistema.
     * 
     * @param instalacion El objeto InstalacionDto que contiene los datos de la instalación a guardar.
     */
    public void guardarInstalacion(InstalacionDto instalacion) {
        try {
            JSONObject json = new JSONObject();
            json.put("nombreInstalacion", instalacion.getNombreInstalacion());
            json.put("direccionInstalacion", instalacion.getDireccionInstalacion());
            json.put("telefonoInstalacion", instalacion.getTelefonoInstalacion());
            json.put("emailInstalacion", instalacion.getEmailInstalacion());
            json.put("tipoCampo1", instalacion.getTipoCampo1().name());
            json.put("tipoCampo2", instalacion.getTipoCampo2().name());
            json.put("tipoCampo3", instalacion.getTipoCampo3().name());
            json.put("serviciosInstalacion", instalacion.getServiciosInstalacion());          
            json.put("estadoInstalacion", instalacion.getEstadoInstalacion().name());
            json.put("passwordInstalacion", instalacion.getPasswordInstalacion());
            json.put("imagenInstalacion", instalacion.getImagenInstalacion());
            json.put("torneoId", instalacion.getTorneoId());

            String urlApi = "http://localhost:9527/api/guardarInstalacion";
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
                // System.out.println("Instalación guardada correctamente.");
            } else {
                // System.out.println("Error al guardar instalación: " + responseCode);
            }

        } catch (Exception e) {
            // System.out.println("ERROR - [InstalacionServicio] " + e);
        }
    }

    /**
     * Obtiene la lista de todas las instalaciones.
     * 
     * @return Una lista de objetos InstalacionDto con los datos de las instalaciones.
     */
    public List<InstalacionDto> listaInstalaciones() {
        List<InstalacionDto> lista = new ArrayList<>();
        try {
            String urlApi = "http://localhost:9527/api/instalaciones";
            URL url = new URL(urlApi);
            HttpURLConnection conex = (HttpURLConnection) url.openConnection();
            conex.setRequestMethod("GET");
            conex.setRequestProperty("Accept", "application/json");
            int responseCode = conex.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                JSONArray jsonLista = new JSONArray(response.toString());
                for (int i = 0; i < jsonLista.length(); i++) {
                    JSONObject jsonInstalacion = jsonLista.getJSONObject(i);
                    InstalacionDto instalacion = new InstalacionDto();
                    instalacion.setIdInstalacion(jsonInstalacion.getLong("idInstalacion"));
                    instalacion.setNombreInstalacion(jsonInstalacion.getString("nombreInstalacion"));
                    instalacion.setDireccionInstalacion(jsonInstalacion.getString("direccionInstalacion"));
                    instalacion.setTelefonoInstalacion(jsonInstalacion.getString("telefonoInstalacion"));
                    instalacion.setEmailInstalacion(jsonInstalacion.getString("emailInstalacion"));
                    instalacion.setTipoCampo1(Modalidad.valueOf(jsonInstalacion.getString("tipoCampo1").toUpperCase()));
                    instalacion.setTipoCampo2(Modalidad.valueOf(jsonInstalacion.getString("tipoCampo2").toUpperCase()));
                    instalacion.setTipoCampo3(Modalidad.valueOf(jsonInstalacion.getString("tipoCampo3").toUpperCase()));
                    instalacion.setServiciosInstalacion(jsonInstalacion.getString("serviciosInstalacion"));      
                    instalacion.setEstadoInstalacion(Estado.valueOf(jsonInstalacion.getString("estadoInstalacion").toUpperCase()));
                    instalacion.setPasswordInstalacion(jsonInstalacion.getString("passwordInstalacion"));
                    instalacion.setImagenInstalacion(jsonInstalacion.getString("imagenInstalacion"));
                    String imagenBase64 = jsonInstalacion.getString("imagenInstalacion");
                    if (imagenBase64 != null && !imagenBase64.isEmpty()) {
                        String fileName = "instalacion_" + instalacion.getIdInstalacion() + ".jpg";
                        byte[] imageBytes = Base64.getDecoder().decode(imagenBase64);
                        try (FileOutputStream fos = new FileOutputStream(fileName)) {
                            fos.write(imageBytes);                           
                        } catch (IOException e) {
                            e.printStackTrace();                         
                        }
                    }
                    JSONArray torneosArray = jsonInstalacion.getJSONArray("torneoId");
                    List<Long> torneoIds = new ArrayList<>();
                    for (int j = 0; j < torneosArray.length(); j++) {
                        torneoIds.add(torneosArray.getLong(j));
                    }
                    instalacion.setTorneoId(torneoIds);

                    lista.add(instalacion);
                }
            } else {
                // System.out.println("Error al obtener instalaciones: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // System.out.println("ERROR - [InstalacionServicio] - listarInstalaciones: " + e.getMessage());
        }
        return lista;
    }
    
    /**
     * Modifica una instalación existente.
     * 
     * @param idInstalacion El ID de la instalación a modificar.
     * @param instalacion El objeto InstalacionDto con los nuevos datos de la instalación.
     * @return true si la modificación fue exitosa, false en caso contrario.
     */
    public boolean modificarInstalacion(String idInstalacion, InstalacionDto instalacion) {
        try {
            JSONObject json = new JSONObject();
            json.put("nombreInstalacion", instalacion.getNombreInstalacion());
            json.put("direccionInstalacion", instalacion.getDireccionInstalacion());
            json.put("telefonoInstalacion", instalacion.getTelefonoInstalacion());
            json.put("emailInstalacion", instalacion.getEmailInstalacion());
            json.put("tipoCampo1", instalacion.getTipoCampo1().name());
            json.put("tipoCampo2", instalacion.getTipoCampo2() != null ? instalacion.getTipoCampo2().name() : JSONObject.NULL);
            json.put("tipoCampo3", instalacion.getTipoCampo3() != null ? instalacion.getTipoCampo3().name() : JSONObject.NULL);
            json.put("serviciosInstalacion", instalacion.getServiciosInstalacion());
            json.put("estadoInstalacion", instalacion.getEstadoInstalacion().name());
            json.put("passwordInstalacion", instalacion.getPasswordInstalacion());
            json.put("imagenInstalacion", instalacion.getImagenInstalacion());
            json.put("torneoId", instalacion.getTorneoId());

            String urlApi = "http://localhost:9527/api/modificarInstalacion/" + idInstalacion;
            URL url = new URL(urlApi);

            HttpURLConnection conex = (HttpURLConnection) url.openConnection();
            conex.setRequestMethod("PUT");
            conex.setRequestProperty("Content-Type", "application/json");
            conex.setDoOutput(true);

            try (OutputStream os = conex.getOutputStream()) {
                byte[] input = json.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conex.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // System.out.println("Instalación modificada correctamente.");
                return true; 
            } else {
                // System.out.println("Error al modificar instalación: " + responseCode);
                return false; 
            }
        } catch (Exception e) {
            e.printStackTrace();
            // System.out.println("ERROR- [InstalacionServicio] " + e.getMessage());
            return false;
        }
    }
}
