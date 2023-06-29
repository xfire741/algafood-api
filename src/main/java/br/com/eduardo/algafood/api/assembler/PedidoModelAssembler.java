package br.com.eduardo.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
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
        
        TemplateVariables pageVariables = new TemplateVariables(
        		new TemplateVariable("page", VariableType.REQUEST_PARAM),
        		new TemplateVariable("size", VariableType.REQUEST_PARAM),
        		new TemplateVariable("sort", VariableType.REQUEST_PARAM));
        
        TemplateVariables filterVariables = new TemplateVariables(
        		new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
        		new TemplateVariable("restauranteID", VariableType.REQUEST_PARAM),
        		new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
        		new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));
		
        String pedidosURL = WebMvcLinkBuilder.linkTo(PedidoController.class).toUri().toString();
        
        pedidoDTO.add(Link.of(UriTemplate.of(pedidosURL, pageVariables.concat(filterVariables)),
        		"pedidos"));
        
        pedidoDTO.getRestaurante().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class)
                .buscar(pedido.getRestaurante().getId())).withSelfRel());
        
        pedidoDTO.getCliente().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
                .buscar(pedido.getCliente().getId())).withSelfRel());
        
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
