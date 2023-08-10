package br.com.eduardo.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.algafood.api.AlgaLinks;
import br.com.eduardo.algafood.api.assembler.GrupoModelAssembler;
import br.com.eduardo.algafood.api.model.GrupoDTO;
import br.com.eduardo.algafood.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import br.com.eduardo.algafood.domain.model.Usuario;
import br.com.eduardo.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private GrupoModelAssembler grupoAssembler;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Override
	@GetMapping
	public CollectionModel<GrupoDTO> listar(@PathVariable Long usuarioId) {
	    Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
	    
	    CollectionModel<GrupoDTO> gruposModel = grupoAssembler.toCollectionModel(usuario.getGrupos())
	            .removeLinks()
	            .add(algaLinks.linkToUsuarioGrupoAssociacao(usuarioId, "associar"));
	    
	    gruposModel.getContent().forEach(grupoModel -> {
	        grupoModel.add(algaLinks.linkToUsuarioGrupoDesassociacao(
	                usuarioId, grupoModel.getId(), "desassociar"));
	    });
	    
	    return gruposModel;
	} 
	
	@Override
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuarioService.associarGrupo(grupoId, usuarioId);
		
		return ResponseEntity.noContent().build();
	}
	
	@Override
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuarioService.disassociarGrupo(grupoId, usuarioId);
		
		return ResponseEntity.noContent().build();
	}
	
}
