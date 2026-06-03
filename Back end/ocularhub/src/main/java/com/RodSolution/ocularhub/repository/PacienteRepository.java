package com.RodSolution.ocularhub.repository;

import com.RodSolution.ocularhub.model.Medico;
import com.RodSolution.ocularhub.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    boolean existsByCpf(String cpf);
}
