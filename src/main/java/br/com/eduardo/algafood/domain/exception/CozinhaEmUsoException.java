package br.com.eduardo.algafood.domain.exception;

public class CozinhaEmUsoException extends EntidadeEmUsoException{

	private static final long serialVersionUID = 1L;
	
	public CozinhaEmUsoException(String mensagem) {
		super(mensagem);
	}
	
	public CozinhaEmUsoException(Long cozinhaId) {
		this(String.format(
				"Cozinha com id %d não pode ser excluído por estar em uso", cozinhaId));
	}
	
}
