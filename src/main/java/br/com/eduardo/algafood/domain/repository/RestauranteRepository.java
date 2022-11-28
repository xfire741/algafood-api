package br.com.eduardo.algafood.domain.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.domain.model.Restaurante;

@Component
public interface RestauranteRepository {

	List<Restaurante> listar();
	Restaurante buscar(Long id);
	Restaurante salvar(Restaurante restaurante);
	void remover(Long id);
	
}
