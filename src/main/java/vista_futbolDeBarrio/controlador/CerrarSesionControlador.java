package vista_futbolDeBarrio.controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.ClubDto;
import vista_futbolDeBarrio.dtos.InstalacionDto;
import vista_futbolDeBarrio.dtos.UsuarioDto;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.utilidades.Utilidades;

@WebServlet("/logout")
/**
 * Controlador encargado de cerrar la sesión del usuario, 
 * borrar cookies, invalidar la sesión y redirigir al login.
 * También registra la acción en el log.
 */
public class CerrarSesionControlador extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    /**
     * Gestiona la finalización de la sesión del usuario.
     * Dependiendo del tipo de usuario, registra en el log qué sesión se cerró.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);

            // 1️⃣ Invalidar sesión primero
            if (session != null) {
                String tipoUsuario = (String) session.getAttribute("tipoUsuario");
                Object datosUsuario = session.getAttribute("datosUsuario");

                if ("jugador".equals(tipoUsuario) && datosUsuario instanceof UsuarioDto) {
                    UsuarioDto usuario = (UsuarioDto) datosUsuario;
                    Log.ficheroLog("Sesión cerrada para el jugador: " + usuario.getNombreCompletoUsuario() + " (ID: " + usuario.getIdUsuario() + ")");
                } else if ("club".equals(tipoUsuario) && datosUsuario instanceof ClubDto) {
                    ClubDto club = (ClubDto) datosUsuario;
                    Log.ficheroLog("Sesión cerrada para el club: " + club.getNombreClub() + " (ID: " + club.getIdClub() + ")");
                } else if ("instalacion".equals(tipoUsuario) && datosUsuario instanceof InstalacionDto) {
                    InstalacionDto instalacion = (InstalacionDto) datosUsuario;
                    Log.ficheroLog("Sesión cerrada para la instalación: " + instalacion.getNombreInstalacion() + " (ID: " + instalacion.getIdInstalacion() + ")");
                } else {
                    Log.ficheroLog("Sesión cerrada para un tipo de usuario desconocido.");
                }

                session.invalidate();
                Log.ficheroLog("Sesión invalidada correctamente.");
            } else {
                Log.ficheroLog("Intento de cierre de sesión sin sesión activa.");
            }

            // 2️⃣ Borrar cookies después
            Utilidades.borrarCookies(response, request.getContextPath());
            Log.ficheroLog("Cookies borradas correctamente.");

            // 3️⃣ Redirigir a login
            response.sendRedirect("login?mensaje=sesion_cerrada");
            Log.ficheroLog("Redirección a login completada tras cierre de sesión.");

        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("Error al cerrar sesión: " + e.getMessage());
            response.sendRedirect("login?mensaje=error_sesion");
        }
    }

}
