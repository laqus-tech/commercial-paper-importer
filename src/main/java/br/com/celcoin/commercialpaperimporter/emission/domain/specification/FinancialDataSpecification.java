package br.com.celcoin.commercialpaperimporter.emission.domain.specification;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.financialdata.FinancialData;
import br.com.celcoin.commercialpaperimporter.shared.domain.specification.AbstractSpecification;
import br.com.celcoin.commercialpaperimporter.shared.domain.specification.Specifications;

public interface FinancialDataSpecification {

    static AbstractSpecification<FinancialData> custoDaEmissaoIsNotEmtpy() {
        return Specifications.basic("O custo da emissão deve ser informado",
                (candidate, v) -> candidate.getCustoDaEmissao() != null && candidate.getCustoDaEmissao() > 0);
    }

}
