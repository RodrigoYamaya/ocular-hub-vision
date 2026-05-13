package com.RodSolution.ocularhub.service;

import com.RodSolution.ocularhub.exceptions.RecursoNaoEncontradoException;
import com.RodSolution.ocularhub.mapper.MedicoMapper;
import com.RodSolution.ocularhub.model.Medico;
import com.RodSolution.ocularhub.model.dto.MedicoRequestDto;
import com.RodSolution.ocularhub.model.dto.MedicoResponseDto;
import com.RodSolution.ocularhub.repository.MedicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private MedicoMapper medicoMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public MedicoResponseDto findById(long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Medico com ID " + id + " não encontrado."));
        return medicoMapper.toDto(medico);
    }

    @Transactional
    public MedicoResponseDto saveMedico(MedicoRequestDto medicoDto) {
        Medico medico = medicoMapper.toEntity(medicoDto);
        medico.setpassword(passwordEncoder.encode(medicoDto.password()));
        Medico medicoSave = medicoRepository.save(medico);
       return medicoMapper.toDto(medicoSave);
    }

    public List<MedicoResponseDto> listarTodosMedicos() {
        return  medicoRepository.findAll()
                .stream()
                .map(medicoMapper::toDto)
                .collect(Collectors.toList());
    }

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
        medico.setNome(medicoDto.nome());
        medico.setEmail(medicoDto.email());
        medico.setEspecialidade(medicoDto.especialidade());

        if (medicoDto.password() != null && !medicoDto.password().isBlank()) {
            medico.setpassword((passwordEncoder.encode(medicoDto.password())));
        }

        Medico medicoSaveUpdate = medicoRepository.save(medico);
        return medicoMapper.toDto(medicoSaveUpdate);

    }


}
