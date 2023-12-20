package ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO;


public class ComprobanteOperacionDTO {

	private Integer nroCuenta;
	private Boolean operacion; //si es false es Extraccion
	private String fechaHora;
	private Double importe;
	private String dniOperador;
	private Double saldo;
	private String nombreOperador;
	
	
	public ComprobanteOperacionDTO() {
		super();
	}
	
	public Integer getNroCuenta() {
		return nroCuenta;
	}
	
	public void setNroCuenta(Integer nroCuenta) {
		this.nroCuenta = nroCuenta;
	}
	
	public Boolean getOperacion() {
		return operacion;
	}
	
	public void setOperacion(Boolean operacion) {
		this.operacion = operacion;
	}
	
	public String getFechaHora() {
		return fechaHora;
	}
	
	public void setFechaHora(String fechaHora) {
		this.fechaHora = fechaHora;
	}
	
	public Double getImporte() {
		return importe;
	}
	
	public void setImporte(Double importe) {
		this.importe = importe;
	}
	
	public String getDniOperador() {
		return dniOperador;
	}
	
	public void setDniOperador(String dniOperador) {
		this.dniOperador = dniOperador;
	}
	
	public Double getSaldo() {
		return saldo;
	}
	
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	
	public String getNombreOperador() {
		return nombreOperador;
	}
	
	public void setNombreOperador(String nombreOperador) {
		this.nombreOperador = nombreOperador;
	}
	
}
