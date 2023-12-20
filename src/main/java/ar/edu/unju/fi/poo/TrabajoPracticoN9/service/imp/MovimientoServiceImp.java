package ar.edu.unju.fi.poo.TrabajoPracticoN9.service.imp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.poo.TrabajoPracticoN9.entity.CuentaBancaria;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entity.Movimiento;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.ComprobanteOperacionDTO;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.MovimientoDTO;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.exception.ModelException;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.repository.CuentaBancariaRepository;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.repository.MovimientoRepository;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.service.MovimientoService;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.util.LoggerUtil;


/**
 * MovimientoServiceImp implementara todos los metodos necesarios para
 * la logica de negocio de los movimientos asociados a una cuenta.
 * @author Grupo6
 *
 */

@Service
public class MovimientoServiceImp implements MovimientoService {

	@Autowired
	CuentaBancariaRepository cuentaRepository;
	
	@Autowired
	MovimientoRepository movimientoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private static final Logger logger = Logger.getLogger(MovimientoServiceImp.class);
	
	
	/**
	 * Metodo que permite generar un comprobante en base a un MovimientoDTO.
	 */
	
	@Override
	public ComprobanteOperacionDTO obtenerComprobante(MovimientoDTO movimiento)throws ModelException {
		if (movimiento==null) {
			throw new ModelException("El movimiento esta vacio.");
		}
		ComprobanteOperacionDTO comprobante = new ComprobanteOperacionDTO();
		comprobante.setDniOperador(movimiento.getDniOperador());
		comprobante.setFechaHora(movimiento.getFechaHora());
		comprobante.setImporte(movimiento.getImporte());
		comprobante.setNroCuenta(movimiento.getCuenta().getNroCuenta());
		comprobante.setOperacion(movimiento.getOperacion());
		comprobante.setSaldo(movimiento.getSaldo());
		comprobante.setNombreOperador(movimiento.getNombreOperador());
		return comprobante;
	}
	
	
	/**
	 * Metodo que permite obtener una lista de comprobantes segun un rango de fechas.
	 */

	@Override
	public List<ComprobanteOperacionDTO> obtenerListaComprobantes(Integer nroCuenta, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws ModelException {
		List<Movimiento> listaMovimientos = movimientoRepository.findByCuentaAndFechaHoraBetweenOrderByFechaHoraDesc(cuentaRepository.findByNroCuenta(nroCuenta), fechaInicio, fechaFin);
		List<ComprobanteOperacionDTO> listaComprobantes = new ArrayList<>();
		for (Movimiento movimiento : listaMovimientos) {
			MovimientoDTO mov =modelMapper.map(movimiento, MovimientoDTO.class);
			ComprobanteOperacionDTO comprobante = obtenerComprobante(mov);
			listaComprobantes.add(comprobante);
		}
		logger.info(LoggerUtil.COMPROBANTE_CREACION_FECHA);
		return listaComprobantes;
	}
	
	
	/**
	 * Metodo que permite obtener una lista de comprobantes por limite de tamaño (20).
	 */
	
	@Override
	public List<ComprobanteOperacionDTO> obtenerListaPorLimite(Integer nroCuenta) {
		CuentaBancaria cuenta = cuentaRepository.findByNroCuenta(nroCuenta);
		List<Movimiento> listaMovimientos = movimientoRepository.findTop20ByCuentaOrderByFechaHoraDesc(cuenta);
		List<ComprobanteOperacionDTO> listaComprobantes = new ArrayList<>();
		for (Movimiento movimiento : listaMovimientos) {
			MovimientoDTO mov =modelMapper.map(movimiento, MovimientoDTO.class);
			ComprobanteOperacionDTO comprobante = obtenerComprobante(mov);
			listaComprobantes.add(comprobante);
		}
		logger.info(LoggerUtil.COMPROBANTE_CREACION_LIMITE);
		return listaComprobantes;
	}
	
}

