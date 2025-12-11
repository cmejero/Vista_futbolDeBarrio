package vista_futbolDeBarrio.dtos;

import vista_futbolDeBarrio.enums.Modalidad;

/**
 * Clase que se encarga de los campos de torneo
 */
public class TorneoDto {

    private long idTorneo;
    private String nombreTorneo;
    private String fechaInicioTorneo;
    private String fechaFinTorneo;
    private String descripcionTorneo;
    private String clubesInscritos;
    private Modalidad modalidad;
    private boolean estaActivo = false;
    private long instalacionId;
    private String direccionInstalacion;
	private String nombreInstalacion;
	



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
    
    public String getClubesInscritos() {
		return clubesInscritos;
	}

	public void setClubesInscritos(String clubesInscritos) {
		this.clubesInscritos = clubesInscritos;
	}

	public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }
    

    public boolean isEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(boolean estaActivo) {
		this.estaActivo = estaActivo;
	}

	public long getInstalacionId() {
        return instalacionId;
    }

    public void setInstalacionId(long instalacionId) {
        this.instalacionId = instalacionId;
    }

	public String getDireccionInstalacion() {
		return direccionInstalacion;
	}

	public void setDireccionInstalacion(String direccionInstalacion) {
		this.direccionInstalacion = direccionInstalacion;
	}

	public String getNombreInstalacion() {
		return nombreInstalacion;
	}

	public void setNombreInstalacion(String nombreInstalacion) {
		this.nombreInstalacion = nombreInstalacion;
	}
    
}
