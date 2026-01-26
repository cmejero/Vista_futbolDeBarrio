package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vista_futbolDeBarrio.dtos.ClubEstadisticaTorneoDto;
import vista_futbolDeBarrio.dtos.JugadorEstadisticaTorneoDto;
import vista_futbolDeBarrio.servicios.ClubEstadisticaTorneoServicio;
import vista_futbolDeBarrio.servicios.JugadorEstadisticaTorneoServicio;
import vista_futbolDeBarrio.log.Log;

@WebServlet("/detalleTorneo")
public class DetalleTorneoControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private JugadorEstadisticaTorneoServicio jugadorServicio;
    private ClubEstadisticaTorneoServicio clubServicio;

    @Override
    public void init() throws ServletException {
        jugadorServicio = new JugadorEstadisticaTorneoServicio();
        clubServicio = new ClubEstadisticaTorneoServicio();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        // ====== CARGA DE JSP ======
        if (accion == null || accion.isEmpty()) {
            request.getRequestDispatcher("/WEB-INF/Vistas/DetallesTorneo.jsp").forward(request, response);
            return;
        }

        // ====== RESPUESTA JSON ======
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            if ("jugadores".equals(accion)) {
                List<JugadorEstadisticaTorneoDto> lista =
                        jugadorServicio.obtenerTodasJugadorEstadisticasTorneo();
                response.getWriter().write(new Gson().toJson(lista));
                return;
            }

            if ("clubes".equals(accion)) {
                List<ClubEstadisticaTorneoDto> lista =
                        clubServicio.obtenerTodasClubEstadisticasTorneo();
                response.getWriter().write(new Gson().toJson(lista));
                return;
            }

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Acción no válida\"}");

        } catch (Exception e) {
            Log.ficheroLog("Error DetalleTorneoControlador: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error interno\"}");
        }
    }
}
