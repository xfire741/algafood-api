package br.com.eduardo.algafood.core.openapi;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.OAS_30)
				.select()
					.apis(RequestHandlerSelectors.basePackage("br.com.eduardo.algafood.api"))
					.paths(PathSelectors.any())
					.build()
				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPutResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
				.apiInfo(apiInfo())
				.tags(new Tag("Cidades", "Gerencia as cidades"));
	}
	
	private List<Response> globalGetResponseMessages() {
		  return Arrays.asList(
		      new ResponseBuilder()
		          .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		          .description("Erro interno do Servidor")
		          .build(),
		      new ResponseBuilder()
		          .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
		          .description("Recurso não possui representação que pode ser aceita pelo consumidor")
		          .build()
		  );
		}
	
	private List<Response> globalPostResponseMessages() {
		  return Arrays.asList(
				  new ResponseBuilder()
		          	.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		          	.description("Erro interno do Servidor")
		          	.build(),
		          new ResponseBuilder()
		          	.code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
		          	.description("Recurso não possui representação que pode ser aceita pelo consumidor")
		          	.build(),
		          new ResponseBuilder()
		      	  	.code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
		      	  	.description("Requisição inválida (erro do cliente)")
		      	  	.build(),
		      	  new ResponseBuilder()
		      	  	.code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
		      	  	.description("Requisição recusada porque o corpo está em um formato não suportado")
		      	  	.build()      
		  );
		}
	
	private List<Response> globalPutResponseMessages() {
		  return Arrays.asList(
				  new ResponseBuilder()
		          	.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		          	.description("Erro interno do Servidor")
		          	.build(),
		          new ResponseBuilder()
		          	.code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
		          	.description("Recurso não possui representação que pode ser aceita pelo consumidor")
		          	.build(),
		          new ResponseBuilder()
		      	  	.code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
		      	  	.description("Requisição inválida (erro do cliente)")
		      	  	.build(),
		      	  new ResponseBuilder()
		      	  	.code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
		      	  	.description("Requisição recusada porque o corpo está em um formato não suportado")
		      	  	.build()      
		  );
		}
	
	private List<Response> globalDeleteResponseMessages() {
		  return Arrays.asList(
				  new ResponseBuilder()
		          	.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		          	.description("Erro interno do Servidor")
		          	.build(),
		          new ResponseBuilder()
		      	  	.code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
		      	  	.description("Requisição inválida (erro do cliente)")
		      	  	.build());
		}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("AlgaFood API")
				.description("API aberta para clientes e restaurantes")
				.version("1")
				.contact(new Contact("Eduardo",
						"https://www.linkedin.com/in/eduardo-victor-silva-de-melo-7649831b3/", "eduardovictor28@gmail.com"))
				.build();
	}

}
