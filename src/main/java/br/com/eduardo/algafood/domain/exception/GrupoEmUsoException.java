package br.com.eduardo.algafood.domain.exception;

public class GrupoEmUsoException extends EntidadeEmUsoException{

	private static final long serialVersionUID = 1L;

	public GrupoEmUsoException(String mensagem) {
		super(mensagem);
	}
	
	public GrupoEmUsoException(Long id) {
		this(String.format("Grupo com o código %d não pode ser excluído, pois está em uso"));
	}

}
