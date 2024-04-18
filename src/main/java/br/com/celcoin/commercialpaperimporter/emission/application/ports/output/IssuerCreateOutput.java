package br.com.celcoin.commercialpaperimporter.emission.application.ports.output;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuer;

public interface IssuerCreateOutput {
    Issuer create(Issuer issuer);

}
