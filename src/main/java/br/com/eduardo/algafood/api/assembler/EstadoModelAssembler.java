package br.com.eduardo.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.controller.EstadoController;
import br.com.eduardo.algafood.api.model.EstadoDTO;
import br.com.eduardo.algafood.domain.model.Estado;

@Component
public class EstadoModelAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoDTO> {

	public EstadoModelAssembler() {
		super(EstadoController.class, EstadoDTO.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	public EstadoDTO toModel(Estado estado) {
		EstadoDTO estadoModel = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado, estadoModel);
        
        estadoModel.add(WebMvcLinkBuilder.linkTo(EstadoController.class).withRel("estados"));
        
        return estadoModel;
	}
	
	@Override
	public CollectionModel<EstadoDTO> toCollectionModel(Iterable<? extends Estado> entities) {
		return super.toCollectionModel(entities)
				.add(WebMvcLinkBuilder.linkTo(EstadoController.class).withSelfRel());
	}
	
}
