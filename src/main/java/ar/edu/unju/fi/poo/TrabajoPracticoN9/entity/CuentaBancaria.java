package ar.edu.unju.fi.poo.TrabajoPracticoN9.entity;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


/**
 * CuentaBancaria va a representar una entidad cuenta bancaria de Banco que tendra id,
 * cliente, fecha de ingreso, saldo , estado y limite de extraccion.
 * @author Grupo06
 *
 */

@Entity
public class CuentaBancaria {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long idCuenta;
	
	@Column(name="NroCuenta")
	private Integer nroCuenta;
	
	@OneToOne
	@JoinColumn(name="cuenta_titular")
	private Titular titular;
	
	@Column(name="Fecha_ingreso")
	private LocalDate fechaIngreso;
	
	@Column(name="Saldo")
	private Double saldoActual;
	
	@Column(name="Estado")
	private Boolean estado;
	
	@Column(name="Limite_extraccion")
	private Double limiteExtraccion;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "cuenta")
	private Set<Movimiento> movimientos;

	
	public CuentaBancaria() {
		super();
	}

	public CuentaBancaria(Long idCuenta, Integer nroCuenta, Titular titular, LocalDate fechaIngreso, Double saldoActual,
			Boolean estado, Double limiteExtraccion, Set<Movimiento> movimientos) {
		super();
		this.idCuenta = idCuenta;
		this.nroCuenta = nroCuenta;
		this.titular = titular;
		this.fechaIngreso = fechaIngreso;
		this.saldoActual = saldoActual;
		this.estado = estado;
		this.limiteExtraccion = limiteExtraccion;
		this.movimientos = movimientos;
	}


	public Long getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}

	public Integer getNroCuenta() {
		return nroCuenta;
	}

	public void setNroCuenta(Integer nroCuenta) {
		this.nroCuenta = nroCuenta;
	}

	public Titular getTitular() {
		return titular;
	}

	public void setTitular(Titular titular) {
		this.titular = titular;
	}

	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Double getSaldoActual() {
		return saldoActual;
	}

	public void setSaldoActual(Double saldoActual) {
		this.saldoActual = saldoActual;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Double getLimiteExtraccion() {
		return limiteExtraccion;
	}

	public void setLimiteExtraccion(Double limiteExtraccion) {
		this.limiteExtraccion = limiteExtraccion;
	}

	public Set<Movimiento> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(Set<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}
	
	
}
