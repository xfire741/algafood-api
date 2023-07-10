package br.com.eduardo.algafood.api;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.controller.CidadeController;
import br.com.eduardo.algafood.api.controller.CozinhaController;
import br.com.eduardo.algafood.api.controller.EstadoController;
import br.com.eduardo.algafood.api.controller.FluxoPedidoController;
import br.com.eduardo.algafood.api.controller.FormaPagamentoController;
import br.com.eduardo.algafood.api.controller.PedidoController;
import br.com.eduardo.algafood.api.controller.RestauranteController;
import br.com.eduardo.algafood.api.controller.RestauranteFormaDePagamentoController;
import br.com.eduardo.algafood.api.controller.RestauranteProdutoController;
import br.com.eduardo.algafood.api.controller.RestauranteUsuarioResponsavelController;
import br.com.eduardo.algafood.api.controller.UsuarioController;
import br.com.eduardo.algafood.api.controller.UsuarioGrupoController;

@Component
public class AlgaLinks {
	
	public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
    		new TemplateVariable("page", VariableType.REQUEST_PARAM),
    		new TemplateVariable("size", VariableType.REQUEST_PARAM),
    		new TemplateVariable("sort", VariableType.REQUEST_PARAM));

	public Link linkToPedidos() {
        
        TemplateVariables filterVariables = new TemplateVariables(
        		new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
        		new TemplateVariable("restauranteID", VariableType.REQUEST_PARAM),
        		new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
        		new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));
		
        String pedidosURL = WebMvcLinkBuilder.linkTo(PedidoController.class).toUri().toString();
        
        return Link.of(UriTemplate.of(pedidosURL, PAGINACAO_VARIABLES.concat(filterVariables)),
        		"pedidos");
	}
	
	public Link linkToRestauranteResponsaveis(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteUsuarioResponsavelController.class)
				.listarUsuariosResponsaveis(restauranteId)).withRel(rel);
	}
	
	public Link linkToResponsaveisRestaurante(Long restauranteId) {
		return linkToRestauranteResponsaveis(restauranteId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToRestaurantes(String rel) {
	    return WebMvcLinkBuilder.linkTo(RestauranteController.class).withRel(rel);
	}

	public Link linkToRestaurantes() {
	    return linkToRestaurantes(IanaLinkRelations.SELF.value());
	}

	public Link linkToRestauranteFormasPagamento(Long restauranteId, String rel) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteFormaDePagamentoController.class)
	            .listar(restauranteId)).withRel(rel);
	}

	public Link linkToCozinha(Long cozinhaId, String rel) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CozinhaController.class)
	            .buscar(cozinhaId)).withRel(rel);
	}

	public Link linkToCozinha(Long cozinhaId) {
	    return linkToCozinha(cozinhaId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToConfirmacaoPedido(String codigoPedido, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FluxoPedidoController.class)
				.confirmar(codigoPedido)).withRel(rel);
	}
	
	public Link linkToEntregaPedido(String codigoPedido, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FluxoPedidoController.class)
				.entregar(codigoPedido)).withRel(rel);
	}
	
	public Link linkToCancelamentoPedido(String codigoPedido, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FluxoPedidoController.class)
				.cancelar(codigoPedido)).withRel(rel);
	}
	
	public Link linkToRestaurante(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class)
				.buscar(restauranteId)).withRel(rel);
	}
	
	public Link linkToRestaurante(Long restauranteId) {
	    return linkToRestaurante(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToUsuario(Long usuarioId, String rel) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
	            .buscar(usuarioId)).withRel(rel);
	}

	public Link linkToUsuario(Long usuarioId) {
	    return linkToUsuario(usuarioId, IanaLinkRelations.SELF.value());
	}

	public Link linkToUsuarios(String rel) {
	    return WebMvcLinkBuilder.linkTo(UsuarioController.class).withRel(rel);
	}

	public Link linkToUsuarios() {
	    return linkToUsuarios(IanaLinkRelations.SELF.value());
	}

	public Link linkToGruposUsuario(Long usuarioId, String rel) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioGrupoController.class)
	            .listar(usuarioId)).withRel(rel);
	}

	public Link linkToGruposUsuario(Long usuarioId) {
	    return linkToGruposUsuario(usuarioId, IanaLinkRelations.SELF.value());
	}

	public Link linkToResponsaveisRestaurante(Long restauranteId, String rel) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteUsuarioResponsavelController.class)
	            .listarUsuariosResponsaveis(restauranteId)).withRel(rel);
	}

	public Link linkToFormaPagamento(Long formaPagamentoId, String rel) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FormaPagamentoController.class)
	            .buscar(formaPagamentoId, null)).withRel(rel);
	}

	public Link linkToFormaPagamento(Long formaPagamentoId) {
	    return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToCidade(Long cidadeId, String rel) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
	            .buscar(cidadeId)).withRel(rel);
	}

	public Link linkToCidade(Long cidadeId) {
	    return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
	}

	public Link linkToCidades(String rel) {
	    return WebMvcLinkBuilder.linkTo(CidadeController.class).withRel(rel);
	}

	public Link linkToCidades() {
	    return linkToCidades(IanaLinkRelations.SELF.value());
	}

	public Link linkToEstado(Long estadoId, String rel) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class)
	            .buscar(estadoId)).withRel(rel);
	}

	public Link linkToEstado(Long estadoId) {
	    return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToEstados(String rel) {
	    return WebMvcLinkBuilder.linkTo(EstadoController.class).withRel(rel);
	}

	public Link linkToEstados() {
	    return linkToEstados(IanaLinkRelations.SELF.value());
	}

	public Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteProdutoController.class)
	            .buscar(restauranteId, produtoId))
	            .withRel(rel);
	}

	public Link linkToProduto(Long restauranteId, Long produtoId) {
	    return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToCozinhas(String rel) {
	    return WebMvcLinkBuilder.linkTo(CozinhaController.class).withRel(rel);
	}

	public Link linkToCozinhas() {
	    return linkToCozinhas(IanaLinkRelations.SELF.value());
	}
	
}
