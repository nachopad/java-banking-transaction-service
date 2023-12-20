package ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO;

import java.util.ArrayList;
import java.util.List;

public class TitularDTO {

	private Long id;
	private String dni;
	private String nombre;
	private String correoElectronico;
	private List<AdherenteDTO> adherentes = new ArrayList<>();
	private CuentaBancariaDTO cuenta;
	private String domicilio;
	private Boolean estado;
	private String contrasenia;
	
	
	public String getDni() {
		return dni;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	
	public List<AdherenteDTO> getAdherentes() {
		return adherentes;
	}
	
	public void setAdherentes(List<AdherenteDTO> adherentes) {
		this.adherentes = adherentes;
	}
	
	public CuentaBancariaDTO getCuenta() {
		return cuenta;
	}
	
	public void setCuenta(CuentaBancariaDTO cuenta) {
		this.cuenta = cuenta;
	}
	
	public String getDomicilio() {
		return domicilio;
	}
	
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	
	public Boolean getEstado() {
		return estado;
	}
	
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getContrasenia() {
		return contrasenia;
	}
	
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	
	
	
}
