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


@WebServlet("/logout")
/**
* Controlador para gestionar el cierre de sesión de los usuarios.
* @param request HttpServletRequest que contiene la solicitud del cliente.
* @param response HttpServletResponse que contiene la respuesta a enviar al cliente.
* @throws ServletException Si ocurre un error relacionado con el servlet.
* @throws IOException Si ocurre un error relacionado con la entrada/salida.
*/
public class CerrarSesionControlador extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /** 
     * Maneja las solicitudes GET para cerrar la sesión.
     * Elimina la sesión del usuario y registra el evento en un archivo de log.
     * @param request HttpServletRequest que contiene la solicitud del cliente.
     * @param response HttpServletResponse que contiene la respuesta a enviar al cliente.
     * @throws ServletException Si ocurre un error relacionado con el servlet.
     * @throws IOException Si ocurre un error relacionado con la entrada/salida.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            String tipoUsuario = (String) session.getAttribute("tipoUsuario");
            Object datosUsuario = session.getAttribute("datosUsuario");
            if (tipoUsuario != null) {
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
            }

            session.removeAttribute("token");
           
            String token = (String) session.getAttribute("token");
            if (token == null) {
                Log.ficheroLog("El token ha sido eliminado correctamente.");
            } else {
                Log.ficheroLog("Error: El token no se ha eliminado.");
            }
            session.invalidate();
        }
        response.sendRedirect("InicioSesion.jsp?mensaje=sesion_cerrada");
    }
}
