package br.com.eduardo.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import br.com.eduardo.algafood.api.exceptionhandler.Problem;
import br.com.eduardo.algafood.api.v1.model.GrupoDTO;
import br.com.eduardo.algafood.api.v1.model.input.GrupoInputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

	@ApiOperation("Busca cidade pelo ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID do Grupo inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	GrupoDTO buscar(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long id);
	
	@ApiOperation("Lista os grupos associados a um usuário")
	@ApiResponses({
	    @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	})
	CollectionModel<GrupoDTO> listar();
	
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cidade cadastrada")
	})
	@ApiOperation("Adiciona Grupo")
	GrupoDTO adicionar(@ApiParam(name = "corpo", value = "Representação de um novo grupo", required = true) GrupoInputDTO grupoInputDTO);
	
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cidade atualizada"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	@ApiOperation("Atualiza Grupo")
	GrupoDTO atualizar(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long id,
			@ApiParam(name = "corpo", value = "Representação de um novo grupo", required = true) GrupoInputDTO grupoInputDTO);
	
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cidade excluída"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	@ApiOperation("Exclui Grupo")
	void excluir(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long id);
	
}
