package br.com.eduardo.algafood.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.eduardo.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long>{

	
	List<Cozinha> findByNomeContaining(String nome);
	
	
}
