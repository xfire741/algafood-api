package br.com.eduardo.algafood.domain.exception;

public class EstadoEmUsoException extends EntidadeEmUsoException{

	private static final long serialVersionUID = 1L;
	
	public EstadoEmUsoException(String mensagem) {
		super(mensagem);
	}
	
	public EstadoEmUsoException(Long estadoId) {
		this(String.format(
				"Estado com id %d não pode ser excluído por estar em uso", estadoId));
	}
	
}
