package br.com.celcoin.commercialpaperimporter.emission.framework.output;

import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.IssuanceGetOutput;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuance;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.persist.data.IssuanceData;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.persist.repository.IssuanceRepository;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.mappers.IssuanceMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IssuanceGetAdapter implements IssuanceGetOutput {

    private static final Logger LOG = LogManager.getLogger(IssuanceGetAdapter.class);

    private IssuanceRepository repository;

    private IssuanceMapper mapper;

    public IssuanceGetAdapter() {
        this.repository = new IssuanceRepository();
        this.mapper = new IssuanceMapper();
    }

    @Override
    public List<Issuance> listByStatus(String status) {
        LOG.info("pesquisa no banco com status: {}", status);
        return repository.listByStatus(status)
                .orElse(Arrays.asList(IssuanceData.builder().build())).stream()
                .map(issuance -> mapper.fromData(issuance))
                .collect(Collectors.toList());
    }

}
