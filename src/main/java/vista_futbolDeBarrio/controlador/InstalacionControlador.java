package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vista_futbolDeBarrio.dtos.InstalacionDto;
import vista_futbolDeBarrio.enums.Estado;
import vista_futbolDeBarrio.enums.Modalidad;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.InstalacionServicio;
import vista_futbolDeBarrio.utilidades.Utilidades;

@WebServlet("/instalacion")
@MultipartConfig
/**
 * Clase controlador que se encarga de los metodos CRUD de la tabla instalacion
 */
public class InstalacionControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private InstalacionServicio servicio;

    @Override
    public void init() throws ServletException {
        this.servicio = new InstalacionServicio();
    }

    @Override
    /**
     * Metodo POST que se encarga de guardar una nueva instalacion
     * @param request La solicitud HTTP que contiene los parámetros del formulario.
     * @param response La respuesta HTTP que se enviará al cliente.
     * @throws ServletException Si ocurre un error en el proceso de la solicitud.
     * @throws IOException Si ocurre un error en el proceso de entrada/salida.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String accion = request.getParameter("accion");
            Log.ficheroLog("Acción recibida desde el formulario: " + accion);


            if ("aniadir".equals(accion)) {
                crearInstalacion(request, response);
            } else if ("modificar".equals(accion)) {
                modificarInstalacion(request, response);
            } else {
                response.getWriter().write("Acción no válida.");
                Log.ficheroLog("Acción no válida recibida: " + accion);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Se ha producido un error en el servidor. " + e.getMessage());
            Log.ficheroLog("Error en el procesamiento de la acción: " + e.getMessage());
        }
    }

    /**
     * Metodo privado que se encarga de crear una nueva instalación.
     * @param request La solicitud HTTP que contiene los parámetros del formulario.
     * @param response La respuesta HTTP que se enviará al cliente.
     * @throws IOException Si ocurre un error durante el proceso de entrada/salida.
     * @throws ServletException Si ocurre un error durante el proceso de la solicitud.
     */
    private void crearInstalacion(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
    	   ServletContext context = request.getServletContext();
        String nombreInstalacionForm = request.getParameter("nombreInstalacion");
        String direccionInstalacionForm = request.getParameter("direccionInstalacion");
        String telefonoInstalacionForm = request.getParameter("telefonoInstalacion");
        String emailInstalacionForm = request.getParameter("emailInstalacion");
        String tipoCampo1Form = request.getParameter("tipoCampo1");
        String tipoCampo2Form = request.getParameter("tipoCampo2");
        String tipoCampo3Form = request.getParameter("tipoCampo3");
        String serviciosInstalacionForm = request.getParameter("serviciosInstalacion");
        String estadoInstalacionForm = request.getParameter("estadoInstalacion");
        String passwordInstalacionForm = request.getParameter("passwordInstalacion");

        Part imagenPart = request.getPart("imagenInstalacion");
        byte[] imagenBytes = null;

        if (imagenPart != null && imagenPart.getSize() > 0) {
            imagenBytes = new byte[(int) imagenPart.getSize()];
            try (InputStream inputStream = imagenPart.getInputStream()) {
                inputStream.read(imagenBytes);
            }
        } else {
            imagenBytes = Utilidades.obtenerImagenPorDefecto(context);
        }

        InstalacionDto nuevaInstalacion = new InstalacionDto();
        nuevaInstalacion.setNombreInstalacion(nombreInstalacionForm);
        nuevaInstalacion.setDireccionInstalacion(direccionInstalacionForm);
        nuevaInstalacion.setTelefonoInstalacion(telefonoInstalacionForm);
        nuevaInstalacion.setEmailInstalacion(emailInstalacionForm);
        if (tipoCampo1Form != null && !tipoCampo1Form.isEmpty()) {
            nuevaInstalacion.setTipoCampo1(Modalidad.valueOf(tipoCampo1Form));
        }

 
        if (tipoCampo2Form != null && !tipoCampo2Form.isEmpty()) {
            nuevaInstalacion.setTipoCampo2(Modalidad.valueOf(tipoCampo2Form));
        } else {
            nuevaInstalacion.setTipoCampo2(nuevaInstalacion.getTipoCampo1());
        }

        if (tipoCampo3Form != null && !tipoCampo3Form.isEmpty()) {
            nuevaInstalacion.setTipoCampo3(Modalidad.valueOf(tipoCampo3Form));
        } else {
            nuevaInstalacion.setTipoCampo3(nuevaInstalacion.getTipoCampo1());
        }


        nuevaInstalacion.setServiciosInstalacion(serviciosInstalacionForm);

        if (estadoInstalacionForm != null && !estadoInstalacionForm.isEmpty()) {
            nuevaInstalacion.setEstadoInstalacion(Estado.valueOf(estadoInstalacionForm));
        }

        nuevaInstalacion.setPasswordInstalacion(passwordInstalacionForm);
        if (imagenBytes == null) {
            imagenBytes = Utilidades.obtenerImagenPorDefecto(context);
        }
        nuevaInstalacion.setImagenInstalacion(imagenBytes);

        nuevaInstalacion.setTorneoIds(new ArrayList<>());

        servicio.guardarInstalacion(nuevaInstalacion);
        Log.ficheroLog("Instalación creada correctamente: " + nombreInstalacionForm);
        response.sendRedirect("InicioSesion.jsp?mensajeAlta=registro_exitoso");
    }

    
    /**
     * Metodo privado que se encarga de modificar una instalación existente.
     * @param request La solicitud HTTP que contiene los parámetros del formulario.
     * @param response La respuesta HTTP que se enviará al cliente.
     * @throws IOException Si ocurre un error durante el proceso de entrada/salida.
     * @throws ServletException Si ocurre un error durante el proceso de la solicitud.
     */
    private void modificarInstalacion(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
 	   ServletContext context = request.getServletContext();

        String idInstalacion = request.getParameter("idInstalacion");
        
        if (idInstalacion == null || idInstalacion.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("ID de instalación no proporcionado.");
            Log.ficheroLog("Modificación fallida: ID de instalación no proporcionado.");
            return;
        }
        String nombreInstalacionForm = request.getParameter("nombreInstalacion");
        String direccionInstalacionForm = request.getParameter("direccionInstalacion");
        String telefonoInstalacionForm = request.getParameter("telefonoInstalacion");
        String emailInstalacionForm = request.getParameter("emailInstalacion");
        String tipoCampo1Form = request.getParameter("tipoCampo1");
        String tipoCampo2Form = request.getParameter("tipoCampo2");
        String tipoCampo3Form = request.getParameter("tipoCampo3");
        String serviciosInstalacionForm = request.getParameter("serviciosInstalacion");
        String estadoInstalacionForm = request.getParameter("estadoInstalacion");
        String passwordInstalacionForm = request.getParameter("passwordInstalacion");
        Part imagenPart = request.getPart("imagenInstalacion");
        byte[] imagenBytes = null;
        if (imagenPart != null && imagenPart.getSize() > 0) {
            imagenBytes = new byte[(int) imagenPart.getSize()];
            InputStream inputStream = imagenPart.getInputStream();
            inputStream.read(imagenBytes);
        }
        
        InstalacionDto instalacionModificada = new InstalacionDto();
        instalacionModificada.setIdInstalacion(Long.parseLong(idInstalacion));
        instalacionModificada.setNombreInstalacion(nombreInstalacionForm);
        instalacionModificada.setDireccionInstalacion(direccionInstalacionForm);
        instalacionModificada.setTelefonoInstalacion(telefonoInstalacionForm);
        instalacionModificada.setEmailInstalacion(emailInstalacionForm);
        if (tipoCampo1Form != null && !tipoCampo1Form.isEmpty()) {
            instalacionModificada.setTipoCampo1(Modalidad.valueOf(tipoCampo1Form));
        }
        if (tipoCampo2Form != null && !tipoCampo2Form.isEmpty()) {
            instalacionModificada.setTipoCampo2(Modalidad.valueOf(tipoCampo2Form));
        }
        if (tipoCampo3Form != null && !tipoCampo3Form.isEmpty()) {
            instalacionModificada.setTipoCampo3(Modalidad.valueOf(tipoCampo3Form));
        }
        instalacionModificada.setServiciosInstalacion(serviciosInstalacionForm);

        if (estadoInstalacionForm != null && !estadoInstalacionForm.isEmpty()) {
            instalacionModificada.setEstadoInstalacion(Estado.valueOf(estadoInstalacionForm));
        }
        instalacionModificada.setPasswordInstalacion(passwordInstalacionForm);
        if (imagenBytes == null) {
            imagenBytes = Utilidades.obtenerImagenPorDefecto(context);
        }
        instalacionModificada.setImagenInstalacion(imagenBytes);


        boolean actualizado = servicio.modificarInstalacion(idInstalacion, instalacionModificada);
        
        if (actualizado) {
            Log.ficheroLog("Instalación modificada: id=" + idInstalacion + ", nombre=" + nombreInstalacionForm);
            response.getWriter().write("Instalación modificada correctamente.");
        } else {
            Log.ficheroLog("Error al modificar instalación: id=" + idInstalacion);
            response.getWriter().write("No se pudo modificar la instalación.");
        }
    }


    @Override
    /**
     * Metodo GET que se encarga para mostrar una lista de las instalaciones
     * @param request La solicitud HTTP que contiene los parámetros de la consulta.
     * @param response La respuesta HTTP que se enviará al cliente.
     * @throws ServletException Si ocurre un error durante el procesamiento de la solicitud.
     * @throws IOException Si ocurre un error durante el procesamiento de la entrada/salida.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<InstalacionDto> listaInstalaciones = servicio.listaInstalaciones();

            // Creamos una lista de mapas para enviar JSON con Base64
            List<Map<String, Object>> listaParaJson = new ArrayList<>();

            for (InstalacionDto i : listaInstalaciones) {
                Map<String, Object> instalacionJson = new HashMap<>();
                instalacionJson.put("idInstalacion", i.getIdInstalacion());
                instalacionJson.put("nombreInstalacion", i.getNombreInstalacion());
                instalacionJson.put("direccionInstalacion", i.getDireccionInstalacion());
                instalacionJson.put("telefonoInstalacion", i.getTelefonoInstalacion());
                instalacionJson.put("emailInstalacion", i.getEmailInstalacion());
                instalacionJson.put("tipoCampo1", i.getTipoCampo1());
                instalacionJson.put("tipoCampo2", i.getTipoCampo2());
                instalacionJson.put("tipoCampo3", i.getTipoCampo3());
                instalacionJson.put("serviciosInstalacion", i.getServiciosInstalacion());
                instalacionJson.put("estadoInstalacion", i.getEstadoInstalacion());
                instalacionJson.put("passwordInstalacion", i.getPasswordInstalacion());

                // Convertimos byte[] a Base64
                String imagenBase64 = "";
                if (i.getImagenInstalacion() != null) {
                    imagenBase64 = Base64.getEncoder().encodeToString(i.getImagenInstalacion());
                }
                instalacionJson.put("imagenInstalacion", imagenBase64);

                // Normalizamos torneoIds
                if (i.getTorneoIds() == null) {
                    i.setTorneoIds(new ArrayList<>());
                }
                instalacionJson.put("torneoIds", i.getTorneoIds());

                listaParaJson.add(instalacionJson);
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(listaParaJson));

            Log.ficheroLog("Lista de instalaciones solicitada. Número de instalaciones: " + listaInstalaciones.size());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Se ha producido un error al obtener las instalaciones: " + e.getMessage());
            Log.ficheroLog("Error al obtener lista de instalaciones: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String tipoUsuario = (String) request.getSession().getAttribute("tipoUsuario");
            if (!"administrador".equals(tipoUsuario)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Acceso denegado. Solo los administradores pueden eliminar instalaciones.");
                return;
            }

            String idParam = request.getParameter("idInstalacion");
            if (idParam == null || idParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("ID de instalación no proporcionado.");
                return;
            }

            Long idInstalacion = Long.parseLong(idParam);
            boolean eliminado = servicio.eliminarInstalacion(idInstalacion);

            if (eliminado) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Instalación eliminada correctamente.");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Error al eliminar la instalación.");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error en el servidor: " + e.getMessage());
        }
    }

}
