package br.com.celcoin.commercialpaperimporter.emission.domain.entity;

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
@EqualsAndHashCode(of = "cnpj")
@AllArgsConstructor
@NoArgsConstructor
public class Issuer {

    private String id;

    private String typeOfCompany;

    private String function;

    private String cnpj;

    private String corporateName;

    private String mainActivity;

    private Integer revenueAverageMonthly12Months;

    private Address address;

    private BankData bankData;

    private List<Phone> phones;

}
