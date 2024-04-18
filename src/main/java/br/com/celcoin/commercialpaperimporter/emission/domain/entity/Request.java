package br.com.celcoin.commercialpaperimporter.emission.domain.entity;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.financialdata.FinancialData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class Request {

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

    private FinancialData dadosFinanceiros;

    private Emissor emissor;

    private List<Investor> investidores;

}
