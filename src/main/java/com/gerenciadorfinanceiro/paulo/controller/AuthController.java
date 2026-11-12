package com.gerenciadorfinanceiro.paulo.controller;

import com.gerenciadorfinanceiro.paulo.dto.CadastroRequest;
import com.gerenciadorfinanceiro.paulo.dto.LoginRequest;
import com.gerenciadorfinanceiro.paulo.dto.LoginResponse;
import com.gerenciadorfinanceiro.paulo.dto.UsuarioResponse;
import com.gerenciadorfinanceiro.paulo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioResponse> cadastro(@RequestBody @Valid CadastroRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.cadastrar(req));
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest req) {
        return authService.login(req);
    }
}