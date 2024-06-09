package br.com.eduardo.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.v1.AlgaLinks;
import br.com.eduardo.algafood.api.v1.controller.RestauranteProdutoController;
import br.com.eduardo.algafood.api.v1.model.ProdutoDTO;
import br.com.eduardo.algafood.core.security.AlgaSecurity;
import br.com.eduardo.algafood.domain.model.Produto;

@Component
public class ProdutoModelAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;

        @Autowired
        private AlgaSecurity algaSecurity;
	
	public ProdutoModelAssembler() {
        super(RestauranteProdutoController.class, ProdutoDTO.class);
    }
	
	public ProdutoDTO toModel(Produto produto) {
		ProdutoDTO produtoModel = createModelWithId(
                produto.getId(), produto, produto.getRestaurante().getId());
        
        modelMapper.map(produto, produtoModel);

        if (algaSecurity.podeConsultarRestaurantes()) {
                produtoModel.add(algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
        
                produtoModel.add(algaLinks.linkToFotoProduto(
                                produto.getRestaurante().getId(), produto.getId(), "foto"));
        }
        
        return produtoModel;
	}
	
	
}
