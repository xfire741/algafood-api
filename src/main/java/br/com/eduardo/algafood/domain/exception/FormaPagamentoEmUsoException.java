package br.com.eduardo.algafood.domain.exception;

public class FormaPagamentoEmUsoException extends EntidadeEmUsoException{

	private static final long serialVersionUID = 1L;
	
	public FormaPagamentoEmUsoException(String mensagem) {
		super(mensagem);
	}

	public FormaPagamentoEmUsoException(Long id) {
		this(String.format(
				"Forma de pagamento com o código %d não pode ser removida, pois está em uso.", id));
	}
	
}
