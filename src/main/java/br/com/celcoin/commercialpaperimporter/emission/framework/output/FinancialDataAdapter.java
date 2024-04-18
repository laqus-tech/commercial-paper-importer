package br.com.celcoin.commercialpaperimporter.emission.framework.output;

import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.FinancialDataOutput;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.financialdata.FinancialData;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api.FinancialDataApi;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api.TokenApi;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.mappers.FinancialDataMapper;

public class FinancialDataAdapter implements FinancialDataOutput {

    private FinancialDataMapper mapper;

    public FinancialDataAdapter() {
        this.mapper = new FinancialDataMapper();
    }

    @Override
    public FinancialData add(String id, FinancialData financialData) {
        var token = new TokenApi().getAccessToken();
        return mapper.fromDto(new FinancialDataApi().postFinancialData(token, id, financialData));
    }

}
