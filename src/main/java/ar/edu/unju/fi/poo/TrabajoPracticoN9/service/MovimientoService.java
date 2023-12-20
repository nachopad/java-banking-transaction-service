package ar.edu.unju.fi.poo.TrabajoPracticoN9.service;

import java.time.LocalDateTime;
import java.util.List;

import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.ComprobanteOperacionDTO;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.MovimientoDTO;

public interface MovimientoService {
	
	public ComprobanteOperacionDTO obtenerComprobante (MovimientoDTO movimiento);
	public List<ComprobanteOperacionDTO> obtenerListaComprobantes (Integer nroCuenta, LocalDateTime fechaInicio, LocalDateTime fechaFin);
	public List<ComprobanteOperacionDTO>obtenerListaPorLimite(Integer nroCuenta);
}
