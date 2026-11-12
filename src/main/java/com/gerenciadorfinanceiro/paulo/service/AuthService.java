package com.gerenciadorfinanceiro.paulo.service;

import com.gerenciadorfinanceiro.paulo.dto.CadastroRequest;
import com.gerenciadorfinanceiro.paulo.dto.LoginRequest;
import com.gerenciadorfinanceiro.paulo.dto.LoginResponse;
import com.gerenciadorfinanceiro.paulo.dto.UsuarioResponse;
import com.gerenciadorfinanceiro.paulo.exception.CredenciaisInvalidasException;
import com.gerenciadorfinanceiro.paulo.exception.EmailJaCadastradoException;
import com.gerenciadorfinanceiro.paulo.model.Usuario;
import com.gerenciadorfinanceiro.paulo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CategoriaService categoriaService;

    @Transactional
    public UsuarioResponse cadastrar(CadastroRequest req) {
        String email = req.email().trim().toLowerCase();

        if (usuarioRepository.existsByEmail(email)) {
            throw new EmailJaCadastradoException(email);
        }

        Usuario usuario = Usuario.builder()
                .nome(req.nome().trim())
                .email(email)
                .senha(passwordEncoder.encode(req.senha()))
                .build();

        Usuario salvo = usuarioRepository.save(usuario);
        categoriaService.criarPadroes(salvo.getId());

        return UsuarioResponse.de(salvo);
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest req) {
        Usuario usuario = usuarioRepository.findByEmail(req.email().trim().toLowerCase())
                .orElseThrow(CredenciaisInvalidasException::new);

        if (!passwordEncoder.matches(req.senha(), usuario.getSenha())) {
            throw new CredenciaisInvalidasException();
        }

        return new LoginResponse(
                jwtService.gerar(usuario),
                "Bearer",
                jwtService.getExpiracaoSegundos(),
                UsuarioResponse.de(usuario)
        );
    }
}