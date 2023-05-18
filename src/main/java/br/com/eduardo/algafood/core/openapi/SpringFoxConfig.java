package br.com.eduardo.algafood.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.OAS_30)
				.select()
					.apis(RequestHandlerSelectors.basePackage("br.com.eduardo.algafood.api"))
					.paths(PathSelectors.any())
					.build()
				.apiInfo(apiInfo())
				.tags(new Tag("Cidades", "Gerencia as cidades"));
	}
	
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("AlgaFood API")
				.description("API aberta para clientes e restaurantes")
				.version("1")
				.contact(new Contact("Eduardo",
						"https://www.linkedin.com/in/eduardo-victor-silva-de-melo-7649831b3/", "eduardovictor28@gmail.com"))
				.build();
	}

}
