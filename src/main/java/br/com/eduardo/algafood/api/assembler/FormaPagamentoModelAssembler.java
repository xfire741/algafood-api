package br.com.eduardo.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.model.FormaPagamentoDTO;
import br.com.eduardo.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamentoDTO toDTO(FormaPagamento formaPagamento) {
		return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
	}
	
	public List<FormaPagamentoDTO> toCollectionDTO(Collection<FormaPagamento> formasPagamento) {
		return formasPagamento.stream()
				.map(formaPagamento -> toDTO(formaPagamento))
				.collect(Collectors.toList());
	}
	
}
