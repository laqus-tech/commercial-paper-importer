package br.com.celcoin.commercialpaperimporter.emission.application.ports.input;

import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.FinancialDataOutput;
import br.com.celcoin.commercialpaperimporter.emission.application.usecases.FinancialDataUseCase;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.financialdata.FinancialData;
import br.com.celcoin.commercialpaperimporter.emission.domain.service.FinancialDataService;
import br.com.celcoin.commercialpaperimporter.emission.domain.vo.InputFinancialData;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.FinancialDataAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FinancialDataInput implements FinancialDataUseCase {

    private static final Logger LOG = LogManager.getLogger(FinancialDataInput.class);

    private FinancialDataOutput financialDataOutput;

    public FinancialDataInput() {
        this.financialDataOutput = new FinancialDataAdapter();
    }

    @Override
    public FinancialData addFinancialData(String id, InputFinancialData input) {
        var financialData = FinancialDataService.fromInput(input);
        LOG.info("Intial: requestFinancialData with entity: {}", financialData);
        return financialDataOutput.add(id, financialData);
    }

}
