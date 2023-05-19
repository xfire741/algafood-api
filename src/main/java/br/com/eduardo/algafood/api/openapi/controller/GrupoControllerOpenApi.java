package br.com.eduardo.algafood.api.openapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.eduardo.algafood.api.exceptionhandler.Problem;
import br.com.eduardo.algafood.api.model.GrupoDTO;
import br.com.eduardo.algafood.api.model.input.GrupoInputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

	@ApiOperation("Busca cidade pelo ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID do Grupo inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	public GrupoDTO buscar(@PathVariable Long id);
	
	@ApiOperation("Lista os Grupos")
	public List<GrupoDTO> listar();
	
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cidade cadastrada")
	})
	@ApiOperation("Adiciona Grupo")
	public GrupoDTO adicionar(@RequestBody @Valid GrupoInputDTO grupoInputDTO);
	
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cidade atualizada"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	@ApiOperation("Atualiza Grupo")
	public GrupoDTO atualizar(@PathVariable Long id, @RequestBody @Valid GrupoInputDTO grupoInputDTO);
	
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cidade excluída"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	@ApiOperation("Exclui Grupo")
	public void excluir(@PathVariable Long id);
	
}
