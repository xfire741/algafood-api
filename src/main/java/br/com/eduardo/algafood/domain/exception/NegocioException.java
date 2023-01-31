package br.com.eduardo.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NegocioException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NegocioException(String mensagem) {
		super(mensagem);
	}

}
