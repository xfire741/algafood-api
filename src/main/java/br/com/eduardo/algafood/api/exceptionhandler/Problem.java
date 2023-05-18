package br.com.eduardo.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {
	
	@ApiModelProperty(example = "400", position = 1)
	private Integer status;
	
	@ApiModelProperty(example = "http://algafood.com.br/dados-invalidos")
	private String type;
	
	@ApiModelProperty(example = "Dados inválidos")
	private String title;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
	private String detail;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
	private String userMessage;
	
	@ApiModelProperty(example = "2023-05-18T21:16:15.455944614Z")
	private OffsetDateTime timestamp;
	
	@ApiModelProperty(value = "Objeto ou campos que geraram o erro (opcional)", position = 5)
	private List<Object> objects;
	
	@ApiModel("ObjetoProblema")
	@Getter
	@Builder
	public static class Object {
		
		@ApiModelProperty(example = "preco")
		private String name;
		
		@ApiModelProperty(example = "O preço é obrigatório")
		private String userMessage;
	}
	
}
