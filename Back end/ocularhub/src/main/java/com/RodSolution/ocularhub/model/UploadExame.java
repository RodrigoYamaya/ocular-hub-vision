package com.RodSolution.ocularhub.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tb_uploads_exames")
@EntityListeners(AuditingEntityListener.class)

public class UploadExame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeOriginalArquivo;

    @Column(nullable = false)
    private String nomeArmazenado;

    @Column(nullable = false)
    private String caminhoArquivo;

    private String tipoArquivo;

    private Long tamanhoArquivo;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime dataUpload;

    public UploadExame() {
    }


    public UploadExame(Long id, String nomeOriginalArquivo, String nomeArmazenado, String caminhoArquivo, String tipoArquivo, Long tamanhoArquivo, LocalDateTime dataUpload) {
        this.id = id;
        this.nomeOriginalArquivo = nomeOriginalArquivo;
        this.nomeArmazenado = nomeArmazenado;
        this.caminhoArquivo = caminhoArquivo;
        this.tipoArquivo = tipoArquivo;
        this.tamanhoArquivo = tamanhoArquivo;
        this.dataUpload = dataUpload;
    }


    public String getNomeOriginalArquivo() {
        return nomeOriginalArquivo;
    }

    public void setNomeOriginalArquivo(String nomeOriginalArquivo) {
        this.nomeOriginalArquivo = nomeOriginalArquivo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeArmazenado() {
        return nomeArmazenado;
    }

    public void setNomeArmazenado(String nomeArmazenado) {
        this.nomeArmazenado = nomeArmazenado;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public String getTipoArquivo() {
        return tipoArquivo;
    }

    public void setTipoArquivo(String tipoArquivo) {
        this.tipoArquivo = tipoArquivo;
    }

    public Long getTamanhoArquivo() {
        return tamanhoArquivo;
    }

    public void setTamanhoArquivo(Long tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;
    }

    public LocalDateTime getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(LocalDateTime dataUpload) {
        this.dataUpload = dataUpload;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UploadExame that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(nomeOriginalArquivo, that.nomeOriginalArquivo) && Objects.equals(nomeArmazenado, that.nomeArmazenado) && Objects.equals(caminhoArquivo, that.caminhoArquivo) && Objects.equals(tipoArquivo, that.tipoArquivo) && Objects.equals(tamanhoArquivo, that.tamanhoArquivo) && Objects.equals(dataUpload, that.dataUpload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeOriginalArquivo, nomeArmazenado, caminhoArquivo, tipoArquivo, tamanhoArquivo, dataUpload);
    }
}
