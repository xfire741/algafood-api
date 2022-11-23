package br.com.eduardo.algafood.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.domain.model.Cozinha;

@Component
public class CadastroCozinha {
	
	@PersistenceContext
	private EntityManager manager;
	
	public List<Cozinha> listar(){
		
		return manager.createQuery("from Cozinha", Cozinha.class)
				.getResultList();

		
	}

}
