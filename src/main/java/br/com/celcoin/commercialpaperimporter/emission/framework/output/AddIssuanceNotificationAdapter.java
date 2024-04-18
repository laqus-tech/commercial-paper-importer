package br.com.celcoin.commercialpaperimporter.emission.framework.output;

import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.AddIssuanceNotificationOutput;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuance;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.sns.Notification;

public class AddIssuanceNotificationAdapter implements AddIssuanceNotificationOutput {
    @Override
    public void notification(Issuance issuance) {
        new Notification().send(null, issuance);
    }

    @Override
    public void notificationError(String error, Issuance issuance) {
        new Notification().send(error, issuance);
    }
}
