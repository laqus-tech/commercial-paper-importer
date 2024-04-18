package br.com.celcoin.commercialpaperimporter.emission.application.ports.output;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuance;

import java.util.List;

public interface IssuanceGetOutput {

    List<Issuance> listByStatus(String status);

}
