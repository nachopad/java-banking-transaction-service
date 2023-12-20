package ar.edu.unju.fi.poo.TrabajoPracticoN9.entity;


import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Titular va a representar una entidad heredada de Cliente que tendra id,
 * nombre, dni, saldo , email, domicilio , cuenta y estado.
 * @author Grupo06
 *
 */

@Entity
@DiscriminatorValue( value="T" )
public class Titular extends Cliente {
	
	@OneToOne(mappedBy = "titular", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	private CuentaBancaria cuenta;
	
	@OneToMany(mappedBy = "titular",cascade = {CascadeType.MERGE},fetch = FetchType.EAGER)
	private Set<Adherente> adherentes;
		
	
	public Titular(Long id, String dni, String nombre, String correoElectronico, String domicilio, Boolean estado,
			String contrasenia) {
		super(id, dni, nombre, correoElectronico, domicilio, estado, contrasenia);
	}

	public Titular() {
		
	}
	
	public Set<Adherente> getAdherentes() {
		return adherentes;
	}

	public void setAdherentes(Set<Adherente> adherentes) {
		this.adherentes = adherentes;
	}

	public CuentaBancaria getCuenta() {
		return cuenta;
	}

	public void setCuenta(CuentaBancaria cuenta) {
		this.cuenta = cuenta;
	}
	
}
