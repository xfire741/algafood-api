package br.com.eduardo.algafood.domain.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.domain.model.Permissao;

@Component
public interface PermissaoRepository {
	
	List<Permissao> listar();
	Permissao buscar(Long id);
	Permissao salvar(Permissao permissao);
	void remover(Permissao permissao);

}
