package br.com.eduardo.algafood.core.email;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {
	
	private Sandbox sandbox = new Sandbox();
	
	private Implementacao impl = Implementacao.FAKE;

	private String remetente;
	
	public enum Implementacao {
		SMTP, FAKE, SANDBOX
	}
	
	@Getter
	@Setter
	public class Sandbox {
		private String destinatario;
	}
	
}
