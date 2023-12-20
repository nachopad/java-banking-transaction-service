package ar.edu.unju.fi.poo.TrabajoPracticoN9.util;

import java.security.SecureRandom;

/**
 * Clase encargada de generar una contrasenia para el titular
 * o adherente registrado. 
 * @author Grupo06
 *
 */

public class GenerarContrasenia {
	
	/**
	 * Metodo que genera una contrasenia aleatoria.
	 * Utiliza un rango ASCII alfanumerico (0-9, a-z, A-Z).
	 * @return String contrasenia.
	 */
	
	public String crearContrasenia(){
		
		final String cadena = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		final Integer longitud = 10;
		StringBuilder contrasenia = new StringBuilder();
		SecureRandom random = new SecureRandom();
		
		for(int i=0;i<longitud;i++) {
			int randomIndex = random.nextInt(cadena.length());
			contrasenia.append(cadena.charAt(randomIndex));		
		}
		return contrasenia.toString();
	}
	
	
}
