package ar.edu.unju.fi.poo.TrabajoPracticoN9.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Esta clase representara todos los formateos de fechas
 * e importes utilizadas en este proyecto.
 * @author Grupo06
 *
 */

public class FormatUtil {
	
	/**
	 * Metodo que permite formatear una fecha de tipo LocalDateTime a String.
	 * Devuelve la fecha en formato dd/MM/yyyy HH:mm:ss.
	 * @param LocalDateTime fecha
	 * @return String formatDateTime
	 */
	
	public String formatearFechaYHora(LocalDateTime fecha) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy    HH:mm:ss");
        return fecha.format(formatter);
	}
	
	/**
	 * Metodo que permite obtener solo la fecha (sin hora) de un LocalDateTime.
	 * Devuelve la fecha en formato dd/MM/yyyy.
	 * @param LocalDateTime fecha
	 * @return String formatDateTime
	 */
	
	public String formatearFecha(LocalDateTime fecha) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
       return fecha.format(formatter); 
	}
	
	/**
	 * Metodo que permite obtener solo la hora (sin fecha) de un LocalDateTime.
	 * Devuelve la hora en formato HH:mm.
	 * @param LocalDateTime fecha
	 * @return String formatDateTime
	 */
	
	public String formatearHora(LocalDateTime fecha) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return fecha.format(formatter);
	}
	
	/**
	 * Metodo que permite formatear una fecha String a tipo LocalDateTime.
	 * Devuelve la fecha en formato yyyy-MM-dd HH:mm.
	 * @param String fecha
	 * @return LocalDateTime fecha
	 */
	
	public LocalDateTime formatearStringaLocalDateTime (String fecha) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
		return LocalDateTime.parse(fecha, formatter);
	}
	
	/**
	 * Metodo que permite formatear una fecha String yyyy/MM/dd a LocalDateTime.
	 * @param String fecha
	 * @return LocalDateTime fechaLocalDateTime
	 */
	
	public LocalDateTime formatearStringaLocalDateTimeReal (String fecha) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		return LocalDateTime.parse(fecha, formatter);
	}

	/**
	 * Metodo que permite formatear una fecha String a tipo LocalDateTime.
	 * Utiliza el patron dd/MM/yyyy HH:mm.
	 * @param String fecha
	 * @return LocalDateTime fecha
	 */
	
	public String formatearFechaString (String fecha) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		LocalDateTime fechaformat = LocalDateTime.parse(fecha, formatter);
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy    HH:mm");
        return fechaformat.format(formatter2);
	}
	
	
	/**
	 * Metodo que permite formatear un double a dos decimales (importes).
	 * @param Double importe
	 * @return String importeFormateado
	 */
	
	public String formatearImporte(Double importe) {
		return "$ "+String.format("%.2f" ,importe);
	}
}
