package br.com.eduardo.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.AlgaLinks;
import br.com.eduardo.algafood.api.controller.GrupoController;
import br.com.eduardo.algafood.api.model.GrupoDTO;
import br.com.eduardo.algafood.domain.model.Grupo;

@Component
public class GrupoModelAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoDTO> {
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public GrupoModelAssembler() {
        super(GrupoController.class, GrupoDTO.class);
    }

	   @Override
	    public GrupoDTO toModel(Grupo grupo) {
	        GrupoDTO grupoModel = createModelWithId(grupo.getId(), grupo);
	        modelMapper.map(grupo, grupoModel);
	        
	        grupoModel.add(algaLinks.linkToGrupos("grupos"));
	        
	        grupoModel.add(algaLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
	        
	        return grupoModel;
	    }
	    
	    @Override
	    public CollectionModel<GrupoDTO> toCollectionModel(Iterable<? extends Grupo> entities) {
	        return super.toCollectionModel(entities)
	                .add(algaLinks.linkToGrupos());
	    }
	
}
