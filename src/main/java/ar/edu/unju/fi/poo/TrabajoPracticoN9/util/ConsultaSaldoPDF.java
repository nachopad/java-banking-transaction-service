package ar.edu.unju.fi.poo.TrabajoPracticoN9.util;

import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.CuentaBancariaDTO;

/**
 * Representa la clase que generará la consulta de saldo de 
 * una cuenta, realizado en un archivo con formato PDF. 
 * @author Grupo06
 */
public class ConsultaSaldoPDF {
	
	private static final String TITULO = "Consulta de Saldo";
	private static final Font ESTILO_CELDA_FONDO = new Font(Font.FontFamily.COURIER,15,Font.BOLD, BaseColor.WHITE);
	private static final Font ESTILO_CELDA_SIN_FONDO = new Font(Font.FontFamily.COURIER,15);
	
	private ReportesUtil reportUtil = new ReportesUtil();
	private FormatUtil formatUtil = new FormatUtil();
	
	/**
	 * Metodo que tiene por objetivo generar el archivo PDF, 
	 * con su respectivo contenido. 
	 * @param CuentaBancariaDTO cuentaDTO
	 * @param HttpServletResponse response
	 * @throws Exception
	 */
	public void generarConsulta(CuentaBancariaDTO cuenta, HttpServletResponse response) throws Exception {
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
		reportUtil.generarPiePagina(writer, "Exhenia Bank");
		document.open();
		reportUtil.generarMetaDataPDF(document, TITULO, "Consultas PDF", "Consulta, PDF", "Grupo06", "");
		reportUtil.generarEncabezadoPDF(cuenta, document, TITULO);
		generarCuerpo(cuenta,document);
		document.close();
	}
	
	
	/**
	 * Metodo que tiene por objetivo generar el cuerpo 
	 * del archivo PDF, mostrando informacion del 
	 * saldo actual de la cuenta solicitada. 
	 * @param CuentaBancariaDTO cuentaDTO
	 * @param Document document
	 * @throws Exception
	 */
	private void generarCuerpo(CuentaBancariaDTO cuenta, Document document) throws Exception {
		
		PdfPTable tabla = new PdfPTable(2);
		tabla.setWidthPercentage(100);
		
		PdfPCell celdaSaldo = new PdfPCell(new Phrase("Saldo actual: ", ESTILO_CELDA_FONDO));
		celdaSaldo.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaSaldo.setVerticalAlignment(Element.ALIGN_MIDDLE);
		celdaSaldo.setBackgroundColor(new BaseColor(20,3,164));
		
		PdfPCell celdaSaldo2 = new PdfPCell(new Phrase(formatUtil.formatearImporte(cuenta.getSaldoActual()), ESTILO_CELDA_SIN_FONDO));
		celdaSaldo2.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaSaldo2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		tabla.addCell(celdaSaldo);
		tabla.addCell(celdaSaldo2);
		document.add(tabla);
	}
	
}
