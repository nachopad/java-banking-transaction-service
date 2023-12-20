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
import org.springframework.web.bind.annotation.RequestParam;

import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.AdherenteDTO;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.TitularDTO;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.exception.ModelException;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.service.ClienteService;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.service.SendEmailService;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.util.ContenidoEmail;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.util.LoggerUtil;

/**
 * AdherenteController representa la capa controladora de los Adherentes.
 * @author Grupo06
 *
 */

@Controller
@RequestMapping("v1/api/adherente")
public class AdherenteController {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	SendEmailService sendMailService;
	
	private ContenidoEmail contenido = new ContenidoEmail();

	private final Logger logger = LoggerFactory.getLogger(TitularController.class);
	
	/**
	 * Metodo que permite registrar un adherente y asociarlo a un titular.
	 * @param String dni
	 * @param AdherenteDTO adherente
	 * @return ResponseEntity
	 * @throws ModelException
	 * @throws MessagingException
	 */
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> saveAdherente(@RequestParam String dni, @RequestBody AdherenteDTO adherente ) throws ModelException, MessagingException {
		logger.debug("API REST dando de alta el adherente con DNI N°: "+adherente.getDni());
		Map<String, Object> response = new HashMap<>();
		try {
			TitularDTO titular = clienteService.buscarTitularPorDni(dni);
			AdherenteDTO adherenteDto = clienteService.registrarAdherente(adherente);
			clienteService.agregarAdherente(titular, adherenteDto);	
			sendMailService.sendEmail(adherenteDto.getCorreoElectronico(), LoggerUtil.ASUNTO_EMAIL, contenido.contenidoEmailBienvenida(adherenteDto.getNombre() ,adherenteDto.getContrasenia()));
			response.put(LoggerUtil.MENSAJE, adherenteDto);
			response.put(LoggerUtil.MENSAJE, "Adherente registrado exitosamente.");
		}catch(ModelException e){
			response.put(LoggerUtil.MENSAJE, "Error al crear el adherente.");
			response.put(LoggerUtil.ERROR, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	/**
	 * Metodo que permite modificar un adherente registrado en la Base de Datos.
	 * @param AdherenteDTO adherente
	 * @return ResponseEntity
	 * @throws ModelException
	 */
	
	@PutMapping
	public ResponseEntity<Map<String, Object>> updateAdherente(@RequestBody AdherenteDTO adherenteDTO) throws ModelException {
		Map<String, Object> response = new HashMap<>();
		logger.debug("API REST modificando el adherente con DNI N°: "+adherenteDTO.getDni());
		try {
			clienteService.modificarAdherente(adherenteDTO);
			response.put(LoggerUtil.OBJETO, adherenteDTO);
			response.put(LoggerUtil.MENSAJE, "Adherente modificado exitosamente.");
		}catch(ModelException e) {
			response.put(LoggerUtil.MENSAJE, "Error al modificar el adherente.");
			response.put(LoggerUtil.ERROR, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
		
	
	/**
	 * Metodo que permite inhabilitar un adherente registrado en la Base de Datos.
	 * @param String dni
	 * @return ResponseEntity
	 * @throws ModelException
	 */
	
	@PutMapping("/habilitado/{dni}")
	public ResponseEntity<Map<String, Object>> disableAdherente(@PathVariable String dni) throws ModelException{
		Map<String, Object> response = new HashMap<>();
		logger.debug("API REST inhabilitando el adherente con DNI N°: "+dni);
		try {
			AdherenteDTO adherente = clienteService.buscarAdherentePorDni(dni);
			clienteService.inhabilitarAdherente(adherente);
			adherente.setTitular(null);
			response.put(LoggerUtil.OBJETO, adherente);
			response.put(LoggerUtil.MENSAJE, "El Adherente fue inhabilitado exitosamente.");
		}catch(ModelException e) {
			response.put(LoggerUtil.MENSAJE, "Error al inhabilitar el adherente.");
			response.put(LoggerUtil.ERROR, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	/**
	 * Metodo uqe permite habilitar un adherente registrado en la Base de Datos.
	 * @param String dni
	 * @return ResponseEntity
	 * @throws ModelException
	 */
	
	@PutMapping("/inhabilitado/{dni}")
	public ResponseEntity<Map<String, Object>> enableAdherente(@PathVariable String dni) throws ModelException {
		Map<String, Object> response = new HashMap<>();
		logger.debug("API REST habilitando el adherente con DNI N°: "+dni);
		try {
			AdherenteDTO adherente = clienteService.buscarAdherentePorDni(dni);
			clienteService.habilitarAdherente(adherente);
			adherente.setTitular(null);
			response.put(LoggerUtil.OBJETO, adherente);
			response.put(LoggerUtil.MENSAJE, "El Adherente fue habilitado exitosamente.");
		}catch(ModelException e) {
			response.put(LoggerUtil.MENSAJE, "Error al habilitar el adherente");
			response.put(LoggerUtil.ERROR, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	
	/**
	 * Metodo que permite buscar un adherente registrado en la Base de Datos.
	 * @param String dni
	 * @return ResponseEntity
	 * @throws ModelException
	 */
	
	@GetMapping("/{dni}")
	public ResponseEntity<Map<String, Object>> getAdherenteByDni(@PathVariable String dni) throws ModelException {
		Map<String, Object> response = new HashMap<>();
		logger.debug("API REST buscando el adherente con DNI N°: "+dni);
		try {
			AdherenteDTO adherente = clienteService.buscarAdherentePorDni(dni);
			adherente.setTitular(null);
			response.put(LoggerUtil.OBJETO, adherente);
			response.put(LoggerUtil.MENSAJE, "Busqueda exitosa.");
		}catch(ModelException e){
			response.put(LoggerUtil.MENSAJE, "Error al buscar el adherente.");
			response.put(LoggerUtil.ERROR, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
}
