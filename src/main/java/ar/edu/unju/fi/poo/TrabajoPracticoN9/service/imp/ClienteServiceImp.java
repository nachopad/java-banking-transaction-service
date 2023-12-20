package ar.edu.unju.fi.poo.TrabajoPracticoN9.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.poo.TrabajoPracticoN9.entity.Adherente;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entity.Cliente;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entity.Titular;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.AdherenteDTO;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.TitularDTO;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.exception.ModelException;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.repository.ClienteRepository;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.service.ClienteService;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.util.GenerarContrasenia;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.util.LoggerUtil;

/**
 * ClienteServiceImp implementara todos los metodos necesarios para
 * la logica de negocio de los titulares y adherentes. 
 * @author Grupo6
 *
 */

@Service
public class ClienteServiceImp implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private static final Logger logger = Logger.getLogger(ClienteServiceImp.class);
	
	private GenerarContrasenia contrasenia = new GenerarContrasenia();

	/**
     * Metodo que permite registrar un cliente en la BD.
     */
	
	@Override
	public TitularDTO registrarTitular(TitularDTO titular) {
		validarRegistroTitular(titular);
		Titular titularEntidad=modelMapper.map(titular, Titular.class);
		titularEntidad.setContrasenia(contrasenia.crearContrasenia());
		titularEntidad.setEstado(true);
		clienteRepository.save(titularEntidad);
		logger.info(LoggerUtil.TITULAR_REGISTRO+titular.getDni());
		return modelMapper.map(titularEntidad, TitularDTO.class);
	}
	
	/**
	 * Metodo que permite modificar un cliente.
	 */

	@Override
	public TitularDTO modificarTitular(TitularDTO titular) {
		validarExistenciaTitular(titular);
		Titular titularEntidad=	modelMapper.map(clienteRepository.findByDni(titular.getDni()), Titular.class);
		titularEntidad.setNombre(titular.getNombre());
		titularEntidad.setCorreoElectronico(titular.getCorreoElectronico());
		titularEntidad.setContrasenia(titular.getContrasenia());
		titularEntidad.setDomicilio(titular.getDomicilio());
		clienteRepository.save(titularEntidad);
		logger.info(LoggerUtil.TITULAR_MODIFICACION+titular.getDni());
		return titular;
	}
	
	/**
	 * Metodo que permite habilitar un cliente de manera logica (estado).
	 */
	
	@Override
	public TitularDTO habilitarTitular(TitularDTO titular) {
		validarExistenciaTitular(titular);
		Titular titularEntidad=	modelMapper.map(clienteRepository.findByDni(titular.getDni()), Titular.class);
		titularEntidad.setEstado(true);
		clienteRepository.save(titularEntidad);
		logger.info(LoggerUtil.TITULAR_HABILITACION+titular.getDni());
		return modelMapper.map(titularEntidad, TitularDTO.class);
	}
	
	/**
	 * Metodo que permite inhabilitar un cliente de manera logica (estado).
	 */
	
	@Override
	public TitularDTO inhabilitarTitular(TitularDTO titular) {
		validarExistenciaTitular(titular);
		Titular titularEntidad=	modelMapper.map(clienteRepository.findByDni(titular.getDni()), Titular.class);
		titularEntidad.setEstado(false);
		clienteRepository.save(titularEntidad);
		logger.info(LoggerUtil.TITULAR_INHABILITACION+titular.getDni());
		return modelMapper.map(titularEntidad, TitularDTO.class);
	}
	
	/**
	 * Metodo que permite buscar y retornar un cliente por su nombre.
	 */
	
	@Override
	public List<TitularDTO> buscarClientePorNombre(String nombre) {
		List<TitularDTO> lista = new ArrayList<>();
		logger.debug("Buscando cliente por nombre...");
		for(Cliente c : clienteRepository.findByNombre(nombre)) {
			TitularDTO titular = modelMapper.map(c, TitularDTO.class);
			lista.add(titular);
		}
		return lista;
	}
	
	/**
	 * Metodo que permite agregar un adherente a su respectivo titular (asociarlos).
	 */
	
	@Override
	public TitularDTO agregarAdherente(TitularDTO titular, AdherenteDTO adherente) {
		validarExistenciaTitular(titular);
		Titular titularEntidad=modelMapper.map(clienteRepository.findByDni(titular.getDni()), Titular.class);
		Adherente adherenteEntidad=modelMapper.map(clienteRepository.findByDni(adherente.getDni()), Adherente.class);
		adherenteEntidad.setTitular(titularEntidad);
		adherenteEntidad.setEstado(true);
		clienteRepository.save(adherenteEntidad);
		logger.info(LoggerUtil.ADHERENTE_AGREGACION+adherente.getDni());
		titularEntidad.setCuenta(null);
		return modelMapper.map(titularEntidad, TitularDTO.class);
	}
	
	/**
	 * Metodo que permite registrar un adherente en la BD.
	 */
	
	@Override
	public AdherenteDTO registrarAdherente(AdherenteDTO adherente) {
		validarRegistroAdherente(adherente);
		Adherente adherenteEntidad=modelMapper.map(adherente, Adherente.class);
		adherenteEntidad.setContrasenia(contrasenia.crearContrasenia());
		clienteRepository.save(adherenteEntidad);
		logger.info(LoggerUtil.ADHERENTE_REGISTRO+adherente.getDni());
		return modelMapper.map(adherenteEntidad, AdherenteDTO.class);
	}
	
	/**
	 * Metodo que permite modificar un adherente en la BD.
	 */

	@Override
	public AdherenteDTO modificarAdherente(AdherenteDTO adherente) {
		validarExistenciaAdherente(adherente);
		Adherente adherenteEntidad = modelMapper.map(clienteRepository.findByDni(adherente.getDni()), Adherente.class);
		adherenteEntidad.setNombre(adherente.getNombre());
		adherenteEntidad.setCorreoElectronico(adherente.getCorreoElectronico());
		adherenteEntidad.setContrasenia(adherente.getContrasenia());
		adherenteEntidad.setDomicilio(adherente.getDomicilio());
		clienteRepository.save(adherenteEntidad);
		logger.info(LoggerUtil.ADHERENTE_MODIFICACION+adherente.getDni());
		return modelMapper.map(adherente, AdherenteDTO.class);
	}
	
	/**
	 * Metodo que permite habilitar un adherente de manera logica (estado).
	 */
	
	@Override
	public AdherenteDTO habilitarAdherente(AdherenteDTO adherente) {
		validarExistenciaAdherente(adherente);
		Adherente adherenteEntidad=	modelMapper.map(clienteRepository.findByDni(adherente.getDni()), Adherente.class);
		adherenteEntidad.setEstado(true);
		clienteRepository.save(adherenteEntidad);
		logger.info(LoggerUtil.ADHERENTE_HABILITACION+adherente.getDni());
		return modelMapper.map(adherenteEntidad, AdherenteDTO.class);
	}

	/**
	 * Metodo que permite inhabilitar un adherente de manera logica (estado).
	 */
	
	@Override
	public AdherenteDTO inhabilitarAdherente(AdherenteDTO adherente) {
		validarExistenciaAdherente(adherente);
		Adherente adherenteEntidad=	modelMapper.map(clienteRepository.findByDni(adherente.getDni()), Adherente.class);
		adherenteEntidad.setEstado(false);
		clienteRepository.save(adherenteEntidad); 
		logger.info(LoggerUtil.ADHERENTE_INHABILITACION+adherente.getDni());
		return modelMapper.map(adherenteEntidad, AdherenteDTO.class);
	}

	/**
	 * Metodo que permite validar correctamente si el titular no se encuentra
	 * ya registrado en la Base de Datos.
	 */
	
	@Override
	public TitularDTO validarRegistroTitular(TitularDTO titular) throws ModelException{
		Cliente clienteBuscadoPorDni = clienteRepository.findByDni(titular.getDni());
		Cliente clienteBuscadoPorEmail = clienteRepository.findByCorreoElectronico(titular.getCorreoElectronico());
		if(clienteBuscadoPorDni!=null) {
			throw new ModelException(LoggerUtil.TITULAR_VALIDACION_REGISTRO+titular.getDni());
		}else {
			if(clienteBuscadoPorEmail!=null) {
				throw new ModelException(LoggerUtil.TITULAR_VALIDACION_EMAIL+titular.getCorreoElectronico());
			}
		}
		logger.debug("Validando registro de titular...");
		return titular;
	}
	
	/**
	 * Metodo que permite validar la existencia de un titular.
	 * Es decir, si ya se encuentra registrado en la Base de Datos.
	 */

	@Override
	public TitularDTO validarExistenciaTitular(TitularDTO titular) {
		Cliente clienteBuscado = clienteRepository.findByDni(titular.getDni());
		if(clienteBuscado==null) {
			throw new ModelException(LoggerUtil.TITULAR_VALIDACION_EXISTENCIA+titular.getDni());
		}else {
			logger.debug("Validando existencia de titular...");
			return titular;
		}
	}

	/**
	 * Metodo que permite validar correctamente si un adherente no se encuentra
	 * ya registrado en la Base de Datos.
	 */
	
	@Override
	public AdherenteDTO validarRegistroAdherente(AdherenteDTO adherente) {
		Cliente clienteBuscado = clienteRepository.findByDni(adherente.getDni());
		Cliente clienteBuscadoPorEmail = clienteRepository.findByCorreoElectronico(adherente.getCorreoElectronico());
		if(clienteBuscado!=null) {
			throw new ModelException(LoggerUtil.ADHERENTE_VALIDACION_REGISTRO+adherente.getDni());
		}else {
			if(clienteBuscadoPorEmail!=null) {
				throw new ModelException(LoggerUtil.ADHERENTE_VALIDACION_EMAIL+adherente.getCorreoElectronico());
			}
		}
		logger.debug("Validando registro de adherente...");
		return adherente;
	}

	/**
	 * Metodo que permite validar la existencia de un adherente.
	 * Es decir, si ya se encuentra registrado en la Base de Datos.
	 */
	
	@Override
	public AdherenteDTO validarExistenciaAdherente(AdherenteDTO adherente) {
		Cliente clienteBuscado = clienteRepository.findByDni(adherente.getDni());
		if(clienteBuscado==null) {
			throw new ModelException(LoggerUtil.ADHERENTE_VALIDACION_EXISTENCIA+adherente.getDni());
		}else {
			logger.debug("Validando existencia de adherente...");
			return adherente;
		}
	}
	
	/**
	 * Metodo que permite buscar un Titular por dni obtenido
	 * por parametro.
	 */

	@Override
	public TitularDTO buscarTitularPorDni(String dni) {
		Cliente titularBuscado = clienteRepository.findByDni(dni);
		if(titularBuscado==null) {
			throw new ModelException(LoggerUtil.TITULAR_VALIDACION_EXISTENCIA+dni);
		}else {
			logger.debug("Validando existencia del titular...");
			return modelMapper.map(titularBuscado, TitularDTO.class);
		}
	}
	
	/**
	 * Metodo que permite buscar un adherente por dni obtenido
	 * por parametro. 
	 */
	
	@Override
	public AdherenteDTO buscarAdherentePorDni(String dni) {
		Cliente adherenteBuscado = clienteRepository.findByDni(dni);
		if(adherenteBuscado==null) {
			throw new ModelException(LoggerUtil.ADHERENTE_VALIDACION_EXISTENCIA+dni);
		}else {
			logger.debug("Validando existencia del adherente...");
			return modelMapper.map(adherenteBuscado, AdherenteDTO.class);
		}
	}
	
}
