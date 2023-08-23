package br.com.eduardo.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.v1.model.input.PermissaoInputDTO;
import br.com.eduardo.algafood.domain.model.Permissao;

@Component
public class PermissaoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	
	public Permissao toDomainObject(PermissaoInputDTO permissaoInputDTO) {
		return modelMapper.map(permissaoInputDTO, Permissao.class);
	}
	
	public void copyToDomainObject(PermissaoInputDTO permissaoInputDTO, Permissao permissao) {
		modelMapper.map(permissaoInputDTO, permissao);
	}
}
