package com.RodSolution.ocularhub.service;

import com.RodSolution.ocularhub.model.Medico;
import com.RodSolution.ocularhub.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public Medico saveMedico(Medico medico) {
        return medicoRepository.save(medico);
    }
}
