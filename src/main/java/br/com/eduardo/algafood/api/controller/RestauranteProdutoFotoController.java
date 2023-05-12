package br.com.eduardo.algafood.api.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.eduardo.algafood.api.assembler.FotoProdutoModelAssembler;
import br.com.eduardo.algafood.api.model.FotoProdutoDTO;
import br.com.eduardo.algafood.api.model.input.FotoProdutoInput;
import br.com.eduardo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.eduardo.algafood.domain.model.FotoProduto;
import br.com.eduardo.algafood.domain.model.Produto;
import br.com.eduardo.algafood.domain.service.CadastroProdutoService;
import br.com.eduardo.algafood.domain.service.CatalogoFotoProdutoService;
import br.com.eduardo.algafood.domain.service.FotoStorageService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

	@Autowired
	private FotoProdutoModelAssembler fotoAssembler;

	@Autowired
	private CadastroProdutoService cadastroProdutoService;

	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProduto;

	@Autowired
	private FotoStorageService fotoStorageService;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@Valid FotoProdutoInput fotoProdutoInput) throws IOException {
		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);

		MultipartFile arquivo = fotoProdutoInput.getArquivo();

		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());

		FotoProduto fotoSalva = catalogoFotoProduto.salvar(foto, arquivo.getInputStream());

		return fotoAssembler.toDTO(fotoSalva);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {

		FotoProduto foto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);

		return fotoAssembler.toDTO(foto);
	}

	@GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId) {

		try {
			FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);
			InputStream inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(new InputStreamResource(inputStream));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
