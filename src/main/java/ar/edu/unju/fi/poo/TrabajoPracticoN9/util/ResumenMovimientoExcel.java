package ar.edu.unju.fi.poo.TrabajoPracticoN9.util;


import java.io.IOException;


import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.ComprobanteOperacionDTO;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.CuentaBancariaDTO;

/**
 * ResumenMovimientoExcel representara la clase que podra generar
 * un archivo .xls que contendra los movimientos de una cuenta bancaria.
 * @author Grupo06
 *
 */

@Component
public class ResumenMovimientoExcel {
	
	private FormatUtil util = new FormatUtil();
	private static final String COURIER = "Courier New";
	
	/**
	 * Metodo que permite generar el archivo .xls y todo su contenido.
	 * @param CuentaBancariaDTO cuentaDTO
	 * @param List<ComprobanteOperacionDTO> lista
	 * @param HttpServletResponse response
	 * @throws IOException
	 */
	
	public void generarResumen(CuentaBancariaDTO cuenta , List<ComprobanteOperacionDTO> lista, HttpServletResponse response) throws IOException {
		HSSFWorkbook libro = new HSSFWorkbook();
		HSSFSheet hoja = libro.createSheet("Movimientos");
		generarEncabezadoExcel(hoja, cuenta, libro);
		generarCuerpoExcel(hoja, libro, lista);
		ServletOutputStream outputStream = response.getOutputStream();
		libro.write(outputStream);
		libro.close();
		outputStream.close();
	}
	
	
	/**
	 * Metodo que permite generar el encabezado de un archivo excel.
	 * @param HSSFSheet hoja
	 * @param CuentaBancariaDTO cuentaDTO
	 * @param HSSFWorkbook libro
	 */
	
	public void generarEncabezadoExcel(HSSFSheet hoja, CuentaBancariaDTO cuenta, HSSFWorkbook libro) {
		
		HSSFRow fila = hoja.createRow(1);
		HSSFCell celda;

		celda = fila.createCell(3);
		celda.setCellValue("Resumen de Movimientos");
		celda.setCellStyle(estiloTitulo(libro, hoja));
		
		fila = hoja.createRow(4);
		celda = fila.createCell(3);
		celda.setCellValue("Cuenta N°:");
		celda.setCellStyle(estiloEncabezado(libro, hoja));
		
		celda = fila.createCell(5);
		celda.setCellValue(cuenta.getNroCuenta());
		celda.setCellStyle(estiloTexto(libro));
		
		fila = hoja.createRow(5);
		celda = fila.createCell(3);
		celda.setCellValue("Fecha actual:");
		celda.setCellStyle(estiloEncabezado(libro, hoja));
		
		celda = fila.createCell(5);
		celda.setCellValue(util.formatearFecha(LocalDateTime.now()));
		celda.setCellStyle(estiloTexto(libro));
		
		fila = hoja.createRow(6);
		celda = fila.createCell(3);
		celda.setCellValue("Hora:");
		celda.setCellStyle(estiloEncabezado(libro, hoja));
		
		celda = fila.createCell(5);
		celda.setCellValue(util.formatearHora(LocalDateTime.now()));
		celda.setCellStyle(estiloTexto(libro));
		
		fila = hoja.createRow(7);
		celda = fila.createCell(3);
		celda.setCellValue("Titular:");
		celda.setCellStyle(estiloEncabezado(libro, hoja));
		
		celda = fila.createCell(5);
		celda.setCellValue(cuenta.getTitular().getNombre());
		celda.setCellStyle(estiloTexto(libro));
		
	}
	
	
	/**
	 * Metodo que permite generar el cuerpo de un archivo excel,
	 * genera las celdas especificadas y su contenido.
	 * @param HSSFSheet hoja
	 * @param HSSFWorkbook libro
	 * @param List<ComprobanteOperacionDTO> lista
	 */
	
	public void generarCuerpoExcel(HSSFSheet hoja, HSSFWorkbook libro, List<ComprobanteOperacionDTO> lista) {
		String[] columnas = {"N°","FECHA - HORA","IMPORTE","SALDO","OPERADOR"};
		HSSFRow fila = hoja.createRow(10);
		HSSFCell celda;
		
		for(int i = 2; i<7 ; i++) {
			celda = fila.createCell(i);
			celda.setCellValue(columnas[i-2]);
			celda.setCellStyle(estiloCelda(libro));
		}
		
		Integer nroFila = 11;
		Integer nroCelda = 1;
		Integer nextNumero = 1;
		
		for(ComprobanteOperacionDTO c : lista) {
			fila = hoja.createRow(nroFila);
			nroCelda++;
			
			celda = fila.createCell(nroCelda++);
			celda.setCellValue(nextNumero++);
			celda.setCellStyle(estiloTexto(libro));
			
			celda = fila.createCell(nroCelda++);
			celda.setCellValue(util.formatearFechaString(c.getFechaHora()));
			celda.setCellStyle(estiloTexto(libro));
			
			celda = fila.createCell(nroCelda++);
			celda.setCellValue(util.formatearImporte(c.getImporte())+" "+verificarTipoMovimiento(c.getOperacion()));
			celda.setCellStyle(estiloTexto(libro));
			
			celda = fila.createCell(nroCelda++);
			celda.setCellValue(util.formatearImporte(c.getSaldo()));
			celda.setCellStyle(estiloTexto(libro));
			
			celda = fila.createCell(nroCelda++);
			celda.setCellValue(c.getNombreOperador());
			celda.setCellStyle(estiloTexto(libro));
			
			nroFila++;
			nroCelda = 1;
		}
		
	}
	
	
	/**
	 * Metodo que permite generar el estilo de fuente utilizado en el titulo
	 * de un archivo excel.
	 * @param HSSFWorkbook workbook
	 * @param HSSFSheet hoja
	 * @return CellStyle estilo
	 */
	
	private CellStyle estiloTitulo(HSSFWorkbook workbook, HSSFSheet hoja) {
				
		Font fuente = workbook.createFont();
		CellStyle estiloTitulo = workbook.createCellStyle();
		hoja.addMergedRegion(new CellRangeAddress(1,1,3,5));
	 
		estiloTitulo.setBorderRight(BorderStyle.THIN);
		estiloTitulo.setBorderLeft(BorderStyle.THIN);
		estiloTitulo.setBorderTop(BorderStyle.THIN);
		estiloTitulo.setBorderBottom(BorderStyle.THIN);
		
		estiloTitulo.setFillForegroundColor(IndexedColors.BLUE.index);
		estiloTitulo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		estiloTitulo.setAlignment(HorizontalAlignment.CENTER);
		fuente.setColor(IndexedColors.WHITE.getIndex());
		fuente.setBold(true);
		fuente.setFontName(COURIER); 
		fuente.setFontHeight((short)(16*20));
		estiloTitulo.setFont(fuente);
		
		return estiloTitulo;
	}
	
	
	/**
	 * Metodo que permite generar el estilo de fuente utilizado en el encabezado
	 * de un archivo excel.
	 * @param HSSFWorkbook workbook
	 * @param HSSFSheet hoja
	 * @return CellStyle estilo
	 */
	
	private CellStyle estiloEncabezado(HSSFWorkbook workbook, HSSFSheet hoja) {
		
		Font fuente = workbook.createFont();
		CellStyle estiloEncabezado = workbook.createCellStyle();
		hoja.setColumnWidth(2,2000);
		hoja.setColumnWidth(3,7000);
		hoja.setColumnWidth(4,7000);
		hoja.setColumnWidth(5,7000);
		hoja.setColumnWidth(6,7000);
		
		estiloEncabezado.setBorderRight(BorderStyle.THIN);
		estiloEncabezado.setBorderLeft(BorderStyle.THIN);
		estiloEncabezado.setBorderTop(BorderStyle.THIN);
		estiloEncabezado.setBorderBottom(BorderStyle.THIN);
		
		estiloEncabezado.setAlignment(HorizontalAlignment.CENTER);
		fuente.setColor(IndexedColors.BLACK.getIndex());
		fuente.setUnderline(HSSFFont.U_SINGLE);
		fuente.setBold(true);
		fuente.setFontName(COURIER); 
		fuente.setFontHeight((short)(12*20));
		estiloEncabezado.setFont(fuente);
		
		return estiloEncabezado;
	}
	
	
	/**
	 * Metodo que permite generar el estilo de fuente utilizado en los textos
	 * de un archivo excel.
	 * @param HSSFWorkbook workbook
	 * @param HSSFSheet hoja
	 * @return CellStyle estilo
	 */
	
	private CellStyle estiloTexto(HSSFWorkbook workbook) {
		
		Font fuente = workbook.createFont();
		CellStyle estiloTexto = workbook.createCellStyle();
		
		estiloTexto.setBorderRight(BorderStyle.THIN);
		estiloTexto.setBorderLeft(BorderStyle.THIN);
		estiloTexto.setBorderTop(BorderStyle.THIN);
		estiloTexto.setBorderBottom(BorderStyle.THIN);
		
		estiloTexto.setAlignment(HorizontalAlignment.CENTER);
		fuente.setColor(IndexedColors.BLACK.getIndex());
		fuente.setFontName(COURIER); 
		fuente.setFontHeight((short)(11*20));
		estiloTexto.setFont(fuente);
		
		return estiloTexto;
	}
	
	
	/**
	 * Metodo que permite generar el estilo de fuente utilizado en el cuerpo
	 * de un archivo excel.
	 * @param HSSFWorkbook workbook
	 * @param HSSFSheet hoja
	 * @return CellStyle estilo
	 */
	
	private CellStyle estiloCelda(HSSFWorkbook workbook) {
			
		Font fuente = workbook.createFont();
		CellStyle estiloCelda = workbook.createCellStyle();
		
		estiloCelda.setBorderRight(BorderStyle.THIN);
		estiloCelda.setBorderLeft(BorderStyle.THIN);
		estiloCelda.setBorderTop(BorderStyle.THIN);
		estiloCelda.setBorderBottom(BorderStyle.THIN);
		
		estiloCelda.setFillForegroundColor(IndexedColors.BLUE.index);
		estiloCelda.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		estiloCelda.setAlignment(HorizontalAlignment.CENTER);
		fuente.setColor(IndexedColors.WHITE.getIndex());
		fuente.setBold(true);
		fuente.setFontName(COURIER); 
		fuente.setFontHeight((short)(11*20));
		estiloCelda.setFont(fuente);
		
		return estiloCelda;
	}

	
	/**
	 * Metodo que permite obtener el tipo de un movimiento (Extraccion o Deposito).
	 * 
	 * @param Boolean tipo
	 * @return String tipo
	 */
	
	private String verificarTipoMovimiento(Boolean tipo) {
		if(tipo==Boolean.FALSE) {
			return " E";
		}
		return " D";
	}
	
}
