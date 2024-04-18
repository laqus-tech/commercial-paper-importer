package br.com.celcoin.commercialpaperimporter.emission.framework.output;

import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.SubmissionCreateOutput;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Request;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api.SubmissionApi;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api.TokenApi;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.mappers.SubmissionMapper;

public class SubmissionCreateAdapter implements SubmissionCreateOutput {

    private SubmissionMapper mapper;

    public SubmissionCreateAdapter() {
        this.mapper = new SubmissionMapper();
    }

    @Override
    public Request submission(String id) {
        var token = new TokenApi().getAccessToken();
        return mapper.fromDto(new SubmissionApi().submissionIssuance(token, id));
    }

}
