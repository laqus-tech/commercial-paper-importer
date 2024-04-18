package br.com.celcoin.commercialpaperimporter.emission.domain.service;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.LegalRepresentative;
import br.com.celcoin.commercialpaperimporter.emission.domain.vo.InputLegalRepresentative;

public interface LegalRepresentativeService {

    static LegalRepresentative fromInput(InputLegalRepresentative input) {
        return LegalRepresentative.builder()
                .fullName(input.getFullName())
                .cpf(input.getCpf())
                .email(input.getEmail())
                .attorney(input.isAttorney())
                .build();

    }

}
