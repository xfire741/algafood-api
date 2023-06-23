package br.com.eduardo.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.algafood.api.assembler.UsuarioModelAssembler;
import br.com.eduardo.algafood.api.model.UsuarioDTO;
import br.com.eduardo.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import br.com.eduardo.algafood.domain.model.Restaurante;
import br.com.eduardo.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavel implements RestauranteUsuarioResponsavelControllerOpenApi {

	@Autowired
	private UsuarioModelAssembler usuarioAssembler;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<UsuarioDTO> listarUsuariosResponsaveis(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		
		return usuarioAssembler.toCollectionModel(restaurante.getUsuariosResponsaveis());
	}
	
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarUsuarioResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroRestauranteService.associarUsuarioResponsavel(restauranteId, usuarioId);
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disassociarUsuarioResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroRestauranteService.disassociarUsuarioResponsavel(restauranteId, usuarioId);
	}
	
}
