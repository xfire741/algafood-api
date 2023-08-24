package br.com.eduardo.algafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.v1.model.input.CozinhaInputDTO;
import br.com.eduardo.algafood.api.v2.model.input.CozinhaInputDTOV2;
import br.com.eduardo.algafood.domain.model.Cozinha;

@Component
public class CozinhaInputDisassemblerV2 {

	@Autowired
	private ModelMapper modelMapper;

	public Cozinha toDomainObject(CozinhaInputDTOV2 cozinhaInputDTO) {
		return modelMapper.map(cozinhaInputDTO, Cozinha.class);
	}

	public void copyToDomainObject(CozinhaInputDTOV2 cozinhaInputDTO, Cozinha cozinha) {
		modelMapper.map(cozinhaInputDTO, cozinha);
	}

}
