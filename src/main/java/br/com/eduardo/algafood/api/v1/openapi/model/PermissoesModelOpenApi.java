package br.com.eduardo.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import br.com.eduardo.algafood.api.v1.model.PermissaoDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("PermissoesModel")
@Data
public class PermissoesModelOpenApi {

    private PermissoesEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    @ApiModel("PermissoesEmbeddedModel")
    @Data
    public class PermissoesEmbeddedModelOpenApi {
        
        private List<PermissaoDTO> permissoes;
        
    }   
}