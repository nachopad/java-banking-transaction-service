package ar.edu.unju.fi.poo.TrabajoPracticoN9.service.imp;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.poo.TrabajoPracticoN9.entity.Adherente;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entity.CuentaBancaria;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entity.Movimiento;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entity.Titular;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.AdherenteDTO;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.ComprobanteOperacionDTO;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.CuentaBancariaDTO;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.MovimientoDTO;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.TitularDTO;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.exception.ModelException;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.repository.ClienteRepository;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.repository.CuentaBancariaRepository;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.repository.MovimientoRepository;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.service.CuentaBancariaService;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.util.FormatUtil;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.util.LoggerUtil;


/**
 * CuentaBancariaServiceImp implementara todos los metodos necesarios para
 * la logica de negocio de las cuentas bancarias y los movimientos. 
 * @author Grupo6
 *
 */

@Service
public class CuentaBancariaServiceImp implements CuentaBancariaService {
    
	@Autowired
	CuentaBancariaRepository cuentaBancariaRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	MovimientoRepository movimientoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private static final Logger logger = Logger.getLogger(CuentaBancariaServiceImp.class);
	
    private static final AtomicInteger count = new AtomicInteger(0); 
    
    private static Double limiteExtraccion = (double) 15000;
	
	private FormatUtil util = new FormatUtil();
	
	
	/**
	 * Metodo que permite crear una cuenta asociandola con su titular.
	 * Aun no la registra en la Base de Datos. 
	 */
	
	@Override
	public CuentaBancariaDTO crearCuenta(TitularDTO titularDTO) {
		CuentaBancariaDTO cuenta = new CuentaBancariaDTO();
		cuenta.setFechaIngreso(LocalDate.now());
		cuenta.setNroCuenta(count.incrementAndGet());
		cuenta.setLimiteExtraccion(limiteExtraccion);
		cuenta.setSaldoActual((double) 0);
		cuenta.setMovimientos(new ArrayList<>());
		cuenta.setTitular(validarTitular(titularDTO));
		logger.info(LoggerUtil.CUENTA_CREACION+cuenta.getNroCuenta());
		return cuenta;
	}
	

	/**
	 * Metodo que permite habilitar una cuenta que ya ha sido creada.
	 * Este metodo ya registra una cuenta creada en la Base de Datos.
	 */
	
	@Override
	public CuentaBancariaDTO habilitarCuenta(CuentaBancariaDTO cuentaDTO) {
		CuentaBancaria cuentaEntidad = modelMapper.map(cuentaDTO, CuentaBancaria.class);
		cuentaEntidad.setEstado(true);
		cuentaBancariaRepository.save(cuentaEntidad);
		logger.info("La cuenta : "+cuentaEntidad.getNroCuenta() +" fue agregada a la bd");
		Titular titularEntidad = modelMapper.map(cuentaDTO.getTitular(), Titular.class);
		titularEntidad.setEstado(true);
		titularEntidad.setCuenta(cuentaEntidad);
		clienteRepository.save(titularEntidad);
		logger.info(LoggerUtil.CUENTA_ASOCIACION+titularEntidad.getDni());
		return cuentaDTO;
	}
	
	
	/**
	 * Metodo que permite modificar una cuenta bancaria.
	 */
	
	@Override
	public CuentaBancariaDTO modificarCuenta(CuentaBancariaDTO cuentaDTO) {
		validarExistenciaCuenta(cuentaDTO);
		cuentaDTO.setIdCuenta(cuentaBancariaRepository.findByNroCuenta(cuentaDTO.getNroCuenta()).getIdCuenta());
		CuentaBancaria cuentaEntidad = obtenerCuenta(cuentaDTO);
		cuentaBancariaRepository.save(cuentaEntidad);
		logger.info(LoggerUtil.CUENTA_MODIFICACION+cuentaDTO.getNroCuenta());
		return cuentaDTO;
	}
	

	/**
	 * Metodo que permite buscar una cuenta por su numero.
	 */
	
	@Override
	public CuentaBancariaDTO buscarCuentaPorNumero(Integer numeroCuenta) throws ModelException{
		CuentaBancaria cuentaReal = cuentaBancariaRepository.findByNroCuenta(numeroCuenta);
		if (cuentaReal==null) {
			throw new ModelException(LoggerUtil.CUENTA_VALIDACION_NO_EXISTENCIA+numeroCuenta);
		}
		logger.debug("Buscando cuenta por numero...");
		return obtenerCuentaDTO(cuentaReal);
	}
	
	
	/**
	 * Metodo que permite validar si un titular se encuentra registrado.
	 * @param TitularDTO titularDTO
	 * @return Titular titularEntidad
	 */
	
	public TitularDTO validarTitular (TitularDTO titularDTO) throws ModelException{
		if (clienteRepository.findByDni(titularDTO.getDni()) instanceof Titular) {
			return titularDTO;
		}
		throw new ModelException("El DNI ingresado no pertenece a un titular. DNI N° "+titularDTO.getDni());
	}
	
	
	/**
	 * Metodo que valida correctamente una cuenta si no se encuentra registrada 
	 * en la Base de Datos.
	 */
	
	@Override
	public CuentaBancariaDTO validarCuenta (CuentaBancariaDTO cuentaDTO) throws ModelException {
		CuentaBancaria cuentaEntidad = cuentaBancariaRepository.findByNroCuenta(cuentaDTO.getNroCuenta());
		if(cuentaEntidad!=null) {
			throw new ModelException(LoggerUtil.CUENTA_VALIDACION_EXISTENCIA+cuentaDTO.getNroCuenta());
		}else {
			logger.debug("Validando cuenta...");
			return cuentaDTO;
		}
	}
	
	
	/**
	 * Metodo que valida correctamente una cuenta si se encuentra registrada 
	 * en la Base de Datos.
	 */
	
	@Override
	public CuentaBancariaDTO validarExistenciaCuenta(CuentaBancariaDTO cuentaDTO) throws ModelException{
		CuentaBancaria cuentaEntidad = cuentaBancariaRepository.findByNroCuenta(cuentaDTO.getNroCuenta());
		if(cuentaEntidad==null) {
			throw new ModelException(LoggerUtil.CUENTA_VALIDACION_NO_EXISTENCIA+cuentaDTO.getNroCuenta());
		}else {
			logger.debug("Validando cuenta...");
			return cuentaDTO;
		}
	}
	
	
	/**
	 * Metodo que permite inhabilitar una cuenta de manera logica (estado).
	 */
	
	@Override
	public CuentaBancariaDTO eliminarCuentaLogica(CuentaBancariaDTO cuentaDTO) {
		validarExistenciaCuenta(cuentaDTO);
		cuentaDTO.setIdCuenta(cuentaBancariaRepository.findByNroCuenta(cuentaDTO.getNroCuenta()).getIdCuenta());
		cuentaDTO.setEstado(false);
		CuentaBancaria cuentaEntidad = obtenerCuenta(cuentaDTO);
		cuentaBancariaRepository.save(cuentaEntidad);
		logger.info(LoggerUtil.CUENTA_INHABILITACION+cuentaDTO.getNroCuenta());
		return cuentaDTO;
	}
	
	
	/**
	 * Metodo que permite habilitar una cuenta de manera logica (estado).
	 */
	
	@Override
	public CuentaBancariaDTO habilitarCuentaLogica(CuentaBancariaDTO cuentaDTO) {
		validarExistenciaCuenta(cuentaDTO);
		cuentaDTO.setIdCuenta(cuentaBancariaRepository.findByNroCuenta(cuentaDTO.getNroCuenta()).getIdCuenta());
		cuentaDTO.setEstado(true);
		CuentaBancaria cuentaEntidad = obtenerCuenta(cuentaDTO);
		cuentaBancariaRepository.save(cuentaEntidad);
		logger.info(LoggerUtil.CUENTA_INHABILITACION+cuentaDTO.getNroCuenta());
		return cuentaDTO;
	}
	

	/**
	 * Metodo que permite realizar un movimiento que ya ha sido creado
	 * y validado con anterioridad. Es decir, el movimiento afectara
	 * el el saldo de la cuenta asociada a ese movimiento.
	 */
	
	/**
	 * Metodo que convierte una cuentaDTO a Cuenta entidad.
	 * @param CuentaBancariaDTO cuentaDTO
	 * @return CuentaBancaria cuentaEntidad
	 */

	private CuentaBancaria obtenerCuenta (CuentaBancariaDTO cuentaDTO) {
		logger.debug("Convirtiendo cuentaDTO a Cuenta entidad...");
		return modelMapper.map(cuentaDTO, CuentaBancaria.class);
	}
	
	
	/**
	 * Metodo que convierte una Cuenta entidad a una cuentaDTO.
	 * @param CuentaBancaria cuentaEntidad
	 * @return CuentaBancariaDTO cuentaDTO
	 */
	
	private CuentaBancariaDTO obtenerCuentaDTO (CuentaBancaria cuenta) {
		logger.debug("Convirtiendo cuenta entidad a cuentaDTO...");
		return modelMapper.map(cuenta, CuentaBancariaDTO.class);
	}

	
	/*
	 * Metodo que valida la existencia de un titular. 
	 * Es decir que ya se encuentre registrado en la Base de Datos.
	 */
	
	@Override
	public TitularDTO validarExistenciaTitular(TitularDTO titularDTO) {
		Titular titularBuscado = modelMapper.map(clienteRepository.findByDni(titularDTO.getDni()), Titular.class);
		if(titularBuscado.getCuenta()!=null) {
			throw new ModelException(LoggerUtil.CUENTA_TITULAR_EXISTENCIA+titularDTO.getDni());
		}else {
			logger.debug("Validando existencia del titular...");
			return titularDTO;
		}
	}

	
	/**
	 * Metodo que permite crear un movimiento asociado a una cuenta y su titular.
	 * Aun no registra el movimiento en la Base de Datos.
	 */
	
	@Override
	public MovimientoDTO crearMovimiento (CuentaBancariaDTO cuentaDTO, String dniOperador,  Double importe) {
		MovimientoDTO movimientoDTO = new MovimientoDTO();
		cuentaDTO.setIdCuenta(cuentaBancariaRepository.findByNroCuenta(cuentaDTO.getNroCuenta()).getIdCuenta());
		movimientoDTO.setCuenta(cuentaDTO);
		movimientoDTO.setFechaHora(LocalDateTime.now().toString());		
		movimientoDTO.setDniOperador(dniOperador);
		movimientoDTO.setImporte(importe);
		movimientoDTO.setNombreOperador(clienteRepository.findByDni(dniOperador).getNombre());
		logger.info(LoggerUtil.MOVIMIENTO_CREACION+cuentaDTO.getNroCuenta());
		return movimientoDTO;
	}
	
	/**
	 * Metodo que permite realizar un deposito de dinero en una cuenta.
	 */
	
	@Override
	public ComprobanteOperacionDTO realizarDeposito(CuentaBancariaDTO cuenta, Double importe, String dniOperador) {
		CuentaBancaria cuentaReal = cuentaBancariaRepository.findByNroCuenta(cuenta.getNroCuenta());
		cuentaReal.setSaldoActual(cuentaReal.getSaldoActual()+importe);
		MovimientoDTO movimiento = crearMovimiento(cuenta, dniOperador, importe);
		movimiento.setOperacion(true);
		movimiento.setSaldo(cuenta.getSaldoActual()+importe);
		cuentaReal.getMovimientos().add(modelMapper.map(movimiento, Movimiento.class));
		cuentaBancariaRepository.save(cuentaReal);
		Movimiento movimientoReal = modelMapper.map(movimiento, Movimiento.class);
		movimientoReal.setFechaHora(util.formatearStringaLocalDateTimeReal(movimiento.getFechaHora()));
		movimientoRepository.save(movimientoReal);
		logger.info(LoggerUtil.MOVIMIENTO_DEPOSITO+cuenta.getNroCuenta());
		return crearComprobante(movimiento, cuenta);
	}
	
	/**
	 * Metodo que permite realizar una extraccion de dinero en una cuenta.
	 */

	@Override
	public ComprobanteOperacionDTO realizarExtraccion(CuentaBancariaDTO cuenta, Double importe, String dniOperador) {
		CuentaBancaria cuentaReal = cuentaBancariaRepository.findByNroCuenta(cuenta.getNroCuenta());
		cuentaReal.setSaldoActual(cuentaReal.getSaldoActual()-importe);
		MovimientoDTO movimiento = crearMovimiento(cuenta, dniOperador, importe);
		movimiento.setOperacion(false);
		movimiento.setSaldo(cuenta.getSaldoActual()-importe);
		cuentaReal.getMovimientos().add(modelMapper.map(movimiento, Movimiento.class));
		cuentaBancariaRepository.save(cuentaReal);
		Movimiento movimientoReal = modelMapper.map(movimiento, Movimiento.class);
		movimientoReal.setFechaHora(util.formatearStringaLocalDateTimeReal(movimiento.getFechaHora()));
		movimientoRepository.save(movimientoReal);
		logger.info(LoggerUtil.MOVIMIENTO_EXTRACCION+cuenta.getNroCuenta());
		return crearComprobante(movimiento, cuenta);
	}


	/**
	 * Metodo que permite validar si un movmiento puede realizarse.
	 * Podra realizarse en caso de que el importe a extraer sea menor que
	 * el limite de extraccion de la cuenta, y si la cuenta posee el saldo suficiente.
	 */
	
	@Override
	public CuentaBancariaDTO validarMovimiento (CuentaBancariaDTO cuenta , Double importe) throws ModelException {
		if (importe<=cuenta.getSaldoActual()&&importe<=cuenta.getLimiteExtraccion()) {
			return cuenta;
		}else {
			throw new ModelException(LoggerUtil.MOVIMINTO_VALIDACION);
		}
	}
	
	
	/**
	 * Metodo que permite crear un comprobante (similar a un MovimientoDTO)
	 * @param MovimientoDTO movimientoDTO
	 * @param CuentaBancariaDTO cuentaDTO
	 * @return ComprobanteOperacionDTO comprobanteDTO
	 */
	
	public ComprobanteOperacionDTO crearComprobante (MovimientoDTO movimiento, CuentaBancariaDTO cuenta) {
		logger.debug("Creando comprobante...");
		ComprobanteOperacionDTO comprobante = new ComprobanteOperacionDTO();
		comprobante.setDniOperador(movimiento.getDniOperador());
		comprobante.setFechaHora(movimiento.getFechaHora());
		comprobante.setImporte(movimiento.getImporte());
		comprobante.setNroCuenta(cuenta.getNroCuenta());
		comprobante.setOperacion(movimiento.getOperacion());
		comprobante.setSaldo(cuentaBancariaRepository.findByNroCuenta(cuenta.getNroCuenta()).getSaldoActual());
		comprobante.setNombreOperador(clienteRepository.findByDni(movimiento.getDniOperador()).getNombre());
		return comprobante;
	}
	
	
	/**
	 * Metodo que permite validar si un titular o adherente pertenece a una cuenta bancaria.
	 */
	
	@Override
	public CuentaBancariaDTO validarDniOperador(String dniOperador, CuentaBancariaDTO cuentaDTO) throws ModelException {
		logger.debug("Validando el operador del movimiento...");
		if (dniOperador.equalsIgnoreCase(cuentaDTO.getTitular().getDni())) {
			return cuentaDTO;
		} else {
			for (AdherenteDTO a : cuentaDTO.getTitular().getAdherentes()) {
				if (a.getDni().equalsIgnoreCase(dniOperador)) {
					return cuentaDTO;
				}
			}
		}
		throw new ModelException(LoggerUtil.CUENTA_VALIDACION_ASOCIACION+dniOperador);
	}
	
	
	/**
	 * Metodo que permite obtener la cuenta de un cliente (Titular o Adherente).
	 */
	
	@Override
	public CuentaBancariaDTO obtenerCuentaPorDni(String dni) throws ModelException  {
		logger.debug("Validando cuenta de un cliente...");
		if (clienteRepository.findByDni(dni) instanceof Titular ){
			TitularDTO titulardto = modelMapper.map(clienteRepository.findByDni(dni), TitularDTO.class) ;
			return titulardto.getCuenta();
		}
		if (clienteRepository.findByDni(dni) instanceof Adherente) {
			AdherenteDTO adherentedto =  modelMapper.map(clienteRepository.findByDni(dni), AdherenteDTO.class) ;
			return adherentedto.getTitular().getCuenta();
		}
		throw new ModelException(LoggerUtil.CUENTA_VALIDACION_ASOCIACION+dni );
	}
}
