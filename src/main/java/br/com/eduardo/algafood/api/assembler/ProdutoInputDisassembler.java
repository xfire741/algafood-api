package br.com.eduardo.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.model.input.ProdutoInputDTO;
import br.com.eduardo.algafood.domain.model.Produto;

@Component
public class ProdutoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Produto toDomainObject(ProdutoInputDTO produtoInputDTO) {
		return modelMapper.map(produtoInputDTO, Produto.class);
	}
	
	public void copyToDomainObject(ProdutoInputDTO produtoInputDTO, Produto produto) {
		modelMapper.map(produtoInputDTO, produto);
	}
	
}
