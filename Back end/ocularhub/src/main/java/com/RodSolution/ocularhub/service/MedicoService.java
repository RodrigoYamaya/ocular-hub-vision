package com.RodSolution.ocularhub.service;

import com.RodSolution.ocularhub.exceptions.RecursoNaoEncontradoException;
import com.RodSolution.ocularhub.mapper.MedicoMapper;
import com.RodSolution.ocularhub.model.Medico;
import com.RodSolution.ocularhub.model.dto.MedicoRequestDto;
import com.RodSolution.ocularhub.model.dto.MedicoResponseDto;
import com.RodSolution.ocularhub.repository.MedicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private MedicoMapper medicoMapper;


    public MedicoResponseDto findById(long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Medico com ID " + id + " não encontrado."));
        return medicoMapper.toDto(medico);
    }

    @Transactional
    public MedicoResponseDto saveMedico(MedicoRequestDto medicoDto) {
        Medico medico = medicoMapper.toEntity(medicoDto);
        Medico medicoSave = medicoRepository.save(medico);
       return medicoMapper.toDto(medicoSave);
    }

    public List<MedicoResponseDto> listarTodosMedicos() {
        return  medicoRepository.findAll()
                .stream()
                .map(medicoMapper::toDto)
                .collect(Collectors.toList());
    }

//continua amanha em criar o cadastro do medico. e tambem pesquisar sobre as entregras do trabalho way hub de IA.
    @Transactional
    public void deletarMedico(long id) {
        if(!medicoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Medico com o ID " + id + " não encontrado");
        }
        medicoRepository.deleteById(id);
    }

    @Transactional
    public MedicoResponseDto updateMedico(MedicoRequestDto medicoDto, long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Medico com ID " + id + " não encontrado."));
        medico.setName(medicoDto.nome());
        medico.setEmail(medicoDto.email());
        medico.setEspecialidade(medicoDto.especialidade());

        if (medicoDto.senha() != null && !medicoDto.senha().isBlank()) {
            medico.setSenha(medicoDto.senha());
        }

        Medico medicoSaveUpdate = medicoRepository.save(medico);
        return medicoMapper.toDto(medicoSaveUpdate);

    }


}
