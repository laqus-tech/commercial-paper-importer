package br.com.celcoin.commercialpaperimporter.emission.framework.output;

import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.IssuerCreateOutput;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuer;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api.RegisterIssuerApi;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api.TokenApi;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.mappers.IssuerMapper;

public class IssuerCreateAdapter implements IssuerCreateOutput {

    private IssuerMapper mapper;

    public IssuerCreateAdapter() {
        this.mapper = new IssuerMapper();
    }

    @Override
    public Issuer create(Issuer issuer) {
        var token = new TokenApi().getAccessToken();
        return mapper.fromDto(new RegisterIssuerApi().postIssuer(token, mapper.toDto(issuer)));
    }
}
