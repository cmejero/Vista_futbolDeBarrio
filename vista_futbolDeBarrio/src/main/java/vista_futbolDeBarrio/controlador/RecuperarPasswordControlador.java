package vista_futbolDeBarrio.controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vista_futbolDeBarrio.servicios.RecuperarPasswordServicio;
import vista_futbolDeBarrio.log.Log;

@WebServlet("/recuperarPassword")
/**
 * clase controlador encargado los metodos CRUD de Recuperar Password
 */
public class RecuperarPasswordControlador extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Servicio encargado de la lógica de recuperación de contraseña
    RecuperarPasswordServicio recuperar = new RecuperarPasswordServicio();

    /**
     * Procesa la solicitud POST para enviar enlace de recuperación.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        Log.ficheroLog("Recibido request para recuperar contraseña con email: " + email);

        procesarSolicitud(email, response);
    }

    /**
     * Maneja el envío del enlace y redirecciones según resultado.
     * @param email Email a recuperar.
     * @param response Objeto HttpServletResponse para redirección.
     * @throws IOException
     */
    private void procesarSolicitud(String email, HttpServletResponse response) throws IOException {
        try {
            boolean enviado = recuperar.enviarEnlaceRecuperacion(email);

            if (enviado) {
                Log.ficheroLog("Enlace de recuperación enviado correctamente para email: " + email);
                response.sendRedirect("mensaje.jsp?msg=Revisa tu email para restablecer la contraseña");
            } else {
                Log.ficheroLog("No se pudo enviar el enlace de recuperación para email: " + email);
                response.sendRedirect("RestablecerPassword.jsp?error=Email no encontrado");
            }
        } catch (Exception e) {
            Log.ficheroLog("Error en RecuperarPasswordControlador: " + e.getMessage());
            response.sendRedirect("RestablecerPassword.jsp?error=Error interno, inténtalo más tarde");
        }
    }
}
