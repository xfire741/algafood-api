package br.com.eduardo.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.eduardo.algafood.AlgaFoodapiApplication;
import br.com.eduardo.algafood.domain.model.FormaPagamento;
import br.com.eduardo.algafood.domain.repository.FormaPagamentoRepository;


public class ConsultaFormaPagamento {
	
	public static void main(String[] args) {
		
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgaFoodapiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		FormaPagamentoRepository cadastroFormaPagamento = applicationContext.getBean(FormaPagamentoRepository.class);
		
		List<FormaPagamento> formas = cadastroFormaPagamento.listar();
		
		for(FormaPagamento forma : formas) {
			System.out.println(forma);
		}
	}

}
