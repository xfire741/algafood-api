package br.com.eduardo.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.AlgaLinks;
import br.com.eduardo.algafood.api.controller.RestauranteProdutoController;
import br.com.eduardo.algafood.api.model.ProdutoDTO;
import br.com.eduardo.algafood.domain.model.Produto;

@Component
public class ProdutoModelAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public ProdutoModelAssembler() {
        super(RestauranteProdutoController.class, ProdutoDTO.class);
    }
	
	public ProdutoDTO toModel(Produto produto) {
		ProdutoDTO produtoModel = createModelWithId(
                produto.getId(), produto, produto.getRestaurante().getId());
        
        modelMapper.map(produto, produtoModel);
        
        produtoModel.add(algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
        
        return produtoModel;
	}
	
	
}
