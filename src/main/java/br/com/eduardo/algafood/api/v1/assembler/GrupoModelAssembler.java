package br.com.eduardo.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.v1.AlgaLinks;
import br.com.eduardo.algafood.api.v1.controller.GrupoController;
import br.com.eduardo.algafood.api.v1.model.GrupoDTO;
import br.com.eduardo.algafood.core.security.AlgaSecurity;
import br.com.eduardo.algafood.domain.model.Grupo;

@Component
public class GrupoModelAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoDTO> {
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaSecurity algaSecurity;
	
	public GrupoModelAssembler() {
        super(GrupoController.class, GrupoDTO.class);
    }

	@Override
	public GrupoDTO toModel(Grupo grupo) {
		GrupoDTO grupoModel = createModelWithId(grupo.getId(), grupo);
		modelMapper.map(grupo, grupoModel);
		
		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			grupoModel.add(algaLinks.linkToGrupos("grupos"));
			
			grupoModel.add(algaLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
		}
		
		return grupoModel;
	}
	
	@Override
	public CollectionModel<GrupoDTO> toCollectionModel(Iterable<? extends Grupo> entities) {
		CollectionModel<GrupoDTO> collectionModel = super.toCollectionModel(entities);
		
		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			collectionModel.add(algaLinks.linkToGrupos());
		}
		
		return collectionModel;
	}
	
}
