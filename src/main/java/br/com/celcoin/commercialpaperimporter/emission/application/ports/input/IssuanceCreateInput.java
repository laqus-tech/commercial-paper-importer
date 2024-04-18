package br.com.celcoin.commercialpaperimporter.emission.application.ports.input;

import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.IssuancePersistOutput;
import br.com.celcoin.commercialpaperimporter.emission.application.usecases.IssuanceCreateUseCase;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuance;
import br.com.celcoin.commercialpaperimporter.emission.domain.service.IssuanceService;
import br.com.celcoin.commercialpaperimporter.emission.domain.vo.InputRequestIssuance;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.IssuancePersistAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class IssuanceCreateInput implements IssuanceCreateUseCase {

    private static final Logger LOG = LogManager.getLogger(SubmissionInput.class);

    private IssuancePersistOutput issuancePersistOutput;

    public IssuanceCreateInput(){
        this.issuancePersistOutput = new IssuancePersistAdapter();
    }

    @Override
    public Issuance createIssuance(InputRequestIssuance input) {
        var issuance = IssuanceService.fromInput(input);

        LOG.info("busca o issuance no banco");
        Optional<Issuance> optionalIssuance = Optional.ofNullable(issuancePersistOutput.getByRequestId(issuance.getRequestId()));

        LOG.info("issuance retornado do banco: {}", optionalIssuance.isPresent());

        if (optionalIssuance.isPresent()) {
            LOG.info("issuance de id: {} cadastrado", optionalIssuance.get().getId());
            return optionalIssuance.get();
        }

        IssuanceService.addCreateDate(issuance);
        IssuanceService.addRequestId(issuance, issuance.getRequestId());
        IssuanceService.addStatusCreate(issuance);

        return issuancePersistOutput.save(issuance);
    }

}
