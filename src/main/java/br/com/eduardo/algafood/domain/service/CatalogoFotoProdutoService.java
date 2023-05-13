package br.com.eduardo.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.eduardo.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import br.com.eduardo.algafood.domain.model.FotoProduto;
import br.com.eduardo.algafood.domain.repository.ProdutoRepository;
import br.com.eduardo.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {

	@Autowired
	private FotoStorageService fotoStorageService;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
		
		return produtoRepository.findFotoById(restauranteId, produtoId)
		.orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
	}
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
		Long restauranteId = foto.getRestauranteId();
		Long produtoId = foto.getProduto().getId();
		String nomeNovoArquivo =  fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
		String nomeArquivoExistente = null;
		
		Optional<FotoProduto> fotoExistente = produtoRepository
				.findFotoById(restauranteId, produtoId);
		
		if (fotoExistente.isPresent()) {
			nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
			produtoRepository.delete(fotoExistente.get());
		}
		
		foto.setNomeArquivo(nomeNovoArquivo);
		foto = produtoRepository.save(foto);
		produtoRepository.flush();
		
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(foto.getNomeArquivo())
				.contentType(foto.getContentType())
				.inputStream(dadosArquivo)
				.build();
		
		fotoStorageService.substituir(nomeArquivoExistente, novaFoto);
		
		return foto;
	}
	
	@Transactional
	public void remover(Long restauranteId, Long produtoId) {
		FotoProduto foto = buscarOuFalhar(restauranteId, produtoId);
		produtoRepository.delete(foto);
		produtoRepository.flush();
		
		fotoStorageService.remover(foto.getNomeArquivo());
		
	}
	
}
