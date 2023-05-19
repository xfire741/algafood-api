package br.com.eduardo.algafood.api.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import br.com.eduardo.algafood.api.assembler.FormaPagamentoInputDisassembler;
import br.com.eduardo.algafood.api.assembler.FormaPagamentoModelAssembler;
import br.com.eduardo.algafood.api.model.FormaPagamentoDTO;
import br.com.eduardo.algafood.api.model.input.FormaPagamentoInputDTO;
import br.com.eduardo.algafood.api.openapi.controller.FormaPagamentoControllerOpenApi;
import br.com.eduardo.algafood.domain.model.FormaPagamento;
import br.com.eduardo.algafood.domain.repository.FormaPagamentoRepository;
import br.com.eduardo.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {
	
	@Autowired
	private FormaPagamentoInputDisassembler disassembler;
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private FormaPagamentoModelAssembler assembler;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long id, ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
		
		OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();
		
		if (dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
		
		if (request.checkNotModified(eTag)) {
			return null;
		}
		
		FormaPagamentoDTO formaPagamento = 
				assembler.toDTO(cadastroFormaPagamentoService.buscarOuFalhar(id));
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(formaPagamento);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FormaPagamentoDTO>> listar(ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
		
		OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();
		
		if (dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
		
		if (request.checkNotModified(eTag)) {
			return null;
		}
		
	  	List<FormaPagamentoDTO> todasFormasPagamento = assembler
	  			.toCollectionDTO(formaPagamentoRepository.findAll());
	  	
	  	return ResponseEntity.ok()
	  			.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
	  			.eTag(eTag)
	  			.body(todasFormasPagamento);
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDTO salvar(@RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInputDTO) {
		FormaPagamento formaPagamento = disassembler.toDomainObject(formaPagamentoInputDTO);
		return assembler.toDTO(cadastroFormaPagamentoService.salvar(formaPagamento));
	}
	
	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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
