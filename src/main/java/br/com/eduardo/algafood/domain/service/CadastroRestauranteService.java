package br.com.eduardo.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.eduardo.algafood.domain.exception.EntidadeEmUsoException;
import br.com.eduardo.algafood.domain.exception.RestauranteNaoEncontradaException;
import br.com.eduardo.algafood.domain.model.Cozinha;
import br.com.eduardo.algafood.domain.model.Restaurante;
import br.com.eduardo.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	private static final String RESTAURANTE_EM_USO = "Restaurante com o código %d não pode ser removido, pois está em uso.";

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	public Restaurante salvar(Restaurante restaurante) {
	    Long cozinhaId = restaurante.getCozinha().getId();
	    
	    Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
	    
	    restaurante.setCozinha(cozinha);
	    
	    return restauranteRepository.save(restaurante);
	}
	
	public void excluir(Long restauranteId) {
		try {
		restauranteRepository.deleteById(restauranteId);
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradaException(restauranteId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(RESTAURANTE_EM_USO, restauranteId));
		}
	}
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
	    return restauranteRepository.findById(restauranteId)
	        .orElseThrow(() -> new RestauranteNaoEncontradaException(restauranteId));
	}

}
