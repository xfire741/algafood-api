package br.com.eduardo.algafood.domain.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.domain.model.FormaPagamento;

@Component
public interface FormaPagamentoRepository {
	
	List<FormaPagamento> listar();
	FormaPagamento buscar(Long id);
	FormaPagamento salvar(FormaPagamento formaPagamento);
	void remover(FormaPagamento formaPagamento);

}
