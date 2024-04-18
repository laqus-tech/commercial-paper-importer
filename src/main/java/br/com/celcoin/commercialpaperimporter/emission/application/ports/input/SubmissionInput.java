package br.com.celcoin.commercialpaperimporter.emission.application.ports.input;

import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.AddIssuanceNotificationOutput;
import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.ConsultRequestOutput;
import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.IssuanceGetOutput;
import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.IssuancePersistOutput;
import br.com.celcoin.commercialpaperimporter.emission.application.usecases.SubmissionUseCase;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuance;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Status;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.AddIssuanceNotificationAdapter;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.ConsultRequestAdapter;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.IssuanceGetAdapter;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.IssuancePersistAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class SubmissionInput implements SubmissionUseCase {

    private static final Logger LOG = LogManager.getLogger(SubmissionInput.class);

    private static final String ISSUER_STATUS_APPROVED = "Aprovado";


    private IssuanceGetOutput issuanceGetOutput;

    private ConsultRequestOutput consultRequestOutput;

    private AddIssuanceNotificationOutput addIssuanceNotificationOutput;

    private IssuancePersistOutput issuancePersistOutput;

    public SubmissionInput() {
        this.issuanceGetOutput = new IssuanceGetAdapter();
        this.consultRequestOutput = new ConsultRequestAdapter();
        this.addIssuanceNotificationOutput = new AddIssuanceNotificationAdapter();
        this.issuancePersistOutput = new IssuancePersistAdapter();
    }

    @Override
    public void processSubmission() {
        List<Issuance> issuanceList = issuanceGetOutput.listByStatus(Status.REGISTERED_ISSUER.toString());
        LOG.info("lista retornado do banco: {}", issuanceList);
        issuanceList.stream().forEach(issuance -> {
            try {
                LOG.info("execute issuance id: {}", issuance.getId());
                Optional.of(consultRequestOutput.consult(issuance.getIdIntegration())).ifPresent(issuancePresent -> {

                    LOG.info("status emissor retornado: {}, esta aprovado: {}", issuancePresent.getEmissor().getStatus(), issuancePresent.getEmissor().getStatus().equals(ISSUER_STATUS_APPROVED));

                    if (issuancePresent.getEmissor().getStatus().equals(ISSUER_STATUS_APPROVED)) {
                        LOG.info("status confirmado para o id: {}", issuance.getId());
                        issuancePersistOutput.updateStatus(issuance, Status.AWAITING_SETTLEMENT.name());
                        addIssuanceNotificationOutput.notification(issuance);
                    }
                });

            } catch (Exception e) {
                LOG.error("error in processSubmission id: {} error: {}", issuance.getId(), e);
            }
        });
    }

}
