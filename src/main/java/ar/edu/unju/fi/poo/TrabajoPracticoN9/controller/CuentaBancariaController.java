package ar.edu.unju.fi.poo.TrabajoPracticoN9.controller;

import java.time.LocalDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.ComprobanteOperacionDTO;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.CuentaBancariaDTO;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.TitularDTO;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.exception.ModelException;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.service.ClienteService;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.service.CuentaBancariaService;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.service.MovimientoService;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.util.ConsultaSaldoPDF;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.util.FormatUtil;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.util.LoggerUtil;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.util.ResumenMovimientoExcel;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.util.ResumenMovimientoPDF;

/**
 * CuentaBancariaController representa la capa controladora
 * de las cuentas bancarias y sus operaciones.
 * @author Grupo06
 *
 */

@Controller
@RequestMapping("v1/api/cuenta")
public class CuentaBancariaController {

	@Autowired 
	MovimientoService movimientoService;
	
	@Autowired
	CuentaBancariaService cuentaBancariaService;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	ResumenMovimientoExcel resumenExcel;
	
	private final Logger logger = LoggerFactory.getLogger(CuentaBancariaController.class);
	
	private FormatUtil util = new FormatUtil();

	/**
	 * Metodo que permite registrar una cuenta bancaria en la Base de Datos.
	 * 
	 * @param String dniTitular
	 * @return ResponseEntity
	 * @throws ModelException
	 * @throws MessagingException
	 */
	
	@PostMapping("/{dniTitular}")
	public ResponseEntity<Map<String, Object>> saveCuentaBancaria(@PathVariable String dniTitular) throws ModelException, MessagingException {
		logger.debug("API REST habilitando una nueva cuenta para el titular "+ clienteService.buscarTitularPorDni(dniTitular).getNombre() );
		Map<String, Object> response = new HashMap<>();
		try {
			TitularDTO titular = cuentaBancariaService.validarExistenciaTitular(clienteService.buscarTitularPorDni(dniTitular));
			CuentaBancariaDTO cuenta = cuentaBancariaService.crearCuenta(titular); 
			cuentaBancariaService.habilitarCuenta(cuenta);
			cuenta.setTitular(null);
			cuenta.setMovimientos(null);
			response.put(LoggerUtil.OBJETO, cuenta);
			response.put(LoggerUtil.MENSAJE, "Cuenta bancaria creada exitosamente.");			
		}catch(ModelException e) {
			response.put(LoggerUtil.MENSAJE, "Error al crear la cuenta bancaria.");
			response.put(LoggerUtil.ERROR, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	 
	
	/**
	 * Metodo que permite modificar una cuenta bancaria registrada en la Base de Datos.
	 * 
	 * @param CuentaBancariaDTO cuenta
	 * @return ResponseEntity
	 * @throws ModelException
	 */
	
	@PutMapping()
	public ResponseEntity<Map<String, Object>> updateCuentaBancaria(@RequestBody CuentaBancariaDTO cuenta) throws ModelException {
		Map<String, Object> response = new HashMap<>();
		logger.debug("API REST modificando la cuenta N° "+cuenta.getNroCuenta());
		try {
			cuentaBancariaService.modificarCuenta(cuenta);
			response.put(LoggerUtil.OBJETO, cuenta);
			response.put(LoggerUtil.MENSAJE, "Cuenta bancaria modificada exitosamente.");
		}catch(ModelException e) {
			response.put(LoggerUtil.MENSAJE, "Error al modificar la cuenta bancaria.");
			response.put(LoggerUtil.ERROR, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	
	/**
	 *Metodo que permite inhabilitar una cuenta registrada en la Base de datos.
	 *
	 * @param Integer nroCuenta
	 * @return ResponseEntity
	 * @throws ModelException
	 */
	
	@PutMapping("/habilitada/{nroCuenta}")
	public ResponseEntity<Map<String, Object>> disableCuentaBancaria(@PathVariable Integer nroCuenta) throws ModelException {
		Map<String, Object> response = new HashMap<>();
		logger.debug("API REST desabilitando la cuenta N° "+nroCuenta);
		try {
			CuentaBancariaDTO cuenta = cuentaBancariaService.buscarCuentaPorNumero(nroCuenta);
			cuentaBancariaService.eliminarCuentaLogica(cuenta);
			cuenta.setTitular(null);
			cuenta.setMovimientos(null);
			response.put(LoggerUtil.OBJETO, cuenta);
			response.put(LoggerUtil.MENSAJE, "Cuenta bancaria desabilitada exitosamente");
		}catch(ModelException e) {
			response.put(LoggerUtil.MENSAJE, "Error al desabilitar la cuenta bancaria.");
			response.put(LoggerUtil.ERROR, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	
	/**
	 * Metodo que permite habilitar una cuenta registrada en la Base de Datos.
	 * @param Integer nroCuenta
	 * @return ResponseEntity
	 * @throws ModelException
	 */
	
	@PutMapping("/deshabilitada/{nroCuenta}")
	public ResponseEntity<Map<String, Object>> enableCuentaBancaria(@PathVariable Integer nroCuenta) throws ModelException {
		Map<String, Object> response = new HashMap<>();
		logger.debug("API REST habilitando la cuenta N° "+nroCuenta);
		try {
			CuentaBancariaDTO cuenta = cuentaBancariaService.buscarCuentaPorNumero(nroCuenta);
			cuentaBancariaService.habilitarCuentaLogica(cuenta);
			cuenta.setTitular(null);
			cuenta.setMovimientos(null);
			response.put(LoggerUtil.OBJETO, cuenta);
			response.put(LoggerUtil.MENSAJE, "Cuenta bancaria habilitada exitosamente.");
		}catch(ModelException e) {
			response.put(LoggerUtil.MENSAJE, "Error al habilitar la cuenta bancaria.");
			response.put(LoggerUtil.ERROR, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	
	/**
	 * Metodo que permite buscar una cuenta bancaria por dni en la Base de Datos.
	 * @param String dni
	 * @return ResponseEntity
	 * @throws ModelException
	 */
	
	@GetMapping("/{dni}")
	public ResponseEntity<Map<String, Object>> getCuentaBancariaByDni(@PathVariable String dni) throws ModelException {
		Map<String, Object> response = new HashMap<>();
		logger.debug("API REST buscando la cuenta con DNI N° "+dni);
		try {
			CuentaBancariaDTO cuenta = cuentaBancariaService.obtenerCuentaPorDni(dni);
			cuenta.setMovimientos(null);
			cuenta.setTitular(null);
			response.put(LoggerUtil.OBJETO, cuenta);
			response.put(LoggerUtil.MENSAJE, "Busqueda exitosa.");
			
		}catch(ModelException e) {
			response.put(LoggerUtil.MENSAJE, "Error al buscar la cuenta bancaria.");
			response.put(LoggerUtil.ERROR, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	/**
	 * Metodo que permite realizar un deposito a una cuenta existente en la Base de Datos (Movimiento).
	 * 
	 * @param String dniOperador
	 * @param Double importe
	 * @return ResponseEntity
	 * @throws ModelException
	 */
	
	@PostMapping("/deposito/{dniOperador}")
	public ResponseEntity<Map<String, Object>> saveDeposito(@PathVariable String dniOperador, @RequestParam Double importe) throws ModelException {
		logger.debug("API REST dando de alta el movimiento Deposito" );
		Map<String, Object> response = new HashMap<>();
		try {
			CuentaBancariaDTO cuenta = cuentaBancariaService.obtenerCuentaPorDni(dniOperador);
			cuenta = cuentaBancariaService.validarDniOperador(dniOperador, cuenta);
			ComprobanteOperacionDTO comprobante = cuentaBancariaService.realizarDeposito(cuenta, importe, dniOperador);
			response.put(LoggerUtil.OBJETO, comprobante);
			response.put(LoggerUtil.MENSAJE, "Deposito realizado correctamente.");
		}catch(ModelException e) {
			response.put(LoggerUtil.MENSAJE, "Error al crear el deposito.");
			response.put(LoggerUtil.ERROR, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	
	/**
	 * Metodo que permite realizar una extraccion a una cuenta registrada en la Base de Datos (Movimiento).
	 * @param String dniOperador
	 * @param Double importe
	 * @return ResponseEntity
	 * @throws ModelException
	 */
	
	@PostMapping("/extraccion/{dniOperador}")
	public ResponseEntity<Map<String, Object>> saveExtraccion(@PathVariable String dniOperador, @RequestParam Double importe) throws ModelException {
		logger.debug("API REST dando de alta el movimiento Extraccion" );
		Map<String, Object> response = new HashMap<>();
		try {
			CuentaBancariaDTO cuenta = cuentaBancariaService.obtenerCuentaPorDni(dniOperador);
			cuenta = cuentaBancariaService.validarDniOperador(dniOperador, cuenta);
			cuenta = cuentaBancariaService.validarMovimiento(cuenta, importe);
			ComprobanteOperacionDTO comprobante = cuentaBancariaService.realizarExtraccion(cuenta, importe, dniOperador);
			response.put(LoggerUtil.OBJETO, comprobante);
			response.put(LoggerUtil.MENSAJE, "Extraccion realizada correctamente.");
		}catch(ModelException e) {
			response.put(LoggerUtil.MENSAJE, "Error al crear la extraccion.");
			response.put(LoggerUtil.ERROR, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	
	/**
	 * Metodo que permite generar y descargar un archivo pdf,
	 * contiene los resumenes de movimientos de una cuenta especifica.
	 * @param String dni
	 * @param HttpServletResponse response
	 * @return ResponseEntity
	 * @throws Exception
	 */
	
	@GetMapping("/reporte/resumen-pdf/{dni}")
	public ResponseEntity<Map<String, Object>> saveResumenPdf(@PathVariable String dni, HttpServletResponse response) throws Exception {
		Map<String, Object> respuesta = new HashMap<>();
		try {
			CuentaBancariaDTO cuenta = cuentaBancariaService.obtenerCuentaPorDni(dni);
			List<ComprobanteOperacionDTO> listaComprobantes =	movimientoService.obtenerListaPorLimite(cuenta.getNroCuenta());
			String hearderKey = LoggerUtil.HEARDER_KEY;
			String hearderValue = "attachment; filename=ResumenMovimientos-"+LocalDate.now().toString()+".pdf";
			response.setHeader(hearderKey, hearderValue);
			ResumenMovimientoPDF resumen = new ResumenMovimientoPDF();
			resumen.generarResumen(cuenta, listaComprobantes, response);
			respuesta.put(LoggerUtil.MENSAJE, "PDF generado exitosamente.");
		}catch(ModelException e) {
			respuesta.put(LoggerUtil.MENSAJE, "Error al crear el PDF.");
			respuesta.put(LoggerUtil.ERROR, e.getMessage());
			return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
	}
	
	
	/**
	 * Metodo que permite generar y descargar un archivo pdf,
	 * contiene la consulta de saldo de una cuenta especifica. 
	 * @param String dni
	 * @param HttpServletResponse response
	 * @return ResponseEntity
	 * @throws Exception
	 */
	
	@GetMapping("/reporte/consulta-pdf/{dni}")
	public ResponseEntity<Map<String, Object>> saveConsultaPdf(@PathVariable String dni, HttpServletResponse response) throws Exception {
		logger.debug("API REST iniciado reportes movimientos" );
		Map<String, Object> respuesta = new HashMap<>();
		try {
			CuentaBancariaDTO cuenta = cuentaBancariaService.obtenerCuentaPorDni(dni);
			String hearderKey = LoggerUtil.HEARDER_KEY;
			String hearderValue = "attachment; filename=ConsultaSaldo-"+LocalDate.now().toString()+".pdf";
			response.setHeader(hearderKey, hearderValue);
			ConsultaSaldoPDF consulta = new ConsultaSaldoPDF();
			consulta.generarConsulta(cuenta, response);
			respuesta.put(LoggerUtil.MENSAJE, "PDF generado exitosamente.");
		}catch(ModelException e) {
			respuesta.put(LoggerUtil.MENSAJE, "Error al crear el PDF.");
			respuesta.put(LoggerUtil.ERROR, e.getMessage());
			return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
	}
	
	
	/**
	 * Metodo que permite generar y descargar un archivo excel,
	 * contiene los resumenes de movimientos de una cuenta especifica.
	 * @param String dni
	 * @param String fechaInicio
	 * @param String fechaFin
	 * @param HttpServletResponse response
	 * @return ResponseEntity
	 * @throws Exception
	 */
	
	@GetMapping("/reporte/resumen-excel/{dni}")
	public ResponseEntity<Map<String, Object>> saveResumenExcel(@PathVariable String dni, @RequestParam(name = "desde") String fechaInicio, @RequestParam(name = "hasta") String fechaFin,HttpServletResponse response) throws Exception {
		logger.debug("API REST iniciado reportes movimientos" );
		Map<String, Object> respuesta = new HashMap<>();
		try {
			CuentaBancariaDTO cuenta = cuentaBancariaService.obtenerCuentaPorDni(dni);
			List<ComprobanteOperacionDTO> listaComprobantes =	movimientoService.obtenerListaComprobantes(cuenta.getNroCuenta(), util.formatearStringaLocalDateTime(fechaInicio), util.formatearStringaLocalDateTime(fechaFin));
			String hearderKey = LoggerUtil.HEARDER_KEY;
			String hearderValue = "attachment; filename=ResumenMovimientos-"+LocalDate.now().toString()+".xls";
			response.setHeader(hearderKey, hearderValue);
			resumenExcel.generarResumen(cuenta, listaComprobantes, response);
			respuesta.put(LoggerUtil.MENSAJE, "Excel generado exitosamente.");
		}catch(ModelException e) {
			respuesta.put(LoggerUtil.MENSAJE, "Error al generar el Excel.");
			respuesta.put(LoggerUtil.ERROR, e.getMessage());
			return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
	}
	

}
