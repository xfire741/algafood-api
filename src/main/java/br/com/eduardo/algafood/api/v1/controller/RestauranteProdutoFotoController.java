package br.com.eduardo.algafood.api.v1.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.eduardo.algafood.api.v1.assembler.FotoProdutoModelAssembler;
import br.com.eduardo.algafood.api.v1.model.FotoProdutoDTO;
import br.com.eduardo.algafood.api.v1.model.input.FotoProdutoInput;
import br.com.eduardo.algafood.api.v1.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import br.com.eduardo.algafood.core.security.CheckSecurity;
import br.com.eduardo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.eduardo.algafood.domain.model.FotoProduto;
import br.com.eduardo.algafood.domain.model.Produto;
import br.com.eduardo.algafood.domain.service.CadastroProdutoService;
import br.com.eduardo.algafood.domain.service.CatalogoFotoProdutoService;
import br.com.eduardo.algafood.domain.service.FotoStorageService;
import br.com.eduardo.algafood.domain.service.FotoStorageService.FotoRecuperada;

@RestController
@RequestMapping("/v1/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {

	@Autowired
	private FotoProdutoModelAssembler fotoAssembler;

	@Autowired
	private CadastroProdutoService cadastroProdutoService;

	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProduto;

	@Autowired
	private FotoStorageService fotoStorageService;

	@CheckSecurity.Restaurantes.PodeEditar
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@Valid FotoProdutoInput fotoProdutoInput, @RequestPart(required = true) MultipartFile arquivo) throws IOException {
		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);

		//MultipartFile arquivo = fotoProdutoInput.getArquivo();

		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());

		FotoProduto fotoSalva = catalogoFotoProduto.salvar(foto, arquivo.getInputStream());

		return fotoAssembler.toModel(fotoSalva);
	}

	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping(produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public FotoProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId)
	{
		FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);
			
			return fotoAssembler.toModel(fotoProduto);
	}
	
	public ResponseEntity<?> recuperarFoto(@PathVariable Long restauranteId,@PathVariable Long produtoId)  {
		FotoProdutoDTO fotoProdutoDTO = fotoAssembler.toModel(catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId));
		return ResponseEntity.ok(fotoProdutoDTO);
	}

	@CheckSecurity.Restaurantes.PodeEditar
	@GetMapping
	public ResponseEntity<?> servirFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		try {
			FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);
			
			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
			
			verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);
			
			FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
			
			if (fotoRecuperada.temUrl()) {
				return ResponseEntity.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
						.build();
			} else {
				return ResponseEntity.ok().contentType(mediaTypeFoto)
						.body(new InputStreamResource(fotoRecuperada.getInputStream()));
			}
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}

	private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, 
			List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
		
		boolean compativel = mediaTypesAceitas.stream()
				.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));
		
		if(!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
		}
		
	}
	
	@CheckSecurity.Restaurantes.PodeEditar
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		catalogoFotoProduto.remover(restauranteId, produtoId);
	}


}
