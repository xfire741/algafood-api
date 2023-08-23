package br.com.eduardo.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import br.com.eduardo.algafood.api.exceptionhandler.Problem;
import br.com.eduardo.algafood.api.v1.model.FormaPagamentoDTO;
import br.com.eduardo.algafood.api.v1.model.input.FormaPagamentoInputDTO;
import br.com.eduardo.algafood.api.v1.openapi.model.FormasPagamentoModelOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Formas de pagamento")
public interface FormaPagamentoControllerOpenApi {

	@ApiOperation("Busca uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
	})
	ResponseEntity<FormaPagamentoDTO> buscar(
			@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long id, ServletWebRequest request);
	
	@ApiOperation(value = "Lista as formas de pagamento")
	@io.swagger.annotations.ApiResponses(value = {
	@io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = FormasPagamentoModelOpenApi.class)
	})
	ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar(ServletWebRequest request);  

	
	@ApiOperation("Cadastra uma forma de pagamento")
	@ApiResponses({
		@ApiResponse(code = 201, message = "forma de pagamento cadastrada")
	})
	FormaPagamentoDTO salvar(@ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento", 
			required = true) FormaPagamentoInputDTO formaPagamentoInputDTO);
	
	@ApiOperation("Atualiza uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Forma de pagamento atualizada"),
		@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
	})
	FormaPagamentoDTO atualizar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long id, 
			@ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com os novos dados", 
			required = true) FormaPagamentoInputDTO formaPagamentoInputDTO);
	
	@ApiOperation("Exclui uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Forma de pagamento excluída"),
		@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class),
		@ApiResponse(code = 409, message = "Forma de pagamento sendo utilizada")
	})
	void remover(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long id);
	
}
