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
import vista_futbolDeBarrio.servicios.InstalacionServicio;

@WebServlet("/instalacion")
@MultipartConfig
public class InstalacionControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final InstalacionServicio servicio = new InstalacionServicio();
    private final ObjectMapper mapper = new ObjectMapper();

 
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            HttpSession session = request.getSession(false);
            String tipoUsuario = (session != null) ? (String) session.getAttribute("tipoUsuario") : null;

            if (tipoUsuario == null || !(tipoUsuario.equals("instalacion") || tipoUsuario.equals("administrador"))) {
                // Si es fetch JSON: enviamos status 403
                if ("datos".equals(request.getParameter("accion"))) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("{\"error\":\"Acceso denegado\"}");
                } else {
                    // Si es navegador directo: redirigimos al login
                    response.sendRedirect("login?error=accesoDenegado");
                }
                return;
            }

            String accion = request.getParameter("accion");
            if ("datos".equals(accion)) {
                String idParam = request.getParameter("idInstalacion");
                if (idParam == null) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\":\"ID no proporcionado\"}");
                    return;
                }

                try {
                    long idInstalacion = Long.parseLong(idParam);
                    InstalacionDto instalacion = servicio.obtenerInstalacionPorId(idInstalacion, request);

                    if (instalacion != null) {
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write(mapper.writeValueAsString(instalacion));
                    } else {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        response.getWriter().write("{\"error\":\"Instalación no encontrada\"}");
                    }

                } catch (NumberFormatException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\":\"ID inválido\"}");
                }
                return; // Muy importante: no hacer forward al JSP
            }

            // =========================
            // Solo mostrar JSP
            // =========================
            request.getRequestDispatcher("/WEB-INF/Vistas/Instalacion.jsp").forward(request, response);
        }
    }


