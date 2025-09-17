package vista_futbolDeBarrio.dtos;

import vista_futbolDeBarrio.enums.EstadoParticipacion;
/**
 * Clase que se encarga de los campos de equipo torneo
 */
public class EquipoTorneoDto {

    private long idEquipoTorneo;
    private String fechaInicioParticipacion;
    private String fechaFinParticipacion;
    private EstadoParticipacion estadoParticipacion;
    private long torneoId;  
    private long clubId;    

    // Getters and Setters
    public long getIdEquipoTorneo() {
        return idEquipoTorneo;
    }

    public void setIdEquipoTorneo(long idEquipoTorneo) {
        this.idEquipoTorneo = idEquipoTorneo;
    }

    public String getFechaInicioParticipacion() {
        return fechaInicioParticipacion;
    }

    public void setFechaInicioParticipacion(String fechaInicioParticipacion) {
        this.fechaInicioParticipacion = fechaInicioParticipacion;
    }

    public String getFechaFinParticipacion() {
        return fechaFinParticipacion;
    }

    public void setFechaFinParticipacion(String fechaFinParticipacion) {
        this.fechaFinParticipacion = fechaFinParticipacion;
    }

    public EstadoParticipacion getEstadoParticipacion() {
        return estadoParticipacion;
    }

    public void setEstadoParticipacion(EstadoParticipacion estadoParticipacion) {
        this.estadoParticipacion = estadoParticipacion;
    }

    public long getTorneoId() {
        return torneoId;
    }

    public void setTorneoId(long torneoId) {
        this.torneoId = torneoId;
    }

    public long getClubId() {
        return clubId;
    }

    public void setClubId(long clubId) {
        this.clubId = clubId;
    }
}
