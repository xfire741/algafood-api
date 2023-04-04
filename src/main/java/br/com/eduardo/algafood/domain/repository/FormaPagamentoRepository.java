package br.com.eduardo.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eduardo.algafood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long>{

}
