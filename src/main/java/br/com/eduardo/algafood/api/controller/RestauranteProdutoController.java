package br.com.eduardo.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.algafood.api.assembler.ProdutoInputDisassembler;
import br.com.eduardo.algafood.api.assembler.ProdutoModelAssembler;
import br.com.eduardo.algafood.api.model.ProdutoDTO;
import br.com.eduardo.algafood.api.model.input.ProdutoInputDTO;
import br.com.eduardo.algafood.domain.model.Produto;
import br.com.eduardo.algafood.domain.model.Restaurante;
import br.com.eduardo.algafood.domain.service.CadastroProdutoService;
import br.com.eduardo.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {
	
	@Autowired
	private ProdutoInputDisassembler produtoInputDisassembler;
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private ProdutoModelAssembler produtoModelAssembler;
	
	@GetMapping
	public List<ProdutoDTO> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		
		return 	produtoModelAssembler.toCollectionDTO(restaurante.getProdutos());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO adicionarProduto(@PathVariable Long restauranteId, 
			@RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
		
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		Produto produto = produtoInputDisassembler.toDomainObject(produtoInputDTO);
		
		produto.setRestaurante(restaurante);
		
			return produtoModelAssembler.toDTO(cadastroProdutoService.salvar(produto));
	}
	
	@PutMapping("/{produtoId}")
	public ProdutoDTO atualizarProduto(@PathVariable Long restauranteId, 
			@PathVariable Long produtoId, @RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
		produtoInputDisassembler.copyToDomainObject(produtoInputDTO, produto);
		return produtoModelAssembler.toDTO(cadastroProdutoService.salvar(produto));
	}
	
	@DeleteMapping("/{produtoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluirProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		cadastroProdutoService.excluir(restauranteId ,produtoId);
	}
	
}
