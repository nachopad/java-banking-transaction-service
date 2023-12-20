package ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CuentaBancariaDTO {
	
	private Long idCuenta;
	private Integer nroCuenta;
	private TitularDTO titular;
	private LocalDate fechaIngreso;
	private Double saldoActual;
	private Boolean estado;
	private Double limiteExtraccion;
	private List<MovimientoDTO> movimientos = new ArrayList<>();
	
	
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
	
	public TitularDTO getTitular() {
		return titular;
	}
	
	public void setTitular(TitularDTO titular) {
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
	
	public List<MovimientoDTO> getMovimientos() {
		return movimientos;
	}
	
	public void setMovimientos(List<MovimientoDTO> movimientos) {
		this.movimientos = movimientos;
	}
	
	
}
