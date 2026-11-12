package com.gerenciadorfinanceiro.paulo.controller;

import com.gerenciadorfinanceiro.paulo.dto.CartaoRequest;
import com.gerenciadorfinanceiro.paulo.dto.CartaoResponse;
import com.gerenciadorfinanceiro.paulo.service.CartaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cartoes")
@RequiredArgsConstructor
public class CartaoController {

    private final CartaoService cartaoService;

    @GetMapping
    public List<CartaoResponse> listar(@AuthenticationPrincipal Jwt jwt) {
        return cartaoService.listar(usuarioId(jwt));
    }

    @GetMapping("/{id}")
    public CartaoResponse buscar(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        return cartaoService.buscar(id, usuarioId(jwt));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartaoResponse criar(@RequestBody @Valid CartaoRequest req, @AuthenticationPrincipal Jwt jwt) {
        return cartaoService.criar(req, usuarioId(jwt));
    }

    @PutMapping("/{id}")
    public CartaoResponse atualizar(@PathVariable Long id,
                                    @RequestBody @Valid CartaoRequest req,
                                    @AuthenticationPrincipal Jwt jwt) {
        return cartaoService.atualizar(id, req, usuarioId(jwt));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        cartaoService.excluir(id, usuarioId(jwt));
    }

    private Long usuarioId(Jwt jwt) {
        return Long.valueOf(jwt.getSubject());
    }
}