package br.com.eduardo.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.algafood.api.v1.assembler.RestauranteApenasNomeModelAssembler;
import br.com.eduardo.algafood.api.v1.assembler.RestauranteBasicoModelAssembler;
import br.com.eduardo.algafood.api.v1.assembler.RestauranteInputDisassembler;
import br.com.eduardo.algafood.api.v1.assembler.RestauranteModelAssembler;
import br.com.eduardo.algafood.api.v1.model.RestauranteApenasNomeModel;
import br.com.eduardo.algafood.api.v1.model.RestauranteBasicoModel;
import br.com.eduardo.algafood.api.v1.model.RestauranteDTO;
import br.com.eduardo.algafood.api.v1.model.input.RestauranteInputDTO;
import br.com.eduardo.algafood.api.v1.openapi.controller.RestauranteControllerOpenApi;
import br.com.eduardo.algafood.core.security.CheckSecurity;
import br.com.eduardo.algafood.domain.exception.CidadeNaoEncontradaException;
import br.com.eduardo.algafood.domain.exception.CozinhaNaoEncontradaException;
import br.com.eduardo.algafood.domain.exception.NegocioException;
import br.com.eduardo.algafood.domain.exception.RestauranteNaoEncontradaException;
import br.com.eduardo.algafood.domain.model.Restaurante;
import br.com.eduardo.algafood.domain.repository.RestauranteRepository;
import br.com.eduardo.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/v1/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;
	
	@Autowired
	private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;
	
	@Autowired
	private RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<RestauranteBasicoModel> listar() {
		return restauranteBasicoModelAssembler
				.toCollectionModel(restauranteRepository.findAll());
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping(params = "projecao=apenas-nome")
	public CollectionModel<RestauranteApenasNomeModel> listarApenasNome() {
		return restauranteApenasNomeModelAssembler
				.toCollectionModel(restauranteRepository.findAll());
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping("/{restauranteId}")
	public RestauranteDTO buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		return restauranteModelAssembler.toModel(restaurante);
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInputDTO restauranteInput) {
		try {
			Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
			
			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@PutMapping("/{restauranteId}")
	public RestauranteDTO atualizar(@PathVariable Long restauranteId,
			@RequestBody @Valid RestauranteInputDTO restauranteInput) {
		try {
			Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
			
			restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
			
			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.ativar(restauranteId);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.inativar(restauranteId);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.ativar(restauranteIds);
		} catch (RestauranteNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.inativar(restauranteIds);
		} catch (RestauranteNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@Override
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> abertura(@PathVariable Long restauranteId) {
		cadastroRestaurante.abrir(restauranteId);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@Override
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> fechamento(@PathVariable Long restauranteId) {
		cadastroRestaurante.fechar(restauranteId);
		
		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
		cadastroRestaurante.excluir(id);
	}
	
}