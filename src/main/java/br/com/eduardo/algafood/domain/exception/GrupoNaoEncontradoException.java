package br.com.eduardo.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException{
	private static final long serialVersionUID = 1L;

	public GrupoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public GrupoNaoEncontradoException(Long id) {
		this(String.format("Grupo com o código %d não encontrado", id));
	}
	
}
