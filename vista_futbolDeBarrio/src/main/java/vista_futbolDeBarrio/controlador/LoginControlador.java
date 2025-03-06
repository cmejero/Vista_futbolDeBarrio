package vista_futbolDeBarrio.controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vista_futbolDeBarrio.dtos.LoginDto;
import vista_futbolDeBarrio.servicios.LoginServicio;

@WebServlet("/login")
@MultipartConfig
public class LoginControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private LoginServicio loginServicio;

    @Override
    public void init() throws ServletException {
        this.loginServicio = new LoginServicio();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Obtener las credenciales del formulario
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Crear el DTO con las credenciales del usuario
            LoginDto loginDto = new LoginDto();
            loginDto.setEmail(email);
            loginDto.setPassword(password);

            // Llamada al servicio para autenticar al usuario
            String token = loginServicio.autenticarUsuario(loginDto);

            if (token != null) {
                // Si el token es devuelto, login exitoso
                request.getSession().setAttribute("authToken", token);
                response.sendRedirect("/Administracion.jsp"); // Redirige al dashboard u otra página
            } else {
                // Si no hay token, login fallido
                response.getWriter().write("Credenciales incorrectas.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error en el servidor: " + e.getMessage());
        }
    }
}
