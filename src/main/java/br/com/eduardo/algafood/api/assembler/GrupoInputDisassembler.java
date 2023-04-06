package br.com.eduardo.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.model.input.GrupoInputDTO;
import br.com.eduardo.algafood.domain.model.Grupo;

@Component
public class GrupoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Grupo toDomainObject(GrupoInputDTO grupoInputDTO) {
		return modelMapper.map(grupoInputDTO, Grupo.class);
	}
	
	public void copyToDomainObject(GrupoInputDTO grupoInputDTO, Grupo grupo) {
		modelMapper.map(grupoInputDTO, grupo);
	}
	
}
