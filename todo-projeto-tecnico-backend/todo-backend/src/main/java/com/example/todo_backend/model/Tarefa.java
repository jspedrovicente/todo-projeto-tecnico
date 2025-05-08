package com.example.todo_backend.model;

import com.example.todo_backend.enums.PrioridadeEnum;
import com.example.todo_backend.enums.SituacaoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @Column
    private String descricao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrioridadeEnum prioridade;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoEnum situacao = SituacaoEnum.ABERTA;

    @NotNull
    @FutureOrPresent(message = "A data prevista de conclusão não pode ser anterior à data atual")
    @Column(nullable = false)
    private LocalDate dataPrevistaConclusao;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;


    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public PrioridadeEnum getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(PrioridadeEnum prioridade) {
        this.prioridade = prioridade;
    }

    public SituacaoEnum getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoEnum situacao) {
        this.situacao = situacao;
    }

    public LocalDate getDataPrevistaConclusao() {
        return dataPrevistaConclusao;
    }

    public void setDataPrevistaConclusao(LocalDate dataPrevistaConclusao) {
        this.dataPrevistaConclusao = dataPrevistaConclusao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
