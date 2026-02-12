package vista_futbolDeBarrio.controlador;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import vista_futbolDeBarrio.dtos.ClubDto;
import vista_futbolDeBarrio.dtos.InstalacionDto;
import vista_futbolDeBarrio.dtos.UsuarioDto;
import vista_futbolDeBarrio.enums.Estado;
import vista_futbolDeBarrio.enums.Modalidad;
import vista_futbolDeBarrio.enums.RolUsuario;
import vista_futbolDeBarrio.servicios.ClubServicio;
import vista_futbolDeBarrio.servicios.InstalacionServicio;
import vista_futbolDeBarrio.servicios.TorneoServicio;
import vista_futbolDeBarrio.servicios.UsuarioServicio;
import vista_futbolDeBarrio.log.Log;

@WebServlet("/administrador")
@MultipartConfig
/**
 * Controlador que gestiona la administración de usuarios, clubes, instalaciones y torneos.
 * Incluye seguimiento de acciones mediante logs.
 */
public class AdministradorControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UsuarioServicio usuarioServicio;
    private ClubServicio clubServicio;
    private InstalacionServicio instalacionServicio;
    private TorneoServicio torneoServicio;

    @Override
    /**
     * Inicializa los servicios de la aplicación.
     *
     * @throws ServletException Si ocurre un error durante la inicialización.
     */
    public void init() throws ServletException {
        try {
            usuarioServicio = new UsuarioServicio();
            clubServicio = new ClubServicio();
            instalacionServicio = new InstalacionServicio();
            torneoServicio = new TorneoServicio();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }


    @Override
    /**
     * Obtiene listas de entidades (usuario, club, instalación, torneo) o carga la vista
     * de administración.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String ajaxHeader = request.getHeader("X-Requested-With");
            HttpSession session = request.getSession(false);

            // 🔐 Seguridad básica
            if (session == null || session.getAttribute("token") == null) {
                Log.ficheroLog("EventoJugadorControlador: intento de acceso sin sesión o token inválido");
                response.sendRedirect(request.getContextPath() + "/login?error=accesoDenegado");
                return;
            }
            // 👉 Navegación normal
            if (!"XMLHttpRequest".equals(ajaxHeader)) {
                request.getRequestDispatcher("/WEB-INF/Vistas/Administrador.jsp").forward(request, response);
                Log.ficheroLog("GET /administrador: vista Administrador.jsp cargada");
                return;
            }
            

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String entidad = request.getParameter("entidad");
            ObjectMapper mapper = new ObjectMapper();

            switch (entidad) {
                case "usuario":
                    response.getWriter().write(mapper.writeValueAsString(usuarioServicio.obtenerUsuarios(request)));
                    Log.ficheroLog("GET /administrador: lista de usuarios obtenida");
                    break;
                case "club":
                    response.getWriter().write(mapper.writeValueAsString(clubServicio.listaClub(request)));
                    Log.ficheroLog("GET /administrador: lista de clubes obtenida");
                    break;
                case "instalacion":
                    response.getWriter().write(mapper.writeValueAsString(instalacionServicio.listaInstalaciones(request)));
                    Log.ficheroLog("GET /administrador: lista de instalaciones obtenida");
                    break;
                case "torneo":
                    response.getWriter().write(mapper.writeValueAsString(torneoServicio.obtenerTodosLosTorneos()));
                    Log.ficheroLog("GET /administrador: lista de torneos obtenida");
                    break;
                default:
                    response.getWriter().write("[]");
                    Log.ficheroLog("GET /administrador: entidad no válida solicitada=" + entidad);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            Log.ficheroLog("GET /administrador error: " + e.getMessage());
        }
    }


    @Override
    /**
     * Modifica entidades: usuario, club, instalación según los parámetros recibidos.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String entidad = request.getParameter("entidad");
        String accion = request.getParameter("accion");

        try {
         
            // USUARIO
          
            if ("usuario".equals(entidad) && "modificar".equals(accion)) {

                String id = request.getParameter("idUsuario");

                UsuarioDto usuario = new UsuarioDto();
                usuario.setIdUsuario(Long.parseLong(id));
                usuario.setNombreCompletoUsuario(request.getParameter("nombreCompletoUsuarioEditar"));
                usuario.setAliasUsuario(request.getParameter("aliasUsuarioEditar"));
                usuario.setEmailUsuario(request.getParameter("emailUsuarioEditar"));
                usuario.setTelefonoUsuario(request.getParameter("telefonoUsuarioEditar"));
                String estado = request.getParameter("estadoUsuarioEditar");
                if (estado != null) usuario.setEstadoUsuario(Estado.valueOf(estado));
                usuario.setDescripcionUsuario(request.getParameter("descripcionUsuarioEditar"));
                String rol = request.getParameter("rolUsuarioEditar");
                if (rol != null) usuario.setRolUsuario(RolUsuario.valueOf(rol));

                Part imagenPart = request.getPart("imagenUsuarioEditar");
                if (imagenPart != null && imagenPart.getSize() > 0) {
                    usuario.setImagenUsuario(imagenPart.getInputStream().readAllBytes());
                }

                boolean ok = usuarioServicio.modificarUsuario(request, id, usuario);

                response.getWriter().write(ok ? "Usuario modificado correctamente." : "No se pudo modificar el usuario.");
                Log.ficheroLog("POST /administrador usuario modificar: id=" + id + ", resultado=" + ok);
            }
     
            // CLUB
            
            if ("club".equals(entidad) && "modificar".equals(accion)) {

                String id = request.getParameter("idClubEditar");

                ClubDto club = new ClubDto();
                club.setIdClub(Long.parseLong(id));
                club.setNombreClub(request.getParameter("nombreClubEditar"));
                club.setAbreviaturaClub(request.getParameter("abreviaturaClubEditar"));
                club.setDescripcionClub(request.getParameter("descripcionClubEditar"));
                club.setEmailClub(request.getParameter("emailClubEditar"));
                club.setTelefonoClub(request.getParameter("telefonoClubEditar"));
                club.setLocalidadClub(request.getParameter("localidadClubEditar"));
                club.setPaisClub(request.getParameter("paisClubEditar"));
                club.setFechaCreacionClub(request.getParameter("fechaCreacionClubEditar"));
                club.setFechaFundacionClub(request.getParameter("fechaFundacionClubEditar"));

                Part logoPart = request.getPart("logoClubEditar");
                if (logoPart != null && logoPart.getSize() > 0) {
                    club.setLogoClub(logoPart.getInputStream().readAllBytes());
                }

                boolean ok = clubServicio.modificarClub(id, club, request);
                response.getWriter().write(ok ? "Club modificado correctamente." : "No se pudo modificar el club.");
                Log.ficheroLog("POST /administrador club modificar: id=" + id + ", resultado=" + ok);
                return;
            }

            // INSTALACION

            if ("instalacion".equals(entidad) && "modificar".equals(accion)) {

                String id = request.getParameter("idInstalacion");
                if (id == null || id.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("ID de instalación no proporcionado.");
                    Log.ficheroLog("POST /administrador instalacion modificar falló: id no proporcionado");
                    return;
                }

                InstalacionDto inst = new InstalacionDto();
                inst.setIdInstalacion(Long.parseLong(id));
                inst.setNombreInstalacion(request.getParameter("nombreInstalacionEditar"));
                inst.setDireccionInstalacion(request.getParameter("direccionInstalacionEditar"));
                inst.setTelefonoInstalacion(request.getParameter("telefonoInstalacionEditar"));
                inst.setEmailInstalacion(request.getParameter("emailInstalacionEditar"));
                inst.setServiciosInstalacion(request.getParameter("serviciosInstalacionEditar"));

                String tipoCampo1 = request.getParameter("tipoCampo1Editar");
                if (tipoCampo1 != null && !tipoCampo1.isEmpty()) inst.setTipoCampo1(Modalidad.valueOf(tipoCampo1));

                String tipoCampo2 = request.getParameter("tipoCampo2Editar");
                if (tipoCampo2 != null && !tipoCampo2.isEmpty()) inst.setTipoCampo2(Modalidad.valueOf(tipoCampo2));

                String tipoCampo3 = request.getParameter("tipoCampo3Editar");
                if (tipoCampo3 != null && !tipoCampo3.isEmpty()) inst.setTipoCampo3(Modalidad.valueOf(tipoCampo3));

                String estado = request.getParameter("estadoInstalacionEditar");
                if (estado != null && !estado.isEmpty()) inst.setEstadoInstalacion(Estado.valueOf(estado));

                Part imagenPart = request.getPart("imagenInstalacionEditar");
                if (imagenPart != null && imagenPart.getSize() > 0) {
                    inst.setImagenInstalacion(imagenPart.getInputStream().readAllBytes());
                }

                boolean ok = instalacionServicio.modificarInstalacion(id, inst, request);

                response.getWriter().write(ok ? "Instalación modificada correctamente." : "No se pudo modificar la instalación.");
                Log.ficheroLog("POST /administrador instalacion modificar: id=" + id + ", resultado=" + ok);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("error");
            Log.ficheroLog("POST /administrador error: " + e.getMessage());
        }
    }

    @Override
    /**
     * Elimina entidades: usuario, club o instalación según los parámetros recibidos.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String entidad = request.getParameter("entidad");

        try {
            HttpSession session = request.getSession(false);
            String tipoUsuario = (session != null) ? (String) session.getAttribute("tipoUsuario") : null;

            switch (entidad) {
                case "usuario":
                    if (!"administrador".equals(tipoUsuario)) {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.getWriter().write("Acceso denegado. Solo los administradores pueden eliminar usuarios.");
                        Log.ficheroLog("DELETE /administrador usuario falló: acceso denegado");
                        return;
                    }

                    String idUsuarioParam = request.getParameter("idUsuario");
                    if (idUsuarioParam == null || idUsuarioParam.isEmpty()) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().write("ID de usuario no proporcionado.");
                        Log.ficheroLog("DELETE /administrador usuario falló: id no proporcionado");
                        return;
                    }

                    Long idUsuario = Long.parseLong(idUsuarioParam);
                    boolean eliminado = usuarioServicio.eliminarUsuario(idUsuario, request);

                    response.setStatus(eliminado ? HttpServletResponse.SC_OK : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write(eliminado ? "Usuario eliminado correctamente." : "Error al eliminar el usuario.");
                    Log.ficheroLog("DELETE /administrador usuario: id=" + idUsuario + ", resultado=" + eliminado);
                    break;

                case "club":
                    String idClubParam = request.getParameter("idClub");
                    if (idClubParam == null || idClubParam.isEmpty()) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().write("ID de club no proporcionado.");
                        Log.ficheroLog("DELETE /administrador club falló: id no proporcionado");
                        return;
                    }

                    Long idClub = Long.parseLong(idClubParam);
                    boolean eliminadoClub = clubServicio.eliminarClub(idClub, request);

                    response.setStatus(eliminadoClub ? HttpServletResponse.SC_OK : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write(eliminadoClub ? "Club eliminado correctamente." : "Error al eliminar el club.");
                    Log.ficheroLog("DELETE /administrador club: id=" + idClub + ", resultado=" + eliminadoClub);
                    break;

                case "instalacion":
                    String idInstParam = request.getParameter("idInstalacion");
                    if (idInstParam == null || idInstParam.isEmpty()) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().write("ID de instalación no proporcionado.");
                        Log.ficheroLog("DELETE /administrador instalacion falló: id no proporcionado");
                        return;
                    }

                    Long idInst = Long.parseLong(idInstParam);
                    boolean eliminadoInst = instalacionServicio.eliminarInstalacion(idInst, request);

                    response.setStatus(eliminadoInst ? HttpServletResponse.SC_OK : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write(eliminadoInst ? "Instalación eliminada correctamente." : "Error al eliminar la instalación.");
                    Log.ficheroLog("DELETE /administrador instalacion: id=" + idInst + ", resultado=" + eliminadoInst);
                    break;

                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Entidad no válida para eliminación.");
                    Log.ficheroLog("DELETE /administrador falló: entidad no válida=" + entidad);
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("ID no válido.");
            Log.ficheroLog("DELETE /administrador error: ID no válido");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error en el servidor: " + e.getMessage());
            Log.ficheroLog("DELETE /administrador error general: " + e.getMessage());
        }
    }
}
