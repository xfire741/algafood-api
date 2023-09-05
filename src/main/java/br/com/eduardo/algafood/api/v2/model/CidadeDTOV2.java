package br.com.eduardo.algafood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cidades")
@Setter
@Getter
@ApiModel("CidadeModel")
public class CidadeDTOV2 extends RepresentationModel<CidadeDTOV2> {
	
	@ApiModelProperty(example = "1")
	private Long idCidade;
	
	@ApiModelProperty(example = "Uberlândia")
	private String nomeCidade;
	
	private Long idEstado;
	private String nomeEstado;

}
