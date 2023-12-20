package ar.edu.unju.fi.poo.TrabajoPracticoN9.util;

/**
 * La clase MensajesUtil representara la clase de mensajes estaticos
 * utilizado en este proyecto. Principalmente presente en loggers.
 * ¡Evita que SonarQube provoque Smell Codes por "textos diplicados"! 
 * @author Grupo06
 *
 */

public final class LoggerUtil {
	
	public static final String TITULAR_REGISTRO = "El titular fue registrado exitosamente. DNI N° ";
	public static final String TITULAR_MODIFICACION= "El titular fue modificado exitosamente. DNI N° ";
	public static final String TITULAR_HABILITACION = "El titular fue habilitado exitosamente. DNI N° ";
	public static final String TITULAR_INHABILITACION = "El titular fue inhabilitado exitosamente. DNI N° ";
	public static final String TITULAR_VALIDACION_REGISTRO = "Ya se encuentra registrado un titular con el DNI N° ";
	public static final String TITULAR_VALIDACION_EMAIL = "Ya se encuentra registrado un titular con el email ";
	public static final String TITULAR_VALIDACION_EXISTENCIA = "NO se encuentra registrado un titular con el DNI N° ";
	
	public static final String ADHERENTE_REGISTRO = "El adherente fue registrado exitosamente. DNI N° ";
	public static final String ADHERENTE_AGREGACION = "El adherente fue agregado exitosamente. DNI N° ";
	public static final String ADHERENTE_MODIFICACION = "El adherente fue modificado exitosamente. DNI N° ";
	public static final String ADHERENTE_HABILITACION = "El adherente fue habilitado exitosamente. DNI N° ";
	public static final String ADHERENTE_INHABILITACION = "El adherente fue inhabilitado exitosamente. DNI N° ";
	public static final String ADHERENTE_VALIDACION_REGISTRO = "Ya se encuentra registrado un adherente con el DNI N° ";
	public static final String ADHERENTE_VALIDACION_EMAIL = "Ya se encuentra registrado un adherente con el email ";
	public static final String ADHERENTE_VALIDACION_EXISTENCIA = "NO se encuentra registrado un adherente con el DNI N° ";
	
	public static final String CUENTA_CREACION = "La cuenta bancaria fue creada exitosamente. Cuenta N° ";
	public static final String CUENTA_ASOCIACION = "La cuenta bancaria fue asociada exitosamente al titular con DNI N° ";
	public static final String CUENTA_MODIFICACION = "La cuenta bancaria fue modificada exitosamente. Cuenta N° ";
	public static final String CUENTA_TITULAR_EXISTENCIA = "Ya existe una cuenta bancaria asociada al Titular con DNI N° ";
	public static final String CUENTA_VALIDACION_EXISTENCIA = "La cuenta bancaria ya se encuentra registrada. Cuenta N° ";
	public static final String CUENTA_VALIDACION_NO_EXISTENCIA = "La cuenta bancaria NO se encuentra registrada. Cuenta N° ";
	public static final String CUENTA_VALIDACION_ASOCIACION = "La cuenta bancaria NO se encuentra asociada a ningun titular/adherente con DNI N°";
	public static final String CUENTA_HABILITACION = "La Cuenta Bancaria fue habilitada exitosamente. Cuenta N° ";
	public static final String CUENTA_INHABILITACION = "La Cuenta Bancaria fue inhabilitada exitosamente. Cuenta N° ";
	
	public static final String MOVIMIENTO_CREACION = "El movimiento fue creado exitosamente. Cuenta N° ";
	public static final String MOVIMIENTO_DEPOSITO = "El deposito fue realizado exitosamente. Cuenta N° ";
	public static final String MOVIMIENTO_EXTRACCION = "La extraccion fue realizada exitosamente. Cuenta N° ";
	public static final String MOVIMINTO_VALIDACION = "El importe excede el limite de extraccion o la cuenta no posee saldo suficiente";
	public static final String COMPROBANTE_CREACION_FECHA = "La lista de comprobantes por rango de fechas fue generada exitosamente.";
	public static final String COMPROBANTE_CREACION_LIMITE = "La lista de comprobantes por limite fue generada exitosamente.";
	
	public static final String OBJETO = "Objeto: ";
	public static final String MENSAJE = "Mensaje: ";
	public static final String ERROR = "Error: ";
	public static final String ASUNTO_EMAIL = "Bienvenido a ExheniaBank";
	public static final String HEARDER_KEY = "Content-Disposition";
	
}
