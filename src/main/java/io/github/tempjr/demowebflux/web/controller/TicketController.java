package io.github.tempjr.demowebflux.web.controller;

import io.github.tempjr.demowebflux.domain.service.TicketService;
import io.github.tempjr.demowebflux.web.dto.request.CompraRequestDTO;
import io.github.tempjr.demowebflux.web.dto.response.EventoResponseDTO;
import io.github.tempjr.demowebflux.web.dto.response.IngressoResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final Sinks.Many<IngressoResponseDTO> ticketSink;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
        this.ticketSink = Sinks.many().multicast().onBackpressureBuffer();
    }


    @GetMapping
    public Flux<IngressoResponseDTO> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public Mono<IngressoResponseDTO> getById(@PathVariable Long id) {
        return ticketService.getById(id);
    }

    @PostMapping
    public Mono<IngressoResponseDTO> createTicket(@RequestBody IngressoResponseDTO requestDTO){
        return ticketService.createTicket(requestDTO)
                .doOnSuccess(ticketSink::tryEmitNext);
    }

    @GetMapping(value = "/categoria/{tipoTicket}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<IngressoResponseDTO> getEventosByTipoEvento(@PathVariable String tipoEvento) {
        return Flux.merge(ticketService.getEventosByTipoDeTicket(tipoEvento), ticketSink.asFlux())
                .delayElements(Duration.ofSeconds(4));
    }

    @PostMapping("/compra")
    public Mono<IngressoResponseDTO> comprar(@RequestBody CompraRequestDTO dto) {
        return ticketService.comprar(dto)
                .doOnSuccess(ticketSink::tryEmitNext);
    }

    @GetMapping(value = "/{id}/disponivel", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<IngressoResponseDTO> totalDisponivel(@PathVariable Long id) {
        return Flux.merge(ticketService.getById(id), ticketSink.asFlux())
                .delayElements(Duration.ofSeconds(4));
    }
}
