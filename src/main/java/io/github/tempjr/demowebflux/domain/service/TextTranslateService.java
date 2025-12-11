package io.github.tempjr.demowebflux.domain.service;

import io.github.tempjr.demowebflux.web.dto.response.TranslateResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class TextTranslateService {

    private static final String apiKey = System.getenv("DEEPL_APIKEY");

    public static Mono<String> getTranslatedText(String text, String targetLanguage) {
        System.out.println("API KEY TESTE " + apiKey);
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api-free.deepl.com/v2/translate")
                .build();

        MultiValueMap<String, String> req = new LinkedMultiValueMap<>();
        req.add("text", text);
        req.add("target_lang", targetLanguage);

        return webClient.post()
                .header("Authorization", "DeepL-Auth-Key " + apiKey)
                .body(BodyInserters.fromFormData(req))
                .retrieve()
                .bodyToMono(TranslateResponseDTO.class)
                .map(TranslateResponseDTO::getText);
    }
}
