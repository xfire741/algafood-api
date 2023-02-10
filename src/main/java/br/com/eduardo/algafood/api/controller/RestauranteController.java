package br.com.eduardo.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.eduardo.algafood.core.validation.ValidacaoException;
import br.com.eduardo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.eduardo.algafood.domain.exception.NegocioException;
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
	
	@Autowired
	private SmartValidator validator;
	
	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Restaurante buscar(@PathVariable Long id) {
		return cadastroRestaurante.buscarOuFalhar(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante) {
		return cadastroRestaurante.salvar(restaurante);
	}
	
	@PutMapping("/{id}")
	public Restaurante atualizar(@PathVariable Long id, @RequestBody @Valid Restaurante restaurante){
		
			Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);
			
				BeanUtils.copyProperties(restaurante, restauranteAtual, "id",
						"formasPagamento", "endereco", "dataCadastro", "produtos");
				
				try {
				return cadastroRestaurante.salvar(restauranteAtual);
				} catch (EntidadeNaoEncontradaException e) {
					throw new NegocioException(e.getMessage());
				}
	}
		
		@DeleteMapping("/{id}")
		public void remover(@PathVariable Long id) {
			cadastroRestaurante.excluir(id);
		}
		
		@PatchMapping("/{id}")
		public Restaurante atualizarParcial(@PathVariable Long id,
				@RequestBody Map<String, Object> campos, HttpServletRequest request) {
			Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);
			
			merge(campos, restauranteAtual, request);
			validate(restauranteAtual, "restaurante");
			
			return atualizar(id, restauranteAtual);
			
		}

		private void validate(Restaurante restaurante, String objectName) {
			BeanPropertyBindingResult bidingResult = new BeanPropertyBindingResult(restaurante, objectName);
			
			validator.validate(restaurante, bidingResult);
			
			if(bidingResult.hasErrors()) {
				throw new ValidacaoException(bidingResult);
			}
			
		}

		private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino,
				HttpServletRequest request) {
			
			ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
			
			try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
			
			dadosOrigem.forEach((nomePropriedade,valorPropriedade) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);
				
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				
				System.out.println(nomePropriedade + "=" + valorPropriedade);
				
				ReflectionUtils.setField(field, restauranteDestino, novoValor);
			
			});
			} catch (IllegalArgumentException e) {
				
				Throwable rootCause = ExceptionUtils.getRootCause(e);
				throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
				
			}
		}
		
		

}
