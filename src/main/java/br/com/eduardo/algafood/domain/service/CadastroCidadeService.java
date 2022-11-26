package br.com.eduardo.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.eduardo.algafood.domain.exception.EntidadeEmUsoException;
import br.com.eduardo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.eduardo.algafood.domain.model.Cidade;
import br.com.eduardo.algafood.domain.model.Estado;
import br.com.eduardo.algafood.domain.repository.CidadeRepository;
import br.com.eduardo.algafood.insfraestructure.repository.EstadoRepositoryImpl;

@Service
public class CadastroCidadeService {
	
	@Autowired
	private EstadoRepositoryImpl estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;

	public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = estadoRepository.buscar(estadoId);
        
        if (estado == null) {
            throw new EntidadeNaoEncontradaException(
                String.format("Não existe cadastro de estado com código %d", estadoId));
        }
        
        cidade.setEstado(estado);
        
        return cidadeRepository.salvar(cidade);
    }
	
	public void excluir(Long cidadeId) {
        try {
            cidadeRepository.remover(cidadeId);
            
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                String.format("Não existe um cadastro de cidade com código %d", cidadeId));
        
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format("Cidade de código %d não pode ser removida, pois está em uso", cidadeId));
        }
    }
	
}
