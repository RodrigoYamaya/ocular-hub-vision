package com.RodSolution.ocularhub.model.entities;

import com.RodSolution.ocularhub.model.enums.StatusExame;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_exames")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Exame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(name = "regiao_analisada")
    private String regiaoAnalisada;

    @Column(name = "precisao_ia")
    private Double precisaoIa;

    @Column(columnDefinition = "TEXT")
    private String diagnosticoIa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusExame status = StatusExame.PENDENTE;

    @CreatedDate
    @Column(name = "data_registro", nullable = false, updatable = false)
    private LocalDateTime dataRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
}
