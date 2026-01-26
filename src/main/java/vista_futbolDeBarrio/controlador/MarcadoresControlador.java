package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vista_futbolDeBarrio.servicios.ClubEstadisticaGlobalServicio;
import vista_futbolDeBarrio.servicios.ClubEstadisticaTorneoServicio;
import vista_futbolDeBarrio.servicios.JugadorEstadisticaGlobalServicio;

@WebServlet("/marcadores")
public class MarcadoresControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ClubEstadisticaGlobalServicio clubGlobalServicio = new ClubEstadisticaGlobalServicio();
    private ClubEstadisticaTorneoServicio clubTorneoServicio = new ClubEstadisticaTorneoServicio();
    private JugadorEstadisticaGlobalServicio jugadorGlobalServicio = new JugadorEstadisticaGlobalServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tipo = request.getParameter("tipo");
        String idParam = request.getParameter("id");

        // Si no viene tipo, mostramos la vista
        if (tipo == null) {
            request.getRequestDispatcher("/WEB-INF/Vistas/MarcadoresClub.jsp").forward(request, response);
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();

        if ("jugadoresGlobal".equals(tipo)) {
            response.getWriter().write(
                mapper.writeValueAsString(jugadorGlobalServicio.obtenerTodosJugadorEstadisticasGlobal())
            );
            return;

        } else if ("clubesGlobal".equals(tipo)) {
            response.getWriter().write(
                mapper.writeValueAsString(clubGlobalServicio.obtenerTodasClubEstadisticasGlobal())
            );
            return;

        } else if ("clubGlobal".equals(tipo)) {
            Long clubId = Long.parseLong(idParam);
            response.getWriter().write(
                mapper.writeValueAsString(clubGlobalServicio.obtenerClubEstadisticasGlobal(clubId))
            );
            return;

        } else if ("clubTorneo".equals(tipo)) {
            Long clubId = Long.parseLong(idParam);
            response.getWriter().write(
                mapper.writeValueAsString(clubTorneoServicio.obtenerClubEstadisticasTorneo(clubId))
            );
            return;

        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"tipo no válido\"}");
        }
    }
}


