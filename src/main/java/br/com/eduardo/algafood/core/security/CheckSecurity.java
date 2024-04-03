package br.com.eduardo.algafood.core.security;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {

    public @interface Cozinhas {

    @PreAuthorize("isAuthenticated()")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface PodeConsultar {}

    @PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface PodeEditar {}

    }
}
