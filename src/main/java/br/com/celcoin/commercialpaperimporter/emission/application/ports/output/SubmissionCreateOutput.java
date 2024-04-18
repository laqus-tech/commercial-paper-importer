package br.com.celcoin.commercialpaperimporter.emission.application.ports.output;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Request;

public interface SubmissionCreateOutput {

    Request submission(String id);

}
