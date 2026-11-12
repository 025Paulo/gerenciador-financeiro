package com.gerenciadorfinanceiro.paulo.dto;

import com.gerenciadorfinanceiro.paulo.model.Categoria;

public record CategoriaResponse(Long id, String nome, String cor) {

    public static CategoriaResponse de(Categoria c) {
        return new CategoriaResponse(c.getId(), c.getNome(), c.getCor());
    }
}