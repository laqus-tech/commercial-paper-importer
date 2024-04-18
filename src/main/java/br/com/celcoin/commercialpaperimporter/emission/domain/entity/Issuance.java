package br.com.celcoin.commercialpaperimporter.emission.domain.entity;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.financialdata.FinancialData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "requestId")
@AllArgsConstructor
@NoArgsConstructor
public class Issuance {

    private String requestId;

    private String id;

    private String idIntegration;

    private String tipo;

    private String status;

    private String numeroDaEmissao;

    private String numeroDaSerie;

    private String dataDaEmissao;

    private String dataDoVencimento;

    private String localDaEmissao;

    private String ufDaEmissao;

    private FinancialData dadosFinanceiros;

    private Emissor emissor;

    private String cnpjDoEmissor;

    private String cnpjDoEscriturador;

    private List<Investor> investidores;

    private LocalDateTime dataCriacao;

    private ConstitutiveTerm constitutiveTerm;

}
