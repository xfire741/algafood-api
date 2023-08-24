package br.com.eduardo.algafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.v2.model.input.CidadeInputDTOV2;
import br.com.eduardo.algafood.domain.model.Cidade;
import br.com.eduardo.algafood.domain.model.Estado;

@Component
public class CidadeInputDisassemblerV2 {

	@Autowired
	private ModelMapper modelMapper;
	
	public Cidade toDomainObject(CidadeInputDTOV2 cidadeInputDTO) {
		return modelMapper.map(cidadeInputDTO, Cidade.class);
	}
	
	public void copyToDomainObject(CidadeInputDTOV2 cidadeInputDTO, Cidade cidade) {
		//Para evitar identifier of an instance of 
		//br.com.eduardo.algafood.domain.model.Cidade was altered from 1 to 2
		cidade.setEstado(new Estado());
		
		modelMapper.map(cidadeInputDTO, cidade);
	}
	
}
