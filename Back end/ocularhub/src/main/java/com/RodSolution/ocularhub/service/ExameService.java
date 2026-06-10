package com.RodSolution.ocularhub.service;

import com.RodSolution.ocularhub.exceptions.RecursoNaoEncontradoException;
import com.RodSolution.ocularhub.mapper.ExameMapper;
import com.RodSolution.ocularhub.model.dto.ExameRequestDto;
import com.RodSolution.ocularhub.model.dto.ExameResponseDto;
import com.RodSolution.ocularhub.model.entities.Exame;
import com.RodSolution.ocularhub.model.entities.Paciente;
import com.RodSolution.ocularhub.model.enums.StatusExame;
import com.RodSolution.ocularhub.repository.ExameRepository;
import com.RodSolution.ocularhub.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ExameService {

    @Autowired
    private ExameRepository exameRepository;

    @Autowired
    private ExameMapper exameMapper;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private GeminiService geminiService;

    
    public ExameResponseDto findById(Long id) {
        Exame exame = exameRepository.findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Exame com ID " + id + " não encontrado."));
        
        return exameMapper.toDto(exame);
    }
    
    @Transactional
    public ExameResponseDto saveExame(ExameRequestDto exameDto) {
        Paciente paciente = pacienteRepository.findById(exameDto.pacienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado com ID: " + exameDto.pacienteId()));

        Exame exame = exameMapper.toEntity(exameDto);
        exame.setPaciente(paciente);

        Exame salvo = exameRepository.save(exame);
        return exameMapper.toDto(salvo);

    }


    public List<ExameResponseDto> listarPorPaciente(Long pacienteId) {
        return exameRepository.findByPacienteId(pacienteId).stream()
                .map(exameMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ExameResponseDto> listarTodos() {
        return exameRepository.findAll().stream()
                .map(exameMapper::toDto)
                .collect(Collectors.toList());
    }

    public ExameResponseDto buscarPorId(Long id) {
        Exame exame = exameRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Exame não encontrado com ID: " + id));
        return exameMapper.toDto(exame);
    }

    @Transactional
    public ExameResponseDto atualizar(Long id, ExameRequestDto dto) {
        Exame exameExistente = exameRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Exame não encontrado com ID: " + id));

        if (!exameExistente.getPaciente().getId().equals(dto.pacienteId())) {
            Paciente novoPaciente = pacienteRepository.findById(dto.pacienteId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado com ID: " + dto.pacienteId()));
            exameExistente.setPaciente(novoPaciente);
        }

        exameExistente.setTitulo(dto.titulo());
        exameExistente.setRegiaoAnalisada(dto.regiaoAnalisada());
        exameExistente.setPrecisaoIa(dto.precisaoIa());
        exameExistente.setDiagnosticoIa(dto.diagnosticoIa());

        Exame atualizado = exameRepository.save(exameExistente);
        return exameMapper.toDto(atualizado);
    }

    @Transactional
    public void deletar(Long id) {
        if(!exameRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("exame com o ID " + id + " não encontrado");
        }
        exameRepository.deleteById(id);
    }

    @Transactional
    public ExameResponseDto salvarExameComAnaliseIa(ExameRequestDto exameDto, MultipartFile imagem) {
        Paciente paciente = pacienteRepository.findById(exameDto.pacienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado com ID: " + exameDto.pacienteId()));

        Exame exame = exameMapper.toEntity(exameDto);
        exame.setPaciente(paciente);

        // 3.AQUI: Se veio imagem, manda pra IA ler!
        if (imagem != null && !imagem.isEmpty()) {
            String laudoIa = geminiService.gerarAnaliseComImagem(exame.getTitulo(), exame.getRegiaoAnalisada(), imagem);
            exame.setDiagnosticoIa(laudoIa);
            exame.setStatus(StatusExame.CONCLUIDO);
        }

        Exame salvo = exameRepository.save(exame);
        return exameMapper.toDto(salvo);
    }

}
