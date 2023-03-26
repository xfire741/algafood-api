package br.com.eduardo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.eduardo.algafood.AlgaFoodapiApplication;
import br.com.eduardo.algafood.domain.model.Cozinha;
import br.com.eduardo.algafood.domain.service.CadastroCozinhaService;

@SpringBootTest(classes = AlgaFoodapiApplication.class)
public class CadastroCozinhaIntegrationTests {
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Test
	public void when_cadastro_cozinha_com_dados_corretos_then_deve_atribuir_id() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");
		
		novaCozinha = cadastroCozinha.salvar(novaCozinha);
		
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}
	
	@Test
	public void when_cozinha_sem_nome_then_deve_lancar_exception() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);
		Executable salvarCozinha = () -> cadastroCozinha.salvar(novaCozinha);
		
		ConstraintViolationException exceptionEsperada = assertThrows(
				ConstraintViolationException.class, salvarCozinha);
		
		assertThat(exceptionEsperada).isNotNull();
		
		
	}

}
