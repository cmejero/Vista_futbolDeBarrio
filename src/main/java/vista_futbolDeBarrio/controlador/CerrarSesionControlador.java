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
public class CerrarSesionControlador extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Logging del usuario que cierra sesión
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
        }

        // 1️⃣ Borrar cookies (Secure y no secure)
        Utilidades.borrarCookies(response);

        // 2️⃣ Invalidar sesión
        if (session != null) session.invalidate();

        // 3️⃣ Redirigir a login
        response.sendRedirect("login?mensaje=sesion_cerrada");
    }

   
    
}
