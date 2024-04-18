package br.com.celcoin.commercialpaperimporter.emission.application.ports.output;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuance;

public interface IssuanceCreateOutput {

    Issuance create(Issuance createCommercialPaper);

}
