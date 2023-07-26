package br.com.eduardo.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.algafood.api.AlgaLinks;
import br.com.eduardo.algafood.api.assembler.ProdutoInputDisassembler;
import br.com.eduardo.algafood.api.assembler.ProdutoModelAssembler;
import br.com.eduardo.algafood.api.model.ProdutoDTO;
import br.com.eduardo.algafood.api.model.input.ProdutoInputDTO;
import br.com.eduardo.algafood.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import br.com.eduardo.algafood.domain.model.Produto;
import br.com.eduardo.algafood.domain.model.Restaurante;
import br.com.eduardo.algafood.domain.repository.ProdutoRepository;
import br.com.eduardo.algafood.domain.service.CadastroProdutoService;
import br.com.eduardo.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ProdutoInputDisassembler produtoInputDisassembler;
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private ProdutoModelAssembler produtoModelAssembler;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<ProdutoDTO> listar(@PathVariable Long restauranteId, 
			@RequestParam(required = false, defaultValue = "false") Boolean incluirInativos) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
	    
	    List<Produto> todosProdutos = null;
	    
	    if (incluirInativos) {
	        todosProdutos = produtoRepository.findAllByRestaurante(restaurante);
	    } else {
	        todosProdutos = produtoRepository.findAtivosByRestaurante(restaurante);
	    }
	    
	    return produtoModelAssembler.toCollectionModel(todosProdutos)
	            .add(algaLinks.linkToProdutos(restauranteId));
	}
	
	@GetMapping(path = "/{produtoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
		
		return produtoModelAssembler.toModel(produto);
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO adicionarProduto(@PathVariable Long restauranteId, 
			@RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
		
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		Produto produto = produtoInputDisassembler.toDomainObject(produtoInputDTO);
		
		produto.setRestaurante(restaurante);
		
			return produtoModelAssembler.toModel(cadastroProdutoService.salvar(produto));
	}
	
	@PutMapping(path = "/{produtoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ProdutoDTO atualizarProduto(@PathVariable Long restauranteId, 
			@PathVariable Long produtoId, @RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
		produtoInputDisassembler.copyToDomainObject(produtoInputDTO, produto);
		return produtoModelAssembler.toModel(cadastroProdutoService.salvar(produto));
	}
	
}
