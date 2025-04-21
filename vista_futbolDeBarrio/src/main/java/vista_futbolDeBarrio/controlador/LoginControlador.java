package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.RespuestaLoginDto;
import vista_futbolDeBarrio.servicios.LoginServicio;

@WebServlet("/login")
@MultipartConfig
public class LoginControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private LoginServicio servicioLogin = new LoginServicio();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener parámetros del formulario
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            System.out.println("Recibido el email: " + email + " y la contraseña: " + password);

            // Llamar al servicio de login
            RespuestaLoginDto respuestaLogin = servicioLogin.login(email, password);

            if (respuestaLogin != null && respuestaLogin.getToken() != null) {
                String token = respuestaLogin.getToken();
                String tipoUsuario = respuestaLogin.getTipoUsuario();
                Object datosUsuario = respuestaLogin.getDatosUsuario();

                System.out.println("Token recibido: " + token);
                System.out.println("Tipo de usuario recibido: " + tipoUsuario);

                // Guardar en sesión
                HttpSession session = request.getSession();
                session.setAttribute("token", token);
                session.setAttribute("tipoUsuario", tipoUsuario);
                session.setAttribute("datosUsuario", datosUsuario); // único atributo para todos los tipos

                // Redirigir según tipo de usuario
                switch (tipoUsuario) {
                    case "administrador":
                        response.sendRedirect("Administrador.jsp");
                        break;
                    case "jugador":
                        response.sendRedirect("Jugador.jsp");
                        break;
                    case "club":
                        response.sendRedirect("Club.jsp");
                        break;
                    case "instalacion":
                        response.sendRedirect("Instalacion.jsp");
                        break;
                    default:
                        System.out.println("Tipo de usuario desconocido: " + tipoUsuario);
                        response.sendRedirect("InicioSesion.jsp?error=tipoDesconocido");
                }
            } else {
                response.sendRedirect("InicioSesion.jsp?error=credenciales");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("InicioSesion.jsp?error=servidor");
        }
    }
}
