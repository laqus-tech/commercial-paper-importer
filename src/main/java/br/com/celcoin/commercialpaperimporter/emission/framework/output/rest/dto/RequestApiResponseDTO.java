package br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder(toBuilder = true)
@Getter
@ToString
@Jacksonized
@JsonIgnoreProperties
public class RequestApiResponseDTO {

    private String id;

    private String codigo;

    private String tipo;

    private String status;

    private Integer numeroDaEmissao;

    private Integer numeroDaSerie;

    private String dataDaEmissao;

    private String dataDoVencimento;

    private String cnpjDoEscriturador;

    private String localDaEmissao;

    private String ufDaEmissao;

    private FinancialDataResponseDTO dadosFinanceiros;

    private EmissorResponseDTO emissor;

    private List<InvestidorResponseDTO> investidores;

    public static RequestApiResponseDTO jsonToInput(final String json) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(json, RequestApiResponseDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
