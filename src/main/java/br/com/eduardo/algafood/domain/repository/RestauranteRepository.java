package br.com.eduardo.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eduardo.algafood.domain.model.Restaurante;

public interface RestauranteRepository 
extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {

	
	@Query("from Restaurante r join fetch r.cozinha")
	List<Restaurante> findAll();
	
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxainicial, BigDecimal taxaFinal);
	
	//@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	List<Restaurante> consultarPorNome(String nome,@Param("id") Long id);
	
	//List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long id);
	
	Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
	
	List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

	boolean existsResponsavel(Long restauranteId, Long usuarioId);
	
}
