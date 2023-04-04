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

import br.com.eduardo.algafood.api.assembler.RestauranteInputDisassembler;
import br.com.eduardo.algafood.api.assembler.RestauranteModelAssembler;
import br.com.eduardo.algafood.api.model.RestauranteDTO;
import br.com.eduardo.algafood.api.model.input.RestauranteInputDTO;
import br.com.eduardo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.eduardo.algafood.domain.exception.NegocioException;
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
	
	@GetMapping
	public List<RestauranteDTO> listar() {
		return restauranteModelAssembler.toCollectionDTO(restauranteRepository.findAll());
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
		} catch(EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public RestauranteDTO atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInputDTO restauranteInput){
		try {
			Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);
			
				restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
				
				return restauranteModelAssembler.toDTO(cadastroRestaurante.salvar(restauranteAtual));
				} catch (EntidadeNaoEncontradaException e) {
					throw new NegocioException(e.getMessage());
				}
	}
		
		@DeleteMapping("/{id}")
		public void remover(@PathVariable Long id) {
			cadastroRestaurante.excluir(id);
		}
}
