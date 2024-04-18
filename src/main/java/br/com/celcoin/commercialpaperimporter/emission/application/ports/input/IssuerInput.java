package br.com.celcoin.commercialpaperimporter.emission.application.ports.input;

import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.IssuancePersistOutput;
import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.IssuerCreateOutput;
import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.RegisterLegalRepresentativeOutput;
import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.SubmitRegistrationForApprovalOutput;
import br.com.celcoin.commercialpaperimporter.emission.application.usecases.IssuerUseCase;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuance;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuer;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.LegalRepresentative;
import br.com.celcoin.commercialpaperimporter.emission.domain.service.IssuerService;
import br.com.celcoin.commercialpaperimporter.emission.domain.service.LegalRepresentativeService;
import br.com.celcoin.commercialpaperimporter.emission.domain.vo.InputIssuer;
import br.com.celcoin.commercialpaperimporter.emission.domain.vo.InputLegalRepresentative;
import br.com.celcoin.commercialpaperimporter.emission.framework.SubmitRegistrationForApprovalAdapter;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.IssuancePersistAdapter;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.IssuerCreateAdapter;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.RegisterLegalRepresentativeAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IssuerInput implements IssuerUseCase {

    private static final Logger LOG = LogManager.getLogger(IssuerInput.class);

    private IssuerCreateOutput issuerCreateOutput;

    private RegisterLegalRepresentativeOutput registerLegalRepresentativeOutput;

    private SubmitRegistrationForApprovalOutput submitRegistrationForApprovalOutput;

    private IssuancePersistOutput issuancePersistOutput;

    private static final String REGISTERED_ISSUER_STATUS = "Registered Issuer";

    public IssuerInput() {
        this.issuerCreateOutput = new IssuerCreateAdapter();
        this.registerLegalRepresentativeOutput = new RegisterLegalRepresentativeAdapter();
        this.submitRegistrationForApprovalOutput = new SubmitRegistrationForApprovalAdapter();
        this.issuancePersistOutput = new IssuancePersistAdapter();
    }

    @Override
    public Issuer addIssuer(Issuance issuanceCreate, InputIssuer input, InputLegalRepresentative inputLegalRepresentative) {
        LOG.info("adiciona emissor: {}", input);

        Issuer issuerCreate = this.create(input);

        LegalRepresentative legalRepresentativeCreate = this.registerLegalRepresentative(issuerCreate.getId(), inputLegalRepresentative);

        this.submitRegistrationRequestForApproval(issuerCreate.getId());

//        this.issuancePersistOutput.updateStatusRegisteredIssuer(issuanceCreate.getId(), issuanceCreate.getCnpjDoEscriturador());

        return issuerCreate;
    }

    private Issuer create(InputIssuer input) {
        return this.issuerCreateOutput.create(IssuerService.fromInput(input));
    }

    private LegalRepresentative registerLegalRepresentative(String id, InputLegalRepresentative inputLegalRepresentative) {
        LOG.info("cadastra representante legal id: {}, objeto: {}", id, inputLegalRepresentative);
        return registerLegalRepresentativeOutput.create(id, LegalRepresentativeService.fromInput(inputLegalRepresentative));
    }

    private void submitRegistrationRequestForApproval(String id) {
        LOG.info("submeter a solicitação de cadastro para aprovação, id: {}", id);
        submitRegistrationForApprovalOutput.approve(id);
    }
}
