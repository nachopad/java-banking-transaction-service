package ar.edu.unju.fi.poo.TrabajoPracticoN9.util;

import java.time.LocalDateTime;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.CuentaBancariaDTO;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.resource.FooterPDF;

/**
 * La clase ReportesUtil contendra las diferentes partes que podran
 * ser utilizadas en un archivo de tipo pdf.
 * Contiene el encabezado, pie de pagina y la metaData.
 * @author Grupo06
 *
 */

public class ReportesUtil {
	
	//estilos utilizados en los metodos de la clase.
	private static final String RUTA_IMG = "src\\main\\resources\\static\\images\\logo.png";
	private static final Font SUBRAYADO = new Font(Font.FontFamily.COURIER,13,Font.UNDERLINE|Font.BOLD);
	private static final Font ESTILO_TEXTO = new Font(Font.FontFamily.COURIER,11);
	private static final Font ESTILO_TITULO = new Font(Font.FontFamily.COURIER,16,Font.BOLD, BaseColor.WHITE);
	
	private FormatUtil util = new FormatUtil();
	
	/**
	 * Metodo que permite generar el encabezado de un archivo pdf.
	 * 
	 * @param CuentaBancariaDTO cuentaDTO
	 * @param Document document
	 * @param String titulo
	 * @throws Exception
	 */
	
	public void generarEncabezadoPDF(CuentaBancariaDTO cuenta, Document document, String titulo) throws Exception{
		
		Chunk chunk = new Chunk(titulo, ESTILO_TITULO);
		chunk.setBackground(new BaseColor(20,3,164));
		Paragraph tituloChunk = new Paragraph(chunk);
		tituloChunk.setAlignment(1);
		tituloChunk.setSpacingAfter(30);
		tituloChunk.setSpacingBefore(20);
		
		Paragraph subtitulo = new Paragraph("Informacion de su cuenta", SUBRAYADO);
		subtitulo.setSpacingAfter(10);
		subtitulo.setSpacingBefore(10);
		
		Paragraph numCuenta = new Paragraph("Cuenta N°:  "+cuenta.getNroCuenta(), ESTILO_TEXTO);
		Paragraph fecha = new Paragraph("Fecha actual:  "+util.formatearFecha(LocalDateTime.now()), ESTILO_TEXTO);
		Paragraph hora = new Paragraph("Hora:  "+util.formatearHora(LocalDateTime.now()), ESTILO_TEXTO);
		Paragraph titular = new Paragraph("Titular:  "+cuenta.getTitular().getNombre(), ESTILO_TEXTO);
		titular.setSpacingAfter(20);
		
		Image imagen = null;
		imagen = Image.getInstance(RUTA_IMG);
		imagen.scaleAbsolute(100, 50);
		imagen.setAbsolutePosition(2, 750);
		
		document.add(imagen);
		document.add(tituloChunk);
		document.add(subtitulo);
		document.add(numCuenta);
		document.add(fecha);
		document.add(hora);
		document.add(titular);
	}
	
	/**
	 * Metodo que permite generar el pie de pagina de un archivo pdf.
	 * 
	 * @param PdfWriter writer
	 * @param String texto
	 */
	
	public void generarPiePagina(PdfWriter writer, String textoPiePagina) {
		FooterPDF evento = new FooterPDF(textoPiePagina);
		writer.setPageEvent(evento);
    }
	
	/**
	 * Metodo que permite generar la metaData de un archivo pdf.
	 * 
	 * @param Document document
	 * @param String titulo
	 * @param String asunto
	 * @param String palabrasClave
	 * @param String autor
	 * @param String creador
	 */
    
	public void generarMetaDataPDF(Document document, String titulo, String asunto, 
			String palabrasClave, String autor, String creador) {
		
		document.addTitle(titulo);
		document.addSubject(asunto);
		document.addKeywords(palabrasClave);
		document.addAuthor(autor);
		document.addCreator(creador);
	}
}
