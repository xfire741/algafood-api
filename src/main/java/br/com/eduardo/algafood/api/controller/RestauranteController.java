package br.com.eduardo.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.eduardo.algafood.api.assembler.RestauranteInputDisassembler;
import br.com.eduardo.algafood.api.assembler.RestauranteModelAssembler;
import br.com.eduardo.algafood.api.model.RestauranteDTO;
import br.com.eduardo.algafood.api.model.input.RestauranteInputDTO;
import br.com.eduardo.algafood.api.model.view.RestauranteView;
import br.com.eduardo.algafood.domain.exception.CidadeNaoEncontradaException;
import br.com.eduardo.algafood.domain.exception.CozinhaNaoEncontradaException;
import br.com.eduardo.algafood.domain.exception.NegocioException;
import br.com.eduardo.algafood.domain.exception.RestauranteNaoEncontradaException;
import br.com.eduardo.algafood.domain.model.Restaurante;
import br.com.eduardo.algafood.domain.repository.RestauranteRepository;
import br.com.eduardo.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;
	
	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@JsonView(RestauranteView.Resumo.class)
	@GetMapping
	public List<RestauranteDTO> listar() {
		List<RestauranteDTO> restaurantesDTO = restauranteModelAssembler
				.toCollectionDTO(restauranteRepository.findAll());
		
		return restaurantesDTO;
	}
	
	@JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=apenas-nome")
	public List<RestauranteDTO> listarApenasNome() {
		return listar();
	}
	
	
	@GetMapping("/{id}")
	public RestauranteDTO buscar(@PathVariable Long id) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);
		
		return restauranteModelAssembler.toDTO(restaurante);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInputDTO restauranteInput) {
		try {
			Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
			
		return restauranteModelAssembler.toDTO(cadastroRestaurante.salvar(restaurante));
		} catch(CozinhaNaoEncontradaException  | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public RestauranteDTO atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInputDTO restauranteInput){
		try {
			Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);
			
				restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
				
				return restauranteModelAssembler.toDTO(cadastroRestaurante.salvar(restauranteAtual));
				} catch (CozinhaNaoEncontradaException  | CidadeNaoEncontradaException e) {
					throw new NegocioException(e.getMessage());
				}
	}
		
		@DeleteMapping("/{id}")
		public void remover(@PathVariable Long id) {
			cadastroRestaurante.excluir(id);
		}
		
		@PutMapping("/{restauranteId}/ativo")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void ativar(@PathVariable Long restauranteId) {
			cadastroRestaurante.ativar(restauranteId);
		}
		
		@DeleteMapping("/{restauranteId}/ativo")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void inativar(@PathVariable Long restauranteId) {
			cadastroRestaurante.inativar(restauranteId);
		}
		
		@PutMapping("/ativacoes")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
			try {
			cadastroRestaurante.ativar(restauranteIds);
			} catch (RestauranteNaoEncontradaException e) {
				throw new NegocioException(e.getMessage(), e);
			}
		}
		
		@DeleteMapping("/ativacoes")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
			try {
				cadastroRestaurante.inativar(restauranteIds);
				} catch (RestauranteNaoEncontradaException e) {
					throw new NegocioException(e.getMessage(), e);
				}
		}
		
		@PutMapping("/{restauranteId}/abertura")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void abertura(@PathVariable Long restauranteId) {
			cadastroRestaurante.abrir(restauranteId);
		}
		
		@PutMapping("/{restauranteId}/fechamento")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void fechamento(@PathVariable Long restauranteId) {
			cadastroRestaurante.fechar(restauranteId);
		}
}
