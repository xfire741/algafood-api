package br.com.eduardo.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.eduardo.algafood.api.v1.AlgaLinks;
import br.com.eduardo.algafood.api.v1.controller.FormaPagamentoController;
import br.com.eduardo.algafood.api.v1.model.FormaPagamentoDTO;
import br.com.eduardo.algafood.core.security.AlgaSecurity;
import br.com.eduardo.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler
		extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	public FormaPagamentoModelAssembler() {
		super(FormaPagamentoController.class, FormaPagamentoDTO.class);
	}

	@Override
	public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
		FormaPagamentoDTO formaPagamentoModel = createModelWithId(formaPagamento.getId(), formaPagamento);

		modelMapper.map(formaPagamento, formaPagamentoModel);

		if (algaSecurity.podeConsultarFormasPagamento()) {
			formaPagamentoModel.add(algaLinks.linkToFormasPagamento("formasPagamento"));
		}

		return formaPagamentoModel;
	}

	@Override
	public CollectionModel<FormaPagamentoDTO> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
		CollectionModel<FormaPagamentoDTO> collectionModel = super.toCollectionModel(entities);

		if (algaSecurity.podeConsultarFormasPagamento()) {
			collectionModel.add(algaLinks.linkToFormasPagamento());
		}

		return collectionModel;
	}
}
