package br.com.eduardo.algafood.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.eduardo.algafood.domain.service.EnvioEmailService;
import br.com.eduardo.algafood.infraestructure.service.email.FakeEnvioEmailService;
import br.com.eduardo.algafood.infraestructure.service.email.SmtpEnvioEmailService;

@Configuration
public class EmailConfig {

	@Autowired
	private EmailProperties emailProperties;
	
	@Bean
	public EnvioEmailService envioEmailService() {
		switch (emailProperties.getImpl()) {
		case FAKE: 
			return new FakeEnvioEmailService();
		case SMTP:
			return new SmtpEnvioEmailService();
		default:
			return null;
		}
	}
	
}
