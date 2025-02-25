package vista_futbolDeBarrio.controlador;
import java.io.IOException;

import java.io.InputStream;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vista_futbolDeBarrio.dtos.UsuarioDto;
import vista_futbolDeBarrio.enums.Estado;
import vista_futbolDeBarrio.enums.RolUsuario;
import vista_futbolDeBarrio.log.log;
import vista_futbolDeBarrio.servicios.UsuarioServicio;



@WebServlet("/usuario")
@MultipartConfig
public class UsuarioControlador extends HttpServlet {
	
	
	
	 private static final long serialVersionUID = 1L;
	private log log = new log();
	
	private UsuarioServicio servicio;

	@Override
	public void init() throws ServletException {
		this.servicio = new UsuarioServicio();
		this.log = new log();
	}

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        String accion = request.getParameter("accion"); // Acción para determinar si es crear o modificar
	        System.out.println("Acción recibida desde el formulario: " + request.getParameter("accion"));

	        
	        if ("aniadir".equals(accion)) {
	            // Obtener los datos del formulario
	            String nombreCompletoUsuarioForm = request.getParameter("nombreCompletoUsuario");
	           
	            String aliasUsuarioForm = request.getParameter("aliasUsuario");
	            String fechaNacimientoUsuarioForm = request.getParameter("fechaNacimientoUsuario");
	            String emailUsuarioForm = request.getParameter("emailUsuario");
	            String telefonoUsuarioForm = request.getParameter("telefonoUsuario");
	            String passwordUsuarioForm = request.getParameter("passwordUsuario");
	            String passwordUsuario2Form = request.getParameter("passwordUsuario2"); // Confirmar contraseña

	            // Verificar si las contraseñas coinciden
	            if (!passwordUsuarioForm.equals(passwordUsuario2Form)) {
	                response.getWriter().write("Las contraseñas no coinciden.");
	                return; // Terminar ejecución si no coinciden
	            }

	            // Obtener el rol
	            String rolUsuarioString = request.getParameter("rolUsuario");
	            RolUsuario rolUsuarioForm = RolUsuario.valueOf(rolUsuarioString.trim());
	            
	            // Obtener descripción
	            String descripcionUsuarioForm = request.getParameter("descripcionUsuario");

	            // Crear el estado del usuario (puedes cambiarlo según tu lógica)
	            String estadoUsuarioString = "Activo"; // Por defecto, 'Activo'
	            Estado estadoUsuarioForm = Estado.valueOf(estadoUsuarioString);

	            // Procesar la imagen
	            Part imagenPart = request.getPart("imagenUsuario");
	            byte[] imagenUsuarioForm = null;
	            if (imagenPart != null && imagenPart.getSize() > 0) {
	                imagenUsuarioForm = new byte[(int) imagenPart.getSize()];
	                InputStream inputStream = imagenPart.getInputStream();
	                inputStream.read(imagenUsuarioForm);
	            }

	            // Crear el objeto UsuarioDto
	            UsuarioDto nuevoUsuario = new UsuarioDto();
	            nuevoUsuario.setNombreCompletoUsuario(nombreCompletoUsuarioForm);
	            nuevoUsuario.setAliasUsuario(aliasUsuarioForm);
	            nuevoUsuario.setFechaNacimientoUsuario(fechaNacimientoUsuarioForm);
	            nuevoUsuario.setEmailUsuario(emailUsuarioForm);
	            nuevoUsuario.setTelefonoUsuario(telefonoUsuarioForm);
	            nuevoUsuario.setPasswordUsuario(passwordUsuarioForm);
	            nuevoUsuario.setRolUsuario(rolUsuarioForm);
	            nuevoUsuario.setDescripcionUsuario(descripcionUsuarioForm);
	            nuevoUsuario.setImagenUsuario(imagenUsuarioForm);
	            nuevoUsuario.setEstadoUsuario(estadoUsuarioForm);

	            // Guardar el usuario en el servicio
	            servicio.guardarUsuario(nuevoUsuario);
	            
	            response.getWriter().write("Usuario creado correctamente.");
	        } else if ("modificar".equals(accion)) {
	            // Lógica para modificar el usuario
	        } else {
	            response.getWriter().write("Acción no válida.");
	            log.ficheroErrores("Acción no válida recibida: " + accion);
	        }

	    } catch (Exception e) {
	        // En caso de que ocurra algún error durante la ejecución del doPost
	        e.printStackTrace(); // Imprime la traza del error en el servidor o consola

	        // Responder con un mensaje genérico de error
	        response.getWriter().write("Se ha producido un error en el servidor. Por favor, inténtelo más tarde." + e.getMessage());
	        
	        // Registrar el error en el archivo de log
	        log.ficheroErrores("Error en el procesamiento de la acción: " + e.getMessage());
	    }
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    // Llamada al servicio que obtiene los usuarios
	    ArrayList<UsuarioDto> listaUsuario = servicio.listausuario();

	    // Configurar la respuesta como JSON
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");

	    // Convertir la lista a JSON
	    ObjectMapper objectMapper = new ObjectMapper();
	    String json = objectMapper.writeValueAsString(listaUsuario);

	    // Escribir la respuesta JSON
	    response.getWriter().write(json);
	}
	
	 @Override
	    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        try {
	            String idUsuarioParam = request.getParameter("idUsuario");
	            if (idUsuarioParam == null || idUsuarioParam.isEmpty()) {
	                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	                response.getWriter().write("ID de usuario no proporcionado.");
	                return;
	            }

	            Long idUsuario = Long.parseLong(idUsuarioParam);
	            boolean eliminado = servicio.eliminarUsuario(idUsuario);

	            if (eliminado) {
	                response.setStatus(HttpServletResponse.SC_OK);
	                response.getWriter().write("Usuario eliminado correctamente.");
	            } else {
	                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	                response.getWriter().write("Error al eliminar el usuario.");
	            }
	        } catch (NumberFormatException e) {
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	            response.getWriter().write("ID de usuario no válido.");
	        } catch (Exception e) {
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            response.getWriter().write("Error en el servidor: " + e.getMessage());
	        }
	    }

}
