package br.com.eduardo.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "formasPagamento")
@Setter
@Getter
public class FormaPagamentoDTO extends RepresentationModel<FormaPagamentoDTO> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Catão de crédito")
	private String descricao;
	
}
