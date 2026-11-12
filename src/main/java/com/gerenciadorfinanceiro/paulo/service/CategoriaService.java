package com.gerenciadorfinanceiro.paulo.service;

import com.gerenciadorfinanceiro.paulo.dto.CategoriaRequest;
import com.gerenciadorfinanceiro.paulo.dto.CategoriaResponse;
import com.gerenciadorfinanceiro.paulo.exception.ConflitoException;
import com.gerenciadorfinanceiro.paulo.exception.RecursoNaoEncontradoException;
import com.gerenciadorfinanceiro.paulo.model.Categoria;
import com.gerenciadorfinanceiro.paulo.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private static final Map<String, String> PADROES = Map.of(
            "Alimentação", "#F97316",
            "Transporte", "#3B82F6",
            "Lazer", "#A855F7",
            "Saúde", "#10B981",
            "Assinaturas", "#EF4444",
            "Compras", "#EAB308",
            "Outros", "#6B7280"
    );

    private final CategoriaRepository categoriaRepository;

    @Transactional
    public void criarPadroes(Long usuarioId) {
        List<Categoria> categorias = PADROES.entrySet().stream()
                .map(e -> Categoria.builder()
                        .usuarioId(usuarioId)
                        .nome(e.getKey())
                        .cor(e.getValue())
                        .build())
                .toList();
        categoriaRepository.saveAll(categorias);
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponse> listar(Long usuarioId) {
        return categoriaRepository.findByUsuarioIdOrderByNomeAsc(usuarioId).stream()
                .map(CategoriaResponse::de)
                .toList();
    }

    @Transactional
    public CategoriaResponse criar(CategoriaRequest req, Long usuarioId) {
        String nome = req.nome().trim();
        if (categoriaRepository.existsByUsuarioIdAndNomeIgnoreCase(usuarioId, nome)) {
            throw new ConflitoException("Já existe uma categoria chamada " + nome);
        }
        Categoria categoria = Categoria.builder()
                .usuarioId(usuarioId)
                .nome(nome)
                .cor(req.cor().toUpperCase())
                .build();
        return CategoriaResponse.de(categoriaRepository.save(categoria));
    }

    @Transactional
    public CategoriaResponse atualizar(Long id, CategoriaRequest req, Long usuarioId) {
        Categoria categoria = buscarEntidade(id, usuarioId);
        String nome = req.nome().trim();
        if (categoriaRepository.existsByUsuarioIdAndNomeIgnoreCaseAndIdNot(usuarioId, nome, id)) {
            throw new ConflitoException("Já existe uma categoria chamada " + nome);
        }
        categoria.setNome(nome);
        categoria.setCor(req.cor().toUpperCase());
        return CategoriaResponse.de(categoria);
    }

    @Transactional
    public void excluir(Long id, Long usuarioId) {
        categoriaRepository.delete(buscarEntidade(id, usuarioId));
    }

    private Categoria buscarEntidade(Long id, Long usuarioId) {
        return categoriaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));
    }
}