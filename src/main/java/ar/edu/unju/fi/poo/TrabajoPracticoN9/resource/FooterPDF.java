package ar.edu.unju.fi.poo.TrabajoPracticoN9.resource;


import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * FooterPDF representara la clase encargada de generar el pie de pagina
 * de cada archivo pdf que implemente dicha clase. 
 * Es un evento que extiende de la interfaz PdfPageEventHelper.
 * @author Grupo06
 *
 */

public class FooterPDF extends PdfPageEventHelper{
	
	private String textoPiePagina;
    
    public FooterPDF(String textoPiePagina) {
		this.textoPiePagina = textoPiePagina;
	}
    
	@Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, new Phrase(String.format(" %d ",  writer.getPageNumber())), document.right() - 2 , document.bottom() - 20, 0);
        ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, new Phrase(String.format(textoPiePagina,  writer.getPageNumber())), document.left() - 2 , document.bottom() - 20, 0);
    }
    
}
