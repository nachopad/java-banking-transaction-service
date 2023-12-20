package ar.edu.unju.fi.poo.TrabajoPracticoN9.entity;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Movimiento va a representar una entidad de movimiento de una cuenta que tendra id,
 * cuenta, tipo de operacion, fecha y hora , importe y un cliente asociado.
 * @author Grupo06
 *
 */

@Entity
public class Movimiento {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "cuenta_id")
	private CuentaBancaria cuenta;
	
	private Boolean operacion; //si es false es Extraccion
	
	private LocalDateTime fechaHora;
	
	private Double importe;

	private String dniOperador;
	
	private Double saldo;
	
	private String nombreOperador;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CuentaBancaria getCuenta() {
		return cuenta;
	}

	public void setCuenta(CuentaBancaria cuenta) {
		this.cuenta = cuenta;
	}

	public Boolean getOperacion() {
		return operacion;
	}

	public void setOperacion(Boolean operacion) {
		this.operacion = operacion;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
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
	
	@Override
	public String toString() {
		return "Movimiento [id=" + id + ", cuenta=" + cuenta + ", operacion=" + operacion + ", fechaHora=" + fechaHora
				+ ", importe=" + importe + "]";
	}
	
}
