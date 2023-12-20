package ar.edu.unju.fi.poo.TrabajoPracticoN9.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unju.fi.poo.TrabajoPracticoN9.entity.CuentaBancaria;

@Repository
public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, Long> {
	
	CuentaBancaria findByNroCuenta(Integer nroCuenta);
}
