package com.gerenciadorfinanceiro.paulo.dto;

import com.gerenciadorfinanceiro.paulo.model.Bandeira;
import com.gerenciadorfinanceiro.paulo.model.Cartao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CartaoResponse(
        Long id,
        String nome,
        Bandeira bandeira,
        String ultimosDigitos,
        BigDecimal limite,
        Integer diaFechamento,
        Integer diaVencimento,
        LocalDateTime criadoEm
) {
    public static CartaoResponse de(Cartao c) {
        return new CartaoResponse(c.getId(), c.getNome(), c.getBandeira(), c.getUltimosDigitos(),
                c.getLimite(), c.getDiaFechamento(), c.getDiaVencimento(), c.getCriadoEm());
    }
}