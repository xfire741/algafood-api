package br.com.eduardo.algafood.api.disassembler;

import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.model.input.RestauranteInputDTO;
import br.com.eduardo.algafood.domain.model.Cozinha;
import br.com.eduardo.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

	public Restaurante toDomainObject(RestauranteInputDTO restauranteInput) {
		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteInput.getNome());
		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteInput.getCozinha().getId());
		restaurante.setCozinha(cozinha);
		
		return restaurante;
		
	}
	
}
