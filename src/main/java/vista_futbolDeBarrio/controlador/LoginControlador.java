package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vista_futbolDeBarrio.dtos.LoginGoogleDto;
import vista_futbolDeBarrio.dtos.RespuestaLoginDto;
import vista_futbolDeBarrio.dtos.UsuarioDto;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.InicioSesionGoogleServicio;
import vista_futbolDeBarrio.servicios.LoginServicio;

@WebServlet("/login")
@MultipartConfig
public class LoginControlador extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final LoginServicio servicioLogin = new LoginServicio();
	private final InicioSesionGoogleServicio servicioGoogle = new InicioSesionGoogleServicio();

	@Override
	/**
	 * Maneja GET en la página de login.
	 *
	 * - Intenta login automático si existe cookie de token persistente. - Si token
	 * válido, reconstruye sesión y redirige. - Si token inválido o ausente, muestra
	 * la página de login con mensaje opcional.
	 *
	 * @param request  Solicitud HTTP con cookies y parámetros.
	 * @param response Respuesta HTTP para forward a JSP o redirección.
	 * @throws ServletException Error de servlet al forward.
	 * @throws IOException      Error de E/S durante forward o redirección.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 🔹 Intento de login automático desde cookie
		Cookie[] cookies = request.getCookies();
		String tokenCookie = null;
		if (cookies != null) {
			for (Cookie c : cookies) {
				if ("tokenUsuario".equals(c.getName()))
					tokenCookie = c.getValue();
			}
		}

		if (tokenCookie != null) {
			Map<String, Object> datos = servicioLogin.validarTokenPersistente(tokenCookie);
			if (datos != null) {
				// Reconstruir sesión y redirigir automáticamente
				servicioLogin.manejarSesion(request, response, datos.get("datosUsuario"), (String) datos.get("jwt"),
						(String) datos.get("tipoUsuario"));
				return;
			} else {
				// Token inválido o expirado
				servicioLogin.borrarCookies(response, request.getContextPath());
			}
		}

		// Mostrar página de login
		String mensaje = request.getParameter("mensaje");
		if (mensaje != null)
			request.setAttribute("mensaje", mensaje);
		request.getRequestDispatcher("/WEB-INF/Vistas/InicioSesion.jsp").forward(request, response);
	}

	@Override
	/**
	 * Maneja POST en la página de login.
	 *
	 * - Soporta login con Google usando código OAuth. - Soporta login normal con
	 * email y contraseña. - Permite recordar sesión mediante token persistente y
	 * cookies. - Reconstruye sesión y redirige según tipo de usuario. - Redirige a
	 * login con mensaje de error si falla.
	 *
	 * @param request  Solicitud HTTP con parámetros de login.
	 * @param response Respuesta HTTP para redirección o forward a JSP.
	 * @throws ServletException Error de servlet al forward.
	 * @throws IOException      Error de E/S durante redirección o forward.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String codeGoogle = request.getParameter("code");
			String tipoUsuario = request.getParameter("tipoUsuario");

			// 🔹 LOGIN CON GOOGLE
			if (codeGoogle != null && !codeGoogle.isEmpty()) {

				LoginGoogleDto loginDto = servicioGoogle.loginConGoogle(codeGoogle, tipoUsuario,
						request.getServletContext(), request);
				if (loginDto != null) {
					Log.ficheroLog("Login exitoso con Google: " + loginDto.getEmail());

					Object datosUsuario = servicioLogin.construirDtoSegunTipo(loginDto);

					// Crear sesión y redirigir según tipo de usuario
					servicioLogin.manejarSesion(request, response, datosUsuario, loginDto.getToken(),
							loginDto.getTipoUsuario());
					return;

				} else {
					// Redirigir al servlet login con error
					response.sendRedirect("login?error=googleAPI");
					return;
				}
			}

			// 🔹 LOGIN NORMAL
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			boolean recordarSesion = "on".equals(request.getParameter("recordarSesion"));

			RespuestaLoginDto respuestaLogin = servicioLogin.login(email, password, tipoUsuario);

			if (respuestaLogin != null && respuestaLogin.getToken() != null) {

				// Manejar token persistente si se desea "recordar sesión"
				if (recordarSesion) {
					String tokenPersistente = servicioLogin.generarTokenPersistente(respuestaLogin.getDatosUsuario(),
							respuestaLogin.getTipoUsuario(), respuestaLogin.getToken());

					if (tokenPersistente != null) {
						servicioLogin.agregarTokenYCookies(response, tokenPersistente, respuestaLogin.getTipoUsuario(),
								request.getContextPath());
					}
				} else {
					servicioLogin.borrarCookies(response, request.getContextPath());
				}

				// Crear sesión y redirigir según tipo de usuario
				servicioLogin.manejarSesion(request, response, respuestaLogin.getDatosUsuario(),
						respuestaLogin.getToken(), respuestaLogin.getTipoUsuario());
				return;

			} else {
				// Credenciales incorrectas → redirigir al servlet login con parámetro error
				response.sendRedirect("login?error=credenciales");
				return;
			}

		} catch (Exception e) {
			Log.ficheroLog("Error en login POST: " + e.getMessage());
			// Error de servidor → redirigir al servlet login con parámetro error
			response.sendRedirect("login?error=servidor");
		}
	}

}
