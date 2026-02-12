package vista_futbolDeBarrio.controlador;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.InstalacionDto;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.InstalacionServicio;

@WebServlet("/instalacion")
@MultipartConfig
/**
 * Controlador para la gestión de la instalación.
 * Permite obtener datos vía AJAX o mostrar la JSP de instalación.
 * Incluye trazabilidad mediante logs.
 */
public class InstalacionControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final InstalacionServicio servicio = new InstalacionServicio();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);
            String tipoUsuario = (session != null) ? (String) session.getAttribute("tipoUsuario") : null;
            
           

            // 🔐 Seguridad básica
            if (session == null || session.getAttribute("token") == null) {
                Log.ficheroLog("EventoJugadorControlador: intento de acceso sin sesión o token inválido");
                response.sendRedirect(request.getContextPath() + "/login?error=accesoDenegado");
                return;
            }

            // 🔐 Seguridad básica
            if (tipoUsuario == null || !(tipoUsuario.equals("instalacion") || tipoUsuario.equals("administrador"))) {
                if ("datos".equals(request.getParameter("accion"))) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("{\"error\":\"Acceso denegado\"}");
                    Log.ficheroLog("InstalacionControlador: Acceso denegado (AJAX) para tipoUsuario=" + tipoUsuario);
                } else {
                    response.sendRedirect("login?error=accesoDenegado");
                    Log.ficheroLog("InstalacionControlador: Redirigido al login por acceso denegado, tipoUsuario=" + tipoUsuario);
                }
                return;
            }

            String accion = request.getParameter("accion");

            if ("datos".equals(accion)) {
                String idParam = request.getParameter("idInstalacion");
                if (idParam == null) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\":\"ID no proporcionado\"}");
                    Log.ficheroLog("InstalacionControlador: Falta idInstalacion en petición AJAX");
                    return;
                }

                try {
                    long idInstalacion = Long.parseLong(idParam);
                    InstalacionDto instalacion = servicio.obtenerInstalacionPorId(idInstalacion, request);

                    if (instalacion != null) {
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write(mapper.writeValueAsString(instalacion));
                        Log.ficheroLog("InstalacionControlador: Datos enviados para Instalacion ID=" + idInstalacion);
                    } else {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        response.getWriter().write("{\"error\":\"Instalación no encontrada\"}");
                        Log.ficheroLog("InstalacionControlador: Instalacion no encontrada ID=" + idInstalacion);
                    }

                } catch (NumberFormatException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\":\"ID inválido\"}");
                    Log.ficheroLog("InstalacionControlador: ID inválido en petición AJAX: " + idParam);
                }
                return; 
            }

  
            request.getRequestDispatcher("/WEB-INF/Vistas/Instalacion.jsp").forward(request, response);
            Log.ficheroLog("InstalacionControlador: Cargada Instalacion.jsp para tipoUsuario=" + tipoUsuario);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error interno del servidor\"}");
            Log.ficheroLog("InstalacionControlador - Error: " + e.getMessage());
        }
    }
}
