package br.com.eduardo.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eduardo.algafood.domain.model.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long>{
	
}
