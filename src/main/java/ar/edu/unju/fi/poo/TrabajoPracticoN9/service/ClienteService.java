package ar.edu.unju.fi.poo.TrabajoPracticoN9.service;

import java.util.List;

import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.AdherenteDTO;
import ar.edu.unju.fi.poo.TrabajoPracticoN9.entityDTO.TitularDTO;

public interface ClienteService {
	public TitularDTO registrarTitular(TitularDTO titular);
	public TitularDTO modificarTitular(TitularDTO titular);
	public TitularDTO habilitarTitular(TitularDTO titular);
	public TitularDTO inhabilitarTitular(TitularDTO titular);
	public TitularDTO buscarTitularPorDni(String dni);
	public List<TitularDTO> buscarClientePorNombre(String nombre);
	public AdherenteDTO registrarAdherente(AdherenteDTO adherente);
	public TitularDTO agregarAdherente(TitularDTO titular, AdherenteDTO adherente);
	public AdherenteDTO modificarAdherente(AdherenteDTO adherente);
	public AdherenteDTO habilitarAdherente(AdherenteDTO adherente);
	public AdherenteDTO inhabilitarAdherente(AdherenteDTO adherente);
	public AdherenteDTO buscarAdherentePorDni(String dni);

	public TitularDTO validarRegistroTitular(TitularDTO titular);
	public TitularDTO validarExistenciaTitular(TitularDTO titular);
	public AdherenteDTO validarRegistroAdherente(AdherenteDTO adherente);
	public AdherenteDTO validarExistenciaAdherente(AdherenteDTO adherente);
	
}
