package io.github.tempjr.demowebflux;

import io.github.tempjr.demowebflux.domain.model.enums.TipoEvento;
import io.github.tempjr.demowebflux.web.dto.request.EventoRequestDTO;
import io.github.tempjr.demowebflux.web.dto.response.EventoResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class DemowebfluxApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

	@Test
	void cadastraNovoEvento() {
        EventoRequestDTO dto = new EventoRequestDTO(null, "Evento de Teste", LocalDate.now(), TipoEvento.CONCERTO, "Local de Teste");

        webTestClient.post()
                .uri("/eventos")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(EventoResponseDTO.class)
                .consumeWith(response -> {
                    EventoResponseDTO responseBody = response.getResponseBody();
                    assert responseBody != null;
                    assert responseBody.id() != null;
                    assert responseBody.nome().equals(dto.nome());
                    assert responseBody.localDate().equals(dto.data());
                    assert responseBody.tipoEvento().equals(dto.tipoEvento());
                    assert responseBody.descricao().equals(dto.descricao());
                });
	}

    @Test
    void buscarEvento() {
        EventoRequestDTO dto = new EventoRequestDTO(13L, "The Weeknd", LocalDate.parse("2025-11-02"), TipoEvento.SHOW, "Um show eletrizante ao ar livre com muitos efeitos especiais.");

        webTestClient.get()
                .uri("/eventos")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(EventoResponseDTO.class)
                .consumeWith(response -> {
                    assert response.getResponseBody() != null;
                    EventoResponseDTO responseBody = response.getResponseBody().get(13);

                    assert Objects.equals(responseBody.id(), dto.id());
                    assert responseBody.nome().equals(dto.nome());
                    assert responseBody.localDate().equals(dto.data());
                    assert responseBody.tipoEvento().equals(dto.tipoEvento());
                    assert responseBody.descricao().equals(dto.descricao());
                });
    }
}
