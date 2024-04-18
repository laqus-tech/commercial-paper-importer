package br.com.celcoin.commercialpaperimporter.infrastructure.config.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class SecretManagerVO {

    @JsonProperty("API_KEY")
    private String apiKey;

    @JsonProperty("SECRET_KEY")
    private String secretKey;

    @JsonProperty("ENVIRONMENT")
    private String environment;

    public static SecretManagerVO jsonToInput(final String json) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(json, SecretManagerVO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
