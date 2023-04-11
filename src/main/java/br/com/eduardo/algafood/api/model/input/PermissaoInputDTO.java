package br.com.eduardo.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class PermissaoInputDTO {

	@NotBlank
	private String nome;
	
	@NotBlank
	private String descricao;
	
}
