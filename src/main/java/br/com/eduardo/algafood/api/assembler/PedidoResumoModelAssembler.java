package br.com.eduardo.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.controller.PedidoController;
import br.com.eduardo.algafood.api.controller.RestauranteController;
import br.com.eduardo.algafood.api.controller.UsuarioController;
import br.com.eduardo.algafood.api.model.PedidoResumoDTO;
import br.com.eduardo.algafood.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	public PedidoResumoModelAssembler() {
		super(PedidoController.class, PedidoResumoDTO.class);
	}
	
	public PedidoResumoDTO toModel(Pedido pedido) {
		PedidoResumoDTO pedidoResumoDTO = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoResumoDTO);
		
		 pedidoResumoDTO.add(WebMvcLinkBuilder.linkTo(PedidoController.class).withRel("pedidos"));
	        
		 pedidoResumoDTO.getRestaurante().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class)
	                .buscar(pedido.getRestaurante().getId())).withSelfRel());
	        
		 pedidoResumoDTO.getCliente().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
	                .buscar(pedido.getCliente().getId())).withSelfRel());
	
		return pedidoResumoDTO;
	}
	
	
}
