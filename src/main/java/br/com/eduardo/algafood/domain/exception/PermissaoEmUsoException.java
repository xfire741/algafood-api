package br.com.eduardo.algafood.domain.exception;

public class PermissaoEmUsoException extends EntidadeEmUsoException{

	private static final long serialVersionUID = 1L;

	public PermissaoEmUsoException(String mensagem) {
		super(mensagem);
	}
	
	public PermissaoEmUsoException(Long id) {
		this(String.format(
				"Permissão com código %d não pode ser excluída pois está em uso", id));
	}

}
