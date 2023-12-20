package ar.edu.unju.fi.poo.TrabajoPracticoN9.mapperConfiguration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ModelMapper representara la clase de configuracion de la 
 * clase modelMapper que permitira mapear entidades y DTO's.
 * @author Grupo06
 *
 */

@Configuration
public class MapperConfiguration {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}
