package vista_futbolDeBarrio.dtos;

public class ClubEstadisticaTorneoDto {

	private Long idEstadisticaTorneo;
    private Long clubId;
    private Long torneoId;
    private int partidosJugados;
    private int ganados;
    private int empatados;
    private int perdidos;
    private int golesFavor;
    private int golesContra;
    
    
	public Long getIdEstadisticaTorneo() {
		return idEstadisticaTorneo;
	}
	public void setIdEstadisticaTorneo(Long idEstadisticaTorneo) {
		this.idEstadisticaTorneo = idEstadisticaTorneo;
	}
	public Long getClubId() {
		return clubId;
	}
	public void setClubId(Long clubId) {
		this.clubId = clubId;
	}
	public Long getTorneoId() {
		return torneoId;
	}
	public void setTorneoId(Long torneoId) {
		this.torneoId = torneoId;
	}
	public int getPartidosJugados() {
		return partidosJugados;
	}
	public void setPartidosJugados(int partidosJugados) {
		this.partidosJugados = partidosJugados;
	}
	public int getGanados() {
		return ganados;
	}
	public void setGanados(int ganados) {
		this.ganados = ganados;
	}
	public int getEmpatados() {
		return empatados;
	}
	public void setEmpatados(int empatados) {
		this.empatados = empatados;
	}
	public int getPerdidos() {
		return perdidos;
	}
	public void setPerdidos(int perdidos) {
		this.perdidos = perdidos;
	}
	public int getGolesFavor() {
		return golesFavor;
	}
	public void setGolesFavor(int golesFavor) {
		this.golesFavor = golesFavor;
	}
	public int getGolesContra() {
		return golesContra;
	}
	public void setGolesContra(int golesContra) {
		this.golesContra = golesContra;
	}
}
