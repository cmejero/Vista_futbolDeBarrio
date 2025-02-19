package vista_futbolDeBarrio.log;

import java.io.FileWriter;


public class log {

			  
	    public void ficheroErrores(String mensaje) {
	        try {
	            // Crear un FileWriter en modo append (añadir al final del archivo)
	            FileWriter fw = new FileWriter("C:\\Users\\CMR\\Desktop\\ficheroLog.txt", true);
	            
	            // Escribir el mensaje en el archivo
	            fw.write(mensaje + "\n"); // Añadir salto de línea al final del mensaje
	            
	            // Cerrar el FileWriter
	            fw.close();
	        } catch (Exception e) {
	            // Manejo de errores en caso de fallo al escribir en el archivo
	            System.out.println("Se ha producido un error al escribir en el log: " + e.getMessage());
	        }
	    }
	
}
