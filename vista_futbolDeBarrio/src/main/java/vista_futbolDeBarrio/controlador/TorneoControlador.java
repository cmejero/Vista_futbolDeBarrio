package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vista_futbolDeBarrio.dtos.TorneoDto;
import vista_futbolDeBarrio.enums.Modalidad;
import vista_futbolDeBarrio.log.log;
import vista_futbolDeBarrio.servicios.TorneoServicio;

@WebServlet("/torneo")
@MultipartConfig
public class TorneoControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private log log = new log();
    private TorneoServicio servicio;

    @Override
    public void init() throws ServletException {
        this.servicio = new TorneoServicio();
        this.log = new log();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String accion = request.getParameter("accion");

            if ("aniadir".equals(accion)) {
                // Obtener los datos del formulario
                String nombreTorneo = request.getParameter("nombreTorneo");
                String fechaInicioTorneoStr = request.getParameter("fechaInicioTorneo");
                String fechaFinTorneoStr = request.getParameter("fechaFinTorneo");
                String descripcionTorneo = request.getParameter("descripcionTorneo");
                String modalidadStr = request.getParameter("modalidad");
                String instalacionIdStr = request.getParameter("instalacionId");

                // Parsear las fechas
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fechaInicioTorneo = LocalDate.parse(fechaInicioTorneoStr, formatter);
                LocalDate fechaFinTorneo = LocalDate.parse(fechaFinTorneoStr, formatter);

                // Parsear la modalidad
                Modalidad modalidad = Modalidad.valueOf(modalidadStr.trim());

                // Parsear el ID de la instalación
                long instalacionId = Long.parseLong(instalacionIdStr);

                // Crear el objeto TorneoDto
                TorneoDto nuevoTorneo = new TorneoDto();
                nuevoTorneo.setNombreTorneo(nombreTorneo);
                nuevoTorneo.setFechaInicioTorneo(fechaInicioTorneo);
                nuevoTorneo.setFechaFinTorneo(fechaFinTorneo);
                nuevoTorneo.setDescripcionTorneo(descripcionTorneo);
                nuevoTorneo.setModalidad(modalidad);
                nuevoTorneo.setInstalacionId(instalacionId);

                // Guardar el torneo en el servicio
                servicio.guardarTorneo(nuevoTorneo);

                response.getWriter().write("Torneo creado correctamente.");
            } else if ("modificar".equals(accion)) {
                // Lógica para modificar el torneo
            } else {
                response.getWriter().write("Acción no válida.");
                log.ficheroErrores("Acción no válida recibida: " + accion);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Se ha producido un error en el servidor. Por favor, inténtelo más tarde." + e.getMessage());
            log.ficheroErrores("Error en el procesamiento de la acción: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<TorneoDto> listaTorneos = servicio.listaTorneos();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Convertir la lista a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(listaTorneos);

        // Escribir la respuesta JSON
        response.getWriter().write(json);
    }
}
