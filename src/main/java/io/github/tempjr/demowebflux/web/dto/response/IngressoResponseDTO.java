package io.github.tempjr.demowebflux.web.dto.response;

import io.github.tempjr.demowebflux.domain.model.Tickets;
import io.github.tempjr.demowebflux.domain.model.enums.TipoIngresso;

public record IngressoResponseDTO(
        Long id,
        Long eventoId,
        TipoIngresso tipo,
        Double valor,
        int total
) {
    public static IngressoResponseDTO toDTO(Tickets ingresso) {
        return new IngressoResponseDTO(
                ingresso.getId(),
                ingresso.getEventoId(),
                ingresso.getTipo(),
                ingresso.getValor(),
                ingresso.getTotal()
        );
    }
    public Tickets toEntity() {
        Tickets ingresso = new Tickets();
        ingresso.setId(this.id);
        ingresso.setEventoId(this.eventoId);
        ingresso.setTipo(this.tipo);
        ingresso.setValor(this.valor);
        return ingresso;
    }
}
