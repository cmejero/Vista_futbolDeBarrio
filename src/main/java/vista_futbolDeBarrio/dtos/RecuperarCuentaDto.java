package vista_futbolDeBarrio.dtos;

/**
 * Clase encargada de los campos de Recuperar Contraseña
 */
public class RecuperarCuentaDto {

    private String email;
    private String tipoUsuario;


    // Getter y setter

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
    
    

}
