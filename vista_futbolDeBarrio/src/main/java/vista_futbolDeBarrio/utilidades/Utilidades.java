package vista_futbolDeBarrio.utilidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utilidades {
	
	public static final String nombreArchivoLog() {
		try {
			LocalDate fechaActual = LocalDate.now();
			String fechaStr = fechaActual.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
			return "log-" + fechaStr + ".txt";
		} catch(Exception e) {
			System.out.println("Se ha producido un error [1003], intentelo más tarde");
			return "log-error.txt";
		}
	}

	public static final String nombreCarpetaFecha() {
		try {
			LocalDate fechaActual = LocalDate.now();
			return fechaActual.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
		} catch(Exception e) {
			System.out.println("Se ha producido un error [1004], intentelo más tarde");
			return "errorFecha";
		}
	}
}
