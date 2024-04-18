package br.com.celcoin.commercialpaperimporter.emission.application.ports.output;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.AttachSignedTerm;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuance;

public interface IssuancePersistOutput {

    Issuance update(String id, Issuance createCommercialPaper, AttachSignedTerm attachSignedTerm, String updateStatus);

    Issuance save(Issuance createCommercialPaper);

    Issuance updateStatus(Issuance issuance, String status);

    Issuance get(String id, String cnpjDoEscriturador);

    Issuance getByRequestId(String issuerId);

    Issuance updateStatusRegisteredIssuer(String id, String cnpjDoEscriturador);

}
