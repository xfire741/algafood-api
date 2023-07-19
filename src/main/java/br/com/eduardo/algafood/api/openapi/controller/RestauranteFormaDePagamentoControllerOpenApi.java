package br.com.eduardo.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import br.com.eduardo.algafood.api.exceptionhandler.Problem;
import br.com.eduardo.algafood.api.model.FormaPagamentoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteFormaDePagamentoControllerOpenApi {

	@ApiOperation("Lista formas de pagamento de um restaurante")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
	CollectionModel<FormaPagamentoDTO> listar(@ApiParam(example = "1", value = "Id de Restaurante", required = true) 
				Long restauranteId);
	
	@ApiOperation("Desassociação de forma de pagamento de restaurante")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrada")
    })
	ResponseEntity<Void> desassociar(@ApiParam(example = "1", value = "Id de Restaurante", required = true)
	Long restauranteId,
	@ApiParam(example = "1", value = "Id da forma de pagamento", required = true) Long formaPagamentoId);
	
	@ApiOperation("Associação de forma de pagamento de restaurante")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrada")
    })
	ResponseEntity<Void> associar(@ApiParam(example = "1", value = "Id de Restaurante", required = true) Long restauranteId,
			@ApiParam(example = "1", value = "Id da forma de pagamento", required = true) Long formaPagamentoId);
	
}
