package vista_futbolDeBarrio.controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vista_futbolDeBarrio.servicios.ClubServicio;
import vista_futbolDeBarrio.servicios.UsuarioServicio;

@WebServlet("/pagoPremium")
public class PagoPremiumControlador extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final UsuarioServicio usuarioServicio = new UsuarioServicio();
	private final ClubServicio clubServicio = new ClubServicio();

	// ==========================
	// GET → CARGA LA VISTA
	// ==========================
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null) {
			response.sendRedirect("InicioSesion.jsp");
			return;
		}

		request.getRequestDispatcher("/WEB-INF/Vistas/PagoPremium.jsp").forward(request, response);
	}

	// ==========================
	// POST → ACTIVA PREMIUM
	// ==========================
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		String tipoUsuario = request.getParameter("tipo");
		boolean actualizado = false;

		if ("jugador".equals(tipoUsuario)) {

			Long usuarioId = (Long) session.getAttribute("usuarioId");
			if (usuarioId == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			actualizado = usuarioServicio.marcarPremium(usuarioId, request);

			if (actualizado) {
				session.setAttribute("esPremium", true);
			}

		} else if ("club".equals(tipoUsuario)) {

			Long clubId = (Long) session.getAttribute("clubId");
			if (clubId == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			actualizado = clubServicio.marcarPremium(clubId, request);

			if (actualizado) {
				session.setAttribute("esPremium", true);
			}
		}

		if (actualizado) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
