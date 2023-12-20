package ar.edu.unju.fi.poo.TrabajoPracticoN9.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unju.fi.poo.TrabajoPracticoN9.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	Cliente findByDni(String dni);
	Cliente findByCorreoElectronico(String email);
	List<Cliente> findByNombre(String nombre);
}
