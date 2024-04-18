package br.com.celcoin.commercialpaperimporter.emission.application.ports.input;

import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.AddIssuanceNotificationOutput;
import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.AttachSignedTermAddOutput;
import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.ConsultRequestOutput;
import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.EmissionQuantityGetOutput;
import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.FinancialDataOutput;
import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.IssuanceCreateOutput;
import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.IssuanceGetOutput;
import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.IssuancePersistOutput;
import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.SubmissionCreateOutput;
import br.com.celcoin.commercialpaperimporter.emission.application.usecases.IssuanceUseCase;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.AttachSignedTerm;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.ConsultRequest;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuance;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Request;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Status;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.financialdata.FinancialData;
import br.com.celcoin.commercialpaperimporter.emission.domain.service.IssuanceService;
import br.com.celcoin.commercialpaperimporter.emission.domain.vo.InputRequestIssuance;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.AddIssuanceNotificationAdapter;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.AttachSignedTermAddAdapter;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.ConsultRequestAdapter;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.EmissionQuantityGetAdapter;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.FinancialDataAdapter;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.IssuanceCreateAdapter;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.IssuanceGetAdapter;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.IssuancePersistAdapter;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.SubmissionCreateAdapter;
import br.com.celcoin.commercialpaperimporter.shared.domain.exception.ErroNegocioException;
import br.com.celcoin.commercialpaperimporter.shared.domain.exception.ErroTecnicoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class IssuanceInput implements IssuanceUseCase {

    private static final Logger LOG = LogManager.getLogger(IssuanceInput.class);

    private EmissionQuantityGetOutput emissionQuantityGetOutput;
    private IssuanceCreateOutput emissionOutput;
    private IssuancePersistOutput issuancePersistOutput;
    private FinancialDataOutput financialDataOutput;

    private AttachSignedTermAddOutput attachSignedTermAddOutput;

    private SubmissionCreateOutput submissionCreateOutput;

    private IssuanceGetOutput issuanceGetOutput;

    private AddIssuanceNotificationOutput addIssuanceNotificationOutput;

    private ConsultRequestOutput consultRequestOutput;

    private IssuerInput issuerInput;

    public IssuanceInput() {
        this.emissionOutput = new IssuanceCreateAdapter();
        this.issuancePersistOutput = new IssuancePersistAdapter();
        this.emissionQuantityGetOutput = new EmissionQuantityGetAdapter();
        this.financialDataOutput = new FinancialDataAdapter();
        this.attachSignedTermAddOutput = new AttachSignedTermAddAdapter();
        this.submissionCreateOutput = new SubmissionCreateAdapter();
        this.issuanceGetOutput = new IssuanceGetAdapter();
        this.addIssuanceNotificationOutput = new AddIssuanceNotificationAdapter();
        this.issuerInput = new IssuerInput();
        this.consultRequestOutput = new ConsultRequestAdapter();
    }

    @Override
    public Issuance requestIssuance(InputRequestIssuance input) {
        try {
            var issuancePersist = this.createOrGet(input);

            issuancePersist = IssuanceService.nextEmission(issuancePersist, this.outputNextEmission(issuancePersist.getCnpjDoEmissor()));
//            issuancePersist = IssuanceService.nextEmission(issuancePersist, "35");

//        var issuance = IssuanceService.fromInput(input, "34");
            LOG.info("Intial: requestIssuance with entity: {}", issuancePersist);

            var issuanceCreate = this.outputCreateIssuance(issuancePersist);
            LOG.info("issuanceCreate: {}", issuanceCreate);

            var issuanceCreateFinancialData = IssuanceService.addFinancialData(issuanceCreate, this.outputAddFinancialData(issuanceCreate.getIdIntegration(), issuancePersist));
            var attachSIgnedTerm = this.outputAddDocument(issuanceCreateFinancialData.getIdIntegration(), issuancePersist.getConstitutiveTerm().getBucketName(), issuancePersist.getConstitutiveTerm().getKeyName());

            if (this.approvedIssuer(issuanceCreate.getIdIntegration())) {
                LOG.info("issuanceCreate2: {}", issuanceCreate);
                Request submission = submissionCreateOutput.submission(issuanceCreate.getIdIntegration());
                LOG.info("status retornado da submissao: {}", submission);
//                return issuancePersistOutput.update(issuancePersist.getId(), issuanceCreate, attachSIgnedTerm, submission.getStatus());
                return this.update(issuancePersist.getId(), issuanceCreate, attachSIgnedTerm, submission.getStatus());
            }
            this.issuerInput.addIssuer(issuancePersist, input.getIssuer(), input.getLegalRepresentative());
            return this.update(issuancePersist.getId(), issuanceCreate, attachSIgnedTerm, Status.REGISTERED_ISSUER.getStatus());
//            return issuancePersist;
        } catch (Exception e) {
            if (e instanceof ErroNegocioException) {
                LOG.error("error: {}", e);
                LOG.error("envia requisição para SNS e para retry");
                this.addIssuanceNotificationOutput.notificationError(e.getMessage(), this.fromInput(input));
                return Issuance.builder().build();
            }
            if (e instanceof ErroTecnicoException) {
                LOG.error("estoura erro e aguarda o retry");
                throw e;
            }
            LOG.error("error: {}", e);
            throw e;
        }
    }

    private boolean approvedIssuer(String id) {
        var status = "Aprovado";
        ConsultRequest consult = this.consultRequestOutput.consult(id);
        return (consult != null && consult.getEmissor().getStatus().equals(status)) ? true : false;
    }

    private String outputNextEmission(String cnpjDoEmissor) {
        return emissionQuantityGetOutput.nextEmission(cnpjDoEmissor);
    }
    private Issuance outputCreateIssuance(Issuance issuance) {
        return this.emissionOutput.create(issuance);
    }

    private FinancialData outputAddFinancialData(String id, Issuance issuanceCreate) {
        return financialDataOutput.add(id, issuanceCreate.getDadosFinanceiros());
    }

    private AttachSignedTerm outputAddDocument(String id, String bucketName, String keyName) {
        return this.attachSignedTermAddOutput.add(id, bucketName, keyName);
    }

    private Issuance createOrGet(InputRequestIssuance input) {
        var issuance = this.fromInput(input);

        LOG.info("busca o issuance no banco");
        Optional<Issuance> optionalIssuance = Optional.ofNullable(issuancePersistOutput.getByRequestId(issuance.getRequestId()));

        if (optionalIssuance.isPresent()) {
            LOG.info("issuance de id: {} cadastrado", optionalIssuance.get().getId());
            IssuanceService.finalStatusForIntegration(optionalIssuance.get());
            return optionalIssuance.get();
        }

        IssuanceService.addCreateDate(issuance);
        IssuanceService.addRequestId(issuance, issuance.getRequestId());
        IssuanceService.addStatusCreate(issuance);

        return issuancePersistOutput.save(issuance);
    }

    private Issuance fromInput(InputRequestIssuance input) {
        return IssuanceService.fromInput(input);
    }

    private Issuance update(String id, Issuance issuance, AttachSignedTerm attachSignedTerm, String status) {
        return issuancePersistOutput.update(id, issuance, attachSignedTerm, status);
    }

}
