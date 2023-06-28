package br.com.eduardo.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.controller.CidadeController;
import br.com.eduardo.algafood.api.controller.FormaPagamentoController;
import br.com.eduardo.algafood.api.controller.PedidoController;
import br.com.eduardo.algafood.api.controller.RestauranteController;
import br.com.eduardo.algafood.api.controller.RestauranteProdutoController;
import br.com.eduardo.algafood.api.controller.UsuarioController;
import br.com.eduardo.algafood.api.model.PedidoDTO;
import br.com.eduardo.algafood.domain.model.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoDTO.class);
	}
	
	public PedidoDTO toModel(Pedido pedido) {
		PedidoDTO pedidoDTO = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoDTO);
		
pedidoDTO.add(WebMvcLinkBuilder.linkTo(PedidoController.class).withRel("pedidos"));
        
        pedidoDTO.getRestaurante().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class)
                .buscar(pedido.getRestaurante().getId())).withSelfRel());
        
        pedidoDTO.getCliente().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
                .buscar(pedido.getCliente().getId())).withSelfRel());
        
        // Passamos null no segundo argumento, porque é indiferente para a
        // construção da URL do recurso de forma de pagamento
        pedidoDTO.getFormaPagamento().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FormaPagamentoController.class)
                .buscar(pedido.getFormaPagamento().getId(), null)).withSelfRel());
        
        pedidoDTO.getEnderecoEntrega().getCidade().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
                .buscar(pedido.getEnderecoEntrega().getCidade().getId())).withSelfRel());
        
        pedidoDTO.getItens().forEach(item -> {
            item.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteProdutoController.class)
                    .buscar(pedidoDTO.getRestaurante().getId(), item.getProdutoId()))
                    .withRel("produto"));
        });
		
		return pedidoDTO;
	}
	
}
