package br.com.eduardo.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.controller.CidadeController;
import br.com.eduardo.algafood.api.controller.EstadoController;
import br.com.eduardo.algafood.api.model.CidadeDTO;
import br.com.eduardo.algafood.domain.model.Cidade;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO> {

	@Autowired
	private ModelMapper modelMapper;

	public CidadeModelAssembler() {
		super(CidadeController.class, CidadeDTO.class);
	}
	
	@Override
	public CidadeDTO toModel(Cidade cidade) {
		CidadeDTO cidadeDTO = createModelWithId(cidade.getId(), cidade);
		
		modelMapper.map(cidade, cidadeDTO);
		
		cidadeDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
				.listar()).withRel("cidades"));
		
		cidadeDTO.getEstado().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class)
				.buscar(cidadeDTO.getEstado().getId())).withSelfRel());
		
		return cidadeDTO;
	}
	
	@Override
	public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities)
				.add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());
	}

}
