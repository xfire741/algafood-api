package br.com.eduardo.algafood.domain.repository;

import java.util.Optional;

import br.com.eduardo.algafood.domain.model.Usuario;

public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long>{

	Optional<Usuario> findByEmail(String email);
	
}
