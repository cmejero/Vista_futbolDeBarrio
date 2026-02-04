package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.TorneoDto;
import vista_futbolDeBarrio.servicios.TorneoServicio;

@WebServlet("/instalacion/eventos")
public class EventoInstalacionControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private TorneoServicio torneoServicio;

    @Override
    public void init() throws ServletException {
        torneoServicio = new TorneoServicio();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("idInstalacion") == null) {
            response.sendRedirect("InicioSesion.jsp?error=accesoDenegado");
            return;
        }

        // 🔑 Detectar AJAX
        String ajaxHeader = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(ajaxHeader)) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            try {
                Long idInstalacion = (Long) session.getAttribute("idInstalacion");
                List<TorneoDto> torneos = torneoServicio.obtenerTorneosPorInstalacion(idInstalacion);
                
                
                for (TorneoDto torneo : torneos) {
                    String progreso = torneoServicio.progresoEquipos(torneo.getIdTorneo());
                    torneo.setClubesInscritos(progreso);
                }

                // Convertir a JSON usando Gson
                String json = new Gson().toJson(torneos);
                response.getWriter().write(json);

            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"Error al cargar torneos\"}");
            }

            return; // muy importante, para que no haga forward después
        }

        // Si NO es AJAX, se sirve la JSP normalmente
        request.getRequestDispatcher("/WEB-INF/Vistas/EventoInstalacion.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("token") == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\":\"No hay sesión activa o token inválido\"}");
            return;
        }

        try {
            String accion = request.getParameter("accion");

            if ("crear".equalsIgnoreCase(accion)) {
                String resultado = torneoServicio.crearTorneoDesdeFormulario(request);

                if ("ok".equals(resultado)) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("{\"mensaje\":\"Torneo creado correctamente\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\":\"Error creando torneo: " + resultado + "\"}");
                }

            } else if ("modificar".equalsIgnoreCase(accion)) {
                boolean exito = torneoServicio.modificarTorneoDesdeFormulario(request);
                if (exito) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("{\"mensaje\":\"Torneo modificado correctamente\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\":\"No se pudo modificar el torneo\"}");
                }

            } else if ("activar".equalsIgnoreCase(accion)) {
                String resultado = torneoServicio.activarTorneoDesdeServicio(request);
                response.getWriter().write("{\"resultado\":\"" + resultado + "\"}");

            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Acción no válida\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error en el servidor: " + e.getMessage() + "\"}");
        }
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            boolean eliminado = torneoServicio.eliminarTorneoDesdeServicio(request);
            if (eliminado) {
                response.getWriter().write("{\"mensaje\":\"Torneo eliminado correctamente\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"No se pudo eliminar el torneo\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error en el servidor: " + e.getMessage() + "\"}");
        }
    }
}

  
