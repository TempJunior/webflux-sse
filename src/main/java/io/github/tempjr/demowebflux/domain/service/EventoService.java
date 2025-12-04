package io.github.tempjr.demowebflux.domain.service;


import io.github.tempjr.demowebflux.domain.model.enums.TipoEvento;
import io.github.tempjr.demowebflux.resources.repository.EventoRepository;
import io.github.tempjr.demowebflux.web.dto.request.EventoRequestDTO;
import io.github.tempjr.demowebflux.web.dto.request.EventoUpdateRequestDTO;
import io.github.tempjr.demowebflux.web.dto.response.EventoResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EventoService {
    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public Flux<EventoResponseDTO> getAllEventos() {
        return eventoRepository.findAll()
                .map(EventoResponseDTO::toDTO);
    }

    public Mono<EventoResponseDTO> getById(Long id) {
        return eventoRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(EventoResponseDTO::toDTO);
    }

    public Mono<EventoResponseDTO> createEvento(EventoRequestDTO requestDTO) {
        return eventoRepository.save(requestDTO.toEntity())
                .map(EventoResponseDTO::toDTO);
    }

    public Mono<Void> delete(Long id) {
        return eventoRepository.findById(id)
                .flatMap(eventoRepository::delete);
    }

    public Mono<EventoResponseDTO> updateEvento(Long id, EventoUpdateRequestDTO requestDTO){
        return eventoRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(ev -> ev.atualizar(requestDTO))
                .flatMap(eventoRepository::save)
                .map(EventoResponseDTO::toDTO);
    }

    public Flux<EventoResponseDTO> getEventosByTipoEvento(String tipoEvento) {
        TipoEvento tipo = TipoEvento.valueOf(tipoEvento.toUpperCase());

        return eventoRepository.findByTipoEvento(tipo)
                .map(EventoResponseDTO::toDTO);
    }
}
