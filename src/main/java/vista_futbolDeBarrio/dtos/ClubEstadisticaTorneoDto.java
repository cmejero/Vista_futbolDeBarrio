package vista_futbolDeBarrio.dtos;

/**
 * Clase que se encarga de los campos de las estadisticas de un torneo de club
 */
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
    private String nombreClub;
    private String abreviaturaClub;
    private String nombreTorneo;
    
    
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
	public String getNombreClub() {
		return nombreClub;
	}
	public void setNombreClub(String nombreClub) {
		this.nombreClub = nombreClub;
	}
	public String getAbreviaturaClub() {
		return abreviaturaClub;
	}
	public void setAbreviaturaClub(String abreviaturaClub) {
		this.abreviaturaClub = abreviaturaClub;
	}
	public String getNombreTorneo() {
		return nombreTorneo;
	}
	public void setNombreTorneo(String nombreTorneo) {
		this.nombreTorneo = nombreTorneo;
	}
	
	
}
