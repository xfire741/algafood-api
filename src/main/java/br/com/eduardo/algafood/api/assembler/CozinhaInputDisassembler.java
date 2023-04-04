package br.com.eduardo.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.model.input.CozinhaInputDTO;
import br.com.eduardo.algafood.domain.model.Cozinha;

@Component
public class CozinhaInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Cozinha toDomainObject(CozinhaInputDTO cozinhaInputDTO) {
		return modelMapper.map(cozinhaInputDTO, Cozinha.class);
	}

	public void copyToDomainObject(CozinhaInputDTO cozinhaInputDTO, Cozinha cozinha) {
		modelMapper.map(cozinhaInputDTO, cozinha);
	}

}
