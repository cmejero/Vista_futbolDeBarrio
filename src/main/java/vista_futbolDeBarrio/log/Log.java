package vista_futbolDeBarrio.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import vista_futbolDeBarrio.utilidades.Utilidades;

/**
 * Clase que se encarga del Log
 */
public class Log {

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Ruta configurable (Linux por defecto)
    private static final String RUTA_BASE_LOGS =
            System.getProperty("log.path", "/var/log/futboldebarrio/vista");

    public static void ficheroLog(String mensaje) {

        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = "[" + timestamp + "] " + mensaje;

        try {

            String nombreCarpeta = Utilidades.nombreCarpetaFecha();
            String nombreArchivo = Utilidades.nombreArchivoLog();

            String rutaCarpeta = RUTA_BASE_LOGS + File.separator + nombreCarpeta;

            File carpeta = new File(rutaCarpeta);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            File archivo = new File(rutaCarpeta + File.separator + nombreArchivo);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
                writer.write(logEntry);
                writer.newLine();
            }

        } catch (IOException e) {
            System.err.println("Error al escribir en el log: " + e.getMessage());
        }
    }
}