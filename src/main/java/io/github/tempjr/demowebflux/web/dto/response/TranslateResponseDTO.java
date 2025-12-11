package io.github.tempjr.demowebflux.web.dto.response;

import java.util.List;

public record TranslateResponseDTO(
        List<TextResponseDTO> translations
) {
    public String getText(){
       return translations.get(0).text();
    }
}
