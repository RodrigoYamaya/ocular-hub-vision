package com.RodSolution.ocularhub.repository;

import com.RodSolution.ocularhub.model.entities.Exame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExameRepository extends JpaRepository<Exame, Long> {
    List<Exame> findByPacienteId(Long PacienteId);
}
