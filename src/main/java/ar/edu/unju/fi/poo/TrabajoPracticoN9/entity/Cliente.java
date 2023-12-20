package ar.edu.unju.fi.poo.TrabajoPracticoN9.entity;


import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;


/**
 * Cliente va a representar una entidad cliente de Banco que tendra id,
 * nombre, email, domicilio y estado.
 * @author Grupo06
 *
 */

@Entity
@Table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( name="type" )
public class Cliente {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="Dni")
	private String dni;
	
	@Column(name="Nombre")
	private String nombre;
	
	@Column(name="Email")
	private String correoElectronico;
	
	@Column(name="Domicilio")
	private String domicilio;
	
	@Column(name="Estado")
	private Boolean estado;
	
	@Column(name="Contraseña")
	private String contrasenia;

	

	/**
	 * Constructor Parametrizado
	 * @param String dni
	 * @param String nombre
	 * @param String correoElectronico
	 * @param String domicilio
	 */
	
	public Cliente(Long id, String dni, String nombre, String correoElectronico, String domicilio, Boolean estado,
			String contrasenia) {
		super();
		this.id = id;
		this.dni = dni;
		this.nombre = nombre;
		this.correoElectronico = correoElectronico;
		this.domicilio = domicilio;
		this.estado = estado;
		this.contrasenia=contrasenia;
	}

	public Cliente() {
		super();
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	
}