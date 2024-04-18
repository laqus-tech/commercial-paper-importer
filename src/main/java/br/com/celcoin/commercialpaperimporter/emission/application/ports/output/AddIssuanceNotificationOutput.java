package br.com.celcoin.commercialpaperimporter.emission.application.ports.output;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuance;

public interface AddIssuanceNotificationOutput {

    void notification(Issuance issuance);

    void notificationError(String error, Issuance issuance);

}
