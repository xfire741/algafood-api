package br.com.eduardo.algafood.infraestructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.eduardo.algafood.domain.model.Pedido;
import br.com.eduardo.algafood.domain.repository.filter.PedidoFilter;

public class PedidoSpecs {

	public static Specification<Pedido> usandoFiltro(PedidoFilter filtro){
		return (root, query, builder) -> {
			var predicates = new ArrayList<Predicate>();
			
			root.fetch("restaurante").fetch("cozinha");
			root.fetch("cliente");
			
			if (filtro.getClienteId() != null) {
				predicates.add(builder.equal(root.get("cliente"), filtro.getClienteId()));
			}
			
			if (filtro.getRestauranteId() != null) {
				predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
			}
			
			if (filtro.getDataCriacaoInicio() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), 
						filtro.getDataCriacaoInicio()));
			}
			
			if (filtro.getDataCriacaoFim() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), 
						filtro.getDataCriacaoFim()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
}
