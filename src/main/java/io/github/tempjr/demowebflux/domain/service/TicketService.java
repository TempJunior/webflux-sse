package io.github.tempjr.demowebflux.domain.service;

import io.github.tempjr.demowebflux.domain.model.Venda;
import io.github.tempjr.demowebflux.domain.model.enums.TipoEvento;
import io.github.tempjr.demowebflux.resources.repository.TicketRepository;
import io.github.tempjr.demowebflux.resources.repository.VendaRepository;
import io.github.tempjr.demowebflux.web.dto.request.CompraRequestDTO;
import io.github.tempjr.demowebflux.web.dto.response.IngressoResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final VendaRepository vendaRepository;

    public TicketService(TicketRepository ticketRepository, VendaRepository vendaRepository) {
        this.ticketRepository = ticketRepository;
        this.vendaRepository = vendaRepository;
    }

    public Flux<IngressoResponseDTO> getAllTickets() {
        return ticketRepository.findAll()
                .map(IngressoResponseDTO::toDTO);
    }

    public Mono<IngressoResponseDTO> getById(Long id) {
        return ticketRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(IngressoResponseDTO::toDTO);
    }

    public Mono<IngressoResponseDTO> createTicket(IngressoResponseDTO requestDTO) {
        return ticketRepository.save(requestDTO.toEntity())
                .map(IngressoResponseDTO::toDTO);
    }

    public Mono<Void> delete(Long id) {
        return ticketRepository.findById(id)
                .flatMap(ticketRepository::delete);
    }

    public Flux<IngressoResponseDTO> getEventosByTipoDeTicket(String tipoTicket) {
        TipoEvento tipo = TipoEvento.valueOf(tipoTicket.toUpperCase());

        return ticketRepository.findByTipo(tipo)
                .map(IngressoResponseDTO::toDTO);
    }

    public Mono<IngressoResponseDTO> comprar(@RequestBody CompraRequestDTO requestDTO) {
        return ticketRepository.findById(requestDTO.ingressoId())
                .flatMap(in -> {
                    Venda venda = new Venda();
                    venda.setIngressoId(in.getId());
                    venda.setTotal(requestDTO.total());
                    return vendaRepository.save(venda).then(Mono.defer(() -> {
                        in.setTotal(in.getTotal() - requestDTO.total());
                        return ticketRepository.save(in);
                    }));
                })
                .map(IngressoResponseDTO::toDTO);
    }
}
