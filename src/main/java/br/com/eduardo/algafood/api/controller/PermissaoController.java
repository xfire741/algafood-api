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

import br.com.eduardo.algafood.api.assembler.PermissaoInputDisassembler;
import br.com.eduardo.algafood.api.assembler.PermissaoModelAssembler;
import br.com.eduardo.algafood.api.model.PermissaoDTO;
import br.com.eduardo.algafood.api.model.input.PermissaoInputDTO;
import br.com.eduardo.algafood.domain.model.Permissao;
import br.com.eduardo.algafood.domain.repository.PermissaoRepository;
import br.com.eduardo.algafood.domain.service.CadastroPermissaoService;

@RestController
@RequestMapping("/permissoes")
public class PermissaoController {
	
	@Autowired
	private PermissaoInputDisassembler disassembler;
	
	@Autowired
	private PermissaoRepository permissaoRepository;

	@Autowired
	private PermissaoModelAssembler assembler;
	
	@Autowired
	private CadastroPermissaoService cadastroPermissaoService;
	
	@GetMapping("/{id}")
	public PermissaoDTO buscar(@PathVariable Long id) {
		return assembler.toDTO(cadastroPermissaoService.buscarOuFalhar(id));
	}
	
	@GetMapping
	public List<PermissaoDTO> listar() { 
		return assembler.toCollectionDTO(permissaoRepository.findAll());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PermissaoDTO salvar(@RequestBody @Valid PermissaoInputDTO permissaoInputDTO) {
		
		Permissao permissao = disassembler.toDomainObject(permissaoInputDTO);
		
		return assembler.toDTO(cadastroPermissaoService.salvar(permissao));
		
	}
	
	@PutMapping("/{id}")
	public PermissaoDTO atualizar(@PathVariable Long id, 
			@RequestBody @Valid PermissaoInputDTO permissaoInputDTO) {
		
		Permissao permissaoAtual = cadastroPermissaoService.buscarOuFalhar(id);
		
		disassembler.copyToDomainObject(permissaoInputDTO, permissaoAtual);
		
		return assembler.toDTO(cadastroPermissaoService.salvar(permissaoAtual));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		cadastroPermissaoService.excluir(id);
	}
	
}
