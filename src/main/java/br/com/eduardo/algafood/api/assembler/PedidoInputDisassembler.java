package br.com.eduardo.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.model.input.PedidoInputDTO;
import br.com.eduardo.algafood.domain.model.Pedido;

@Component
public class PedidoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;
    
    public Pedido toDomainObject(PedidoInputDTO pedidoInput) {
        return modelMapper.map(pedidoInput, Pedido.class);
    }
    
    public void copyToDomainObject(PedidoInputDTO pedidoInput, Pedido pedido) {
        modelMapper.map(pedidoInput, pedido);
    }            
}
