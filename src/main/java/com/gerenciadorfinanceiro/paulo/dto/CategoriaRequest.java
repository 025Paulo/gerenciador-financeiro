package com.gerenciadorfinanceiro.paulo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CategoriaRequest(
        @NotBlank @Size(max = 60) String nome,
        @NotBlank @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "deve ser uma cor hexadecimal, ex: #FF8800") String cor
) {}