package vista_futbolDeBarrio.utilidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que se encarga de os metodos que se usaran varias veces en la aplicacion
 */
public class Utilidades {
	
	   /**
     * Obtiene el nombre del archivo de log con la fecha actual en formato "ddMMyyyy".
     * 
     * @return Nombre del archivo de log.
     */
	public static final String nombreArchivoLog() {
		try {
			LocalDate fechaActual = LocalDate.now();
			String fechaStr = fechaActual.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
			return "log-" + fechaStr + ".txt";
		} catch(Exception e) {
			// System.out.println("Se ha producido un error [1003], intentelo más tarde");
			return "log-error.txt";
		}
	}


    /**
     * Obtiene el nombre de la carpeta basada en la fecha actual en formato "ddMMyyyy".
     * 
     * @return Nombre de la carpeta con la fecha actual.
     */
	public static final String nombreCarpetaFecha() {
		try {
			LocalDate fechaActual = LocalDate.now();
			return fechaActual.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
		} catch(Exception e) {
			// System.out.println("Se ha producido un error [1004], intentelo más tarde");
			return "errorFecha";
		}
	}
	
    /**
     * Cache en memoria para guardar el flujo de partidos de cada torneo.
     * 
     * Estructura:
     * Key: id del torneo
     * Value: Map<idPartidoActual, idSiguientePartido>
     * 
     * Esto permite saber a qué partido debe avanzar el ganador de cada partido.
     */
    private static final Map<Long, Map<Long, Long>> cache = new HashMap<>();

    /**
     * Guarda un mapa de flujo de partidos en la cache para un torneo específico.
     * 
     * @param torneoId ID del torneo
     * @param mapa Mapa de idPartidoActual -> idSiguientePartido
     */
    public static void guardarMapa(Long torneoId, Map<Long, Long> mapa) {
        cache.put(torneoId, mapa);
    }

    /**
     * Obtiene el mapa de flujo de partidos guardado en la cache para un torneo.
     * 
     * @param torneoId ID del torneo
     * @return Mapa de idPartidoActual -> idSiguientePartido, o null si no existe
     */
    public static Map<Long, Long> obtenerMapa(Long torneoId) {
        return cache.get(torneoId);
    }
}
