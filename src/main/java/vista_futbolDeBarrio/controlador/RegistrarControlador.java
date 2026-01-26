package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.UsuarioServicio;
import vista_futbolDeBarrio.servicios.ClubServicio;
import vista_futbolDeBarrio.servicios.InstalacionServicio;

@WebServlet("/registrar")
@MultipartConfig
public class RegistrarControlador extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private UsuarioServicio usuarioServicio = new UsuarioServicio();
	private ClubServicio clubServicio = new ClubServicio();
	private InstalacionServicio instalacionServicio = new InstalacionServicio();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Log.ficheroLog("[INFO] GET /registrar → mostrar vista Registrar.jsp");

		request.getRequestDispatcher("/WEB-INF/Vistas/Registrar.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String accion = request.getParameter("accion");
		String tipoRegistro = request.getParameter("tipoUsuario");

		Log.ficheroLog("[INFO] POST /registrar → accion=" + accion + ", tipoRegistro=" + tipoRegistro);

		try {
			// 🔍 Validación inicial
			if (!"aniadir".equals(accion) || tipoRegistro == null) {

				Log.ficheroLog(
						"[WARN] Acción inválida en /registrar → accion=" + accion + ", tipoRegistro=" + tipoRegistro);

				response.sendRedirect("registrar?error=accion_invalida");
				return;
			}

			String resultado;

			// ▶️ Llamada a servicio
			switch (tipoRegistro) {
			case "jugador":
				Log.ficheroLog("[INFO] Registro de JUGADOR iniciado");
				resultado = usuarioServicio.crearUsuarioDesdeFormulario(request);
				break;

			case "club":
				Log.ficheroLog("[INFO] Registro de CLUB iniciado");
				resultado = clubServicio.crearClubDesdeFormulario(request);
				break;

			case "instalacion":
				Log.ficheroLog("[INFO] Registro de INSTALACIÓN iniciado");
				resultado = instalacionServicio.crearInstalacionDesdeFormulario(request);
				break;

			default:
				Log.ficheroLog("[WARN] Tipo de registro desconocido: " + tipoRegistro);
				response.sendRedirect("registrar?error=tipoDesconocido");
				return;
			}

			Log.ficheroLog("[INFO] Resultado registro (" + tipoRegistro + "): " + resultado);

			// 🧭 Navegación centralizada
			switch (resultado) {
			case "ok":
				Log.ficheroLog("[INFO] Registro correcto → redirect a /login");
				response.sendRedirect("login?mensajeAlta=registro_exitoso");
				break;

			case "usuario_existente":
				Log.ficheroLog("[WARN] Usuario / Club / Instalación ya existente");
				response.sendRedirect("registrar?mensajeAlta=usuario_existente");
				break;

			case "email_invalido":
				Log.ficheroLog("[WARN] Email inválido en registro");
				response.sendRedirect("registrar?mensajeAlta=email_invalido");
				break;

			case "password_no_coincide":
				Log.ficheroLog("[WARN] Password no coincide");
				response.sendRedirect("registrar?mensajeAlta=password_no_coincide");
				break;

			default:
				Log.ficheroLog("[ERROR] Resultado desconocido: " + resultado);
				response.sendRedirect("registrar?mensajeAlta=error_servidor");
				break;
			}

		} catch (Exception e) {

			// 🧨 Stacktrace completo al log
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));

			Log.ficheroLog("[ERROR] Excepción en RegistrarControlador POST\n" + sw.toString());

			response.sendRedirect("registrar?error=error_servidor");
		}
	}
}
