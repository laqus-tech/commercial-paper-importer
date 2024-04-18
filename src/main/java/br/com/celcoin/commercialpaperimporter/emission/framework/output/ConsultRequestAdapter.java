package br.com.celcoin.commercialpaperimporter.emission.framework.output;

import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.ConsultRequestOutput;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.ConsultRequest;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api.ConsultRequestApi;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api.TokenApi;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.mappers.ConsultRequestMapper;

public class ConsultRequestAdapter implements ConsultRequestOutput {

    private ConsultRequestMapper mapper;

    public ConsultRequestAdapter() {
        this.mapper = new ConsultRequestMapper();
    }

    @Override
    public ConsultRequest consult(String id) {
        var token = new TokenApi().getAccessToken();
        return mapper.fromDto(new ConsultRequestApi().consult(token, id));
    }
}
