package com.gerenciadorfinanceiro.paulo.service;

import com.gerenciadorfinanceiro.paulo.dto.CartaoRequest;
import com.gerenciadorfinanceiro.paulo.dto.CartaoResponse;
import com.gerenciadorfinanceiro.paulo.exception.RecursoNaoEncontradoException;
import com.gerenciadorfinanceiro.paulo.model.Cartao;
import com.gerenciadorfinanceiro.paulo.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoService {

    private final CartaoRepository cartaoRepository;

    @Transactional(readOnly = true)
    public List<CartaoResponse> listar(Long usuarioId) {
        return cartaoRepository.findByUsuarioIdOrderByNomeAsc(usuarioId).stream()
                .map(CartaoResponse::de)
                .toList();
    }

    @Transactional(readOnly = true)
    public CartaoResponse buscar(Long id, Long usuarioId) {
        return CartaoResponse.de(buscarEntidade(id, usuarioId));
    }

    @Transactional
    public CartaoResponse criar(CartaoRequest req, Long usuarioId) {
        Cartao cartao = Cartao.builder()
                .usuarioId(usuarioId)
                .nome(req.nome().trim())
                .bandeira(req.bandeira())
                .ultimosDigitos(req.ultimosDigitos())
                .limite(req.limite())
                .diaFechamento(req.diaFechamento())
                .diaVencimento(req.diaVencimento())
                .build();
        return CartaoResponse.de(cartaoRepository.save(cartao));
    }

    @Transactional
    public CartaoResponse atualizar(Long id, CartaoRequest req, Long usuarioId) {
        Cartao cartao = buscarEntidade(id, usuarioId);
        cartao.setNome(req.nome().trim());
        cartao.setBandeira(req.bandeira());
        cartao.setUltimosDigitos(req.ultimosDigitos());
        cartao.setLimite(req.limite());
        cartao.setDiaFechamento(req.diaFechamento());
        cartao.setDiaVencimento(req.diaVencimento());
        return CartaoResponse.de(cartao);
    }

    @Transactional
    public void excluir(Long id, Long usuarioId) {
        cartaoRepository.delete(buscarEntidade(id, usuarioId));
    }

    private Cartao buscarEntidade(Long id, Long usuarioId) {
        return cartaoRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cartão não encontrado"));
    }
}