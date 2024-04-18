package br.com.celcoin.commercialpaperimporter.emission.domain.service;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.TabelaPrice;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.financialdata.FinancialData;
import br.com.celcoin.commercialpaperimporter.emission.domain.specification.FinancialDataSpecification;
import br.com.celcoin.commercialpaperimporter.emission.domain.vo.InputFinancialData;

public interface FinancialDataService {

    static FinancialData fromInput(InputFinancialData input) {

        var data = FinancialData.builder()
                .custoDaEmissao(input.getCostOfIssuance())
                .valorNominalUnitario(input.getValueNominalUnitary())
                .quantidade(input.getAmount())
                .percentualDosJurosRemuneratorios(input.getPercentageOfInterestRemuneration())
                .percentualDoIndexador(input.getIndexerPercentage())
                .indexador(input.getIndexer())
                .convencao(input.getConvention())
                .localDePagamento(input.getPaymentPlace())
                .ufDePagamento(input.getUfPayment())
                .tipoDePagamento(input.getTypeOfPayment())
                .tabelaPrice(TabelaPrice.builder()
//                        .percentualSobre(input.getTabelaPrice().getPercentualSobre())
//                        .vencimentoDa1aParcela(input.getTabelaPrice().getVencimentoDa1aParcela())
//                        .periodicidade(input.getTabelaPrice().getPeriodicidade())
                        .percentualSobre("")
                        .vencimentoDa1aParcela("")
                        .periodicidade("")
                        .build())
                .build();

        FinancialDataSpecification.custoDaEmissaoIsNotEmtpy().check(data);

        return data;
    }

    static FinancialData fromData(FinancialData data) {

        var financialData = FinancialData.builder()
                .custoDaEmissao(data.getCustoDaEmissao())
                .valorNominalUnitario(data.getValorNominalUnitario())
                .quantidade(data.getQuantidade())
                .percentualDosJurosRemuneratorios(data.getPercentualDosJurosRemuneratorios())
                .percentualDoIndexador(data.getPercentualDoIndexador())
                .indexador(data.getIndexador())
                .convencao(data.getConvencao())
                .localDePagamento(data.getLocalDePagamento())
                .ufDePagamento(data.getUfDePagamento())
                .tipoDePagamento(data.getTipoDePagamento())
                .tabelaPrice(TabelaPrice.builder()
                        .percentualSobre(data.getTabelaPrice().getPercentualSobre())
                        .vencimentoDa1aParcela(data.getTabelaPrice().getVencimentoDa1aParcela())
                        .periodicidade(data.getTabelaPrice().getPeriodicidade())
                        .build())
                .periodo(data.getPeriodo())
                .build();

        return financialData;

    }

}
