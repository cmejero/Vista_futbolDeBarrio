package vista_futbolDeBarrio.controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.ClubDto;
import vista_futbolDeBarrio.dtos.InstalacionDto;
import vista_futbolDeBarrio.dtos.RespuestaLoginDto;
import vista_futbolDeBarrio.dtos.UsuarioDto;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.LoginServicio;

@WebServlet("/login")
@MultipartConfig
/**
 * Clase que se encarga de el inicio de sesion 
 */
public class LoginControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private LoginServicio servicioLogin = new LoginServicio();

    @Override
    /**
     * Metodo POST que se encarga de enviar los datos necesarios para iniciar sesion
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            
            Log.ficheroLog("Recibido intento de login. Email: " + email );

            
            RespuestaLoginDto respuestaLogin = servicioLogin.login(email, password);

            if (respuestaLogin != null && respuestaLogin.getToken() != null) {
                String token = respuestaLogin.getToken();
                String tipoUsuario = respuestaLogin.getTipoUsuario();
                Object datosUsuario = respuestaLogin.getDatosUsuario();

                
                Log.ficheroLog("Login exitoso para el usuario: " + email + ", Tipo de usuario: " + tipoUsuario );

                HttpSession session = request.getSession();
                session.setAttribute("token", token);
                session.setAttribute("tipoUsuario", tipoUsuario);
                session.setAttribute("datosUsuario", datosUsuario); 

                
                if ("instalacion".equals(tipoUsuario)) {
                    if (datosUsuario instanceof InstalacionDto) {
                        InstalacionDto instalacion = (InstalacionDto) datosUsuario;
                        session.setAttribute("instalacionId", instalacion.getIdInstalacion()); 
                    }
                }
                
                if ("club".equals(tipoUsuario)) {
                    if (datosUsuario instanceof ClubDto) {
                        ClubDto club = (ClubDto) datosUsuario;
                        session.setAttribute("clubId", club.getIdClub()); 
                    }
                }

                if ("jugador".equals(tipoUsuario)) {
                    if (datosUsuario instanceof UsuarioDto) {
                        UsuarioDto club = (UsuarioDto) datosUsuario;
                        session.setAttribute("usuarioId", club.getIdUsuario()); 
                    }
                }
                
                
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
                        
                        Log.ficheroLog("Tipo de usuario desconocido: " + tipoUsuario );
                        response.sendRedirect("InicioSesion.jsp?error=tipoDesconocido");
                }
            } else {
                
                Log.ficheroLog("Credenciales incorrectas para el email: " + email );
                response.sendRedirect("InicioSesion.jsp?error=credenciales");
            }
        } catch (Exception e) {
            e.printStackTrace();
           
            Log.ficheroLog("Error en el proceso de login: " + e.getMessage() );
            response.sendRedirect("InicioSesion.jsp?error=servidor");
        }
    }
}
