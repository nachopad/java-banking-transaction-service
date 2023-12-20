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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ar.edu.unju.fi.poo.TrabajoPracticoN9.resource.EmailMessage;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.service.SendEmailService;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.util.LoggerUtil;

/**
 * SendEmailController representa la capa controladora de los correos electronicos.
 * @author Grupo06
 *
 */

@Controller
@RequestMapping("v1/api/email")
public class SendEmailController {
	
	@Autowired
	private SendEmailService sendEmailService;
	
	private final Logger logger = LoggerFactory.getLogger(SendEmailController.class);
	
	/**
	 * Metodo que permite enviar un correo electronico.
	 * @param EmailMessage emailMessage
	 * @return ResponseEntity
	 * @throws MessagingException
	 */
	
	@PostMapping("/mensaje")
	public ResponseEntity<Map<String, Object>> sendEmail(@RequestBody EmailMessage emailMessage) throws MessagingException {
		Map<String, Object> response = new HashMap<>();
		logger.debug("API REST enviando un correo electronico a "+emailMessage.getTo());
		try {
			sendEmailService.sendEmail(emailMessage.getTo(), emailMessage.getSubject(), emailMessage.getMessage());
			response.put(LoggerUtil.OBJETO, emailMessage);
			response.put(LoggerUtil.MENSAJE, "El email se ha enviado exitosamente.");
			
		}catch(MessagingException e) {
			response.put(LoggerUtil.MENSAJE, "Error al enviar el email.");
			response.put(LoggerUtil.ERROR, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
