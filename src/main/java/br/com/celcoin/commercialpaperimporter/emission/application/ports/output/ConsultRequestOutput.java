package br.com.celcoin.commercialpaperimporter.emission.application.ports.output;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.ConsultRequest;

public interface ConsultRequestOutput {

    ConsultRequest consult(String id);

}
