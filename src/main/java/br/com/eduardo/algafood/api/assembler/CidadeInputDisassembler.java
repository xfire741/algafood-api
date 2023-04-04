package br.com.eduardo.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.model.input.CidadeInputDTO;
import br.com.eduardo.algafood.domain.model.Cidade;
import br.com.eduardo.algafood.domain.model.Estado;

@Component
public class CidadeInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Cidade toDomainObject(CidadeInputDTO cidadeInputDTO) {
		return modelMapper.map(cidadeInputDTO, Cidade.class);
	}
	
	public void copyToDomainObject(CidadeInputDTO cidadeInputDTO, Cidade cidade) {
		//Para evitar identifier of an instance of 
		//br.com.eduardo.algafood.domain.model.Cidade was altered from 1 to 2
		cidade.setEstado(new Estado());
		
		modelMapper.map(cidadeInputDTO, cidade);
	}
	
}
