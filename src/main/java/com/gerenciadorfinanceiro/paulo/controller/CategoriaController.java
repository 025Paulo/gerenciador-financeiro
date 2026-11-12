package com.gerenciadorfinanceiro.paulo.controller;

import com.gerenciadorfinanceiro.paulo.dto.CategoriaRequest;
import com.gerenciadorfinanceiro.paulo.dto.CategoriaResponse;
import com.gerenciadorfinanceiro.paulo.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public List<CategoriaResponse> listar(@AuthenticationPrincipal Jwt jwt) {
        return categoriaService.listar(usuarioId(jwt));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaResponse criar(@RequestBody @Valid CategoriaRequest req, @AuthenticationPrincipal Jwt jwt) {
        return categoriaService.criar(req, usuarioId(jwt));
    }

    @PutMapping("/{id}")
    public CategoriaResponse atualizar(@PathVariable Long id,
                                       @RequestBody @Valid CategoriaRequest req,
                                       @AuthenticationPrincipal Jwt jwt) {
        return categoriaService.atualizar(id, req, usuarioId(jwt));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        categoriaService.excluir(id, usuarioId(jwt));
    }

    private Long usuarioId(Jwt jwt) {
        return Long.valueOf(jwt.getSubject());
    }
}