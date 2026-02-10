package vista_futbolDeBarrio.controlador;

import java.io.IOException;

import vista_futbolDeBarrio.log.Log;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vista_futbolDeBarrio.servicios.RestablecerPasswordServicio;

@WebServlet("/restablecerCuenta")
public class RestablecerCuentaControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    RestablecerPasswordServicio restablecer = new RestablecerPasswordServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tokenUrl = request.getParameter("token");

        if (tokenUrl != null && !tokenUrl.isEmpty()) {
            // Guardar token en sesión y redirigir sin token en URL
            request.getSession().setAttribute("token", tokenUrl);
            response.sendRedirect("restablecerCuenta");
            return;
        }

        // Si no hay token en URL, verificamos sesión
        String tokenSession = (String) request.getSession().getAttribute("token");

        if (tokenSession == null || tokenSession.isEmpty()) {
            // No existe token -> redirigir al controlador login
            response.sendRedirect("login?error=token_faltante");
            return;
        }

        // Token existe en sesión -> mostrar JSP
        request.getRequestDispatcher("/WEB-INF/Vistas/RestablecerCuenta.jsp")
               .forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = (String) request.getSession().getAttribute("token");

        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");

        Log.ficheroLog("Intento de restablecer contraseña con token: " + token);

        procesarRestablecimiento(token, password, passwordConfirm, request, response);
    }

    private void procesarRestablecimiento(String token, String password, String passwordConfirm,
                                          HttpServletRequest request, HttpServletResponse response)
            throws IOException {
    	if (token == null || token.isEmpty()) {
    	    response.sendRedirect("login?error=token_invalido");
    	    return;
    	}

        if (!password.equals(passwordConfirm)) {
            response.sendRedirect("restablecerCuenta?error=Las claves no coinciden");
            return;
        }

        boolean actualizado = restablecer.actualizarPassword(token, password);

        if (actualizado) {
            request.getSession().removeAttribute("token"); 
            response.sendRedirect("login?mensaje=clave_actualizada");

    

        } else {
            response.sendRedirect("restablecerCuenta?error=Token inválido o expirado");
        }
    }
}
