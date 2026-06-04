package com.RodSolution.ocularhub.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_consultas")
@Data
@EqualsAndHashCode(of = "id")
public class Consulta {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Muitos agendamentos podem pertencer a um único Médico
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    // Muitos agendamentos podem pertencer a um único Paciente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    // Campo útil para futuras regras de negócio de cancelamento
    @Column(name = "motivo_cancelamento")
    private String motivoCancelamento;
}
