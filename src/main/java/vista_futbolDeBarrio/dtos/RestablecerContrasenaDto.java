package vista_futbolDeBarrio.dtos;

/**
 * Clase encargada de los campos de Restabelcer Contraseña
 */
public class RestablecerContrasenaDto {

   
    private String token;
 
    private String nuevaContrasena;
    private String repetirContrasena;

    // Getters y setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNuevaContrasena() {
        return nuevaContrasena;
    }

    public void setNuevaContrasena(String nuevaContrasena) {
        this.nuevaContrasena = nuevaContrasena;
    }

    public String getRepetirContrasena() {
        return repetirContrasena;
    }

    public void setRepetirContrasena(String repetirContrasena) {
        this.repetirContrasena = repetirContrasena;
    }

}
