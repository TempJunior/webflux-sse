package io.github.tempjr.demowebflux.resources.repository;

import io.github.tempjr.demowebflux.domain.model.Venda;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface VendaRepository extends ReactiveCrudRepository<Venda, Long> {
}
