package vista_futbolDeBarrio.dtos;

import java.time.LocalDate;

import vista_futbolDeBarrio.enums.Modalidad;

public class TorneoDto {

    private long idTorneo;
    private String nombreTorneo;
    private String fechaInicioTorneo;
    private String fechaFinTorneo;
    private String descripcionTorneo;
    private Modalidad modalidad;
    private long instalacionId;

    // Getters and Setters
    public long getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(long idTorneo) {
        this.idTorneo = idTorneo;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public void setNombreTorneo(String nombreTorneo) {
        this.nombreTorneo = nombreTorneo;
    }

    public String getFechaInicioTorneo() {
        return fechaInicioTorneo;
    }

    public void setFechaInicioTorneo(String fechaInicioTorneo) {
        this.fechaInicioTorneo = fechaInicioTorneo;
    }

    public String getFechaFinTorneo() {
        return fechaFinTorneo;
    }

    public void setFechaFinTorneo(String fechaFinTorneo) {
        this.fechaFinTorneo = fechaFinTorneo;
    }

    public String getDescripcionTorneo() {
        return descripcionTorneo;
    }

    public void setDescripcionTorneo(String descripcionTorneo) {
        this.descripcionTorneo = descripcionTorneo;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public long getInstalacionId() {
        return instalacionId;
    }

    public void setInstalacionId(long instalacionId) {
        this.instalacionId = instalacionId;
    }
}
