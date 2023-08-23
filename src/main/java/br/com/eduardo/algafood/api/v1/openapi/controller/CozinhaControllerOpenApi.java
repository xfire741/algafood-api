package br.com.eduardo.algafood.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import br.com.eduardo.algafood.api.exceptionhandler.Problem;
import br.com.eduardo.algafood.api.v1.model.CozinhaDTO;
import br.com.eduardo.algafood.api.v1.model.input.CozinhaInputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {
	
	@ApiOperation("Lista as cozinhas")
	PagedModel<CozinhaDTO> listar(Pageable pageable);
	
	@ApiOperation("Busca uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	CozinhaDTO buscar(@ApiParam(value = "ID de uma cozinha", required = true) Long id);

	@ApiOperation("Cadastra uma cozinha")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cozinha cadastrada")
	})
	CozinhaDTO adicionar(@ApiParam (name = "corpo", value = "Representação de uma cozinha com os dados a serem adicionados",
	required = true) CozinhaInputDTO cozinhaInputDTO);

	@ApiOperation("Atualiza uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cozinha atualizada"),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class),
	})
	CozinhaDTO atualizar(@ApiParam(value = "ID de uma cozinha", required = true) Long id, 
			@ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados", required = true) CozinhaInputDTO cozinhaInputDTO);

	@ApiOperation("Exclui uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cozinha excluída"),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class),
		@ApiResponse(code = 409, message = "Cozinha sendo utilizada")
	})
	void remover(@ApiParam(value = "ID de uma cozinha", required = true) Long id);
	
}
