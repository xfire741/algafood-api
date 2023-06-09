package br.com.eduardo.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoDTO extends RepresentationModel<EstadoDTO> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Minas Gerais")
	private String nome;
	
}
