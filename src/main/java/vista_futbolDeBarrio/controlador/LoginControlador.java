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
     * Método POST que maneja el inicio de sesión de un usuario.
     *
     * @param request  Objeto HttpServletRequest que contiene los parámetros del formulario.
     * @param response Objeto HttpServletResponse para enviar la respuesta.
     * @throws ServletException En caso de error en la solicitud.
     * @throws IOException      En caso de error en la respuesta.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        	String tipoUsuario = request.getParameter("tipoUsuario");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            Log.ficheroLog("Recibido intento de login. Email: " + email);

            RespuestaLoginDto respuestaLogin = servicioLogin.login(email, password, tipoUsuario);


            if (respuestaLogin != null && respuestaLogin.getToken() != null) {
                procesarLoginExitoso(request, response, email, respuestaLogin);
            } else {
                Log.ficheroLog("Credenciales incorrectas para el email: " + email);
                response.sendRedirect("InicioSesion.jsp?error=credenciales");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.ficheroLog("Error en el proceso de login: " + e.getMessage());
            response.sendRedirect("InicioSesion.jsp?error=servidor");
        }
    }

    /**
     * Procesa el inicio de sesión exitoso, guardando los datos en sesión y redirigiendo según el tipo de usuario.
     *
     * @param request         La solicitud HTTP.
     * @param response        La respuesta HTTP.
     * @param email           El correo electrónico del usuario.
     * @param respuestaLogin  Objeto que contiene el token, tipo de usuario y datos del usuario.
     * @throws IOException Si ocurre un error al redirigir.
     */
    private void procesarLoginExitoso(HttpServletRequest request, HttpServletResponse response, String email, RespuestaLoginDto respuestaLogin) throws IOException {
        String token = respuestaLogin.getToken();
        String tipoUsuario = respuestaLogin.getTipoUsuario();
        Object datosUsuario = respuestaLogin.getDatosUsuario();

        Log.ficheroLog("Login exitoso para el usuario: " + email + ", Tipo de usuario: " + tipoUsuario);

        HttpSession session = request.getSession();
        session.setAttribute("token", token);
        session.setAttribute("tipoUsuario", tipoUsuario);
        session.setAttribute("datosUsuario", datosUsuario);

        asignarIdUsuarioASesion(session, tipoUsuario, datosUsuario);
        redirigirPorTipoUsuario(response, tipoUsuario);
    }

    /**
     * Asigna a la sesión el ID correspondiente del usuario según su tipo.
     *
     * @param session      La sesión HTTP del usuario.
     * @param tipoUsuario  El tipo de usuario (jugador, club, instalacion).
     * @param datosUsuario El objeto con los datos del usuario.
     */
    private void asignarIdUsuarioASesion(HttpSession session, String tipoUsuario, Object datosUsuario) {
        switch (tipoUsuario) {
            case "instalacion":
                if (datosUsuario instanceof InstalacionDto) {
                    session.setAttribute("instalacionId", ((InstalacionDto) datosUsuario).getIdInstalacion());
                }
                break;
            case "club":
                if (datosUsuario instanceof ClubDto) {
                    session.setAttribute("clubId", ((ClubDto) datosUsuario).getIdClub());
                    session.setAttribute("esPremium", false); 
                    session.setAttribute("nombreClub", ((ClubDto) datosUsuario).getNombreClub()); 
                    session.setAttribute("esPremium", ((ClubDto) datosUsuario).isEsPremium()); 
                }
                break;
            case "jugador":
                if (datosUsuario instanceof UsuarioDto) {
                    UsuarioDto usuario = (UsuarioDto) datosUsuario;
                    session.setAttribute("usuarioId", usuario.getIdUsuario());
                    session.setAttribute("esPremium", usuario.isEsPremium()); 
                    session.setAttribute("nombreUsuario", usuario.getNombreCompletoUsuario()); 
                }
                break;
            default:
                session.setAttribute("esPremium", false);
                break;
        }
    }


    /**
     * Redirige al usuario a la página correspondiente según su tipo.
     *
     * @param response     La respuesta HTTP.
     * @param tipoUsuario  El tipo de usuario autenticado.
     * @throws IOException Si ocurre un error al redirigir.
     */
    private void redirigirPorTipoUsuario(HttpServletResponse response, String tipoUsuario) throws IOException {
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
                Log.ficheroLog("Tipo de usuario desconocido: " + tipoUsuario);
                response.sendRedirect("InicioSesion.jsp?error=tipoDesconocido");
        }
    }


}
