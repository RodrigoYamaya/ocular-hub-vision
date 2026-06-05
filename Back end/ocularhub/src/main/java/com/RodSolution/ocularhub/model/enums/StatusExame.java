package com.RodSolution.ocularhub.model.enums;

public enum StatusExame {

    PENDENTE("Aguardando Análise"),
    EM_ANALISE_IA("Em Análise pela IA"),
    CONCLUIDO("Concluído"),
    CANCELADO("Cancelado");

    private String descricao;

    StatusExame(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
