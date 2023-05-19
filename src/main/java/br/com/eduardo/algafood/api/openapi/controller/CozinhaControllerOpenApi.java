package br.com.eduardo.algafood.api.openapi.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.eduardo.algafood.api.exceptionhandler.Problem;
import br.com.eduardo.algafood.api.model.CozinhaDTO;
import br.com.eduardo.algafood.api.model.input.CozinhaInputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {
	
	@ApiOperation("Lista as cozinhas")
	public Page<CozinhaDTO> listar(@PageableDefault(size = 2) Pageable pageable);
	
	@ApiOperation("Busca uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	public CozinhaDTO buscar(@PathVariable("id") Long id);

	@ApiOperation("Cadastra uma cozinha")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cozinha cadastrada")
	})
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInputDTO cozinhaInputDTO);

	@ApiOperation("Atualiza uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cozinha atualizada"),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	public CozinhaDTO atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInputDTO cozinhaInputDTO);

	@ApiOperation("Exclui uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cozinha excluída"),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	public void remover(@PathVariable Long id);
	
}
