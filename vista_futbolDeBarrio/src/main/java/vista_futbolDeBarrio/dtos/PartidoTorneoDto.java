package vista_futbolDeBarrio.dtos;

/**
 * DTO para la gestión de partidos de un torneo.
 */
public class PartidoTorneoDto {

    private Long idPartidoTorneo;
    private Long torneoId;
    private Long instalacionId;
    private Long clubLocalId;
    private Long clubVisitanteId;
    private Long equipoLocalId;
    private Long equipoVisitanteId;
    private Long actaPartidoId;
    private int golesLocal = 0;
    private int golesVisitante = 0;
    private String fechaPartido;
    private String ronda;
    private String estado;

    

    public PartidoTorneoDto() {
        super();
    }

	public Long getIdPartidoTorneo() {
		return idPartidoTorneo;
	}

	public void setIdPartidoTorneo(Long idPartidoTorneo) {
		this.idPartidoTorneo = idPartidoTorneo;
	}

	public Long getTorneoId() {
		return torneoId;
	}

	public void setTorneoId(Long torneoId) {
		this.torneoId = torneoId;
	}

	public Long getInstalacionId() {
		return instalacionId;
	}

	public void setInstalacionId(Long instalacionId) {
		this.instalacionId = instalacionId;
	}

	public Long getClubLocalId() {
		return clubLocalId;
	}

	public void setClubLocalId(Long clubLocalId) {
		this.clubLocalId = clubLocalId;
	}

	public Long getClubVisitanteId() {
		return clubVisitanteId;
	}

	public void setClubVisitanteId(Long clubVisitanteId) {
		this.clubVisitanteId = clubVisitanteId;
	}

	public Long getActaPartidoId() {
		return actaPartidoId;
	}

	public void setActaPartidoId(Long actaPartidoId) {
		this.actaPartidoId = actaPartidoId;
	}

	public Long getEquipoLocalId() {
		return equipoLocalId;
	}

	public void setEquipoLocalId(Long equipoLocalId) {
		this.equipoLocalId = equipoLocalId;
	}

	public Long getEquipoVisitanteId() {
		return equipoVisitanteId;
	}

	public void setEquipoVisitanteId(Long equipoVisitanteId) {
		this.equipoVisitanteId = equipoVisitanteId;
	}

	public int getGolesLocal() {
		return golesLocal;
	}

	public void setGolesLocal(int golesLocal) {
		this.golesLocal = golesLocal;
	}

	public int getGolesVisitante() {
		return golesVisitante;
	}

	public void setGolesVisitante(int golesVisitante) {
		this.golesVisitante = golesVisitante;
	}

	public String getFechaPartido() {
		return fechaPartido;
	}

	public void setFechaPartido(String fechaPartido) {
		this.fechaPartido = fechaPartido;
	}

	public String getRonda() {
		return ronda;
	}

	public void setRonda(String ronda) {
		this.ronda = ronda;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

   
}
