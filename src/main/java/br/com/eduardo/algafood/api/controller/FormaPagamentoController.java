package br.com.eduardo.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.algafood.api.assembler.FormaPagamentoInputDisassembler;
import br.com.eduardo.algafood.api.assembler.FormaPagamentoModelAssembler;
import br.com.eduardo.algafood.api.model.FormaPagamentoDTO;
import br.com.eduardo.algafood.api.model.input.FormaPagamentoInputDTO;
import br.com.eduardo.algafood.domain.model.FormaPagamento;
import br.com.eduardo.algafood.domain.repository.FormaPagamentoRepository;
import br.com.eduardo.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {
	
	@Autowired
	private FormaPagamentoInputDisassembler disassembler;
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private FormaPagamentoModelAssembler assembler;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@GetMapping("/{id}")
	public FormaPagamentoDTO buscar(@PathVariable Long id) {
		return assembler.toDTO(cadastroFormaPagamentoService.buscarOuFalhar(id));
	}
	
	@GetMapping
	public List<FormaPagamentoDTO> listar() {
	  	return assembler.toCollectionDTO(formaPagamentoRepository.findAll());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDTO salvar(@RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInputDTO) {
		FormaPagamento formaPagamento = disassembler.toDomainObject(formaPagamentoInputDTO);
		return assembler.toDTO(cadastroFormaPagamentoService.salvar(formaPagamento));
	}
	
	@PutMapping("/{id}")
	public FormaPagamentoDTO atualizar(@PathVariable Long id, 
			@RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInputDTO) {
		
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(id);
		disassembler.copyToDomainObject(formaPagamentoInputDTO, formaPagamento);
		
		return assembler.toDTO(cadastroFormaPagamentoService.salvar(formaPagamento));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroFormaPagamentoService.excluir(id);
	}
	
}
