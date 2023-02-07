package br.com.eduardo.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	MENSAGEM_INCOMPREENSIVEL("/mesagem-incompreensivel", "Mensagem incompreensível"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação da regra de negócio"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro Inválido"),
	ERRO_SISTEMA("/erro-sistema", "Erro no sistema"),
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.uri = "http://algafood.com.br" + path;
		this.title = title;
	}

}
