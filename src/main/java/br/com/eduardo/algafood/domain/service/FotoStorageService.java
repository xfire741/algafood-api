package br.com.eduardo.algafood.domain.service;

import java.io.InputStream;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {

	void armazenar(NovaFoto novaFoto);
	
	@Builder
	@Getter
	class NovaFoto {
		
		private String nomeArquivo;
		private InputStream inputStream;
		
	}
	
}
