package com.gerenciadorfinanceiro.paulo.repository;

import com.gerenciadorfinanceiro.paulo.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByUsuarioIdOrderByNomeAsc(Long usuarioId);

    Optional<Categoria> findByIdAndUsuarioId(Long id, Long usuarioId);

    boolean existsByUsuarioIdAndNomeIgnoreCase(Long usuarioId, String nome);

    boolean existsByUsuarioIdAndNomeIgnoreCaseAndIdNot(Long usuarioId, String nome, Long id);
}