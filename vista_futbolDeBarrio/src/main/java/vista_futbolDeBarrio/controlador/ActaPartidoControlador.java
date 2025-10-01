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
/**
 * Clase controlador que gestiona la creación, modificación, listado y eliminación
 * de actas de partidos.
 */
public class ActaPartidoControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ActaPartidoServicio actaServicio;

    @Override
    /**
     * Inicializa el servicio de actas de partido.
     * 
     * @throws ServletException Si ocurre un error durante la inicialización.
     */
    public void init() throws ServletException {
        this.actaServicio = new ActaPartidoServicio();
    }

    @Override
    /**
     * Maneja la creación o modificación de un acta de partido.
     * 
     * @param request La solicitud HTTP que contiene los datos del acta en JSON.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error durante la ejecución del servlet.
     * @throws IOException Si ocurre un error al leer o escribir datos.
     */
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

            ObjectMapper mapper = new ObjectMapper();
            ActaPartidoDto acta = mapper.readValue(request.getInputStream(), ActaPartidoDto.class);

            if (acta.getIdActaPartido() == null) {
                actaServicio.guardarActaPartido(acta);
            } else {
                actaServicio.modificarActaPartido(acta.getIdActaPartido(), acta);
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
    /**
     * Recupera la lista de todas las actas de partidos en formato JSON.
     * 
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP con la lista en JSON.
     * @throws ServletException Si ocurre un error durante la ejecución del servlet.
     * @throws IOException Si ocurre un error al leer o escribir datos.
     */
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
    /**
     * Elimina un acta de partido por su ID.
     * 
     * @param request La solicitud HTTP que contiene el ID del acta a eliminar.
     * @param response La respuesta HTTP con el resultado de la eliminación.
     * @throws ServletException Si ocurre un error durante la ejecución del servlet.
     * @throws IOException Si ocurre un error al leer o escribir datos.
     */
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
