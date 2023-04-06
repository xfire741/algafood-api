package br.com.eduardo.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public UsuarioNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public UsuarioNaoEncontradoException(Long id) {
		this(String.format("Usuario com o código %d não encontrado", id));
	}
	
}
