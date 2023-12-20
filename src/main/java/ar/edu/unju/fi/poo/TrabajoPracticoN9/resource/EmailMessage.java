package ar.edu.unju.fi.poo.TrabajoPracticoN9.resource;

/**
 * EmailMessage representara el cuerpo de un Email.
 * @author Grupo06
 *
 */

public class EmailMessage {
	private String to;
	private String subject;
	private String message;
	
	public EmailMessage(String to, String subject, String message) {
		super();
		this.to = to;
		this.subject = subject;
		this.message = message;
	}
	
	public EmailMessage() {
		
	}

	
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
}
