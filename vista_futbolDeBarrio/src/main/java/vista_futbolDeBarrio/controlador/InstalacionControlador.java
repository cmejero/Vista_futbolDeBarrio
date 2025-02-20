package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

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
import vista_futbolDeBarrio.log.log;
import vista_futbolDeBarrio.servicios.InstalacionServicio;

@WebServlet("/instalacion")
@MultipartConfig
public class InstalacionControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private log log = new log();
    private InstalacionServicio servicio;

    @Override
    public void init() throws ServletException {
        this.servicio = new InstalacionServicio();
        this.log = new log();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String accion = request.getParameter("accion"); // Determina la acción a realizar
            System.out.println("Acción recibida desde el formulario: " + accion);

            if ("aniadir".equals(accion)) {
                // Obtener los datos del formulario
                String nombreInstalacionForm = request.getParameter("nombreInstalacion");
                String direccionInstalacionForm = request.getParameter("direccionInstalacion");
                String telefonoInstalacionForm = request.getParameter("telefonoInstalacion");
                String emailInstalacionForm = request.getParameter("emailInstalacion");
                String tipoCampo1Form = request.getParameter("tipoCampo1"); // Debe coincidir con un valor de Modalidad
                String tipoCampo2Form = request.getParameter("tipoCampo2");
                String tipoCampo3Form = request.getParameter("tipoCampo3");
                String serviciosInstalacionForm = request.getParameter("serviciosInstalacion");
                String estadoInstalacionForm = request.getParameter("estadoInstalacion"); // Debe coincidir con un valor de Estado
                String passwordInstalacionForm = request.getParameter("passwordInstalacion");

                // Procesar la imagen de la instalación
                Part imagenPart = request.getPart("imagenInstalacion");
                byte[] imagenBytes = null;
                if (imagenPart != null && imagenPart.getSize() > 0) {
                    imagenBytes = new byte[(int) imagenPart.getSize()];
                    InputStream inputStream = imagenPart.getInputStream();
                    inputStream.read(imagenBytes);
                }

                // Crear el objeto InstalacionDto y asignar los valores
                InstalacionDto nuevaInstalacion = new InstalacionDto();
                nuevaInstalacion.setNombreInstalacion(nombreInstalacionForm);
                nuevaInstalacion.setDireccionInstalacion(direccionInstalacionForm);
                nuevaInstalacion.setTelefonoInstalacion(telefonoInstalacionForm);
                nuevaInstalacion.setEmailInstalacion(emailInstalacionForm);

                // Convertir las cadenas recibidas a los enum correspondientes (si no están vacíos)
                if (tipoCampo1Form != null && !tipoCampo1Form.isEmpty()) {
                    nuevaInstalacion.setTipoCampo1(Modalidad.valueOf(tipoCampo1Form));
                }
                if (tipoCampo2Form != null && !tipoCampo2Form.isEmpty()) {
                    nuevaInstalacion.setTipoCampo2(Modalidad.valueOf(tipoCampo2Form));
                }
                if (tipoCampo3Form != null && !tipoCampo3Form.isEmpty()) {
                    nuevaInstalacion.setTipoCampo3(Modalidad.valueOf(tipoCampo3Form));
                }
                
                nuevaInstalacion.setServiciosInstalacion(serviciosInstalacionForm);
              
                
                if (estadoInstalacionForm != null && !estadoInstalacionForm.isEmpty()) {
                    nuevaInstalacion.setEstadoInstalacion(Estado.valueOf(estadoInstalacionForm));
                }
                
                nuevaInstalacion.setPasswordInstalacion(passwordInstalacionForm);

                // Asignar la imagen convertida a String (o bien utilizar otro método, por ejemplo, Base64)
                if (imagenBytes != null) {
                    nuevaInstalacion.setImagenInstalacion(new String(imagenBytes));
                } else {
                    nuevaInstalacion.setImagenInstalacion("");
                }
                
                // Inicializar las listas de IDs para  torneos (si aplica)
                
                nuevaInstalacion.setTorneoId(new ArrayList<>());
                
                // Guardar la instalación a través del servicio
                servicio.guardarInstalacion(nuevaInstalacion);
                response.getWriter().write("Instalación creada correctamente.");
                
            } else if ("modificar".equals(accion)) {
                // Lógica para modificar la instalación (similar a la de añadir, obteniendo el ID y actualizando los campos)
                // Puedes implementar este bloque según tus necesidades.
            } else {
                response.getWriter().write("Acción no válida.");
                log.ficheroErrores("Acción no válida recibida: " + accion);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Se ha producido un error en el servidor. " + e.getMessage());
            log.ficheroErrores("Error en el procesamiento de la acción: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener la lista de instalaciones a través del servicio
        List<InstalacionDto> listaInstalaciones = servicio.listaInstalaciones();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Convertir la lista a JSON y escribir la respuesta
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(listaInstalaciones);
        response.getWriter().write(json);
    }
}
