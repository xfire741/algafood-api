package br.com.eduardo.algafood.domain.repository;

import java.util.List;

import br.com.eduardo.algafood.domain.model.Restaurante;

public interface RestauranteRepository {

	List<Restaurante> listar();
	Restaurante buscar(Long id);
	Restaurante salvar(Restaurante restaurante);
	void remover(Restaurante restaurante);
	
}
