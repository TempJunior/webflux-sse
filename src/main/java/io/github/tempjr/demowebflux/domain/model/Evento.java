package io.github.tempjr.demowebflux.domain.model;

import io.github.tempjr.demowebflux.domain.model.enums.TipoEvento;
import io.github.tempjr.demowebflux.web.dto.request.EventoUpdateRequestDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("eventos")

public class Evento {

    @Id
    private Long id;
    @Column("tipo")
    private TipoEvento tipoEvento;
    private String nome;
    @Column("localDate")
    private LocalDate data;
    private String descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // Atualiza apenas os campos presentes no DTO e retorna a própria entidade
    public Evento atualizar(EventoUpdateRequestDTO dto) {
        if (dto == null) return this;
        if (dto.nome() != null) {
            this.nome = dto.nome();
        }
        if (dto.descricao() != null) {
            this.descricao = dto.descricao();
        }
        return this;
    }
}
