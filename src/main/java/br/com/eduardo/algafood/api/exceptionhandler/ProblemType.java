package br.com.eduardo.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.uri = "http://algafood.com.br/" + path;
		this.title = title;
	}

}
