package br.com.eduardo.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.eduardo.algafood.domain.exception.EntidadeEmUsoException;
import br.com.eduardo.algafood.domain.exception.EstadoNaoEncontradaException;
import br.com.eduardo.algafood.domain.model.Estado;
import br.com.eduardo.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	
	private static final String MSG_ESTADO_EM_USO = 
			"Estado de código %d não pode ser removida";
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	public void excluir(Long id) {
		try {
			
			estadoRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradaException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, id));
		}
	}
	
	public Estado buscarOuFalhar(Long id) {
		return estadoRepository.findById(id)
				.orElseThrow(() -> new EstadoNaoEncontradaException(id));
	}

}
