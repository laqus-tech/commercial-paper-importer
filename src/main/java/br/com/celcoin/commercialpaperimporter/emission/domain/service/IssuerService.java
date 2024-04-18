package br.com.celcoin.commercialpaperimporter.emission.domain.service;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Address;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.BankData;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuer;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Phone;
import br.com.celcoin.commercialpaperimporter.emission.domain.vo.InputIssuer;

import java.util.stream.Collectors;

public interface IssuerService {

    static Issuer fromInput(InputIssuer input) {

        return Issuer.builder()
                .typeOfCompany(input.getTypeOfCompany())
                .function(input.getFunction())
                .cnpj(input.getCnpj())
                .corporateName(input.getCorporateName())
                .mainActivity(input.getMainActivity())
                .revenueAverageMonthly12Months(input.getRevenueAverageMonthly12Months())
                .address(Address.builder()
                        .zipCode(input.getAddress().getZipCode())
                        .address(input.getAddress().getAddress())
                        .number(input.getAddress().getNumber())
                        .complement(input.getAddress().getComplement())
                        .neighborhood(input.getAddress().getNeighborhood())
                        .city(input.getAddress().getCity())
                        .uf(input.getAddress().getUf())
                        .build())
                .bankData(BankData.builder()
                        .bankCode(input.getBankData().getBankCode())
                        .agency(input.getBankData().getAgency())
                        .agencyDigit(input.getBankData().getAgencyDigit())
                        .currentAccount(input.getBankData().getCurrentAccount())
                        .digitOfAccount(input.getBankData().getDigitOfAccount())
                        .build())
                .phones(input.getPhones().stream().map(phone ->
                        Phone.builder()
                                .number(phone.getNumber())
                                .type(phone.getType())
                                .build()).collect(Collectors.toList()))
                .build();

    }

}
