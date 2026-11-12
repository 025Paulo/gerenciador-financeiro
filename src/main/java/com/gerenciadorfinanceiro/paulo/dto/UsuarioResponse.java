package com.gerenciadorfinanceiro.paulo.dto;

import com.gerenciadorfinanceiro.paulo.model.Usuario;

import java.time.LocalDateTime;

public record UsuarioResponse(Long id, String nome, String email, LocalDateTime criadoEm) {

    public static UsuarioResponse de(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getNome(), u.getEmail(), u.getCriadoEm());
    }
}