package br.com.celcoin.commercialpaperimporter.emission.domain.entity;

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
@EqualsAndHashCode(of = "currentAccount")
@AllArgsConstructor
@NoArgsConstructor
public class BankData {

    private String bankCode;

    private String agency;

    private String agencyDigit;

    private String currentAccount;

    private String digitOfAccount;


}
