package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vista_futbolDeBarrio.dtos.MiembroClubDto;
import vista_futbolDeBarrio.log.log;
import vista_futbolDeBarrio.servicios.MiembroClubServicio;

@WebServlet("/miembroClub")
public class MiembroClubControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private log log = new log();
    private MiembroClubServicio servicio;

    @Override
    public void init() throws ServletException {
        this.servicio = new MiembroClubServicio();
        this.log = new log();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String accion = request.getParameter("accion");

            if ("aniadir".equals(accion)) {
                // Obtener los datos del formulario
                String fechaAltaUsuarioForm = request.getParameter("fechaAltaUsuario");
                String fechaBajaUsuarioForm = request.getParameter("fechaBajaUsuario");
                long clubIdForm = Long.parseLong(request.getParameter("clubId"));
                long usuarioIdForm = Long.parseLong(request.getParameter("usuarioId"));

                // Crear el objeto MiembroClubDto
                MiembroClubDto nuevoMiembro = new MiembroClubDto();
                nuevoMiembro.setFechaAltaUsuario(LocalDate.parse(fechaAltaUsuarioForm));
                if (fechaBajaUsuarioForm != null && !fechaBajaUsuarioForm.isEmpty()) {
                    nuevoMiembro.setFechaBajaUsuario(LocalDate.parse(fechaBajaUsuarioForm));
                }
                nuevoMiembro.setClubId(clubIdForm);
                nuevoMiembro.setUsuarioId(usuarioIdForm);

                // Guardar el miembro en el servicio
                servicio.guardarMiembroClub(nuevoMiembro);

                response.getWriter().write("Miembro del club creado correctamente.");
            } else if ("modificar".equals(accion)) {
                // Lógica para modificar el miembro del club
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
        ArrayList<MiembroClubDto> listaMiembros = servicio.listarMiembrosClub();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Convertir la lista a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(listaMiembros);

        // Escribir la respuesta JSON
        response.getWriter().write(json);
    }
}
