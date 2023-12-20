package ar.edu.unju.fi.poo.TrabajoPracticoN9;

import static org.junit.jupiter.api.Assertions.*;



import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.CuentaBancariaDTO;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.exception.ModelException;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.service.ClienteService;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.service.CuentaBancariaService;

/**
 * MovimientoTestCase sera la clase que contendra las pruebas unitarias
 * correspondientes a los DTO de los movimientos.
 * @author Grupo06.
 *
 */

//Deshabilitar imports para evitar conflictos. 
@SpringBootTest
class MovimientoTestCase {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	CuentaBancariaService cuentaBancariaService;

	
	@Autowired
	ModelMapper modelMapper;
	
	CuentaBancariaDTO cuentaBancariaActualizada;

	private static final Logger logger = Logger.getLogger(MovimientoTestCase.class);
	
	/**
	 * Metodo que se ejecutara antes de cada metodo de prueba.
	 * Utilizado principalmente para inicializar las variables y objetos
	 * necesarios para los diferentes casos de pruebas.
	 * @throws Exception
	 */
	
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Metodo que se ejecutara despues de cada metodo de prueba.
	 * Utilizado principalmente para limpiar las variables y objetos
	 * usados durante los diferentes casos pruebas.
	 * @throws Exception
	 */
	
	@AfterEach
	void tearDown() throws Exception {	
	}
	
	@Test
	@DisplayName("Depositar dinero")
	void depositarDinero() {
		try {
			cuentaBancariaActualizada = cuentaBancariaService.buscarCuentaPorNumero(100);
			cuentaBancariaService.realizarDeposito(cuentaBancariaActualizada, (double) 15000, "12345678");
			assertEquals(cuentaBancariaService.buscarCuentaPorNumero(cuentaBancariaActualizada.getNroCuenta())
					.getSaldoActual(), 55000.0);
		} catch (ModelException e) {
			logger.error(e.getMessage());
		}
	}

	@Test
	@DisplayName("Extraer dinero")
	void extraerDinero() {
		try {
			cuentaBancariaActualizada = cuentaBancariaService.buscarCuentaPorNumero(100);
			cuentaBancariaActualizada = cuentaBancariaService.validarMovimiento(cuentaBancariaActualizada,
					(double) 8000);
			cuentaBancariaService.realizarExtraccion(cuentaBancariaActualizada, (double) 8000, "12345678");
			assertEquals(cuentaBancariaService.buscarCuentaPorNumero(cuentaBancariaActualizada.getNroCuenta())
					.getSaldoActual(), 47000.0);
		} catch (ModelException e) {
			logger.error(e.getMessage());
		}
	}

}

