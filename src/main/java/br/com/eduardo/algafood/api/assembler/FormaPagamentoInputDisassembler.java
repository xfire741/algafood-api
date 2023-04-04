package br.com.eduardo.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.model.input.FormaPagamentoInputDTO;
import br.com.eduardo.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamento toDomainObject(FormaPagamentoInputDTO formaPagamentoInputDTO) {
		return modelMapper.map(formaPagamentoInputDTO, FormaPagamento.class);
	}
	
	public void copyToDomainObject(FormaPagamentoInputDTO formaPagamentoInputDTO, 
			FormaPagamento formaPagamento) {
		modelMapper.map(formaPagamentoInputDTO, formaPagamento);
	}
	
}
