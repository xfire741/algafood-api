package br.com.eduardo.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.model.CozinhaDTO;
import br.com.eduardo.algafood.api.model.RestauranteDTO;
import br.com.eduardo.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public RestauranteDTO toDTO(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteDTO.class);
	}
	
	public List<RestauranteDTO> toCollectionDTO(List<Restaurante> restaurantes) {
		return  restaurantes.stream()
				.map(restaurante -> toDTO(restaurante))
				.collect(Collectors.toList());
	}
	
}
