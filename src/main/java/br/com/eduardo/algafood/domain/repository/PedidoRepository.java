package br.com.eduardo.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import br.com.eduardo.algafood.domain.filter.VendaDiariaFilter;
import br.com.eduardo.algafood.domain.model.Pedido;
import br.com.eduardo.algafood.domain.model.dto.VendaDiaria;

public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>, 
		JpaSpecificationExecutor<Pedido> { 

	public Optional<Pedido> findByCodigo(String codigo);
	
	@Query("from Pedido p join  fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
	public List<Pedido> findAll();
	
	
}
