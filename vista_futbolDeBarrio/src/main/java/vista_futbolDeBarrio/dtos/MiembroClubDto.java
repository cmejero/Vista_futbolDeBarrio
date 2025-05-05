package vista_futbolDeBarrio.dtos;

import java.time.LocalDate;

public class MiembroClubDto {

    private long idMiembroClub;
    private String fechaAltaUsuario;
    private String fechaBajaUsuario;
    private long clubId;  
    private long usuarioId;  

    // Getters and Setters
    public long getIdMiembroClub() {
        return idMiembroClub;
    }

    public void setIdMiembroClub(long idMiembroClub) {
        this.idMiembroClub = idMiembroClub;
    }

    public String getFechaAltaUsuario() {
        return fechaAltaUsuario;
    }

    public void setFechaAltaUsuario(String fechaAltaUsuario) {
        this.fechaAltaUsuario = fechaAltaUsuario;
    }

    public String getFechaBajaUsuario() {
        return fechaBajaUsuario;
    }

    public void setFechaBajaUsuario(String fechaBajaUsuario) {
        this.fechaBajaUsuario = fechaBajaUsuario;
    }

    public long getClubId() {
        return clubId;
    }

    public void setClubId(long clubId) {
        this.clubId = clubId;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
