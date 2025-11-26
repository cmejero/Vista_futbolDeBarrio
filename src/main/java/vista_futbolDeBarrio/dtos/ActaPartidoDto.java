package vista_futbolDeBarrio.dtos;

import java.util.List;

/**
 * Clase que se encarga de los campos del Acta partido
 */
public class ActaPartidoDto {

	private Long idActaPartido;
	private Long torneoId;
	private Long instalacionId;
	private Long clubLocalId;
	private Long clubVisitanteId;
	private Long equipoLocalId;
	private Long equipoVisitanteId;
	private Long partidoTorneoId;
	private int golesLocal;
	private int golesVisitante;
	private int golesPenaltisLocal;
	private int golesPenaltisVisitante;
	private long clubGanadorId;
	private String fechaPartido;
	private String observaciones;
	private boolean cerrado;
	private List<EventoPartidoDto> eventos;


	public Long getIdActaPartido() {
		return idActaPartido;
	}

	public void setIdActaPartido(Long idActaPartido) {
		this.idActaPartido = idActaPartido;
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


	public Long getPartidoTorneoId() {
		return partidoTorneoId;
	}

	public void setPartidoTorneoId(Long partidoTorneoId) {
		this.partidoTorneoId = partidoTorneoId;
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
	
	

	public int getGolesPenaltisLocal() {
		return golesPenaltisLocal;
	}

	public void setGolesPenaltisLocal(int golesPenaltisLocal) {
		this.golesPenaltisLocal = golesPenaltisLocal;
	}

	public int getGolesPenaltisVisitante() {
		return golesPenaltisVisitante;
	}

	public void setGolesPenaltisVisitante(int golesPenaltisVisitante) {
		this.golesPenaltisVisitante = golesPenaltisVisitante;
	}
	
	

	public long getClubGanadorId() {
		return clubGanadorId;
	}

	public void setClubGanadorId(long clubGanadorId) {
		this.clubGanadorId = clubGanadorId;
	}

	public String getFechaPartido() {
		return fechaPartido;
	}

	public void setFechaPartido(String fechaPartido) {
		this.fechaPartido = fechaPartido;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public boolean isCerrado() {
		return cerrado;
	}

	public boolean estaCerrado() {
		return cerrado;
	}

	public void setCerrado(boolean cerrado) {
		this.cerrado = cerrado;
	}

	public List<EventoPartidoDto> getEventos() {
		return eventos;
	}

	public void setEventos(List<EventoPartidoDto> eventos) {
		this.eventos = eventos;
	}

}
