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
import vista_futbolDeBarrio.servicios.PartidoTorneoServicio;
import vista_futbolDeBarrio.servicios.TorneoServicio;

@WebServlet("/torneo/bracket")
public class TorneoBracketControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private TorneoServicio torneoServicio;
    private PartidoTorneoServicio partidoServicio;

    @Override
    public void init() throws ServletException {
        torneoServicio = new TorneoServicio();
        partidoServicio = new PartidoTorneoServicio();
    }

    // ==========================
    // LISTAR PARTIDOS DE UN TORNEO (Bracket)
    // ==========================
    @Override
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

            // Obtener datos del torneo
            try {
                resp.put("torneo", torneoServicio.obtenerTorneo(torneoId));
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"Torneo no encontrado\"}");
                Log.ficheroLog("TorneoBracketControlador: Torneo no encontrado id=" + torneoId);
                return;
            }

            // Rondas y cantidad esperada de partidos
            String[] rondas = {"octavos", "cuartos", "semifinal", "partidoFinal", "tercerpuesto"};
            int[] expected = {8, 4, 2, 1, 1};

            for (int r = 0; r < rondas.length; r++) {
                String ronda = rondas[r];
                int expCount = expected[r];

                List<PartidoTorneoDto> partidos = partidoServicio.obtenerPartidosPorRonda(torneoId, ronda);

                // Completar con placeholders si faltan partidos
                while (partidos.size() < expCount) partidos.add(new PartidoTorneoDto());

                // Ordenar por ubicacionRonda para que el HTML muestre correctamente
                Collections.sort(partidos, Comparator.comparingInt(p -> p.getUbicacionRonda()));

                resp.put(ronda, partidos);
            }

            // Devolver JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new Gson().toJson(resp));

        } catch (Exception e) {
            Log.ficheroLog("Error en GET /torneo/bracket: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error interno del servidor: " + e.getMessage() + "\"}");
        }
    }

    // ==========================
    // REGISTRAR RESULTADO / AVANZAR GANADOR
    // ==========================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String partidoIdParam = request.getParameter("partidoId");
            String ganadorIdParam = request.getParameter("ganadorId");

            if (partidoIdParam == null || partidoIdParam.isEmpty() ||
                ganadorIdParam == null || ganadorIdParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Parámetros partidoId o ganadorId no proporcionados\"}");
                return;
            }

            Long partidoId, ganadorId;
            try {
                partidoId = Long.parseLong(partidoIdParam);
                ganadorId = Long.parseLong(ganadorIdParam);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"partidoId o ganadorId inválidos\"}");
                return;
            }

            PartidoTorneoDto partido = partidoServicio.listaPartidos().stream()
                    .filter(p -> p.getIdPartidoTorneo() != null && p.getIdPartidoTorneo().equals(partidoId))
                    .findFirst()
                    .orElse(null);

            if (partido == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Partido no encontrado\"}");
                return;
            }

            // Avanzar ganador
            torneoServicio.avanzarGanador(partido, ganadorId, partidoServicio);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensaje\": \"Resultado registrado correctamente\"}");

        } catch (Exception e) {
            Log.ficheroLog("Error en POST /torneo/bracket: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error al registrar el resultado: " + e.getMessage() + "\"}");
        }
    }
}
