package ar.edu.unju.fi.poo.TrabajoPracticoN9;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.AdherenteDTO;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.TitularDTO;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.exception.ModelException;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.service.ClienteService;



/**
 * ClienteTestCase sera la clase que contendra las pruebas unitarias
 * correspondientes a los DTO de titulares y adherentes.
 * @author Grupo06.
 *
 */

//Deshabilitar imports para evitar conflictos. 
@SpringBootTest
class ClienteTestCase {

	@Autowired
	private ClienteService clienteService;
	
	static AdherenteDTO adherente;
	static AdherenteDTO adherente2;
	static TitularDTO titular;
	static TitularDTO titular2;
	
	private static final Logger logger = Logger.getLogger(ClienteTestCase.class);
	
	/**
	 * Metodo que se ejecutara antes de cada metodo de prueba.
	 * Utilizado principalmente para inicializar las variables y objetos
	 * necesarios para los diferentes casos de pruebas.
	 * @throws Exception
	 */
	
	@BeforeEach
	void setUp() throws Exception {
		
		adherente = new AdherenteDTO();
		adherente.setDni("12345678");
		adherente.setCorreoElectronico("hernesto@gmail.com");
		adherente.setDomicilio("Alto la viña");
		adherente.setEstado(true);
		adherente.setNombre("Hernesto");
		
		adherente2 = new AdherenteDTO();
		adherente2.setDni("44877663");
		adherente2.setCorreoElectronico("carlos@gmail.com");
		adherente2.setDomicilio("Alto la viña");
		adherente2.setEstado(true);
		adherente2.setNombre("Carlos");
		
		titular = new TitularDTO();
		titular.setDni("42791976");
		titular.setCorreoElectronico("nacho@gmail.com");
		titular.setDomicilio("Bajo la viña");
		titular.setEstado(true);
		titular.setNombre("Rodolfo");
		
		titular2 = new TitularDTO();
		titular2.setDni("38345214");
		titular2.setCorreoElectronico("maria@gmail.com");
		titular2.setDomicilio("Alto Comedero");
		titular2.setEstado(true);
		titular2.setNombre("Maria");
	
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
	
	/**
	 * Prueba unitaria que verifica el caso de prueba
	 * donde se registran clientes.
	 * Valida si el agregado se ha realizado correctamente.
	 */
	
	@Test
	@Disabled
	void registrarClienteTestCase() {
		try {
			
			//prueba realizada correctamente. Se registrara en la Base de Datos.
			clienteService.registrarTitular(titular);
			assertNotNull(titular);
			
			//Prueba realizada incorrectamente. El titular ya se encuentra registrado.
			//Se captura la exepcion y pasa al catch.
			clienteService.registrarTitular(titular);
			assertNotNull(titular);
			
		}catch(ModelException e) {
			logger.error(e.getMessage());
			
			//Prueba realizada correctamente. Se registrara en la Base de Datos.
			clienteService.registrarTitular(titular2);
			assertNotNull(titular2);
			
		}
	}
	
	/**
	 * Prueba unitaria que verifica el caso de prueba
	 * donde se modifican clientes.
	 * Valida si la modificacion se ha realizado correctamente.
	 */
	
	@Test
	@Disabled
	void modificarClienteTestCase() {
		try {
			
			//Prueba realizada incorrectamente. El titular NO se encuentra registrado
			//para realizar la modificacion. Se captura la exepcion y pasa al catch.
			titular.setNombre("Juan Carlos");
			clienteService.modificarTitular(titular);
			assertEquals(titular.getNombre(), "Juan Carlos");
			
		}catch(ModelException e) {
			logger.error(e.getMessage());
			
			//Prueba realizada correctamente. Se modificara en la Base de Datos.
			clienteService.registrarTitular(titular);
			titular.setNombre("Vanesa");
			titular.setContrasenia("password123");
			clienteService.modificarTitular(titular);
			assertEquals(titular.getNombre(), "Vanesa");
			
		}
		
	}
	
	/**
	 * Prueba unitaria que verifica el caso de prueba
	 * donde se habilitan clientes.
	 * Valida si el habilitado se ha realizado correctamente.
	 */
	
	@Test
	@Disabled
	void habilitarClienteTestCase() {
		try {
	
			//Prueba realizada incorrectamente. El titular NO se encuentra registrado
			//para realizar la habilitacion. Se captura la exepcion y pasa al catch.
			assertEquals(clienteService.inhabilitarTitular(titular).getEstado(), false);
			assertEquals(clienteService.habilitarTitular(titular).getEstado(), true);
			
		}catch(ModelException e) {
			logger.error(e.getMessage());
			
			//Prueba realizada correctamente. Se modificara el estado en la Base de Datos.
			clienteService.registrarTitular(titular2);
			assertEquals(clienteService.inhabilitarTitular(titular2).getEstado(), false);
			assertEquals(clienteService.habilitarTitular(titular2).getEstado(), true);
		}
		
	}
	
	/**
	 * Prueba unitaria que verifica el caso de prueba
	 * donde se inhabilitan clientes.
	 * Valida si el inhabilitado se ha realizado correctamente.
	 */
	
	@Test
	@Disabled
	void inhabilitarClienteTestCase() {
		try {
			//Prueba realizada incorrectamente. El titular NO se encuentra registrado
			//para realizar la inhabilitacion. Se captura la exepcion y pasa al catch.
			clienteService.inhabilitarTitular(titular);
			assertEquals(titular.getEstado(), false);
			
		}catch(ModelException e) {
			logger.error(e.getMessage());
			
			//Prueba realizada correctamente. Se modificara el estado en la Base de Datos.
			clienteService.registrarTitular(titular2);
			assertEquals(clienteService.inhabilitarTitular(titular2).getEstado(), false);
		}
		
	}
	
	/**
	 * Prueba unitaria que verifica el caso de prueba
	 * donde se registran adherentes.
	 * Valida si el agregado se ha realizado correctamente.
	 */
	
	@Test
	@Disabled
	void registrarAdherenteTestCase() {
		try {
		
			//Prueba realizada correctamente. Se registrara el adherente en la Base de Datos.
			clienteService.validarRegistroAdherente(adherente);
			clienteService.registrarAdherente(adherente);
			assertNotNull(adherente);
			
			//Prueba realizada incorrectamente. El adherente YA se encuentra registrado.
			//Se captura la exepcion y pasa al catch.
			clienteService.validarRegistroAdherente(adherente);
			clienteService.registrarAdherente(adherente);
			assertNotNull(adherente);
			
		}catch(ModelException e) {
			logger.error(e.getMessage());
			
			//Prueba realizada correctamente. Se registrara el adherente en la Base de Datos.
			clienteService.validarRegistroAdherente(adherente2);
			clienteService.registrarAdherente(adherente2);
			assertNotNull(adherente2);
		}
		
	}
	
	/**
	 * Prueba unitaria que verifica el caso de prueba
	 * donde se modificaran adherentes.
	 * Valida si la modificacion se ha realizado correctamente.
	 */
	
	@Test
	@Disabled
	void modificarAdherenteTestCase() {
		try {
			//Prueba realizada incorrectamente. El adherente NO se encuentra registrado
			//para la modificacion. Se captura la exepcion y pasa al catch.
			adherente.setNombre("Jose");
			clienteService.modificarAdherente(adherente);
			assertEquals(adherente.getNombre(), "Jose");
			
		}catch(ModelException e) {
			logger.error(e.getMessage());
			//Prueba realizada correctamente. Se modificara el adherente en la Base de Datos.
			clienteService.registrarAdherente(adherente2);
			adherente2.setNombre("Ambar");
			adherente2.setContrasenia("password123");
			clienteService.modificarAdherente(adherente2);
			assertEquals(adherente2.getNombre(), "Ambar");
		}
		
	}
	
	/**
	 * Prueba unitaria que verifica el caso de prueba
	 * donde se habilitaran adherentes.
	 * Valida si la habilitacion se ha realizado correctamente.
	 */
	
	@Test
	@Disabled
	void habilitarAdherenteTestCase() {
		try {
			//Prueba realizada incorrectamente. El adherente NO se encuentra registrado
			//para la habilitacion. Se captura la exepcion y pasa al catch.
			assertEquals(clienteService.inhabilitarAdherente(adherente).getEstado(), false);
			assertEquals(clienteService.habilitarAdherente(adherente).getEstado(), true);
			
		}catch(ModelException e) {
			logger.error(e.getMessage());
			//Prueba realizada correctamente. Se modificara el estado en la Base de Datos.
			clienteService.registrarAdherente(adherente2);
			assertEquals(clienteService.inhabilitarAdherente(adherente2).getEstado(), false);
			assertEquals(clienteService.habilitarAdherente(adherente2).getEstado(), true);
		}
		
	}
	
	/**
	 * Prueba unitaria que verifica el caso de prueba
	 * donde se inhabilitaran adherentes.
	 * Valida si la inhabilitacion se ha realizado correctamente.
	 */
	
	@Test
	@Disabled
	void inhabilitarAdherenteTestCase() {
		try {
			
			//Prueba realizada incorrectamente. El adherente NO se encuentra registrado
			//para la inhabilitacion. Se captura la exepcion y pasa al catch.
			assertEquals(clienteService.inhabilitarAdherente(adherente).getEstado(), false);
			
		}catch(ModelException e) {
			logger.error(e.getMessage());
			
			//Prueba realizada correctamente. Se modificara el estado en la Base de Datos.
			clienteService.registrarAdherente(adherente2);
			assertEquals(clienteService.inhabilitarAdherente(adherente2).getEstado(), false);
		}
		
	}
	
}
