package vista_futbolDeBarrio.dtos;

/**
 * Clase que se encarga de los campos de los eventos de un partido
 */
public class EventoPartidoDto {


    private Long idEventoPartido;
    private Long actaPartidoId;
    private Long jugadorId;
    private Long clubId;
    private Long equipoTorneoId;
    private String tipoEvento; 
    private int minuto;
    
    
    
    
    
    

	public Long getIdEventoPartido() {
		return idEventoPartido;
	}
	public void setIdEventoPartido(Long idEventoPartido) {
		this.idEventoPartido = idEventoPartido;
	}
	public Long getActaPartidoId() {
		return actaPartidoId;
	}
	public void setActaPartidoId(Long actaPartidoId) {
		this.actaPartidoId = actaPartidoId;
	}
	public Long getJugadorId() {
		return jugadorId;
	}
	public void setJugadorId(Long jugadorId) {
		this.jugadorId = jugadorId;
	}
	public Long getClubId() {
		return clubId;
	}
	public void setClubId(Long clubId) {
		this.clubId = clubId;
	}
	public Long getEquipoTorneoId() {
		return equipoTorneoId;
	}
	public void setEquipoTorneoId(Long equipoTorneoId) {
		this.equipoTorneoId = equipoTorneoId;
	}
	public String getTipoEvento() {
		return tipoEvento;
	}
	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	public int getMinuto() {
		return minuto;
	}
	public void setMinuto(int minuto) {
		this.minuto = minuto;
	}
	public EventoPartidoDto() {
		super();
	}
    
    
    
    
}
