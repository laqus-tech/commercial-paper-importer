package br.com.celcoin.commercialpaperimporter.emission.domain.entity.financialdata;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.TabelaPrice;
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
@EqualsAndHashCode(of = "numeroDaEmissao")
@AllArgsConstructor
@NoArgsConstructor
public class FinancialData {

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

    private TabelaPrice tabelaPrice;

    private String periodo;

}
