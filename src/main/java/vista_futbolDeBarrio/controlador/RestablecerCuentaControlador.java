package vista_futbolDeBarrio.controlador;

import java.io.IOException;

import vista_futbolDeBarrio.log.Log;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vista_futbolDeBarrio.servicios.RestablecerPasswordServicio;

/**
 * Controlador para el restablecimiento de la contraseña.
 * Gestiona la visualización del formulario y la actualización de la clave
 * usando un token de recuperación.
 */
@WebServlet("/restablecerCuenta")
public class RestablecerCuentaControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final RestablecerPasswordServicio restablecer = new RestablecerPasswordServicio();

    /**
     * Muestra la vista de restablecimiento de contraseña.
     * Si llega el token por URL, se guarda en sesión y se redirige.
     * Si no hay token en sesión, redirige al login con error.
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
            String tokenUrl = request.getParameter("token");

            if (tokenUrl != null && !tokenUrl.isEmpty()) {
                // Guardar token en sesión y redirigir sin token en URL
                request.getSession().setAttribute("token", tokenUrl);
                Log.ficheroLog("[INFO] Token recibido por URL y almacenado en sesión");
                response.sendRedirect("restablecerCuenta");
                return;
            }

            // Si no hay token en URL, verificamos sesión
            String tokenSession = (String) request.getSession().getAttribute("token");

            if (tokenSession == null || tokenSession.isEmpty()) {
                Log.ficheroLog("[WARN] Acceso sin token válido → redirigiendo a login");
                response.sendRedirect("login?error=token_faltante");
                return;
            }

            // Token existe en sesión → mostrar JSP
            Log.ficheroLog("[INFO] Mostrar vista RestablecerCuenta.jsp con token en sesión");
            request.getRequestDispatcher("/WEB-INF/Vistas/RestablecerCuenta.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("[ERROR] Excepción en RestablecerCuentaControlador doGet: " + e.getMessage());
            response.sendRedirect("login?error=error_servidor");
        }
    }

    /**
     * Procesa el envío del formulario para restablecer la contraseña.
     * Valida token y coincidencia de passwords, luego llama al servicio de actualización.
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
            String token = (String) request.getSession().getAttribute("token");
            String password = request.getParameter("password");
            String passwordConfirm = request.getParameter("passwordConfirm");

            Log.ficheroLog("[INFO] Intento de restablecer contraseña con token: " + token);

            procesarRestablecimiento(token, password, passwordConfirm, request, response);

        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("[ERROR] Excepción en RestablecerCuentaControlador doPost: " + e.getMessage());
            response.sendRedirect("restablecerCuenta?error=error_servidor");
        }
    }

    /**
     * Lógica interna para validar token y actualizar la contraseña.
     *
     * @param token Token de recuperación almacenado en sesión.
     * @param password Nueva contraseña.
     * @param passwordConfirm Confirmación de la nueva contraseña.
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    private void procesarRestablecimiento(String token, String password, String passwordConfirm,
                                          HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        if (token == null || token.isEmpty()) {
            Log.ficheroLog("[WARN] Token inválido o ausente");
            response.sendRedirect("login?error=token_invalido");
            return;
        }

        if (!password.equals(passwordConfirm)) {
            Log.ficheroLog("[WARN] Password y confirmación no coinciden");
            response.sendRedirect("restablecerCuenta?error=Las claves no coinciden");
            return;
        }

        boolean actualizado = restablecer.actualizarPassword(token, password);

        if (actualizado) {
            request.getSession().removeAttribute("token"); 
            Log.ficheroLog("[INFO] Contraseña restablecida correctamente → redirigiendo a login");
            response.sendRedirect("login?mensaje=clave_actualizada");
        } else {
            Log.ficheroLog("[WARN] Token inválido o expirado → no se pudo actualizar contraseña");
            response.sendRedirect("restablecerCuenta?error=Token inválido o expirado");
        }
    }
}
