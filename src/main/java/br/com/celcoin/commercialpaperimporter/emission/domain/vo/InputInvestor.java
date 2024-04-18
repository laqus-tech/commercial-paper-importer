package br.com.celcoin.commercialpaperimporter.emission.domain.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Getter
@ToString
@Jacksonized
public class InputInvestor {

    private String cpfCnpj;

    private String cnpjCustodian;

    private Double unitPrice;

    private Integer quantitySubscribed;

}
