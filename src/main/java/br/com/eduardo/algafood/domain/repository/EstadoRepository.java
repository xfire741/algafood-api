package br.com.eduardo.algafood.domain.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.domain.model.Estado;

@Component
public interface EstadoRepository {
	
	List<Estado> listar();
	Estado buscar(Long id);
	Estado salvar(Estado estado);
	void remover(Estado estado);

}
