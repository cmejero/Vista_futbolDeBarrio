package vista_futbolDeBarrio.controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vista_futbolDeBarrio.servicios.RecuperarCuentaServicio;
import vista_futbolDeBarrio.log.Log;

@WebServlet("/recuperarCuenta")
public class RecuperarCuentaControlador extends HttpServlet {
    private static final long serialVersionUID = 1L;

    RecuperarCuentaServicio recuperar = new RecuperarCuentaServicio();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/Vistas/RecuperarCuenta.jsp")
               .forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String tipoUsuario = request.getParameter("tipoUsuario");  // <-- NUEVO

        Log.ficheroLog("Recibido request para recuperar contraseña con email: " + email);

        procesarSolicitud(request, response, email, tipoUsuario);
    }

    private void procesarSolicitud(HttpServletRequest request, HttpServletResponse response, String email, String tipoUsuario)
            throws ServletException, IOException {

        try {
            boolean enviado = recuperar.enviarEnlaceRecuperacion(email, tipoUsuario);

            if (enviado) {
                Log.ficheroLog("Enlace de recuperación enviado correctamente para email: " + email);

                // 🔥 REDIRECCIÓN con mensaje en URL
                response.sendRedirect("recuperarCuenta?mensaje=correo_enviado");
            } else {
                Log.ficheroLog("No se pudo enviar el enlace de recuperación para email: " + email);

                response.sendRedirect("recuperarCuenta?mensaje=email_no_encontrado");
            }

        } catch (Exception e) {
            Log.ficheroLog("Error en RecuperarPasswordControlador: " + e.getMessage());

            response.sendRedirect("recuperarCuenta?mensaje=error_servidor");
        }
    }

}

