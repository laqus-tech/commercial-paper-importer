package br.com.celcoin.commercialpaperimporter.emission.framework.output;

import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.IssuanceCreateOutput;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuance;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api.RequestIssuanceApi;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api.TokenApi;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.mappers.IssuanceMapper;

public class IssuanceCreateAdapter implements IssuanceCreateOutput {

    private IssuanceMapper mapper;

    public IssuanceCreateAdapter() {
        this.mapper = new IssuanceMapper();
    }

    @Override
    public Issuance create(Issuance createCommercialPaper) {
        var token = new TokenApi().getAccessToken();
        return mapper.fromDto(new RequestIssuanceApi().postRequestIssuance(token, createCommercialPaper));
    }

}
