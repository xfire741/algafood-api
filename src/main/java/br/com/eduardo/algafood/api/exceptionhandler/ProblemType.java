package br.com.eduardo.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	MENSAGEM_INCOMPREENSIVEL("/mesagem-incompreensivel", "Mensagem incompreensível"),
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação da regra de negócio"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro Inválido");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.uri = "http://algafood.com.br" + path;
		this.title = title;
	}

}
