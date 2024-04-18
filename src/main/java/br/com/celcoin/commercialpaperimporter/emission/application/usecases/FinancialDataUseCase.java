package br.com.celcoin.commercialpaperimporter.emission.application.usecases;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.financialdata.FinancialData;
import br.com.celcoin.commercialpaperimporter.emission.domain.vo.InputFinancialData;

public interface FinancialDataUseCase {

    FinancialData addFinancialData(String id, InputFinancialData input);


}
