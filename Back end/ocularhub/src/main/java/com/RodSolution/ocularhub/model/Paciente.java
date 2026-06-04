package com.RodSolution.ocularhub.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
//@Entity lembrar-se E uma anotação que vai ele indicar para Hibernate que essa classe que vai ser mapeada para tabela no banco de daods. jPA SO VAI INSTRUIR QUE COM @anotação Entity. O JPA NAO EXECUTA NADA.
@Table(name = "tb_pacientes")
@Data
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(columnDefinition = "TEXT")
    private String observacao;



}
