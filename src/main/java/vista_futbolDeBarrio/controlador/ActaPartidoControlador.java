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
import vista_futbolDeBarrio.log.Log;

@WebServlet("/instalacion/actaPartido")
/**
 * Clase controlador que gestiona la creación, modificación, listado y eliminación
 * de actas de partidos, con seguimiento mediante logs.
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
        try {
            this.actaServicio = new ActaPartidoServicio();
        } catch (Exception e) {
            throw new ServletException(e);
        }
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
                Log.ficheroLog("POST /instalacion/actaPartido falló: usuario no logueado");
                return;
            }

            String tipoUsuario = (String) session.getAttribute("tipoUsuario");
            if (!"instalacion".equals(tipoUsuario)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Usuario no autorizado");
                Log.ficheroLog("POST /instalacion/actaPartido falló: usuario no autorizado");
                return;
            }

            // Leer JSON del body
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = request.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                Log.ficheroLog("POST /instalacion/actaPartido falló al leer body: " + e.getMessage());
                throw e;
            }

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> data = mapper.readValue(sb.toString(), Map.class);

            if (data.containsKey("partidoIdSeleccionado") && !data.containsKey("goles")) {
                // Solo guardar partidoId en sesión
                try {
                    Long partidoId = Long.valueOf(data.get("partidoIdSeleccionado").toString());
                    session.setAttribute("partidoIdSeleccionado", partidoId);
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("Partido guardado en sesión");
                    Log.ficheroLog("POST /instalacion/actaPartido: partidoId guardado en sesión id=" + partidoId);
                    return;
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de partido inválido");
                    Log.ficheroLog("POST /instalacion/actaPartido falló: partidoId inválido");
                    return;
                }
            }

            // Si hay datos de acta, seguimos con el flujo normal
            try {
                ActaPartidoDto acta = mapper.readValue(sb.toString(), ActaPartidoDto.class);
                Long idGuardado = actaServicio.guardarActaPartido(acta, request);

                if (idGuardado != null) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("Acta guardada con ID: " + idGuardado);
                    Log.ficheroLog("POST /instalacion/actaPartido: acta guardada correctamente, id=" + idGuardado);
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo guardar el acta");
                    Log.ficheroLog("POST /instalacion/actaPartido falló: no se pudo guardar acta");
                }
            } catch (Exception e) {
                Log.ficheroLog("POST /instalacion/actaPartido error al guardar acta: " + e.getMessage());
                throw e;
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar acta");
            Log.ficheroLog("POST /instalacion/actaPartido error general: " + e.getMessage());
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
                Log.ficheroLog("GET /instalacion/actaPartido falló: usuario no logueado");
                return;
            }
            if (session == null || session.getAttribute("token") == null) {
                Log.ficheroLog("EventoJugadorControlador: intento de acceso sin sesión o token inválido");
                response.sendRedirect(request.getContextPath() + "/login?error=accesoDenegado");
                return;
            }

            // Leer ID del partido desde GET o sesión
            String partidoIdParam = request.getParameter("partidoIdTorneo");
            Long partidoId = null;

            if (partidoIdParam != null) {
                try {
                    partidoId = Long.parseLong(partidoIdParam);
                    session.setAttribute("partidoIdSeleccionado", partidoId);
                    Log.ficheroLog("GET /instalacion/actaPartido: partidoId recibido por parámetro id=" + partidoId);
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de partido inválido");
                    Log.ficheroLog("GET /instalacion/actaPartido falló: ID de partido inválido");
                    return;
                }
            } else {
                partidoId = (Long) session.getAttribute("partidoIdSeleccionado");
                if (partidoId != null) {
                    Log.ficheroLog("GET /instalacion/actaPartido: partidoId obtenido de sesión id=" + partidoId);
                }
            }

            if (partidoId == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No se seleccionó ningún partido");
                Log.ficheroLog("GET /instalacion/actaPartido falló: ningún partido seleccionado");
                return;
            }

            PartidoTorneoServicio partidoTorneoServicio = new PartidoTorneoServicio();
            PartidoTorneoDto partido = partidoTorneoServicio.obtenerPartidoPorId(partidoId);

            if (partido == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Partido no encontrado");
                Log.ficheroLog("GET /instalacion/actaPartido falló: partido no encontrado id=" + partidoId);
                return;
            }

            request.setAttribute("partido", partido);
            request.getRequestDispatcher("/WEB-INF/Vistas/Acta.jsp").forward(request, response);
            Log.ficheroLog("GET /instalacion/actaPartido: vista de acta cargada correctamente para partidoId=" + partidoId);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error del servidor");
            Log.ficheroLog("GET /instalacion/actaPartido error general: " + e.getMessage());
        }
    }
}
