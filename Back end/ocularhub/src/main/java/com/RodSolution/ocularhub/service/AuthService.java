package com.RodSolution.ocularhub.service;

import com.RodSolution.ocularhub.exceptions.RecursoNaoEncontradoException;
import com.RodSolution.ocularhub.model.Medico;
import com.RodSolution.ocularhub.model.dto.auth.LoginRequestDto;
import com.RodSolution.ocularhub.model.dto.auth.LoginResponseDto;
import com.RodSolution.ocularhub.repository.MedicoRepository;
import com.RodSolution.ocularhub.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthService {

    private final MedicoRepository medicoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(MedicoRepository medicoRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.medicoRepository = medicoRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponseDto login(LoginRequestDto dto) {
        // Busca o médico pelo e-mail
        Medico medico = medicoRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Email ou senha inválidos"));

        // Verifica se a senha digitada (texto puro) bate com o hash do banco
        if (!passwordEncoder.matches(dto.password(), medico.getpassword())) {
            throw new RecursoNaoEncontradoException("Senha inválida ou email incorreto");
        }

        // Gera o token JWT específico para este médico
        String token = jwtService.generateToken(medico);

        // Calcula a expiração baseada na configuração do seu JwtService
        Instant expiresAt = jwtService.generateExpirationDate();

        // Retorna o DTO com o Token e a Role fixa de MÉDICO
        return new LoginResponseDto(token, "MEDICO", expiresAt);
    }
}
