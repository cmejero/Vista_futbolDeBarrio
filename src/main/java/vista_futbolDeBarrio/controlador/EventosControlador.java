package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vista_futbolDeBarrio.servicios.TorneoServicio;

@WebServlet("/eventos")
public class EventosControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private TorneoServicio torneoServicio = new TorneoServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tipo = request.getParameter("tipo");

        // 1️⃣ Si no hay tipo -> mostrar JSP
        if (tipo == null) {
            request.getRequestDispatcher("/WEB-INF/Vistas/Eventos.jsp").forward(request, response);
            return;
        }

        // 2️⃣ Si viene tipo=json -> devolver torneos en JSON
        if ("json".equals(tipo)) {
            try {
                List<JSONObject> torneos = torneoServicio.obtenerTorneosConProgreso(request);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(new Gson().toJson(torneos));

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"Error al obtener torneos\"}");
            }
            return;
        }


        // 3️⃣ Si el tipo no es válido
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("{\"error\":\"tipo no válido\"}");
    }

}
