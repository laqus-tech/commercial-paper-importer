package br.com.celcoin.commercialpaperimporter.emission.application.ports.output;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.financialdata.FinancialData;

public interface FinancialDataOutput {
    FinancialData add(String id, FinancialData addFinancialData);

}
