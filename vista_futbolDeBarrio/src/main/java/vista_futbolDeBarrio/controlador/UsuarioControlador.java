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
import vista_futbolDeBarrio.servicios.UsuarioServicio;
import vista_futbolDeBarrio.log.Log;

@WebServlet("/usuario")
@MultipartConfig
/**
 * Clase controlador que se encarga de los metodos CRUD de la tabla usuario
 */
public class UsuarioControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UsuarioServicio servicio;

    @Override
    public void init() throws ServletException {
        this.servicio = new UsuarioServicio();
    }

    @Override
    /**
     * Metodo POST que se encarga de guardar o modificar un usuario
     */
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
                Log.ficheroLog("Intento de acceso no autorizado a POST /usuario");
            }

        } catch (Exception e) {
            Log.ficheroLog("Error en doPost /usuario: " + e.getMessage() );
            e.printStackTrace();
            response.getWriter().write("Se ha producido un error en el servidor. Por favor, inténtelo más tarde.");
        }
    }

    /**
     * Metodo que se encarga de de crear un usuario
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
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
            Log.ficheroLog("Error al crear usuario - contraseñas no coinciden (alias=" + alias + ") " );
            return;
        }

        RolUsuario rol = RolUsuario.valueOf(rolString);

        Part imagenPart = request.getPart("imagenUsuario");
        byte[] imagenBytes = null;

        if (imagenPart != null && imagenPart.getSize() > 0) {
            imagenBytes = new byte[(int) imagenPart.getSize()];
            try (InputStream inputStream = imagenPart.getInputStream()) {
                inputStream.read(imagenBytes);
            }
        } else {
            imagenBytes = servicio.obtenerImagenPorDefecto(context);
        }

        UsuarioDto usuario = new UsuarioDto();
        usuario.setNombreCompletoUsuario(nombreCompleto);
        usuario.setAliasUsuario(alias);
        usuario.setFechaNacimientoUsuario(fechaNac);
        usuario.setEmailUsuario(email);
        usuario.setTelefonoUsuario(telefono);
        usuario.setPasswordUsuario(password);
        usuario.setRolUsuario(rol);
        usuario.setImagenUsuario(imagenBytes);
        usuario.setDescripcionUsuario(request.getParameter("descripcionUsuario"));
        usuario.setEstadoUsuario(Estado.Activo);

        servicio.guardarUsuario(usuario);

        Log.ficheroLog("Usuario creado: alias=" + alias );
        response.getWriter().write("Usuario creado correctamente.");
    }

    /**
     * Metodo que se encarga de modificar un usuario
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void modificarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String idUsuarioForm = request.getParameter("idUsuario");

        if (idUsuarioForm == null || idUsuarioForm.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Los administradores solo pueden modificar usuarios existentes.");
            Log.ficheroLog("Intento de modificación sin ID de usuario" );
            return;
        }

        String nombre = request.getParameter("nombreCompletoUsuario");
        String alias = request.getParameter("aliasUsuario");
        String fechaNac = request.getParameter("fechaNacimientoUsuario");
        String email = request.getParameter("emailUsuario");
        String telefono = request.getParameter("telefonoUsuario");
        String password = request.getParameter("passwordUsuario");
        String rolString = request.getParameter("rolUsuario");
        String descripcion = request.getParameter("descripcionUsuario");
        String estadoStr = request.getParameter("estadoUsuario");

        RolUsuario rol = RolUsuario.valueOf(rolString);
        Estado estado = Estado.valueOf(estadoStr);

        Part imagenPart = request.getPart("imagenUsuario");
        byte[] imagenBytes = null;

        if (imagenPart != null && imagenPart.getSize() > 0) {
            imagenBytes = new byte[(int) imagenPart.getSize()];
            try (InputStream inputStream = imagenPart.getInputStream()) {
                inputStream.read(imagenBytes);
            }
        }

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
        usuarioModificado.setImagenUsuario(imagenBytes);

        boolean actualizado = servicio.modificarUsuario(idUsuarioForm, usuarioModificado);

        if (actualizado) {
            Log.ficheroLog("Usuario modificado: id=" + idUsuarioForm + ", alias=" + alias );
            response.getWriter().write("Usuario modificado correctamente.");
        } else {
            Log.ficheroLog("Error al modificar usuario: id=" + idUsuarioForm );
            response.getWriter().write("No se pudo modificar el usuario.");
        }
    }

    @Override
    /**
     * Metodo GET que se encarga de mostrar una lista de todos los usuarios
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            String tipoUsuario = (session != null) ? (String) session.getAttribute("tipoUsuario") : null;

            // Verificar si hay sesión activa y si el usuario tiene algún tipo definido
            if (tipoUsuario == null || tipoUsuario.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Acceso denegado. Debe iniciar sesión para acceder.");
                Log.ficheroLog("Intento de acceso no autorizado sin sesión a GET /usuario");
                return;
            }

            // Puedes agregar validaciones específicas aquí si en el futuro quieres restringir más
            // Por ejemplo: solo ciertos tipos de usuarios

            ArrayList<UsuarioDto> listaUsuario = servicio.listausuario();

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(listaUsuario);
            response.getWriter().write(json);
        } catch (Exception e) {
            Log.ficheroLog("Error en GET /usuario: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error en el servidor: " + e.getMessage());
        }
    }


    @Override
    /**
     * Metodo DELETE que se encarga de eliminar un usuario
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            String tipoUsuario = (session != null) ? (String) session.getAttribute("tipoUsuario") : null;

            if (!"administrador".equals(tipoUsuario)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Acceso denegado. Solo los administradores pueden eliminar usuarios.");
                Log.ficheroLog("Intento de eliminación no autorizado");
                return;
            }

            String idUsuarioParam = request.getParameter("idUsuario");
            if (idUsuarioParam == null || idUsuarioParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("ID de usuario no proporcionado.");
                Log.ficheroLog("Eliminación fallida: ID no proporcionado");
                return;
            }

            Long idUsuario = Long.parseLong(idUsuarioParam);
            boolean eliminado = servicio.eliminarUsuario(idUsuario);

            if (eliminado) {
                Log.ficheroLog("Usuario eliminado: id=" + idUsuario);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Usuario eliminado correctamente.");
            } else {
                Log.ficheroLog("Error al eliminar usuario: id=" + idUsuario);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Error al eliminar el usuario.");
            }
        } catch (NumberFormatException e) {
            Log.ficheroLog("Error de formato en ID de usuario para eliminar: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("ID de usuario no válido.");
        } catch (Exception e) {
            Log.ficheroLog("Error en DELETE /usuario: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error en el servidor: " + e.getMessage());
        }
    }
}
