package br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Getter
@ToString
@Jacksonized
@JsonIgnoreProperties
public class AttachSignedTermResponseDTO {

    private String id;

    private String link;

    private String dataDaCriacao;

    private String dataDaAtualizacao;

    public static AttachSignedTermResponseDTO jsonToInput(final String json) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(json, AttachSignedTermResponseDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
