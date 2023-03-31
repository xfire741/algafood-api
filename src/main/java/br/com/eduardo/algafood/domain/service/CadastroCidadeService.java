package br.com.eduardo.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.eduardo.algafood.domain.exception.CidadeNaoEncontradaException;
import br.com.eduardo.algafood.domain.exception.EntidadeEmUsoException;
import br.com.eduardo.algafood.domain.model.Cidade;
import br.com.eduardo.algafood.domain.model.Estado;
import br.com.eduardo.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {
	
	private static final String MSG_CIDADE_NAO_PODE_SER_REMOVIDA = "Cidade de código %d não pode ser removida, pois está em uso";

	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@Autowired
	private CidadeRepository cidadeRepository;

	@Transactional
	public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
       
        Estado estado = cadastroEstado.buscarOuFalhar(estadoId);
        
        cidade.setEstado(estado);
        
        return cidadeRepository.save(cidade);
    }
	
	@Transactional
	public void excluir(Long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);
            
        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNaoEncontradaException(cidadeId);
        
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format(MSG_CIDADE_NAO_PODE_SER_REMOVIDA, cidadeId));
        }
    }
	
	public Cidade buscarOuFalhar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId)
				.orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
	}
	
}
