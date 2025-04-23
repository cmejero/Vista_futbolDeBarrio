package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vista_futbolDeBarrio.dtos.EquipoTorneoDto;
import vista_futbolDeBarrio.enums.EstadoParticipacion;
import vista_futbolDeBarrio.log.Log;
import vista_futbolDeBarrio.servicios.EquipoTorneoServicio;

@WebServlet("/equipoTorneo")
public class EquipoTorneoControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private EquipoTorneoServicio servicio;

    @Override
    public void init() throws ServletException {
        this.servicio = new EquipoTorneoServicio();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String accion = request.getParameter("accion");

            Log.ficheroLog("Acción recibida desde el formulario: " + accion );

            if ("aniadir".equals(accion)) {
                // Obtener los datos del formulario
                long torneoId = Long.parseLong(request.getParameter("torneoId"));
                long clubId = Long.parseLong(request.getParameter("clubId"));
                Date fechaInicioParticipacion = new Date(); // Asignar la fecha actual
                EstadoParticipacion estadoParticipacion = EstadoParticipacion.Activo; // Estado por defecto

                // Crear el objeto EquipoTorneoDto
                EquipoTorneoDto nuevoEquipoTorneo = new EquipoTorneoDto();
                nuevoEquipoTorneo.setTorneoId(torneoId);
                nuevoEquipoTorneo.setClubId(clubId);
                nuevoEquipoTorneo.setFechaInicioParticipacion(fechaInicioParticipacion);
                nuevoEquipoTorneo.setEstadoParticipacion(estadoParticipacion);

                // Guardar el equipo en el servicio
                servicio.guardarEquipoTorneo(nuevoEquipoTorneo);
                Log.ficheroLog("Equipo-Torneo creado correctamente. Torneo ID: " + torneoId + ", Club ID: " + clubId );
                response.getWriter().write("Equipo-Torneo creado correctamente.");
            } else if ("modificar".equals(accion)) {
                // Lógica para modificar el equipo-torneo
                // Puedes implementar este bloque según tus necesidades.
                Log.ficheroLog("Modificar equipo-torneo. Acción no implementada aún." );
            } else {
                response.getWriter().write("Acción no válida.");
                Log.ficheroLog("Acción no válida recibida: " + accion);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Se ha producido un error en el servidor. Por favor, inténtelo más tarde." + e.getMessage());
            Log.ficheroLog("Error en el procesamiento de la acción: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener la lista de equipos-torneo a través del servicio
            ArrayList<EquipoTorneoDto> listaEquiposTorneo = servicio.listaEquiposTorneo();

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Convertir la lista a JSON y escribir la respuesta
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(listaEquiposTorneo);
            Log.ficheroLog("Lista de equipos-torneo solicitada. Número de equipos: " + listaEquiposTorneo.size());
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Se ha producido un error al obtener los equipos-torneo. " + e.getMessage());
            Log.ficheroLog("Error al obtener lista de equipos-torneo: " + e.getMessage() );
        }
    }
}
