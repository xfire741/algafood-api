package br.com.eduardo.algafood.domain.exception;

public class EstadoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public EstadoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public EstadoNaoEncontradaException(Long estadoId) {
		this(String.format("Estado com o código %d não encontrado", estadoId));
	}

}
