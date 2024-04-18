package br.com.celcoin.commercialpaperimporter.emission.domain.specification;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuance;
import br.com.celcoin.commercialpaperimporter.shared.domain.specification.AbstractSpecification;
import br.com.celcoin.commercialpaperimporter.shared.domain.specification.Specifications;

public interface CommercialPaperSpecification {

    static AbstractSpecification<Issuance> cnpjDoEmissorIsNotEmtpy() {
        return Specifications.basic("O cnpj do emissor deve ser informado",
                (candidate, v) -> candidate.getCnpjDoEmissor() != null && !candidate.getCnpjDoEmissor().isEmpty());
    }

}
