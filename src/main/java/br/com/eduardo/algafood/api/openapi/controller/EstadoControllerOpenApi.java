package br.com.eduardo.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import br.com.eduardo.algafood.api.exceptionhandler.Problem;
import br.com.eduardo.algafood.api.model.EstadoDTO;
import br.com.eduardo.algafood.api.model.input.EstadoInputDTO;
import br.com.eduardo.algafood.domain.model.Estado;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

	@ApiOperation(value = "Lista estados")
	CollectionModel<EstadoDTO> listar();
	
	@ApiOperation("Busca um estado por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID do estado inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "estado não encontrado", response = Problem.class)
	})
	EstadoDTO buscar(@ApiParam(value = "ID de um estado", required = true) Long id);
	
	@ApiOperation("Cadastra um estado")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Estado cadastrado")
	})
	Estado adicionar(@ApiParam
			(name = "corpo", value = "Representação de um estado com os dados a serem adicionados", required = true) EstadoInputDTO estadoInputDTO);

	
	void remover(@ApiParam(value = "ID de um estado", required = true) Long id);
	
	@ApiOperation("Atualiza um estado por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Estado atualizado"),
		@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class),
	})
	EstadoDTO atualizar(@ApiParam(value = "ID de um estado", required = true) Long id, 
			@ApiParam
			(name = "corpo", value = "Representação de um estado com os dados a serem adicionados", required = true) EstadoInputDTO estadoInputDTO);
	
}
