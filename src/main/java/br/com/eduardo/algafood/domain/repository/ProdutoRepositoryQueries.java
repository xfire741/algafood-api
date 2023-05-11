package br.com.eduardo.algafood.domain.repository;

import br.com.eduardo.algafood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

	FotoProduto save(FotoProduto foto);
	void delete(FotoProduto foto);
}
