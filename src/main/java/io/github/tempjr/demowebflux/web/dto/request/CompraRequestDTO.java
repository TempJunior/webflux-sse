package io.github.tempjr.demowebflux.web.dto.request;

public record CompraRequestDTO(
        Long ingressoId,
        int total
) {
}
