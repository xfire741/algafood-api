package br.com.eduardo.algafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.v2.AlgaLinksV2;
import br.com.eduardo.algafood.api.v2.controller.CozinhaControllerV2;
import br.com.eduardo.algafood.api.v2.model.CozinhaDTOV2;
import br.com.eduardo.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelAssemblerV2 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTOV2> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinksV2 algaLinks;
	
	public CozinhaModelAssemblerV2() {
		super(CozinhaControllerV2.class, CozinhaDTOV2.class);
	}
	
	@Override
	public CozinhaDTOV2 toModel(Cozinha cozinha) {
		CozinhaDTOV2 cozinhaDTO = createModelWithId(cozinha.getId(), cozinha);
		modelMapper.map(cozinha, cozinhaDTO);
		
		cozinhaDTO.add(algaLinks.linkToCozinhas("cozinhas"));
		
		
		return cozinhaDTO;
	}
	
	
}
