package io.github.tempjr.demowebflux.resources.repository;

import io.github.tempjr.demowebflux.domain.model.Tickets;
import io.github.tempjr.demowebflux.domain.model.enums.TipoEvento;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TicketRepository extends ReactiveCrudRepository<Tickets, Long> {
    Flux<Tickets> findByTipo(TipoEvento tipo);
}
