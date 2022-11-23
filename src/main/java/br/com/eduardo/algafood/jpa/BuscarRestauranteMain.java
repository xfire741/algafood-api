package br.com.eduardo.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.eduardo.algafood.AlgaFoodapiApplication;
import br.com.eduardo.algafood.domain.model.Restaurante;
import br.com.eduardo.algafood.domain.repository.RestauranteRepository;

public class BuscarRestauranteMain {

	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgaFoodapiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		RestauranteRepository cadastroRestaurante = applicationContext.getBean(RestauranteRepository.class);
		
		Restaurante restaurante = cadastroRestaurante.buscar(1L);
		
		System.out.println(restaurante.getNome());
	}
}
