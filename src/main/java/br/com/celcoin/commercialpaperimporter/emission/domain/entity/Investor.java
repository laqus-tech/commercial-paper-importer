package br.com.celcoin.commercialpaperimporter.emission.domain.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "cnpjCpf")
@AllArgsConstructor
@NoArgsConstructor
public class Investor {

    private String cpfCnpj;

    private String cnpjDoCustodiante;

    private Double precoUnitario;

    private Integer quantidadeSubscrita;

    public static Investor jsonToEntity(final String json) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(json, Investor.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
