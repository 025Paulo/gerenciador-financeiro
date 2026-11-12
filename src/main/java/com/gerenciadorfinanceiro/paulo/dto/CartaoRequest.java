package com.gerenciadorfinanceiro.paulo.dto;

import com.gerenciadorfinanceiro.paulo.model.Bandeira;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CartaoRequest(
        @NotBlank @Size(max = 80) String nome,
        @NotNull Bandeira bandeira,
        @Pattern(regexp = "\\d{4}", message = "deve ter exatamente 4 dígitos") String ultimosDigitos,
        @NotNull @PositiveOrZero BigDecimal limite,
        @NotNull @Min(1) @Max(31) Integer diaFechamento,
        @NotNull @Min(1) @Max(31) Integer diaVencimento
) {}