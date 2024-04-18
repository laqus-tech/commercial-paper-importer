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
public class RequestIssuanceApiResponseDTO {

    private String id;

    private String tipo;

    private String status;

    private int numeroDaEmissao;

    private int numeroDaSerie;

    private String dataDaEmissao;

    private String dataDoVencimento;

    private String cnpjDoEscriturador;

    private String localDaEmissao;

    private String ufDaEmissao;

    private DadosFinanceirosResponseDTO dadosFinanceiros;

    private EmissorResponseDTO emissor;

    private List<InvestidorResponseDTO> investidores;

    public static RequestIssuanceApiResponseDTO jsonToInput(final String json) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(json, RequestIssuanceApiResponseDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
