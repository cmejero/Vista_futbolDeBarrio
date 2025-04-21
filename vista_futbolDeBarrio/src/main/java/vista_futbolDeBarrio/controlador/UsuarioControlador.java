package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
            HttpSession session = request.getSession(false);
            String tipoUsuario = (session != null) ? (String) session.getAttribute("tipoUsuario") : null;

            if (tipoUsuario == null) {
                crearUsuario(request, response);
            } else if ("administrador".equals(tipoUsuario)) {
                modificarUsuario(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "No tienes permiso para esta acción.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Se ha producido un error en el servidor. Por favor, inténtelo más tarde.");
        }
    }

    private void crearUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	ServletContext context = request.getServletContext();
    	
        String nombreCompleto = request.getParameter("nombreCompletoUsuario");
        String alias = request.getParameter("aliasUsuario");
        String fechaNac = request.getParameter("fechaNacimientoUsuario");
        String email = request.getParameter("emailUsuario");
        String telefono = request.getParameter("telefonoUsuario");
        String password = request.getParameter("passwordUsuario");
        String password2 = request.getParameter("passwordUsuario2");
        String rolString = request.getParameter("rolUsuario");

        if (!password.equals(password2)) {
            response.getWriter().write("Las contraseñas no coinciden.");
            return;
        }

        RolUsuario rol = RolUsuario.valueOf(rolString); // Se obtiene del formulario

        // Obtener la imagen (si fue enviada)
        Part imagenPart = request.getPart("imagenUsuario");
        byte[] imagenBytes = null;

        if (imagenPart != null && imagenPart.getSize() > 0) {
            // Si el usuario sube una imagen, la guardamos
            imagenBytes = new byte[(int) imagenPart.getSize()];
            try (InputStream inputStream = imagenPart.getInputStream()) {
                inputStream.read(imagenBytes);
            }
        } else {
            // Si no se sube una imagen, asignamos la imagen por defecto
            imagenBytes = servicio.obtenerImagenPorDefecto(context);  // Asignamos la imagen por defecto
        }

        // Crear el usuario y guardarlo en la base de datos
        UsuarioDto usuario = new UsuarioDto();
        usuario.setNombreCompletoUsuario(nombreCompleto);
        usuario.setAliasUsuario(alias);
        usuario.setFechaNacimientoUsuario(fechaNac);
        usuario.setEmailUsuario(email);
        usuario.setTelefonoUsuario(telefono);
        usuario.setPasswordUsuario(password);
        usuario.setRolUsuario(rol);
        usuario.setImagenUsuario(imagenBytes);  // Usamos la imagen (por defecto o subida)
        usuario.setDescripcionUsuario(request.getParameter("descripcionUsuario"));
        usuario.setEstadoUsuario(Estado.Activo);  // O cualquier estado por defecto

        // Llamar al servicio para guardar el usuario
        servicio.guardarUsuario(usuario);

        response.getWriter().write("Usuario creado correctamente.");
    }


      

    private void modificarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String idUsuarioForm = request.getParameter("idUsuario");

        // Validar que el ID del usuario esté presente
        if (idUsuarioForm == null || idUsuarioForm.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Los administradores solo pueden modificar usuarios existentes.");
            return;
        }

        // Recoger los demás datos del formulario enviados desde el frontend
        String nombre = request.getParameter("nombreCompletoUsuario");
        String alias = request.getParameter("aliasUsuario");
        String fechaNac = request.getParameter("fechaNacimientoUsuario");
        String email = request.getParameter("emailUsuario");
        String telefono = request.getParameter("telefonoUsuario");
        String password = request.getParameter("passwordUsuario");
        String rolString = request.getParameter("rolUsuario");
        String descripcion = request.getParameter("descripcionUsuario");
        String estadoStr = request.getParameter("estadoUsuario");

        // Convertir roles y estado
        RolUsuario rol = RolUsuario.valueOf(rolString);
        Estado estado = Estado.valueOf(estadoStr);

        // Obtener la imagen subida por el usuario
        Part imagenPart = request.getPart("imagenUsuario");
        byte[] imagenBytes = null;

        // Verificar si el usuario subió una nueva imagen
        if (imagenPart != null && imagenPart.getSize() > 0) {
            // Si sube una imagen, la usamos
            imagenBytes = new byte[(int) imagenPart.getSize()];
            try (InputStream inputStream = imagenPart.getInputStream()) {
                inputStream.read(imagenBytes);
            }
        } else {
            // Si no sube nada, mantener la imagen que ya tenía (si la había)
            imagenBytes = null; // Aquí puedes establecer un valor predeterminado si lo deseas
        }

        // Crear un objeto UsuarioDto con todos los datos nuevos
        UsuarioDto usuarioModificado = new UsuarioDto();
        usuarioModificado.setIdUsuario(Long.parseLong(idUsuarioForm));
        usuarioModificado.setNombreCompletoUsuario(nombre);
        usuarioModificado.setAliasUsuario(alias);
        usuarioModificado.setFechaNacimientoUsuario(fechaNac);
        usuarioModificado.setEmailUsuario(email);
        usuarioModificado.setTelefonoUsuario(telefono);
        usuarioModificado.setPasswordUsuario(password);
        usuarioModificado.setRolUsuario(rol);
        usuarioModificado.setDescripcionUsuario(descripcion);
        usuarioModificado.setEstadoUsuario(estado);
        usuarioModificado.setImagenUsuario(imagenBytes);  // Imagen actual o nueva

        // Llamar al servicio para modificar el usuario
        boolean actualizado = servicio.modificarUsuario(idUsuarioForm, usuarioModificado);

        // Enviar respuesta al usuario
        if (actualizado) {
            response.getWriter().write("Usuario modificado correctamente.");
        } else {
            response.getWriter().write("No se pudo modificar el usuario.");
        }
    }





    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            String tipoUsuario = (session != null) ? (String) session.getAttribute("tipoUsuario") : null;

            if (!"administrador".equals(tipoUsuario)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Acceso denegado. Solo los administradores pueden ver la lista de usuarios.");
                return;
            }

            ArrayList<UsuarioDto> listaUsuario = servicio.listausuario();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(listaUsuario);
            response.getWriter().write(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error en el servidor: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            String tipoUsuario = (session != null) ? (String) session.getAttribute("tipoUsuario") : null;

            if (!"administrador".equals(tipoUsuario)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Acceso denegado. Solo los administradores pueden eliminar usuarios.");
                return;
            }

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
