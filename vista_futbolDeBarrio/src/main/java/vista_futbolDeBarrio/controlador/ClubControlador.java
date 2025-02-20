package vista_futbolDeBarrio.controlador;

import java.io.IOException;
import java.io.InputStream;
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
import jakarta.servlet.http.Part;
import vista_futbolDeBarrio.dtos.ClubDto;
import vista_futbolDeBarrio.log.log;
import vista_futbolDeBarrio.servicios.ClubServicio;

@WebServlet("/club")
@MultipartConfig
public class ClubControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private log log = new log();
    private ClubServicio servicio;

    @Override
    public void init() throws ServletException {
        this.servicio = new ClubServicio();
        this.log = new log();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String accion = request.getParameter("accion"); // Acción para determinar si es crear o modificar
            System.out.println("Acción recibida desde el formulario: " + request.getParameter("accion"));

            if ("aniadir".equals(accion)) {
                // Obtener los datos del formulario
                String nombreClubForm = request.getParameter("nombreClub");
                String abreviaturaClubForm = request.getParameter("abreviaturaClub");
                String descripcionClubForm = request.getParameter("descripcionClub");
                String fechaCreacionClubForm = request.getParameter("fechaCreacionClub");
                String fechaFundacionClubForm = request.getParameter("fechaFundacionClub");
                String localidadClubForm = request.getParameter("localidadClub");
                String paisClubForm = request.getParameter("paisClub");
                String emailClubForm = request.getParameter("emailClub");
                String passwordClubForm = request.getParameter("passwordClub");
                String telefonoClubForm = request.getParameter("telefonoClub");

                // Procesar la imagen
                Part imagenPart = request.getPart("logoClub");
                byte[] logoClubForm = null;
                if (imagenPart != null && imagenPart.getSize() > 0) {
                    logoClubForm = new byte[(int) imagenPart.getSize()];
                    InputStream inputStream = imagenPart.getInputStream();
                    inputStream.read(logoClubForm);
                }

                // Crear el objeto ClubDto
                ClubDto nuevoClub = new ClubDto();
                nuevoClub.setNombreClub(nombreClubForm);
                nuevoClub.setAbreviaturaClub(abreviaturaClubForm);
                nuevoClub.setDescripcionClub(descripcionClubForm);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"); // Ajusta el formato según el de tu fecha
                if (fechaCreacionClubForm != null && !fechaCreacionClubForm.isEmpty()) {
                    try {
                        LocalDate fechaCreacion = LocalDate.parse(fechaCreacionClubForm, formatter);
                        nuevoClub.setFechaCreacionClub(fechaCreacion);
                    } catch (Exception e) {
                        response.getWriter().write("Error al convertir la fecha de creación.");
                        log.ficheroErrores("Error al convertir la fecha de creación: " + e.getMessage());
                        return; 
                    }
                }
                nuevoClub.setFechaFundacionClub(fechaFundacionClubForm);
                nuevoClub.setLocalidadClub(localidadClubForm);
                nuevoClub.setPaisClub(paisClubForm);
                nuevoClub.setEmailClub(emailClubForm);
                nuevoClub.setPasswordClub(passwordClubForm);
                nuevoClub.setTelefonoClub(telefonoClubForm);
                
                // Asignar el logo solo si se recibió la imagen
                if (logoClubForm != null) {
                    nuevoClub.setLogoClub(new String(logoClubForm));
                } else {
                    nuevoClub.setLogoClub(""); // O asigna null, según tu lógica
                }
                
                // Guardar el club en el servicio
                servicio.guardarClub(nuevoClub);
                response.getWriter().write("Club creado correctamente.");
                
            } else if ("modificar".equals(accion)) {
                // Lógica para modificar el club
            } else {
                response.getWriter().write("Acción no válida.");
                log.ficheroErrores("Acción no válida recibida: " + accion);
            }
            
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la traza del error en el servidor o consola
            response.getWriter().write("Se ha producido un error en el servidor. Por favor, inténtelo más tarde." + e.getMessage());
            log.ficheroErrores("Error en el procesamiento de la acción: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<ClubDto> listaClub = servicio.listaClub();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Convertir la lista a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(listaClub);

        // Escribir la respuesta JSON
        response.getWriter().write(json);
    }
}

