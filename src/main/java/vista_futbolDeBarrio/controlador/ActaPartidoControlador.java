package vista_futbolDeBarrio.controlador;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vista_futbolDeBarrio.dtos.ActaPartidoDto;
import vista_futbolDeBarrio.dtos.PartidoTorneoDto;
import vista_futbolDeBarrio.servicios.ActaPartidoServicio;
import vista_futbolDeBarrio.servicios.PartidoTorneoServicio;

@WebServlet("/instalacion/actaPartido")
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
            if (session == null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Debe iniciar sesión.");
                return;
            }

            String tipoUsuario = (String) session.getAttribute("tipoUsuario");
            if (!"instalacion".equals(tipoUsuario)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Usuario no autorizado");
                return;
            }

            // Leer JSON del body
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = request.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }

            ObjectMapper mapper = new ObjectMapper();
            // Usamos un Map para distinguir si es solo partidoId o acta
            Map<String, Object> data = mapper.readValue(sb.toString(), Map.class);

            if (data.containsKey("partidoIdSeleccionado") && !data.containsKey("goles") ) {
                // Solo guardar partidoId en sesión
                Long partidoId = Long.valueOf(data.get("partidoIdSeleccionado").toString());
                session.setAttribute("partidoIdSeleccionado", partidoId);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Partido guardado en sesión");
                return;
            }

            // Si hay datos de acta, seguimos con el flujo normal
            ActaPartidoDto acta = mapper.readValue(sb.toString(), ActaPartidoDto.class);

            ActaPartidoServicio servicio = new ActaPartidoServicio();
            Long idGuardado = servicio.guardarActaPartido(acta, request);

            if (idGuardado != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Acta guardada con ID: " + idGuardado);
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo guardar el acta");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar acta");
        }
    }


    @Override
    /**
     * Recupera la vista del acta de un partido.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @throws ServletException Si ocurre un error durante la ejecución del servlet.
     * @throws IOException Si ocurre un error al leer o escribir datos.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Debe iniciar sesión.");
                return;
            }

            // Leer ID del partido desde GET o sesión
            String partidoIdParam = request.getParameter("partidoIdTorneo");
            Long partidoId = null;

            if (partidoIdParam != null) {
                partidoId = Long.parseLong(partidoIdParam);
                // Guardar en sesión para futuras referencias
                session.setAttribute("partidoIdSeleccionado", partidoId);
            } else {
                partidoId = (Long) session.getAttribute("partidoIdSeleccionado");
            }

            if (partidoId == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No se seleccionó ningún partido");
                return;
            }

            PartidoTorneoServicio partidoTorneoServicio = new PartidoTorneoServicio();
            PartidoTorneoDto partido = partidoTorneoServicio.obtenerPartidoPorId(partidoId);

            if (partido == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Partido no encontrado");
                return;
            }

            request.setAttribute("partido", partido);
            request.getRequestDispatcher("/WEB-INF/Vistas/Acta.jsp")
                   .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de partido inválido");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error del servidor");
        }
    }
}
