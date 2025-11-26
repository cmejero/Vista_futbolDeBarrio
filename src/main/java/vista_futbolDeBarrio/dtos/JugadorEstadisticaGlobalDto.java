package vista_futbolDeBarrio.dtos;


/**
 * Clase que se encarga de los campos de las estadisticas globales de un jugador
 */
public class JugadorEstadisticaGlobalDto {

	
	private Long idGlobal;
    private Long jugadorGlobalId;
    private int golesGlobal;
    private int asistenciasGlobal;
    private int amarillasGlobal;
    private int rojasGlobal;
    private int partidosJugadosGlobal;
    private int partidosGanadosGlobal;
    private int partidosPerdidosGlobal;
    private int minutosJugadosGlobal;
    private String nombreJugador;
    private String aliasJugador;
    
 
    
	public Long getIdGlobal() {
		return idGlobal;
	}
	public void setIdGlobal(Long idGlobal) {
		this.idGlobal = idGlobal;
	}
	public Long getJugadorGlobalId() {
		return jugadorGlobalId;
	}
	public void setJugadorGlobalId(Long jugadorGlobalId) {
		this.jugadorGlobalId = jugadorGlobalId;
	}
	public int getGolesGlobal() {
		return golesGlobal;
	}
	public void setGolesGlobal(int golesGlobal) {
		this.golesGlobal = golesGlobal;
	}
	public int getAsistenciasGlobal() {
		return asistenciasGlobal;
	}
	public void setAsistenciasGlobal(int asistenciasGlobal) {
		this.asistenciasGlobal = asistenciasGlobal;
	}
	public int getAmarillasGlobal() {
		return amarillasGlobal;
	}
	public void setAmarillasGlobal(int amarillasGlobal) {
		this.amarillasGlobal = amarillasGlobal;
	}
	public int getRojasGlobal() {
		return rojasGlobal;
	}
	public void setRojasGlobal(int rojasGlobal) {
		this.rojasGlobal = rojasGlobal;
	}
	public int getPartidosJugadosGlobal() {
		return partidosJugadosGlobal;
	}
	public void setPartidosJugadosGlobal(int partidosJugadosGlobal) {
		this.partidosJugadosGlobal = partidosJugadosGlobal;
	}
	
	
	
	public int getPartidosGanadosGlobal() {
		return partidosGanadosGlobal;
	}

	public void setPartidosGanadosGlobal(int partidosGanadosGlobal) {
		this.partidosGanadosGlobal = partidosGanadosGlobal;
	}

	public int getPartidosPerdidosGlobal() {
		return partidosPerdidosGlobal;
	}

	public void setPartidosPerdidosGlobal(int partidosPerdidosGlobal) {
		this.partidosPerdidosGlobal = partidosPerdidosGlobal;
	}

	public int getMinutosJugadosGlobal() {
		return minutosJugadosGlobal;
	}
	public void setMinutosJugadosGlobal(int minutosJugadosGlobal) {
		this.minutosJugadosGlobal = minutosJugadosGlobal;
	}

	
	public String getNombreJugador() {
		return nombreJugador;
	}
	public void setNombreJugador(String nombreJugador) {
		this.nombreJugador = nombreJugador;
	}
	public String getAliasJugador() {
		return aliasJugador;
	}
	public void setAliasJugador(String aliasJugador) {
		this.aliasJugador = aliasJugador;
	}
	public JugadorEstadisticaGlobalDto() {
		super();
	}
    
    
    
}
