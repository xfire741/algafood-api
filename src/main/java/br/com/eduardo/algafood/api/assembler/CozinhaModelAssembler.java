package br.com.eduardo.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.model.CozinhaDTO;
import br.com.eduardo.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public CozinhaDTO toDTO(Cozinha cozinha) {
		return modelMapper.map(cozinha, CozinhaDTO.class);
	}
	
	public List<CozinhaDTO> toCollectionDTO(List<Cozinha> cozinhas) {
		return cozinhas.stream()
				.map(cozinha -> toDTO(cozinha))
				.collect(Collectors.toList());
	}
	
}
