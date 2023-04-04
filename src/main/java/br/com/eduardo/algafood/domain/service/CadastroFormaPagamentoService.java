package br.com.eduardo.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.eduardo.algafood.domain.exception.FormaPagamentoEmUsoException;
import br.com.eduardo.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import br.com.eduardo.algafood.domain.model.FormaPagamento;
import br.com.eduardo.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	public FormaPagamento buscarOuFalhar(Long id) {
		return formaPagamentoRepository.findById(id)
				.orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
	}
	
	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}
	
	@Transactional
	public void excluir(Long id) {
		try {
			formaPagamentoRepository.deleteById(id);
			formaPagamentoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontradaException(id);
		} catch (DataIntegrityViolationException e) {
			throw new FormaPagamentoEmUsoException(id);
		}
	}
	
}
