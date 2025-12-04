package io.github.tempjr.demowebflux.web.dto.request;

import io.github.tempjr.demowebflux.domain.model.Evento;
import io.github.tempjr.demowebflux.domain.model.enums.TipoEvento;

import java.time.LocalDate;

public record EventoRequestDTO(
        Long id,
    String nome,
    LocalDate data,
    TipoEvento tipoEvento,
    String descricao
) {
    public static EventoRequestDTO toDTO(Evento evento) {
        return new EventoRequestDTO(
                evento.getId(),
                evento.getNome(),
                evento.getData(),
                evento.getTipoEvento(),
                evento.getDescricao()
        );
    }

    public Evento toEntity() {
        Evento evento = new Evento();
        evento.setId(this.id);
        evento.setNome(this.nome);
        evento.setData(this.data);
        evento.setTipoEvento(this.tipoEvento);
        evento.setDescricao(this.descricao);
        return evento;
    }
}
