package br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Getter
@ToString
@Jacksonized
@JsonIgnoreProperties
public class DadosFinanceirosResponseDTO {

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

}
