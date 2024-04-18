package br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.mappers;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.AttachSignedTerm;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.AttachSignedTermResponseDTO;

public class AttachSignedTermMapper {

    public AttachSignedTerm fromDto(AttachSignedTermResponseDTO dto) {
        return AttachSignedTerm.builder()
                .id(dto.getId())
                .link(dto.getLink())
                .dataDaCriacao(dto.getDataDaCriacao())
                .dataDaAtualizacao(dto.getDataDaAtualizacao())
                .build();
    }

}
