package com.gerenciadorfinanceiro.paulo.repository;

import com.gerenciadorfinanceiro.paulo.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    List<Cartao> findByUsuarioIdOrderByNomeAsc(Long usuarioId);

    Optional<Cartao> findByIdAndUsuarioId(Long id, Long usuarioId);
}