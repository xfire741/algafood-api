package br.com.eduardo.algafood.api.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.eduardo.algafood.domain.exception.EntidadeEmUsoException;
import br.com.eduardo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.eduardo.algafood.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(
			EntidadeNaoEncontradaException e, WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;
		String detail = e.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
//		Problem problem = Problem.builder()
//				.status(status.value())
//				.type("http://algafood.com.br/entidade-nao-encontrada")
//				.title("Entidade não encontrada")
//				.detail(e.getMessage())
//				.build();
		
		return handleExceptionInternal(e, problem, 
				new HttpHeaders(), status, request);

	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request) {
		return handleExceptionInternal(e, e.getMessage(), 
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {
		return handleExceptionInternal(e, e.getMessage(), 
				new HttpHeaders(), HttpStatus.CONFLICT, request);
		
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if(body == null) {
		body = Problem.builder()
				.title(status.getReasonPhrase())
				.status(status.value())
				.build();
		} else if (body instanceof String) {
			body = Problem.builder()
					.title((String) body)
					.status(status.value())
					.build();
		}
			
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, 
			ProblemType problemType, String detail) {
		return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail);
	}
	
}
