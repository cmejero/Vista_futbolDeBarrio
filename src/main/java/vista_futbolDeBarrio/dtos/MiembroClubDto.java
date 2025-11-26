package vista_futbolDeBarrio.dtos;

/**
 * Clase que se encarga de los campos de miembro club
 */
public class MiembroClubDto {

    private long idMiembroClub;
    private String fechaAltaUsuario;
    private String fechaBajaUsuario;
    private long idClub;  
    private long usuarioId; 
    private UsuarioDto usuario;
    private ClubDto club;

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

    public long getIdClub() {
        return idClub;
    }

    public void setIdClub(long idClub) {
        this.idClub = idClub;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public UsuarioDto getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDto usuario) {
        this.usuario = usuario;
    }

	public ClubDto getClub() {
		return club;
	}

	public void setClub(ClubDto club) {
		this.club = club;
	}
    
    
}
