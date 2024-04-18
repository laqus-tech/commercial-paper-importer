package br.com.celcoin.commercialpaperimporter.emission.application.ports.output;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.LegalRepresentative;

public interface RegisterLegalRepresentativeOutput {

    LegalRepresentative create(String id, LegalRepresentative legalRepresentative);

}
