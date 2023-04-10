package br.com.eduardo.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.algafood.api.assembler.UsuarioInputDisassembler;
import br.com.eduardo.algafood.api.assembler.UsuarioModelAssembler;
import br.com.eduardo.algafood.api.model.UsuarioDTO;
import br.com.eduardo.algafood.api.model.input.SenhaInput;
import br.com.eduardo.algafood.api.model.input.UsuarioComSenhaInput;
import br.com.eduardo.algafood.api.model.input.UsuarioInput;
import br.com.eduardo.algafood.domain.model.Usuario;
import br.com.eduardo.algafood.domain.repository.UsuarioRepository;
import br.com.eduardo.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	 
		@Autowired
	    private UsuarioRepository usuarioRepository;
	    
	    @Autowired
	    private CadastroUsuarioService cadastroUsuario;
	    
	    @Autowired
	    private UsuarioModelAssembler usuarioModelAssembler;
	    
	    @Autowired
	    private UsuarioInputDisassembler usuarioInputDisassembler;
	    
	    @GetMapping
	    public List<UsuarioDTO> listar() {
	        List<Usuario> todasUsuarios = usuarioRepository.findAll();
	        
	        return usuarioModelAssembler.toCollectionDTO(todasUsuarios);
	    }
	    
	    @GetMapping("/{usuarioId}")
	    public UsuarioDTO buscar(@PathVariable Long usuarioId) {
	        Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
	        
	        return usuarioModelAssembler.toDTO(usuario);
	    }
	    
	    @PostMapping
	    @ResponseStatus(HttpStatus.CREATED)
	    public UsuarioDTO adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
	        Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
	        usuario = cadastroUsuario.salvar(usuario);
	        
	        return usuarioModelAssembler.toDTO(usuario);
	    }
	    
	    @PutMapping("/{usuarioId}")
	    public UsuarioDTO atualizar(@PathVariable Long usuarioId,
	            @RequestBody @Valid UsuarioInput usuarioInput) {
	        Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);
	        usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
	        usuarioAtual = cadastroUsuario.salvar(usuarioAtual);
	        
	        return usuarioModelAssembler.toDTO(usuarioAtual);
	    }
	    
	    @PutMapping("/{usuarioId}/senha")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
	        cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
	    }
	
}