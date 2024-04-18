package br.com.celcoin.commercialpaperimporter.emission.application.ports.output;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.AttachSignedTerm;

public interface AttachSignedTermPersistOutput {

    AttachSignedTerm save(AttachSignedTerm attachSIgnedTerm);

}
