package br.com.eduardo.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import br.com.eduardo.algafood.domain.exception.EntidadeEmUsoException;
import br.com.eduardo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.eduardo.algafood.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Erro no sistema, por favor tente novamente mais tarde. "
			+ "Se o erro persistir entre em contato com o administrador do sistema";
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status).headers(headers).build();
	}
	
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		return handleValidationInternal(ex, ex, headers, status, request, ex.getBindingResult());
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if(rootCause instanceof UnrecognizedPropertyException) {
			return handleUnrecognizedPropertyException((UnrecognizedPropertyException) rootCause, headers, status, request);
		}
		
		if(rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}
		
		if(rootCause instanceof IgnoredPropertyException) {
			return handleIgnoredPropertyException((IgnoredPropertyException) rootCause, headers, status, request);
		}
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da resposta está inválido. Verifique a sintaxe.";
		
		Problem problem = createProblemBuilder(status, problemType, detail, OffsetDateTime.now()).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
		
		return handleExceptionInternal(ex, problem, 
				new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor compatívelcom o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problem problem = createProblemBuilder(status, problemType, detail, OffsetDateTime.now()).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	protected ResponseEntity<?> handleEntidadeNaoEncontradaException(
			EntidadeNaoEncontradaException e, WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = e.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail, OffsetDateTime.now()).userMessage(detail).build();
		
		return handleExceptionInternal(e, problem, 
				new HttpHeaders(), status, request);

	}
	
	protected ResponseEntity<Object> handleUnrecognizedPropertyException(UnrecognizedPropertyException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' não existe", path);
		
		Problem problem = createProblemBuilder(status, problemType, detail, OffsetDateTime.now()).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
		
	}
	
	protected ResponseEntity<Object> handleIgnoredPropertyException(IgnoredPropertyException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining());
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' não pode ser inserida no corpo da resposta", path);
		
		Problem problem = createProblemBuilder(status, problemType, detail, OffsetDateTime.now()).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
		
	}
	
	@ExceptionHandler(NegocioException.class)
	protected ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		String detail = e.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail, OffsetDateTime.now()).build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	protected ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {
		
		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = e.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail, OffsetDateTime.now()).userMessage(detail).build();
		
		return handleExceptionInternal(e, problem,
				new HttpHeaders(), status, request);
		
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if(body == null) {
			body = Problem.builder()
					.timestamp(OffsetDateTime.now())
				.title(status.getReasonPhrase())
				.status(status.value())
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		} else if (body instanceof String) {
			body = Problem.builder()
					.timestamp(OffsetDateTime.now())
					.title((String) body)
					.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
					.status(status.value())
					.build();
		}
			
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if(ex instanceof MethodArgumentTypeMismatchException) {
				return handleMethodArgumentTypeMismatch(
						(MethodArgumentTypeMismatchException) ex, headers, status, request);
		}
		
		return super.handleTypeMismatch(ex, headers, status, request);
		
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String detail = String.format("Recurso %s não encontrado", ex.getRequestURL());
		
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		Problem problem = createProblemBuilder(status, problemType, detail, OffsetDateTime.now()).build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpHeaders headers,
	        HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
		
		String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
		            + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
		            ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		Problem problem = createProblemBuilder(status, problemType, detail, OffsetDateTime.now()).build();

		    return handleExceptionInternal(ex, problem, headers, status, request);
		
	}
	
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleUncaughts(EntidadeEmUsoException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.ERRO_SISTEMA;
		
		
		String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;
		
		ex.printStackTrace();
		
		Problem problem = createProblemBuilder(status, problemType, detail, OffsetDateTime.now()).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
		
	}

	@ExceptionHandler(AccessDeniedException.class)
	protected ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e, WebRequest request) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		ProblemType problemType = ProblemType.ACESSO_NEGADO;
		String detail = e.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail, OffsetDateTime.now()).userMessage(detail).build();

		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request, ex.getBindingResult());
	}
	
	private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult,
			HttpHeaders headers, HttpStatus status, WebRequest request, BindingResult bindResult) {
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
		
		List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
		        .map(objectError -> {
		            String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
		            
		            String name = objectError.getObjectName();
		            
		            if (objectError instanceof FieldError) {
		                name = ((FieldError) objectError).getField();
		            }
		            
		            return Problem.Object.builder()
		                .name(name)
		                .userMessage(message)
		                .build();
		        })
		        .collect(Collectors.toList());
		
		Problem problem = createProblemBuilder(status, problemType, detail, OffsetDateTime.now())
		    .userMessage(detail)
		    .objects(problemObjects)
		    .build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, 
			ProblemType problemType, String detail, OffsetDateTime timestamp) {
		return Problem.builder()
				.timestamp(timestamp)
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail);
	}
	
}
