package com.RodSolution.ocularhub.service;

import com.RodSolution.ocularhub.exceptions.RecursoNaoEncontradoException;
import com.RodSolution.ocularhub.mapper.PacienteMapper;
import com.RodSolution.ocularhub.model.entities.Paciente;
import com.RodSolution.ocularhub.model.dto.PacienteRequestDto;
import com.RodSolution.ocularhub.model.dto.PacienteResponseDto;
import com.RodSolution.ocularhub.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    @Autowired
    private  PacienteRepository pacienteRepository;
    @Autowired
    private  PacienteMapper pacienteMapper;


    @Transactional
    public PacienteResponseDto cadastrar(PacienteRequestDto dto) {
        if (pacienteRepository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException("Já existe um paciente cadastrado com este CPF.");
        }

        Paciente paciente = pacienteMapper.toEntity(dto);
        Paciente pacienteSalvo = pacienteRepository.save(paciente);

        return pacienteMapper.toDto(pacienteSalvo);
    }


    @Transactional
    public List<PacienteResponseDto> listarTodos() {
        return  pacienteRepository.findAll()
                .stream()
                .map(pacienteMapper::toDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public PacienteResponseDto buscarPorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado com o ID: " + id));

        return pacienteMapper.toDto(paciente);
    }

    @Transactional
    public PacienteResponseDto atualizar(Long id, PacienteRequestDto dto) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado com o ID: " + id));

        if (!paciente.getCpf().equals(dto.cpf()) && pacienteRepository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException("Este CPF já está em uso por outro paciente.");
        }

        paciente.setNome(dto.nome());
        paciente.setIdade(dto.idade());
        paciente.setCpf(dto.cpf());
        paciente.setDataNascimento(dto.dataNascimento());
        paciente.setObservacao(dto.observacao());

        Paciente pacienteSaveUpdate = pacienteRepository.save(paciente);
        return pacienteMapper.toDto(pacienteSaveUpdate);
    }


    @Transactional
    public void excluir(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Paciente não encontrado para exclusão.");
        }
        pacienteRepository.deleteById(id);
    }
}
