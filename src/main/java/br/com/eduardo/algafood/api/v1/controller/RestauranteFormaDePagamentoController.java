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
import br.com.eduardo.algafood.api.v1.assembler.FormaPagamentoModelAssembler;
import br.com.eduardo.algafood.api.v1.model.FormaPagamentoDTO;
import br.com.eduardo.algafood.api.v1.openapi.controller.RestauranteFormaDePagamentoControllerOpenApi;
import br.com.eduardo.algafood.domain.model.Restaurante;
import br.com.eduardo.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/v1/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaDePagamentoController implements RestauranteFormaDePagamentoControllerOpenApi {
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoAssembler;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<FormaPagamentoDTO> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
	    
		CollectionModel<FormaPagamentoDTO> formasPagamentoDTO =
				formaPagamentoAssembler.toCollectionModel(restaurante.getFormasPagamento())
	            .removeLinks()
	            .add(algaLinks.linkToRestauranteFormasPagamento(restauranteId))
	            .add(algaLinks.linkToRestauranteFormaPagamentoAssociacao(restauranteId, "associar"));
		
		formasPagamentoDTO.getContent().forEach(formaPagamentoDTO -> {
			formaPagamentoDTO.add(algaLinks.linkToRestauranteFormaPagamentoDesassociacao(
					restauranteId, formaPagamentoDTO.getId(), "desassociar"));
		});
		
		return formasPagamentoDTO;
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		cadastroRestaurante.associarFormaPagamento(restauranteId, formaPagamentoId);
		
		return ResponseEntity.noContent().build();
	}
	
}
