package br.com.celcoin.commercialpaperimporter.emission.application.usecases;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuance;
import br.com.celcoin.commercialpaperimporter.emission.domain.vo.InputRequestIssuance;

public interface IssuanceUseCase {

//    Void processIssuance();

    Issuance requestIssuance(InputRequestIssuance input);

}
