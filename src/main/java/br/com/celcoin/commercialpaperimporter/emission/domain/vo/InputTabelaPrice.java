package br.com.celcoin.commercialpaperimporter.emission.domain.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Getter
@ToString
@Jacksonized
public class InputTabelaPrice {

    private String percentageAbout;

    private String dueDateOfThe1stInstallment;

    private String frequency;

}
