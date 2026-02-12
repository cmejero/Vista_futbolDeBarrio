package vista_futbolDeBarrio.controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vista_futbolDeBarrio.servicios.RecuperarCuentaServicio;
import vista_futbolDeBarrio.log.Log;

/**
 * Controlador para la recuperación de cuentas de usuario.
 * Permite mostrar la vista de recuperación y procesar solicitudes de envío de enlace de restablecimiento.
 */
@WebServlet("/recuperarCuenta")
public class RecuperarCuentaControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final RecuperarCuentaServicio recuperar = new RecuperarCuentaServicio();

    /**
     * Muestra la vista de recuperación de cuenta.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Log.ficheroLog("RecuperarCuentaControlador: GET /recuperarCuenta → mostrar vista RecuperarCuenta.jsp");

            request.getRequestDispatcher("/WEB-INF/Vistas/RecuperarCuenta.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("RecuperarCuentaControlador - Error en doGet: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al cargar la página de recuperación de cuenta");
        }
    }

    /**
     * Procesa el envío del enlace de recuperación para un usuario dado.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String email = request.getParameter("email");
            String tipoUsuario = request.getParameter("tipoUsuario");  // Puede ser jugador, club, etc.

            Log.ficheroLog("RecuperarCuentaControlador: POST → Solicitud de recuperación para email: " + email);

            procesarSolicitud(request, response, email, tipoUsuario);

        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("RecuperarCuentaControlador - Error en doPost: " + e.getMessage());
            response.sendRedirect("recuperarCuenta?mensaje=error_servidor");
        }
    }

    /**
     * Método auxiliar que llama al servicio para enviar el enlace de recuperación
     * y redirige según el resultado.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @param email El correo del usuario que solicita recuperación.
     * @param tipoUsuario Tipo de usuario (jugador, club, etc.)
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    private void procesarSolicitud(HttpServletRequest request, HttpServletResponse response, String email, String tipoUsuario)
            throws ServletException, IOException {

        try {
            boolean enviado = recuperar.enviarEnlaceRecuperacion(email, tipoUsuario);

            if (enviado) {
                Log.ficheroLog("RecuperarCuentaControlador: Enlace enviado correctamente a " + email);
                response.sendRedirect("recuperarCuenta?mensaje=correo_enviado");
            } else {
                Log.ficheroLog("RecuperarCuentaControlador: No se encontró el email " + email);
                response.sendRedirect("recuperarCuenta?mensaje=email_no_encontrado");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("RecuperarCuentaControlador - Error en procesarSolicitud: " + e.getMessage());
            response.sendRedirect("recuperarCuenta?mensaje=error_servidor");
        }
    }
}
