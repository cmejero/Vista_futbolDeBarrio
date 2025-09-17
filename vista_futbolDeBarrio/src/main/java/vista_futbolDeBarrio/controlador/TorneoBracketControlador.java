package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

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
            Long torneoId = Long.parseLong(request.getParameter("torneoId"));
            Map<String, List<PartidoTorneoDto>> brackets = new LinkedHashMap<>();
            String[] rondas = {"octavos", "cuartos", "semifinal", "final"};

            for (String ronda : rondas) {
                List<PartidoTorneoDto> partidosRonda = partidoServicio.obtenerPartidosPorRonda(torneoId, ronda);

                int expected = 0;
                switch (ronda) {
                    case "octavos":
                        expected = 8;
                        break;
                    case "cuartos":
                        expected = 4;
                        break;
                    case "semifinal":
                        expected = 2;
                        break;
                    case "final":
                        expected = 1;
                        break;
                }

                while (partidosRonda.size() < expected) partidosRonda.add(new PartidoTorneoDto());
                brackets.put(ronda, partidosRonda);
            }

             

            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(mapper.writeValueAsString(brackets));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error generando brackets: " + e.getMessage());
        }
    }
    // ==========================
    // AVANZAR GANADOR DE UN PARTIDO
    // ==========================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String partidoIdParam = request.getParameter("partidoId");
            String ganadorIdParam = request.getParameter("ganadorId");

            if (partidoIdParam == null || ganadorIdParam == null ||
                partidoIdParam.isEmpty() || ganadorIdParam.isEmpty()) {

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Parámetros partidoId o ganadorId no proporcionados\"}");
                return;
            }

            Long partidoId = Long.parseLong(partidoIdParam);
            Long ganadorId = Long.parseLong(ganadorIdParam);

            // Obtener el partido actual
            PartidoTorneoDto partido = partidoServicio.listaPartidos().stream()
                    .filter(p -> p.getIdPartidoTorneo().equals(partidoId))
                    .findFirst()
                    .orElse(null);

            if (partido == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Partido no encontrado\"}");
                return;
            }

            // Avanzar ganador usando TorneoServicio
            torneoServicio.avanzarGanador(partido, ganadorId, partidoServicio);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensaje\": \"Resultado registrado correctamente\"}");

        } catch (Exception e) {
            Log.ficheroLog("Error en POST /torneo/bracket: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error al registrar el resultado\"}");
        }
    }
}
