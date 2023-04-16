package br.com.eduardo.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;

	public PedidoNaoEncontradoException(String codigoPedido) {
		super(String.format("Pedido com o código %s não encontrado", codigoPedido));
	}
	
}
