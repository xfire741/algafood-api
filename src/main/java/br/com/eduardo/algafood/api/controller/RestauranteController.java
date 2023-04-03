package br.com.eduardo.algafood.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

import br.com.eduardo.algafood.api.model.CozinhaDTO;
import br.com.eduardo.algafood.api.model.RestauranteDTO;
import br.com.eduardo.algafood.api.model.input.RestauranteInputDTO;
import br.com.eduardo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.eduardo.algafood.domain.exception.NegocioException;
import br.com.eduardo.algafood.domain.model.Cozinha;
import br.com.eduardo.algafood.domain.model.Restaurante;
import br.com.eduardo.algafood.domain.repository.RestauranteRepository;
import br.com.eduardo.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@GetMapping
	public List<RestauranteDTO> listar() {
		return toCollectionDTO(restauranteRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public RestauranteDTO buscar(@PathVariable Long id) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);
		
		return toDTO(restaurante);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInputDTO restauranteInput) {
		try {
			Restaurante restaurante = toDomainObject(restauranteInput);
			
		return toDTO(cadastroRestaurante.salvar(restaurante));
		} catch(EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public RestauranteDTO atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInputDTO restauranteInput){
		try {
			Restaurante restaurante = toDomainObject(restauranteInput);
			Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);
			
				BeanUtils.copyProperties(restaurante, restauranteAtual, "id",
						"formasPagamento", "endereco", "dataCadastro", "produtos");
				
				
				return toDTO(cadastroRestaurante.salvar(restauranteAtual));
				} catch (EntidadeNaoEncontradaException e) {
					throw new NegocioException(e.getMessage());
				}
	}
		
		@DeleteMapping("/{id}")
		public void remover(@PathVariable Long id) {
			cadastroRestaurante.excluir(id);
		}
		
		private RestauranteDTO toDTO(Restaurante restaurante) {
			CozinhaDTO cozinhaDTO = new CozinhaDTO();
			cozinhaDTO.setId(restaurante.getCozinha().getId());
			cozinhaDTO.setNome(restaurante.getCozinha().getNome());
			
			RestauranteDTO restauranteDTO = new RestauranteDTO();
			restauranteDTO.setId(restaurante.getId());
			restauranteDTO.setNome(restaurante.getNome());
			restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
			restauranteDTO.setCozinha(cozinhaDTO);
			return restauranteDTO;
		}
		
		private List<RestauranteDTO> toCollectionDTO(List<Restaurante> restaurantes) {
			return  restaurantes.stream()
					.map(restaurante -> toDTO(restaurante))
					.collect(Collectors.toList());
		}
		
		private Restaurante toDomainObject(RestauranteInputDTO restauranteInput) {
			Restaurante restaurante = new Restaurante();
			restaurante.setNome(restauranteInput.getNome());
			restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
			
			Cozinha cozinha = new Cozinha();
			cozinha.setId(restauranteInput.getCozinha().getId());
			restaurante.setCozinha(cozinha);
			
			return restaurante;
			
		}
}
