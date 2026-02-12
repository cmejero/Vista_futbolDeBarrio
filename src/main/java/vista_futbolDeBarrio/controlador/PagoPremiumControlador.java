package vista_futbolDeBarrio.controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.ClubServicio;
import vista_futbolDeBarrio.servicios.UsuarioServicio;

/**
 * Controlador para activar la suscripción Premium de un jugador o club.
 * Permite cargar la vista de pago y actualizar el estado Premium en sesión.
 */
@WebServlet("/pagoPremium")
public class PagoPremiumControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final UsuarioServicio usuarioServicio = new UsuarioServicio();
    private final ClubServicio clubServicio = new ClubServicio();

    /**
     * Maneja solicitudes GET.
     * Carga la vista JSP de pago Premium.
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
            HttpSession session = request.getSession(false);

            if (session == null) {
                response.sendRedirect("InicioSesion.jsp");
                Log.ficheroLog("PagoPremiumControlador: Acceso denegado (sin sesión)");
                return;
            }

            request.getRequestDispatcher("/WEB-INF/Vistas/PagoPremium.jsp")
                   .forward(request, response);
            Log.ficheroLog("PagoPremiumControlador: Cargada vista PagoPremium.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Log.ficheroLog("PagoPremiumControlador - Error en doGet: " + e.getMessage());
        }
    }

    /**
     * Maneja solicitudes POST.
     * Marca al usuario o club como Premium y actualiza la sesión.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                Log.ficheroLog("PagoPremiumControlador: POST denegado (sin sesión)");
                return;
            }

            String tipoUsuario = request.getParameter("tipo");
            boolean actualizado = false;

            if ("jugador".equals(tipoUsuario)) {
                Long usuarioId = (Long) session.getAttribute("usuarioId");
                if (usuarioId == null) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    Log.ficheroLog("PagoPremiumControlador: usuarioId nulo para tipo 'jugador'");
                    return;
                }

                actualizado = usuarioServicio.marcarPremium(usuarioId, request);

                if (actualizado) {
                    session.setAttribute("esPremium", true);
                    Log.ficheroLog("PagoPremiumControlador: Jugador marcado como Premium, usuarioId=" + usuarioId);
                }

            } else if ("club".equals(tipoUsuario)) {
                Long clubId = (Long) session.getAttribute("clubId");
                if (clubId == null) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    Log.ficheroLog("PagoPremiumControlador: clubId nulo para tipo 'club'");
                    return;
                }

                actualizado = clubServicio.marcarPremium(clubId, request);

                if (actualizado) {
                    session.setAttribute("esPremium", true);
                    Log.ficheroLog("PagoPremiumControlador: Club marcado como Premium, clubId=" + clubId);
                }
            }

            if (actualizado) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                Log.ficheroLog("PagoPremiumControlador: No se pudo actualizar estado Premium para tipoUsuario=" + tipoUsuario);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Log.ficheroLog("PagoPremiumControlador - Error en doPost: " + e.getMessage());
        }
    }
}
