package br.com.eduardo.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.eduardo.algafood.domain.exception.EntidadeEmUsoException;
import br.com.eduardo.algafood.domain.exception.RestauranteNaoEncontradaException;
import br.com.eduardo.algafood.domain.model.Cidade;
import br.com.eduardo.algafood.domain.model.Cozinha;
import br.com.eduardo.algafood.domain.model.FormaPagamento;
import br.com.eduardo.algafood.domain.model.Restaurante;
import br.com.eduardo.algafood.domain.model.Usuario;
import br.com.eduardo.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	private static final String RESTAURANTE_EM_USO = "Restaurante com o código %d não pode ser removido, pois está em uso.";
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) { 
	    Cozinha cozinha = cadastroCozinha.buscarOuFalhar(restaurante.getCozinha().getId());
	    
	    Cidade cidade = cadastroCidadeService
	    		.buscarOuFalhar(restaurante.getEndereco().getCidade().getId());
	    
	    restaurante.setCozinha(cozinha);
	    restaurante.getEndereco().setCidade(cidade);
	    
	    return restauranteRepository.save(restaurante);
	}
	
	@Transactional
	public void excluir(Long restauranteId) {
		try {
		restauranteRepository.deleteById(restauranteId);
		restauranteRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradaException(restauranteId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(RESTAURANTE_EM_USO, restauranteId));
		}
	}
	
	@Transactional
	public void ativar(Long id) {
		Restaurante restauranteAtual = buscarOuFalhar(id);
		restauranteAtual.ativar();;
	}
	
	@Transactional
	public void inativar(Long id) {
		Restaurante restauranteAtual = buscarOuFalhar(id);
		
		restauranteAtual.inativar();;
	}
	
	@Transactional
	public void ativar(List<Long> restauranteIds) {
		restauranteIds.forEach(this::ativar);
	}
	
	@Transactional
	public void inativar(List<Long> restauranteIds) {
		restauranteIds.forEach(this::inativar);
	}
	
	@Transactional
	public void abrir(Long id) {
		Restaurante restauranteAtual = buscarOuFalhar(id);
		
		restauranteAtual.abertura();
	}
	
	@Transactional
	public void fechar(Long id) {
		Restaurante restauranteAtual = buscarOuFalhar(id);
		
		restauranteAtual.fechamento();
	}
	
	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);
		
		restaurante.removerFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);
		
		restaurante.adicionarFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void associarUsuarioResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
		
		restaurante.adicionarUsuarioResponsavel(usuario);
	}
	
	@Transactional
	public void disassociarUsuarioResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
		
		restaurante.removerUsuarioResponsavel(usuario);
	}
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
	    return restauranteRepository.findById(restauranteId)
	        .orElseThrow(() -> new RestauranteNaoEncontradaException(restauranteId));
	}

}
