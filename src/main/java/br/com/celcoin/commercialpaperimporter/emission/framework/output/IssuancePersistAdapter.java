package br.com.celcoin.commercialpaperimporter.emission.framework.output;

import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.IssuancePersistOutput;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.AttachSignedTerm;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuance;
import br.com.celcoin.commercialpaperimporter.emission.domain.service.IssuanceService;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.persist.repository.IssuanceRepository;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.mappers.IssuanceMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IssuancePersistAdapter implements IssuancePersistOutput {

    private static final Logger LOG = LogManager.getLogger(IssuancePersistAdapter.class);

    private IssuanceMapper mapper;
    private IssuanceRepository repository;

    public IssuancePersistAdapter() {
        this.mapper = new IssuanceMapper();
        this.repository = new IssuanceRepository();
    }

    @Override
    public Issuance update(String id, Issuance issuance, AttachSignedTerm attachSignedTerm, String updateStatus) {
        LOG.info("id: {} e objeto para atualizar: {}", id, issuance);
        Issuance persistIssuance = IssuanceService.updateStatus(this.get(id, issuance.getCnpjDoEscriturador()), updateStatus);
        LOG.info("objeto encontrado no banco: {}", persistIssuance);

        return mapper.fromData(repository.merge(mapper.toData(persistIssuance.getId(), persistIssuance, issuance, attachSignedTerm)));
    }

    @Override
    public Issuance save(Issuance issuance) {
        return mapper.fromData(repository.create(mapper.toData(issuance)));
    }

    @Override
    public Issuance updateStatus(Issuance issuance, String status) {
        var issuanceRetrieved = repository.get(issuance.getId(), issuance.getCnpjDoEscriturador());
        if (issuanceRetrieved != null) {
            issuanceRetrieved.setStatus(status);
            repository.merge(issuanceRetrieved);
            return mapper.fromData(issuanceRetrieved);
        }
        return issuance;
    }

    @Override
    public Issuance updateStatusRegisteredIssuer(String id, String cnpjDoEscriturador) {
        Issuance issuance = IssuanceService.updateStatusRegisteredIssuer(this.get(id, cnpjDoEscriturador));
        LOG.info("objeto update no banco: {}", issuance);
        return mapper.fromData(repository.merge(mapper.toData(issuance.getId(), issuance)));
    }

    @Override
    public Issuance get(String id, String cnpjDoEscriturador) {
        return mapper.fromData(repository.get(id, cnpjDoEscriturador));
    }

    @Override
    public Issuance getByRequestId(String requestId) {
        return mapper.fromData(repository.getByRequestId(requestId));
    }

}
