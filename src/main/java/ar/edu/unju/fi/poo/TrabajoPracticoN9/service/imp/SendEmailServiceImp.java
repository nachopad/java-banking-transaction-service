package ar.edu.unju.fi.poo.TrabajoPracticoN9.service.imp;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.poo.TrabajoPracticoN9.service.SendEmailService;

/**
 * SendEmailServiceImp implementara todos los metodos necesarios para
 * la logica de negocio de los emails a enviar en cada situacion.
 * @author Grupo6
 *
 */

@Service
public class SendEmailServiceImp implements SendEmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final Logger logger = Logger.getLogger(SendEmailServiceImp.class);
	
	/**
	 * Metodo que permite enviar un email de manera general.
	 * Recibe como parametros un email de destino, un asunto y un texto (contenido).
	 */
	
	@Override
	public void sendEmail(String to, String subject, String text) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper messageEmail = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		messageEmail.setFrom("exheniasoftware@gmail.com");
		messageEmail.setTo(to);
		messageEmail.setSubject(subject);
		messageEmail.setText(text, true);
		logger.info("El email se ha enviado exitosamente a "+to);
		javaMailSender.send(mimeMessage);
	}
	
}
