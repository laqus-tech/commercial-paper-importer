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
public class FinancialDataResponseDTO {

    private Double custoDaEmissao;

    private Double valorNominalUnitario;

    private Integer quantidade;

    private Double percentualDosJurosRemuneratorios;

    private Double percentualDoIndexador;

    private String indexador;

    private String convencao;

    private String localDePagamento;

    private String ufDePagamento;

    private String tipoDePagamento;

    private TabelaPriceDTO tabelaPrice;

    private String periodo;

    public static FinancialDataResponseDTO jsonToInput(final String json) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(json, FinancialDataResponseDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
