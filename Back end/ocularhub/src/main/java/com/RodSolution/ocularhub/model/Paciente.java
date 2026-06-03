package com.RodSolution.ocularhub.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
//@Entity lembrar-se E uma anotação que vai ele indicar para Hibernate que essa classe que vai ser mapeada para tabela no banco de daods. jPA SO VAI INSTRUIR QUE COM @anotação Entity. O JPA NAO EXECUTA NADA.
@Table(name = "tb_pacientes")
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
    private String observação;


    public Paciente() {
    }

    public Paciente(Long id, String nome, LocalDate dataNascimento, String cpf, String observação) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.observação = observação;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObservação() {
        return observação;
    }

    public void setObservação(String observação) {
        this.observação = observação;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
