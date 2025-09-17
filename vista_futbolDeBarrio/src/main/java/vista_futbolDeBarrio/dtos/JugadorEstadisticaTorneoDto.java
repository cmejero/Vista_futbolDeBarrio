package vista_futbolDeBarrio.dtos;

/**
 * Clase que se encarga de los campos de Jugador estadisitica torneo
 */
public class JugadorEstadisticaTorneoDto {

	   private Long idJugadorEstadisticaTorneo;
	    private Long jugadorId;
	    private Long torneoId;
	    private int golesTorneo;
	    private int asistenciasTorneo;
	    private int amarillasTorneo;
	    private int rojasTorneo;
	    private int partidosJugadosTorneo;
	    private int minutosJugadosTorneo;
	    
	   

	    
		public Long getIdJugadorEstadisticaTorneo() {
			return idJugadorEstadisticaTorneo;
		}
		public void setIdJugadorEstadisticaTorneo(Long id) {
			this.idJugadorEstadisticaTorneo = id;
		}
		public Long getJugadorId() {
			return jugadorId;
		}
		public void setJugadorId(Long jugadorId) {
			this.jugadorId = jugadorId;
		}
		public Long getTorneoId() {
			return torneoId;
		}
		public void setTorneoId(Long torneoId) {
			this.torneoId = torneoId;
		}
		public int getGolesTorneo() {
			return golesTorneo;
		}
		public void setGolesTorneo(int golesTorneo) {
			this.golesTorneo = golesTorneo;
		}
		public int getAsistenciasTorneo() {
			return asistenciasTorneo;
		}
		public void setAsistenciasTorneo(int asistenciasTorneo) {
			this.asistenciasTorneo = asistenciasTorneo;
		}
		public int getAmarillasTorneo() {
			return amarillasTorneo;
		}
		public void setAmarillasTorneo(int amarillasTorneo) {
			this.amarillasTorneo = amarillasTorneo;
		}
		public int getRojasTorneo() {
			return rojasTorneo;
		}
		public void setRojasTorneo(int rojasTorneo) {
			this.rojasTorneo = rojasTorneo;
		}
		public int getPartidosJugadosTorneo() {
			return partidosJugadosTorneo;
		}
		public void setPartidosJugadosTorneo(int partidosJugadosTorneo) {
			this.partidosJugadosTorneo = partidosJugadosTorneo;
		}
		public int getMinutosJugadosTorneo() {
			return minutosJugadosTorneo;
		}
		public void setMinutosJugadosTorneo(int minutosJugadosTorneo) {
			this.minutosJugadosTorneo = minutosJugadosTorneo;
		}


		public JugadorEstadisticaTorneoDto() {
			super();
		}
	    
	    
}
