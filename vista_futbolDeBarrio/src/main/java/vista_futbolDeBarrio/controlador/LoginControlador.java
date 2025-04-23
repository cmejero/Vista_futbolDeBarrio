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
import vista_futbolDeBarrio.log.Log;

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

            // Log de recepción de parámetros
            Log.ficheroLog("Recibido intento de login. Email: " + email );

            // Llamar al servicio de login
            RespuestaLoginDto respuestaLogin = servicioLogin.login(email, password);

            if (respuestaLogin != null && respuestaLogin.getToken() != null) {
                String token = respuestaLogin.getToken();
                String tipoUsuario = respuestaLogin.getTipoUsuario();
                Object datosUsuario = respuestaLogin.getDatosUsuario();

                // Log de éxito en login
                Log.ficheroLog("Login exitoso para el usuario: " + email + ", Tipo de usuario: " + tipoUsuario );

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
                        // Log de tipo de usuario desconocido
                        Log.ficheroLog("Tipo de usuario desconocido: " + tipoUsuario );
                        response.sendRedirect("InicioSesion.jsp?error=tipoDesconocido");
                }
            } else {
                // Log de error de credenciales
                Log.ficheroLog("Credenciales incorrectas para el email: " + email );
                response.sendRedirect("InicioSesion.jsp?error=credenciales");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Log de error en servidor
            Log.ficheroLog("Error en el proceso de login: " + e.getMessage() );
            response.sendRedirect("InicioSesion.jsp?error=servidor");
        }
    }
}
