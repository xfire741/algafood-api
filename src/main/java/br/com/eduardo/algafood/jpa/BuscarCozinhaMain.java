package br.com.eduardo.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.eduardo.algafood.AlgaFoodapiApplication;
import br.com.eduardo.algafood.domain.model.Cozinha;
import br.com.eduardo.algafood.domain.repository.CozinhaRepository;

public class BuscarCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgaFoodapiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);
		
		Cozinha cozinha = cadastroCozinha.buscar(1L);
		
		System.out.println(cozinha.getNome());
	}
	
	
	
}
