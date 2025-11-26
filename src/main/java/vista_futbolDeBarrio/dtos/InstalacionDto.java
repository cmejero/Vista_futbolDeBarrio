package vista_futbolDeBarrio.dtos;

import java.util.ArrayList;
import java.util.List;

import vista_futbolDeBarrio.enums.Estado;
import vista_futbolDeBarrio.enums.Modalidad;

/**
 * Clase que se encarga de los campos de instalacion
 */
public class InstalacionDto {

    private long idInstalacion;
    private String nombreInstalacion;
    private String direccionInstalacion;
    private String telefonoInstalacion;
    private String emailInstalacion;
    private Modalidad tipoCampo1;
    private Modalidad tipoCampo2;
    private Modalidad tipoCampo3;
    private String serviciosInstalacion;
    private Estado estadoInstalacion;
    private String passwordInstalacion;
    private byte[] imagenInstalacion;
	private List<Long> torneoIds = new ArrayList<>(); 

    // Getters and Setters
    public long getIdInstalacion() {
        return idInstalacion;
    }

    public void setIdInstalacion(long idInstalacion) {
        this.idInstalacion = idInstalacion;
    }

    public String getNombreInstalacion() {
        return nombreInstalacion;
    }

    public void setNombreInstalacion(String nombreInstalacion) {
        this.nombreInstalacion = nombreInstalacion;
    }

    public String getDireccionInstalacion() {
        return direccionInstalacion;
    }

    public void setDireccionInstalacion(String direccionInstalacion) {
        this.direccionInstalacion = direccionInstalacion;
    }

    public String getTelefonoInstalacion() {
        return telefonoInstalacion;
    }

    public void setTelefonoInstalacion(String telefonoInstalacion) {
        this.telefonoInstalacion = telefonoInstalacion;
    }

    public String getEmailInstalacion() {
        return emailInstalacion;
    }

    public void setEmailInstalacion(String emailInstalacion) {
        this.emailInstalacion = emailInstalacion;
    }

    public Modalidad getTipoCampo1() {
        return tipoCampo1;
    }

    public void setTipoCampo1(Modalidad tipoCampo1) {
        this.tipoCampo1 = tipoCampo1;
    }

    public Modalidad getTipoCampo2() {
        return tipoCampo2;
    }

    public void setTipoCampo2(Modalidad tipoCampo2) {
        this.tipoCampo2 = tipoCampo2;
    }

    public Modalidad getTipoCampo3() {
        return tipoCampo3;
    }

    public void setTipoCampo3(Modalidad tipoCampo3) {
        this.tipoCampo3 = tipoCampo3;
    }

    public String getServiciosInstalacion() {
        return serviciosInstalacion;
    }

    public void setServiciosInstalacion(String serviciosInstalacion) {
        this.serviciosInstalacion = serviciosInstalacion;
    }


    public Estado getEstadoInstalacion() {
        return estadoInstalacion;
    }

    public void setEstadoInstalacion(Estado estadoInstalacion) {
        this.estadoInstalacion = estadoInstalacion;
    }

    public String getPasswordInstalacion() {
        return passwordInstalacion;
    }

    public void setPasswordInstalacion(String passwordInstalacion) {
        this.passwordInstalacion = passwordInstalacion;
    }

  

	public byte[] getImagenInstalacion() {
		return imagenInstalacion;
	}

	public void setImagenInstalacion(byte[] imagenInstalacion) {
		this.imagenInstalacion = imagenInstalacion;
	}

	public List<Long> getTorneoIds() {
		return torneoIds;
	}

	public void setTorneoIds(List<Long> torneoIds) {
		this.torneoIds = torneoIds;
	}

   
}


