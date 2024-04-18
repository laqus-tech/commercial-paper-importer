package br.com.celcoin.commercialpaperimporter.emission.framework.output;

import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.EmissionQuantityGetOutput;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api.EmissionQuantityApi;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api.TokenApi;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.mappers.EmissionQuantityMapper;

public class EmissionQuantityGetAdapter implements EmissionQuantityGetOutput {
    private EmissionQuantityMapper mapper;
    public EmissionQuantityGetAdapter() {
        this.mapper = new EmissionQuantityMapper();
    }
    @Override
    public String nextEmission(String cnpjEmissor) {
        var token = new TokenApi().getAccessToken();
        return mapper.fromDto(new EmissionQuantityApi().postRequestIssuance(token, cnpjEmissor)).getProximaEmissao();
    }
}
