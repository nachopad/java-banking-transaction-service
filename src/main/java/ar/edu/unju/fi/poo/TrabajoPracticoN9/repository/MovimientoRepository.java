package ar.edu.unju.fi.poo.TrabajoPracticoN9.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unju.fi.poo.TrabajoPracticoN9.entity.CuentaBancaria;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entity.Movimiento;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long>{

	List<Movimiento> findByCuenta(CuentaBancaria cuenta);
	List<Movimiento> findByCuentaAndFechaHoraBetweenOrderByFechaHoraDesc(CuentaBancaria cuenta, LocalDateTime fechaMin, LocalDateTime fechaMax);
	List<Movimiento> findTop20ByCuentaOrderByFechaHoraDesc(CuentaBancaria cuenta);
}
