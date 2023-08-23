package br.com.eduardo.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import br.com.eduardo.algafood.api.exceptionhandler.Problem;
import br.com.eduardo.algafood.api.v1.model.CidadeDTO;
import br.com.eduardo.algafood.api.v1.model.input.CidadeInputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

	@ApiOperation("Lista as cidades")
	public CollectionModel<CidadeDTO> listar();

	@ApiOperation("Busca uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	public CidadeDTO buscar(@ApiParam(value = "ID de uma cidade", required = true) Long id);
		

	@ApiOperation("Cadastra uma cidade")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cidade cadastrada")
	})
	public CidadeDTO adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
			CidadeInputDTO cidadeInputDTO);
		

	@ApiOperation("Exclui uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cidade excluída"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	public void remover(@ApiParam(value = "ID de uma cidade", required = true) Long id);

	@ApiOperation("Atualiza uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cidade atualizada"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class),
		@ApiResponse(code = 409, message = "Cidade sendo utilizada")
	})
	public CidadeDTO atualizar(@ApiParam(value = "ID de uma cidade", required = true) Long id,
			@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados") 
			CidadeInputDTO cidadeInputDTO);
	
}
