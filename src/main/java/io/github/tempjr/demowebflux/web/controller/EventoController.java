package io.github.tempjr.demowebflux.web.controller;

import io.github.tempjr.demowebflux.domain.service.EventoService;
import io.github.tempjr.demowebflux.web.dto.request.EventoRequestDTO;
import io.github.tempjr.demowebflux.web.dto.request.EventoUpdateRequestDTO;
import io.github.tempjr.demowebflux.web.dto.response.EventoResponseDTO;
import io.github.tempjr.demowebflux.web.dto.response.TranslateResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
@RequestMapping("/eventos")
public class EventoController {
    private final EventoService eventoService;
    private final Sinks.Many<EventoResponseDTO> eventoSink;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
        this.eventoSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    //Devolve uma stream de eventos em tempo real - Arquivo de texto
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EventoResponseDTO> getAllEventos() {
        return eventoService.getAllEventos();
    }

    @GetMapping("/{id}")
    public Mono<EventoResponseDTO> getById(@PathVariable Long id) {
        return eventoService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Mono<EventoResponseDTO>> createEvento(@RequestBody EventoRequestDTO requestDTO){
        var evento = eventoService.createEvento(requestDTO)
                .doOnSuccess(eventoSink::tryEmitNext);
        return ResponseEntity.status(HttpStatus.CREATED).body(evento);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteAllEventos(@PathVariable Long id) {
        return eventoService.delete(id);
    }

    @PutMapping("/{id}")
    public Mono<EventoResponseDTO> updateEvento(@PathVariable Long id, @RequestBody EventoUpdateRequestDTO requestDTO){
        return eventoService.updateEvento(id, requestDTO);
    }

    @GetMapping(value = "/categoria/{tipoEvento}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EventoResponseDTO> getEventosByTipoEvento(@PathVariable String tipoEvento) {
        return Flux.merge(eventoService.getEventosByTipoEvento(tipoEvento), eventoSink.asFlux())
                .delayElements(Duration.ofSeconds(4));
    }

    @GetMapping("/{id}/translate/{language}")
    public Mono<String> translateText(@PathVariable Long id, @PathVariable String language) {
        return eventoService.getTranslate(id, language);
    }

}
