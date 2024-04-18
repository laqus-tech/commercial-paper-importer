package br.com.celcoin.commercialpaperimporter.emission.application.usecases;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuance;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuer;
import br.com.celcoin.commercialpaperimporter.emission.domain.vo.InputIssuer;
import br.com.celcoin.commercialpaperimporter.emission.domain.vo.InputLegalRepresentative;

public interface IssuerUseCase {

    Issuer addIssuer(Issuance issuanceCreate, InputIssuer input, InputLegalRepresentative inputLegalRepresentative);

}
