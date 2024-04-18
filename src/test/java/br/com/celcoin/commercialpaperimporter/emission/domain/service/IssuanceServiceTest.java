package br.com.celcoin.commercialpaperimporter.emission.domain.service;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuance;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IssuanceServiceTest {

    @Test
    void when_StatusAwaitingSettlement_returnAwaitingSettlement() {
        Issuance issuanceUpdate = IssuanceService.updateStatus(Issuance.builder().build(), "Aguardando Liquidação");
        Assertions.assertEquals(Status.AWAITING_SETTLEMENT.name(), issuanceUpdate.getStatus());
    }

    @Test
    void when_StatusRegisteredIssuer_returnRegisteredIssuer() {
        Issuance issuanceUpdate = IssuanceService.updateStatus(Issuance.builder().build(), "Emissor Registrado");
        Assertions.assertEquals(Status.REGISTERED_ISSUER.name(), issuanceUpdate.getStatus());
    }

    @Test
    void when_StatusCreated_returnInProgress() {
        Issuance issuanceUpdate = IssuanceService.updateStatus(Issuance.builder().build(), "Em Andamento");
        Assertions.assertEquals(Status.IN_PROGRESS.name(), issuanceUpdate.getStatus());
    }

}
