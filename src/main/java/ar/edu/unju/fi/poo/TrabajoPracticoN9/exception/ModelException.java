package ar.edu.unju.fi.poo.TrabajoPracticoN9.exception;

/**
 * La clase ModelException representa una clase de errores personalizados.
 * Aquí se pueden tratar errores de diferentes formas. En este caso solo
 * recibe un mensaje personalizado de ser necesario.
 * @author Grupo06
 *
 */

public class ModelException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Recibe como parametro un mensaje de error personalizado.
	 * @param String message
	 */
	
	public ModelException(String message) {
		super(message);
	}
	
}
