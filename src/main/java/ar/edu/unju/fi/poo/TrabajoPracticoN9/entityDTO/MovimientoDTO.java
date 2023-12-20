package ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO;

public class MovimientoDTO {
	
	private Long id;
	private CuentaBancariaDTO cuenta;
	private Boolean operacion; //si es false es Extraccion
	private String fechaHora;
	private Double importe;
	private String dniOperador;
	private double saldo;
	private String nombreOperador;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public CuentaBancariaDTO getCuenta() {
		return cuenta;
	}
	
	public void setCuenta(CuentaBancariaDTO cuenta) {
		this.cuenta = cuenta;
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
	
	public double getSaldo() {
		return saldo;
	}
	
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	public String getNombreOperador() {
		return nombreOperador;
	}
	
	public void setNombreOperador(String nombreOperador) {
		this.nombreOperador = nombreOperador;
	}
	
}
