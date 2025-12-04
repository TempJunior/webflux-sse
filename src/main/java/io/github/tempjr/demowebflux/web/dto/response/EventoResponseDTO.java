package io.github.tempjr.demowebflux.web.dto.response;

import io.github.tempjr.demowebflux.domain.model.Evento;
import io.github.tempjr.demowebflux.domain.model.enums.TipoEvento;

import java.time.LocalDate;

public record EventoResponseDTO(
    Long id,
    String nome,
    LocalDate localDate,
    TipoEvento tipoEvento,
    String descricao
) {
    public static EventoResponseDTO toDTO(Evento evento) {
        return new EventoResponseDTO(
            evento.getId(),
            evento.getNome(),
            evento.getData(),
            evento.getTipoEvento(),
            evento.getDescricao()
        );
    }

    public Evento toEntity() {
        Evento evento = new Evento();
        evento.setNome(this.nome);
        evento.setDescricao(this.descricao);
        return evento;
    }
}
