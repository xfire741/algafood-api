package br.com.eduardo.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import br.com.eduardo.algafood.api.v1.model.PermissaoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Permissões")
public interface PermissaoControllerOpenApi {

	@ApiOperation("Lista as permissões")
    CollectionModel<PermissaoDTO> listar();
	
}
