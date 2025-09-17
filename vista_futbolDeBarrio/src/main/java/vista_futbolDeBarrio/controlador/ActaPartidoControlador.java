package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.ActaPartidoDto;
import vista_futbolDeBarrio.servicios.ActaPartidoServicio;
import vista_futbolDeBarrio.log.Log;

@WebServlet("/actaPartido")
public class ActaPartidoControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ActaPartidoServicio actaServicio;

    @Override
    public void init() throws ServletException {
        this.actaServicio = new ActaPartidoServicio();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            String tipoUsuario = (session != null) ? (String) session.getAttribute("tipoUsuario") : null;

            if (tipoUsuario == null || tipoUsuario.isEmpty()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Debe iniciar sesión.");
                Log.ficheroLog("Intento de acceso no autorizado a POST /actaPartido");
                return;
            }

            // Leer JSON completo de la request (acta + eventos)
            ObjectMapper mapper = new ObjectMapper();
            ActaPartidoDto acta = mapper.readValue(request.getInputStream(), ActaPartidoDto.class);

            // Llamada al servicio: guardar o modificar acta y actualizar estadísticas
            if (acta.getIdActaPartido() == null) {
                actaServicio.guardarActaPartido(acta);
            } else {
                actaServicio.modificarActaPartido(acta.getIdActaPartido(),acta);
            }

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Operación realizada correctamente.");

        } catch (Exception e) {
            Log.ficheroLog("Error en doPost /actaPartido: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error en el servidor. Inténtelo más tarde.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ArrayList<ActaPartidoDto> listaActas = actaServicio.listaActaPartido();
            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(listaActas));
        } catch (Exception e) {
            Log.ficheroLog("Error en GET /actaPartido: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error en el servidor: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idParam = request.getParameter("idActaPartido");
            if (idParam == null || idParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("ID de acta no proporcionado.");
                Log.ficheroLog("Eliminación fallida: ID no proporcionado");
                return;
            }

            Long idActa = Long.parseLong(idParam);

            boolean eliminado = actaServicio.eliminarActaPartido(idActa);

            if (eliminado) {
                Log.ficheroLog("Acta eliminada con eventos, id=" + idActa);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Acta eliminada correctamente.");
            } else {
                Log.ficheroLog("Error al eliminar acta, id=" + idActa);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Error al eliminar el acta.");
            }

        } catch (Exception e) {
            Log.ficheroLog("Error en DELETE /actaPartido: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error en el servidor: " + e.getMessage());
        }
    }
}
