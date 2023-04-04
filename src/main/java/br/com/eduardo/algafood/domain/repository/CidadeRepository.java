package br.com.eduardo.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eduardo.algafood.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long>{

}
