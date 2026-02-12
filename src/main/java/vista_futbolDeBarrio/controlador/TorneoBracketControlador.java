package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vista_futbolDeBarrio.dtos.PartidoTorneoDto;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.EquipoTorneoServicio;
import vista_futbolDeBarrio.servicios.PartidoTorneoServicio;
import vista_futbolDeBarrio.servicios.TorneoServicio;

@WebServlet("/torneo/bracket")
/**
 * Controlador que gestiona el bracket de un torneo: listado de partidos por ronda
 * y avance de ganadores en el torneo.
 */
public class TorneoBracketControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private TorneoServicio torneoServicio;
    private PartidoTorneoServicio partidoServicio;
    private EquipoTorneoServicio equipoTorneoServicio;

    @Override
    /**
     * Inicializa los servicios de torneo y partidos de torneo.
     * 
     * @throws ServletException Si ocurre un error durante la inicialización.
     */
    public void init() throws ServletException {
        torneoServicio = new TorneoServicio();
        partidoServicio = new PartidoTorneoServicio();
    }

    @Override
    /**
     * Obtiene el bracket completo de un torneo en formato JSON.
     * Incluye todas las rondas: octavos, cuartos, semifinales, final y tercer puesto.
     * 
     * @param request La solicitud HTTP que debe incluir el parámetro "torneoId".
     * @param response La respuesta HTTP con los datos del bracket en JSON.
     * @throws ServletException Si ocurre un error durante la ejecución del servlet.
     * @throws IOException Si ocurre un error al leer o escribir datos.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String torneoIdParam = request.getParameter("torneoId");
            

            if (torneoIdParam == null || torneoIdParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"torneoId no proporcionado\"}");
                Log.ficheroLog("GET /torneo/bracket falló: torneoId no proporcionado");
                return;
            }

            Long torneoId;
            try {
                torneoId = Long.parseLong(torneoIdParam);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"torneoId inválido\"}");
                Log.ficheroLog("GET /torneo/bracket falló: torneoId inválido: " + torneoIdParam);
                return;
            }

            Log.ficheroLog("TorneoBracketControlador: Recibiendo torneoId=" + torneoId);

            Map<String, Object> resp = new LinkedHashMap<>();

            try {
                resp.put("torneo", torneoServicio.obtenerTorneo(torneoId));
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"Torneo no encontrado\"}");
                Log.ficheroLog("TorneoBracketControlador: Torneo no encontrado id=" + torneoId);
                return;
            }

            String[] rondas = {"octavos", "cuartos", "semifinal", "partidoFinal", "tercerpuesto"};
            int[] expected = {8, 4, 2, 1, 1};

            for (int r = 0; r < rondas.length; r++) {
                String ronda = rondas[r];
                int expCount = expected[r];

                List<PartidoTorneoDto> partidos = partidoServicio.obtenerPartidosPorRonda(torneoId, ronda);

                while (partidos.size() < expCount) partidos.add(new PartidoTorneoDto());

                Collections.sort(partidos, Comparator.comparingInt(p -> p.getUbicacionRonda()));

                resp.put(ronda, partidos);
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new Gson().toJson(resp));

        } catch (Exception e) {
            Log.ficheroLog("Error en GET /torneo/bracket: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error interno del servidor: " + e.getMessage() + "\"}");
        }
    }

    @Override
    /**
     * Registra el resultado de un partido y avanza al ganador en el bracket.
     * 
     * @param request La solicitud HTTP con parámetros "partidoId" y "ganadorId".
     * @param response La respuesta HTTP indicando si la operación fue exitosa.
     * @throws ServletException Si ocurre un error durante la ejecución del servlet.
     * @throws IOException Si ocurre un error al leer o escribir datos.
     */

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	  Log.ficheroLog("doPost /torneo/bracket recibido");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String partidoIdParam = request.getParameter("partidoId");
            String ganadorIdParam = request.getParameter("ganadorId");

            Log.ficheroLog("POST /torneo/bracket recibido: partidoId=" + partidoIdParam + ", ganadorId=" + ganadorIdParam);

            if (partidoIdParam == null || ganadorIdParam == null) {
                Map<String, String> error = Map.of("error", "Parámetros partidoId o ganadorId no proporcionados");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(new Gson().toJson(error));
                Log.ficheroLog("⚠️ POST /torneo/bracket falló: parámetros no proporcionados");
                return;
            }

            Long partidoId = Long.parseLong(partidoIdParam);
            Long ganadorId = Long.parseLong(ganadorIdParam);

            if (equipoTorneoServicio == null) {
                Log.ficheroLog("⚠️ equipoTorneoServicio es null, inicializando...");
                equipoTorneoServicio = new EquipoTorneoServicio();
            }

            PartidoTorneoDto partido = partidoServicio.listaPartidos().stream()
                    .filter(p -> p.getIdPartidoTorneo() != null && p.getIdPartidoTorneo().equals(partidoId))
                    .findFirst()
                    .orElse(null);

            if (partido == null) {
                Map<String, String> error = Map.of("error", "Partido no encontrado");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write(new Gson().toJson(error));
                Log.ficheroLog("⚠️ Partido no encontrado para id=" + partidoId);
                return;
            }

            Log.ficheroLog("✅ Partido encontrado, avanzando ganador...");

            // Llamada al método con logs internos
            torneoServicio.avanzarGanador(partido, ganadorId, partidoServicio, equipoTorneoServicio, torneoServicio, request);

            Map<String, String> mensaje = Map.of("mensaje", "Resultado registrado y ganador avanzado");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(new Gson().toJson(mensaje));

        } catch (Exception e) {
            Log.ficheroLog("❌ Error en POST /torneo/bracket: " + e.getMessage());
            Map<String, String> error = Map.of("error", "Error al registrar el resultado: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(new Gson().toJson(error));
        }
    }



}
