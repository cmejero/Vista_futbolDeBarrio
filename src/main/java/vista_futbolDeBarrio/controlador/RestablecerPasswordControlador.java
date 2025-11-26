package vista_futbolDeBarrio.controlador;

import java.io.IOException;

import vista_futbolDeBarrio.log.Log;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vista_futbolDeBarrio.servicios.RestablecerPasswordServicio;

@WebServlet("/restablecerPassword")
/**
 * clase controlador encargado los metodos CRUD de Restablecer Password
 */
public class RestablecerPasswordControlador extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    // Servicio encargado de la lógica para restablecer contraseña
    RestablecerPasswordServicio restablecer = new RestablecerPasswordServicio();
    
    /**
     * Procesa la solicitud POST para restablecer contraseña.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");

        Log.ficheroLog("Intento de restablecer contraseña con token: " + token);

        procesarRestablecimiento(token, password, passwordConfirm, response);
    }

    /**
     * Valida contraseñas y actualiza si todo es correcto, luego redirige.
     * @param token Token de recuperación.
     * @param password Nueva contraseña.
     * @param passwordConfirm Confirmación de contraseña.
     * @param response HttpServletResponse para redirección.
     * @throws IOException
     */
    private void procesarRestablecimiento(String token, String password, String passwordConfirm, HttpServletResponse response) throws IOException {
        if (!password.equals(passwordConfirm)) {
            Log.ficheroLog("Las contraseñas no coinciden para token: " + token);
            response.sendRedirect("resetPassword.jsp?token=" + token + "&error=Las contraseñas no coinciden");
            return;
        }

        boolean actualizado = restablecer.actualizarPassword(token, password);

        if (actualizado) {
            Log.ficheroLog("Contraseña actualizada correctamente para token: " + token);
            response.sendRedirect("InicioSesion.jsp?mensaje=contraseña_actualizada");
        } else {
            Log.ficheroLog("Error al actualizar contraseña para token: " + token);
            response.sendRedirect("resetPassword.jsp?token=" + token + "&error=Token inválido o expirado");
        }
    }
}
