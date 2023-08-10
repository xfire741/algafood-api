package br.com.eduardo.algafood.core.openapi;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Links;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;

import com.ctc.wstx.shaded.msv_core.util.Uri;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.eduardo.algafood.api.exceptionhandler.Problem;
import br.com.eduardo.algafood.api.model.CozinhaDTO;
import br.com.eduardo.algafood.api.model.PedidoResumoDTO;
import br.com.eduardo.algafood.api.openapi.model.CozinhasModelOpenApi;
import br.com.eduardo.algafood.api.openapi.model.LinksModelOpenApi;
import br.com.eduardo.algafood.api.openapi.model.PageableModelOpenApi;
import br.com.eduardo.algafood.api.openapi.model.PedidosResumoModelOpenApi;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

	@Bean
	public Docket apiDocket() {
		TypeResolver typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.OAS_30)
				.select()
					.apis(RequestHandlerSelectors.basePackage("br.com.eduardo.algafood.api"))
					.paths(PathSelectors.any())
					.build()
				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
				.additionalModels(typeResolver.resolve(Problem.class))
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.directModelSubstitute(Links.class, LinksModelOpenApi.class)
				.ignoredParameterTypes(ServletWebRequest.class,
						URL.class, Uri.class, URLStreamHandler.class, Resource.class, File.class
						,InputStream.class)
	            .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
	            .alternateTypeRules(AlternateTypeRules.newRule(
	                    typeResolver.resolve(Page.class, CozinhaDTO.class),
	                    CozinhasModelOpenApi.class))
	            .alternateTypeRules(AlternateTypeRules.newRule(
	                    typeResolver.resolve(Page.class, PedidoResumoDTO.class),
	                    PedidosResumoModelOpenApi.class))
	            .apiInfo(apiInfo())
	            .tags(new Tag("Cidades", "Gerencia as cidades"),
	                    new Tag("Grupos", "Gerencia os grupos de usuários"),
	                    new Tag("Cozinhas", "Gerencia as cozinhas"),
	                    new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
	                    new Tag("Pedidos", "Gerencia os pedidos"),
	                    new Tag("Restaurantes", "Gerencia os restaurantes"),
	                    new Tag("Estados", "Gerencia os estados"),
	                    new Tag("Produtos", "Gerencia os produtos de restaurantes"),
	                    new Tag("Usuários", "Gerencia os usuários"),
	                    new Tag("Estatísticas", "Estatísticas da AlgaFood"),
	                    new Tag("Permissões", "Gerencia as permissões")
	                    );
	}
	
	private List<Response> globalGetResponseMessages() {
		  return Arrays.asList(
		      new ResponseBuilder()
		          .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		          .description("Erro interno do Servidor")
		          .representation( MediaType.APPLICATION_JSON )
		          .apply(getProblemaModelReference())
		          .build(),
		      new ResponseBuilder()
		          .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
		          .description("Recurso não possui representação que pode ser aceita pelo consumidor")
		          .build()
		  );
		}
	
	private List<Response> globalPostPutResponseMessages() {
		  return Arrays.asList(
				  new ResponseBuilder()
		          	.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		          	.description("Erro interno do Servidor")
		          	.representation( MediaType.APPLICATION_JSON )
			        .apply(getProblemaModelReference())
		          	.build(),
		          new ResponseBuilder()
		          	.code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
		          	.description("Recurso não possui representação que pode ser aceita pelo consumidor")
		          	.build(),
		          new ResponseBuilder()
		      	  	.code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
		      	  	.description("Requisição inválida (erro do cliente)")
		      	  	.representation( MediaType.APPLICATION_JSON )
		      	  	.apply(getProblemaModelReference())
		      	  	.build(),
		      	  new ResponseBuilder()
		      	  	.code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
		      	  	.description("Requisição recusada porque o corpo está em um formato não suportado")
		      	  	.representation( MediaType.APPLICATION_JSON )
		      	  	.apply(getProblemaModelReference())
		      	  	.build()      
		  );
		}
	
	private List<Response> globalDeleteResponseMessages() {
		  return Arrays.asList(
				  new ResponseBuilder()
		          	.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		          	.description("Erro interno do Servidor")
		          	.representation( MediaType.APPLICATION_JSON )
			        .apply(getProblemaModelReference())
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
						"https://github.com/xfire741", "eduardovictor28@gmail.com"))
				.build();
	}
	
	@Bean
	public JacksonModuleRegistrar springFoxJacksonConfig() {
		return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
	}
	
	private Consumer<RepresentationBuilder> getProblemaModelReference() {
	    return r -> r.model(m -> m.name("Problema")
	            .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
	                    q -> q.name("Problema").namespace("br.com.eduardo.algafood.api.exceptionhandler")))));
	}

}
