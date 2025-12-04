package io.github.tempjr.demowebflux.web.dto.request;

import io.github.tempjr.demowebflux.domain.model.Evento;

public record EventoUpdateRequestDTO(
    String nome,
    String descricao
) {
    public static EventoUpdateRequestDTO toDTO(Evento evento) {
        return new EventoUpdateRequestDTO(
                evento.getNome(),
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
