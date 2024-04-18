package br.com.celcoin.commercialpaperimporter.emission.domain.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Getter
@ToString
@Jacksonized
public class InputFinancialData {

    private Double costOfIssuance;

    private Double valueNominalUnitary;

    private Integer amount;

    private Double percentageOfInterestRemuneration;

    private Double indexerPercentage;

    private String indexer;

    private String convention;

    private String paymentPlace;

    private String ufPayment;

    private String typeOfPayment;

    private InputTabelaPrice tablePrice;

    private String period;

}
