package br.com.eduardo.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.AlgaLinks;
import br.com.eduardo.algafood.api.controller.PedidoController;
import br.com.eduardo.algafood.api.model.PedidoResumoDTO;
import br.com.eduardo.algafood.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public PedidoResumoModelAssembler() {
		super(PedidoController.class, PedidoResumoDTO.class);
	}
	
	public PedidoResumoDTO toModel(Pedido pedido) {
		PedidoResumoDTO pedidoResumoDTO = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoResumoDTO);
		
        pedidoResumoDTO.add(algaLinks.linkToPedidos("pedidos"));
        
        pedidoResumoDTO.getRestaurante().add(
                algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoResumoDTO.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));
	
		return pedidoResumoDTO;
	}
	
	
}
