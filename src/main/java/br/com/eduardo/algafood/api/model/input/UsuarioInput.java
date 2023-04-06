package br.com.eduardo.algafood.api.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioInput {

	@NotBlank
	private String nome;
	
	@Email
	@NotBlank
	private String email;
	
}
