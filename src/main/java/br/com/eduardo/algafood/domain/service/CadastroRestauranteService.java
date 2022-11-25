package br.com.eduardo.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eduardo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.eduardo.algafood.domain.model.Cozinha;
import br.com.eduardo.algafood.domain.model.Restaurante;
import br.com.eduardo.algafood.domain.repository.CozinhaRepository;
import br.com.eduardo.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	public Restaurante salvar(Restaurante restaurante) {
		
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
		
		if (cozinha == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não exite cozinha cadastrada com o código informado %d", cozinhaId));
		}
		restaurante.setCozinha(cozinha);
		return restauranteRepository.salvar(restaurante);
	}

}
