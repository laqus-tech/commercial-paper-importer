package br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.mappers;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.LegalRepresentative;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.LegalRepresentativeRequestDTO;

public class RegisterLegalRepresentativeMapper {
    public LegalRepresentativeRequestDTO toDto(LegalRepresentative legalRepresentative) {
        return LegalRepresentativeRequestDTO.builder()
                .nomeCompleto(legalRepresentative.getFullName())
                .cpf(legalRepresentative.getCpf())
                .email(legalRepresentative.getEmail())
                .procurador(legalRepresentative.isAttorney())
                .build();
    }

    public LegalRepresentative fromDto(LegalRepresentativeRequestDTO legalRepresentativeRequestDTO) {
        return LegalRepresentative.builder()
                .fullName(legalRepresentativeRequestDTO.getNomeCompleto())
                .cpf(legalRepresentativeRequestDTO.getCpf())
                .email(legalRepresentativeRequestDTO.getEmail())
                .attorney(legalRepresentativeRequestDTO.isProcurador())
                .build();
    }
}
