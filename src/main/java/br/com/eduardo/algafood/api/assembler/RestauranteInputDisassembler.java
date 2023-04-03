package br.com.eduardo.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.model.input.RestauranteInputDTO;
import br.com.eduardo.algafood.domain.model.Cozinha;
import br.com.eduardo.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public Restaurante toDomainObject(RestauranteInputDTO restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
		
	}
	
	public void copyToDomainObject(RestauranteInputDTO restauranteInputDTO, Restaurante restaurante) {
		//Para evitar identifier of an instance of 
		//br.com.eduardo.algafood.domain.model.Cozinha was altered from 1 to 2
		restaurante.setCozinha(new Cozinha());
		
		modelMapper.map(restauranteInputDTO, restaurante);
	}
	
}
