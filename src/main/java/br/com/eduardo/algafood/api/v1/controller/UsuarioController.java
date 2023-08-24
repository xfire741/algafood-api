package br.com.eduardo.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.algafood.api.v1.assembler.UsuarioInputDisassembler;
import br.com.eduardo.algafood.api.v1.assembler.UsuarioModelAssembler;
import br.com.eduardo.algafood.api.v1.model.UsuarioDTO;
import br.com.eduardo.algafood.api.v1.model.input.SenhaInput;
import br.com.eduardo.algafood.api.v1.model.input.UsuarioComSenhaInput;
import br.com.eduardo.algafood.api.v1.model.input.UsuarioInput;
import br.com.eduardo.algafood.api.v1.openapi.controller.UsuarioControllerOpenApi;
import br.com.eduardo.algafood.domain.model.Usuario;
import br.com.eduardo.algafood.domain.repository.UsuarioRepository;
import br.com.eduardo.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController implements UsuarioControllerOpenApi {
	 
		@Autowired
	    private UsuarioRepository usuarioRepository;
	    
	    @Autowired
	    private CadastroUsuarioService cadastroUsuario;
	    
	    @Autowired
	    private UsuarioModelAssembler usuarioModelAssembler;
	    
	    @Autowired
	    private UsuarioInputDisassembler usuarioInputDisassembler;
	    
	    @GetMapping
	    public CollectionModel<UsuarioDTO> listar() {
	        List<Usuario> todasUsuarios = usuarioRepository.findAll();
	        
	        return usuarioModelAssembler.toCollectionModel(todasUsuarios);
	    }
	    
	    @GetMapping("/{usuarioId}")
	    public UsuarioDTO buscar(@PathVariable Long usuarioId) {
	        Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
	        
	        return usuarioModelAssembler.toModel(usuario);
	    }
	    
	    @PostMapping
	    @ResponseStatus(HttpStatus.CREATED)
	    public UsuarioDTO adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
	        Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
	        usuario = cadastroUsuario.salvar(usuario);
	        
	        return usuarioModelAssembler.toModel(usuario);
	    }
	    
	    @PutMapping("/{usuarioId}")
	    public UsuarioDTO atualizar(@PathVariable Long usuarioId,
	            @RequestBody @Valid UsuarioInput usuarioInput) {
	        Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);
	        usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
	        usuarioAtual = cadastroUsuario.salvar(usuarioAtual);
	        
	        return usuarioModelAssembler.toModel(usuarioAtual);
	    }
	    
	    @PutMapping("/{usuarioId}/senha")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
	        cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
	    }
	
	    
	    
}
