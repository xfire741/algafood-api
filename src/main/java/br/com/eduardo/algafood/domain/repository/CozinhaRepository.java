package br.com.eduardo.algafood.domain.repository;

import java.util.List;

import br.com.eduardo.algafood.domain.model.Cozinha;

public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long>{

	
	List<Cozinha> findByNomeContaining(String nome);
	
	
}
