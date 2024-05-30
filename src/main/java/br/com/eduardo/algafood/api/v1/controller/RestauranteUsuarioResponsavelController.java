package br.com.eduardo.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.algafood.api.v1.AlgaLinks;
import br.com.eduardo.algafood.api.v1.assembler.UsuarioModelAssembler;
import br.com.eduardo.algafood.api.v1.model.UsuarioDTO;
import br.com.eduardo.algafood.api.v1.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import br.com.eduardo.algafood.core.security.CheckSecurity;
import br.com.eduardo.algafood.domain.model.Restaurante;
import br.com.eduardo.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/v1/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

	@Autowired
	private UsuarioModelAssembler usuarioAssembler;

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;

	@Autowired
	private AlgaLinks algaLinks;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<UsuarioDTO> listarUsuariosResponsaveis(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

		CollectionModel<UsuarioDTO> usuariosModel = usuarioAssembler
				.toCollectionModel(restaurante.getUsuariosResponsaveis())
				.removeLinks()
				.add(algaLinks.linkToRestauranteResponsaveis(restauranteId))
				.add(algaLinks.linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));

		usuariosModel.getContent().stream().forEach(usuarioModel -> {
			usuarioModel.add(algaLinks.linkToRestauranteResponsavelDesassociacao(
					restauranteId, usuarioModel.getId(), "desassociar"));
		});

		return usuariosModel;
	}

	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroRestauranteService.associarUsuarioResponsavel(restauranteId, usuarioId);

		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroRestauranteService.disassociarUsuarioResponsavel(restauranteId, usuarioId);

		return ResponseEntity.noContent().build();
	}

}
