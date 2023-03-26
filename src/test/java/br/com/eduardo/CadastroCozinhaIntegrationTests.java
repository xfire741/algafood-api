package br.com.eduardo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.eduardo.algafood.AlgaFoodapiApplication;
import br.com.eduardo.algafood.domain.exception.EntidadeEmUsoException;
import br.com.eduardo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.eduardo.algafood.domain.model.Cozinha;
import br.com.eduardo.algafood.domain.service.CadastroCozinhaService;

@SpringBootTest(classes = AlgaFoodapiApplication.class)
public class CadastroCozinhaIntegrationTests {
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Test
	public void quando_cadastro_cozinha_com_dados_corretos_then_deve_atribuir_id() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");
		
		novaCozinha = cadastroCozinha.salvar(novaCozinha);
		
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}
	
	@Test
	public void quando_cozinha_sem_nome_entao_deve_lancar_exception() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);
		
		Executable salvarCozinha = () -> cadastroCozinha.salvar(novaCozinha);
		
		ConstraintViolationException exceptionEsperada = assertThrows(
				ConstraintViolationException.class, salvarCozinha);
		
		assertThat(exceptionEsperada).isNotNull();
	}
	
	@Test
	public void quando_excluir_cozinha_em_uso_entao_lancar_exception() {
		Executable excluirCozinha = () -> cadastroCozinha.excluir(1L);
		
		EntidadeEmUsoException exceptionEsperada = 
				assertThrows(EntidadeEmUsoException.class, excluirCozinha);
		
		assertThat(exceptionEsperada).isNotNull();		
	}
	
	@Test
	public void quando_excluir_cozinha_inexistente_entao_lancar_exception() {
		Executable excluirCozinha = () -> cadastroCozinha.excluir(1000L);
		
		EntidadeNaoEncontradaException exceptionEsperada = 
				assertThrows(EntidadeNaoEncontradaException.class, excluirCozinha);
		
		assertThat(exceptionEsperada).isNotNull();
		
	}

}
