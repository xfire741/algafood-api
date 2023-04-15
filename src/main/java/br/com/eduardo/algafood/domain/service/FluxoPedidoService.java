package br.com.eduardo.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.eduardo.algafood.domain.model.Pedido;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedidoService;
	
	@Transactional
	public void confirmar(Long pedidoId) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
		pedido.confirmar();
	}
	
	@Transactional
	public void entregar(Long id) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(id);
		pedido.entregar();
		
	}
	
	@Transactional
	public void cancelar(Long id) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(id);
		pedido.cancelar();	
	}
	
	
	
	
}
