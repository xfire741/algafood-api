package br.com.eduardo.algafood.infraestructure.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import br.com.eduardo.algafood.domain.service.EnvioEmailService.Mensagem;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class ProcessadorEmailTemplate {

    @Autowired
	private  Configuration freemarkerConfig;

    protected String processarTemplate(Mensagem mensagem) {
		try {
			Template template = freemarkerConfig.getTemplate(mensagem.getCorpo());
			
			return FreeMarkerTemplateUtils.processTemplateIntoString(template,
					mensagem.getVariaveis());
		
		} catch (Exception e) {
			throw new EmailException("Não foi possível montar o template do e-mail", e);
		}
		
	}

}
