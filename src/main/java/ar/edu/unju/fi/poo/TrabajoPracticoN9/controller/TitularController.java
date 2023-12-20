package ar.edu.unju.fi.poo.TrabajoPracticoN9.controller;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

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

import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.TitularDTO;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.exception.ModelException;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.service.ClienteService;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.service.SendEmailService;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.util.ContenidoEmail;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.util.LoggerUtil;

/**
 * TitularController representa la capa controladora de los Titulares.
 * @author Grupo06
 *
 */

@Controller
@RequestMapping("v1/api/titular")
public class TitularController {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	SendEmailService sendMailService;
	
	private ContenidoEmail contenido = new ContenidoEmail();
	
	private final Logger logger = LoggerFactory.getLogger(TitularController.class);
	
	/**
	 * Metodo que permite registrar un titular en la Base de Datos.
	 * @param TitularDTO titular
	 * @return ResponseEntity
	 * @throws ModelException
	 * @throws MessagingException
	 */
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> saveTitular(@RequestBody TitularDTO titular) throws ModelException, MessagingException {
		logger.debug("API REST dando de alta el titular con DNI N° "+titular.getDni());
		Map<String, Object> response = new HashMap<>();
		try {
			TitularDTO titularDto = clienteService.registrarTitular(titular);
			sendMailService.sendEmail(titularDto.getCorreoElectronico(), LoggerUtil.ASUNTO_EMAIL ,contenido.contenidoEmailBienvenida(titularDto.getNombre() ,titularDto.getContrasenia()));
			response.put(LoggerUtil.MENSAJE, titularDto);
			response.put(LoggerUtil.MENSAJE, "Titular creado exitosamente.");
		}catch(ModelException e) {
			response.put(LoggerUtil.MENSAJE, "Error al crear el titular.");
			response.put(LoggerUtil.ERROR, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	/**
	 * Metodo que permite modificar un titular registrado en la Base de Datos.
	 * @param TitularDTO titular
	 * @return ResponseEntity
	 * @throws ModelException
	 */
	
	@PutMapping()
	public ResponseEntity<Map<String, Object>> updateTitular(@RequestBody TitularDTO titular) throws ModelException {
		Map<String, Object> response = new HashMap<>();
		logger.debug("API REST modificando el titular con DNI N° "+titular.getDni());
		try {
			clienteService.modificarTitular(titular);
			response.put(LoggerUtil.OBJETO, titular);
			response.put(LoggerUtil.MENSAJE, "Titular modificado exitosamente.");
		}catch(ModelException e) {
			response.put(LoggerUtil.MENSAJE, "Error al modificar el titular.");
			response.put(LoggerUtil.ERROR, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	/**
	 * Metodo que permite inhabilitar un titular registrado en la Base de Datos.
	 * @param String dni
	 * @return ResponseEntity
	 * @throws ModelException
	 */
	
	@PutMapping("/habilitado/{dni}")
	public ResponseEntity<Map<String, Object>> disableTitular(@PathVariable String dni) throws ModelException {
		Map<String, Object> response = new HashMap<>();
		logger.debug("API REST inhabilitando el titular con DNI N° "+dni);
		try {
			TitularDTO titular = clienteService.buscarTitularPorDni(dni);
			clienteService.inhabilitarTitular(titular);
			titular.setCuenta(null);
			titular.setAdherentes(null);
			response.put(LoggerUtil.OBJETO, titular);
			response.put(LoggerUtil.MENSAJE, "Titular inhabilitado exitosamente.");
		}catch(ModelException e) {
			response.put(LoggerUtil.MENSAJE, "Error al inhabilitar el titular.");
			response.put(LoggerUtil.ERROR, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	/**
	 * Metodo que permite habilitar un titular registrado en la Base de Datos.
	 * @param String dni
	 * @return ResponseEntity
	 * @throws ModelException
	 */
	
	@PutMapping("/deshabilitado/{dni}")
	public ResponseEntity<Map<String, Object>> enableTitular(@PathVariable String dni) throws ModelException {
		Map<String, Object> response = new HashMap<>();
		logger.debug("API REST habilitando el titular con DNI N° "+dni);
		try {
			TitularDTO titular = clienteService.buscarTitularPorDni(dni);
			clienteService.habilitarTitular(titular);
			titular.setCuenta(null);
			titular.setAdherentes(null);
			response.put(LoggerUtil.OBJETO, titular);
			response.put(LoggerUtil.MENSAJE, "Titular habilitado exitosamente.");
		}catch(ModelException e) {
			response.put(LoggerUtil.MENSAJE, "Error al habilitar el titular.");
			response.put(LoggerUtil.ERROR, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	/**
	 * Metodo que permite buscar un titular registrado en la Base de Datos.
	 * @param String dni
	 * @return ResponseEntity
	 * @throws ModelException
	 */
	
	@GetMapping("/{dni}")
	public ResponseEntity<Map<String, Object>> getClientesByDni(@PathVariable String dni) throws ModelException {
		Map<String, Object> response = new HashMap<>();
		logger.debug("API REST buscando el titular con DNI N° "+dni);
		try {
			TitularDTO titular = clienteService.buscarTitularPorDni(dni);
			titular.setCuenta(null);
			titular.setAdherentes(null);
			response.put(LoggerUtil.OBJETO, titular);
			response.put(LoggerUtil.MENSAJE, "Busqueda exitosa.");
		}catch(ModelException e) {
			response.put(LoggerUtil.MENSAJE, "Error al buscar el titular.");
			response.put(LoggerUtil.ERROR, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
