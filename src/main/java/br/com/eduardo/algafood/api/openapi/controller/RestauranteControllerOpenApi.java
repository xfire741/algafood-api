package br.com.eduardo.algafood.api.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import br.com.eduardo.algafood.api.exceptionhandler.Problem;
import br.com.eduardo.algafood.api.model.RestauranteApenasNomeModel;
import br.com.eduardo.algafood.api.model.RestauranteBasicoModel;
import br.com.eduardo.algafood.api.model.RestauranteDTO;
import br.com.eduardo.algafood.api.model.input.RestauranteInputDTO;
import br.com.eduardo.algafood.api.openapi.model.RestauranteBasicoModelOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

	@ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoModelOpenApi.class)
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome", 
				dataType = "java.lang.String",
				name = "projecao", paramType = "query", type = "string")
	})
	CollectionModel<RestauranteBasicoModel> listar();
	
	@ApiOperation(value = "Lista restaurantes", hidden = true)
	CollectionModel<RestauranteApenasNomeModel> listarApenasNome();
	
	@ApiOperation("Busca um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	RestauranteDTO buscar(@ApiParam(value = "ID de um restaurante", required = true) Long id);
	

	@ApiOperation("Cadastra um restaurante")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Restaurante cadastrado")
	})
	RestauranteDTO adicionar(@ApiParam
			(name = "corpo", value = "Representação de um restaurante com os dados a serem adicionados", required = true) RestauranteInputDTO restauranteInput);
	
	@ApiOperation("Atualiza um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Restaurante atualizado"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
	})
	RestauranteDTO atualizar(@ApiParam(value = "ID de um restaurante", required = true) Long id,@ApiParam(name = "corpo", value = "Representação de um restaurante com os novos dados", required = true) RestauranteInputDTO restauranteInput);
		

		@ApiOperation("Exclui uma cozinha por ID")
		@ApiResponses({
			@ApiResponse(code = 204, message = "Cozinha excluída"),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class),
			@ApiResponse(code = 409, message = "Cozinha sendo utilizada")
		})
		void remover(@ApiParam(value = "ID de um restaurante", required = true) Long id);
		
		@ApiOperation("Ativa um restaurante por ID")
		@ApiResponses({
		    @ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
		    @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
		})
		ResponseEntity<Void> ativar(
		        @ApiParam(value = "ID de um restaurante", example = "1", required = true)
		        Long restauranteId);

		@ApiOperation("Inativa um restaurante por ID")
		@ApiResponses({
		    @ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
		    @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
		})
		ResponseEntity<Void> inativar(
		        @ApiParam(value = "ID de um restaurante", example = "1", required = true)
		        Long restauranteId);
		void ativarMultiplos(@ApiParam(required = true) List<Long> restauranteIds);
		
		@ApiOperation("Desativa vários restaurantes de uma lista de IDs")
		@ApiResponses({
			@ApiResponse(code = 204, message = "Restaurantes inativados"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
		})		
		void inativarMultiplos(@ApiParam(required = true) List<Long> restauranteIds);
		
		@ApiOperation("Abre um restaurante pelo ID")
		@ApiResponses({
			@ApiResponse(code = 204, message = "Restaurante aberto"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
		})
		ResponseEntity<Void> abertura(@ApiParam(value = "ID de um restaurante", required = true) Long restauranteId);
		
		@ApiOperation("Fecha um restaurante pelo ID")
		@ApiResponses({
			@ApiResponse(code = 204, message = "Restaurante fechado"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
		})
		ResponseEntity<Void> fechamento(@ApiParam(value = "ID de um restaurante", required = true) Long restauranteId);
	
}
