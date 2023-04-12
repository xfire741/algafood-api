package br.com.eduardo.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eduardo.algafood.domain.exception.PedidoNaoEncontradoException;
import br.com.eduardo.algafood.domain.model.Pedido;
import br.com.eduardo.algafood.domain.repository.PedidoRepository;

@Service
public class EmissaoPedidoService {
 
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido buscarOuFalhar(Long id) {
		return pedidoRepository.findById(id)
				.orElseThrow(() -> new PedidoNaoEncontradoException(id));
	}
	
	
	
}
