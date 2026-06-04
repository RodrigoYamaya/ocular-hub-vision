package com.RodSolution.ocularhub.service;

import com.RodSolution.ocularhub.exceptions.RecursoNaoEncontradoException;
import com.RodSolution.ocularhub.mapper.ConsultaMapper;
import com.RodSolution.ocularhub.model.Consulta;
import com.RodSolution.ocularhub.model.Medico;
import com.RodSolution.ocularhub.model.Paciente;
import com.RodSolution.ocularhub.model.dto.ConsultaRequestDto;
import com.RodSolution.ocularhub.model.dto.ConsultaResponseDto;
import com.RodSolution.ocularhub.repository.ConsultaRepository;
import com.RodSolution.ocularhub.repository.MedicoRepository;
import com.RodSolution.ocularhub.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaMapper consultaMapper;


    public List<ConsultaResponseDto> listarTodas() {
        return consultaRepository.findAll()
                .stream()
                .map(consultaMapper::toDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public ConsultaResponseDto agendar(ConsultaRequestDto dto) {

        Medico medico = medicoRepository.findById(dto.medicoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Médico não encontrado com ID: " + dto.medicoId()));

        Paciente paciente = pacienteRepository.findById(dto.pacienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado com ID: " + dto.pacienteId()));

        Consulta consulta = consultaMapper.toEntity(dto);
        consulta.setMedico(medico);
        consulta.setPaciente(paciente);

        Consulta consultaSalva = consultaRepository.save(consulta);

        return consultaMapper.toDto(consultaSalva);
    }

    public ConsultaResponseDto buscarPorId(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada com o ID: " + id));

        return consultaMapper.toDto(consulta);
    }

    @Transactional
    public ConsultaResponseDto cancelar(Long id, String motivoCancelamento) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada com o ID: " + id));

        if (consulta.getMotivoCancelamento() != null) {
            throw new IllegalArgumentException("Esta consulta já foi cancelada anteriormente.");
        }

        consulta.setMotivoCancelamento(motivoCancelamento);

        Consulta consultaAtualizada = consultaRepository.save(consulta);
        return consultaMapper.toDto(consultaAtualizada);
    }
}
