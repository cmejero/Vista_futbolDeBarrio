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
import vista_futbolDeBarrio.utilidades.Utilidades;

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

	        if (instalacion.getTipoCampo1() != null)
	            json.put("tipoCampo1", instalacion.getTipoCampo1().name());
	        if (instalacion.getTipoCampo2() != null)
	            json.put("tipoCampo2", instalacion.getTipoCampo2().name());
	        if (instalacion.getTipoCampo3() != null)
	            json.put("tipoCampo3", instalacion.getTipoCampo3().name());

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
	            String line;
	            StringBuilder resp = new StringBuilder();
	            while ((line = in.readLine()) != null) {
	                resp.append(line);
	            }
	            responseMessage = resp.toString();
	        }


	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            System.out.println("Instalación guardada correctamente.");
	        } else {
	            System.err.println("Error al guardar instalación: " + responseCode + " -> " + responseMessage);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        System.err.println("ERROR - [InstalacionServicio] " + e.getMessage());
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
            String urlApi = "http://localhost:9527/api/mostrarInstalaciones";
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

                    String estado = Utilidades.getValorSeguro(jsonInstalacion, "estadoInstalacion");
                    instalacion.setEstadoInstalacion(estado != null && !estado.isEmpty() ? Estado.valueOf(estado) : null);


                    instalacion.setPasswordInstalacion(null);
                    String imagenBase64 = jsonInstalacion.optString("imagenInstalacion", "");
                    if (!imagenBase64.isEmpty()) {
                        byte[] imageBytes = Base64.getDecoder().decode(imagenBase64);
                        instalacion.setImagenInstalacion(imageBytes);

                        // Opcional: guardar archivo físico si lo necesitas
                        String fileName = "instalacion_" + instalacion.getIdInstalacion() + ".jpg";
                        try (FileOutputStream fos = new FileOutputStream(fileName)) {
                            fos.write(imageBytes);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        instalacion.setImagenInstalacion(null); // o tu imagen por defecto
                    }
                    JSONArray torneosArray;
                    if (jsonInstalacion.isNull("torneoIds")) {
                        torneosArray = new JSONArray(); 
                    } else {
                        torneosArray = jsonInstalacion.getJSONArray("torneoIds");
                    }
                    List<Long> torneoIds = new ArrayList<>();
                    for (int j = 0; j < torneosArray.length(); j++) {
                        torneoIds.add(torneosArray.getLong(j));
                    }
                    instalacion.setTorneoIds(torneoIds);

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
    
    
    public InstalacionDto obtenerInstalacionPorId(Long idInstalacion) {
    	try {
    	String urlApi = "http://localhost:9527/api/instalacion/" + idInstalacion;
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

    	        instalacion.setEstadoInstalacion(
    	            Utilidades.getValorSeguro(jsonInstalacion, "estadoInstalacion") != null 
    	            ? Estado.valueOf(Utilidades.getValorSeguro(jsonInstalacion, "estadoInstalacion")) 
    	            : null
    	        );

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
    
    /**
     * Elimina una instalación por su ID mediante la API.
     * 
     * @param idInstalacion El ID de la instalación a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminarInstalacion(Long idInstalacion) {
        try {
            // URL de tu API para eliminar la instalación
            String urlApi = "http://localhost:9527/api/eliminarInstalacion/" + idInstalacion;
            URL url = new URL(urlApi);

            HttpURLConnection conex = (HttpURLConnection) url.openConnection();
            conex.setRequestMethod("DELETE");
            conex.setRequestProperty("Accept", "application/json");

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
