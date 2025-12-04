package io.github.tempjr.demowebflux.resources.repository;

import io.github.tempjr.demowebflux.domain.model.Evento;
import io.github.tempjr.demowebflux.domain.model.enums.TipoEvento;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface EventoRepository extends ReactiveCrudRepository<Evento, Long> {
    Flux<Evento> findByTipoEvento(TipoEvento tipoEvento);
}
