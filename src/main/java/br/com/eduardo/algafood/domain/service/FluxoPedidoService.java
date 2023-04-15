package br.com.eduardo.algafood.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.eduardo.algafood.domain.exception.NegocioException;
import br.com.eduardo.algafood.domain.model.Pedido;
import br.com.eduardo.algafood.domain.model.StatusPedido;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedidoService;
	
	@Transactional
	public void confirmar(Long pedidoId) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
		
		if(!pedido.getStatus().equals(StatusPedido.CRIADO)) {
			throw new NegocioException(String.format(
					"Status do pedido %d não pode ser alterado de %s para %s", 
					pedido.getId(), pedido.getStatus().getDescricao(), 
					StatusPedido.CONFIRMADO.getDescricao()));
		}
		
		pedido.setDataConfirmacao(OffsetDateTime.now());
		pedido.setStatus(StatusPedido.CONFIRMADO);
	}
	
	@Transactional
	public void entregar(Long id) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(id);
		
		if (!pedido.getStatus().equals(StatusPedido.CONFIRMADO)) {
			throw new NegocioException(String.format(
					"Status do pedido %d não pode ser alterado de %s para %s",
					pedido.getId(), pedido.getStatus().getDescricao(), StatusPedido.ENTREGUE.getDescricao()));
		}
		
		pedido.setDataEntrega(OffsetDateTime.now());
		pedido.setStatus(StatusPedido.ENTREGUE);
		
	}
	
	@Transactional
	public void cancelar(Long id) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(id);
		
		if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
			throw new NegocioException(String.format(
					"Status do pedido %d não pode ser alterado de %s para %s",
					pedido.getId(), pedido.getStatus().getDescricao(), StatusPedido.CANCELADO.getDescricao()));
		}
		
		pedido.setDataCancelamento(OffsetDateTime.now());
		pedido.setStatus(StatusPedido.CANCELADO);	
	}
	
	
	
	
}
