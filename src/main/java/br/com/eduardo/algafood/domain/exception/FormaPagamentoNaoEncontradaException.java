package br.com.eduardo.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;
	
	public FormaPagamentoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public FormaPagamentoNaoEncontradaException(Long formaPagamentoId) {
		this(String.format("Forma de pagamento com o código %d não encontrada", formaPagamentoId));
	}

}
