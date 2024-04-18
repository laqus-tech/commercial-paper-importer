package br.com.celcoin.commercialpaperimporter.emission.framework.output;

import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.RegisterLegalRepresentativeOutput;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.LegalRepresentative;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api.RegisterLegalRepresentativeApi;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api.TokenApi;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.mappers.RegisterLegalRepresentativeMapper;

public class RegisterLegalRepresentativeAdapter implements RegisterLegalRepresentativeOutput {

    private RegisterLegalRepresentativeMapper mapper;

    public RegisterLegalRepresentativeAdapter() {
        this.mapper = new RegisterLegalRepresentativeMapper();
    }

    @Override
    public LegalRepresentative create(String id, LegalRepresentative legalRepresentative) {
        var token = new TokenApi().getAccessToken();
        return mapper.fromDto(new RegisterLegalRepresentativeApi().postLegalRepresentative(token, id, mapper.toDto(legalRepresentative)));

    }

}
