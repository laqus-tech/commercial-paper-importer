package br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.mappers;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.ConsultRequest;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Emissor;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.ConsultRequestResponseDTO;

public class ConsultRequestMapper {

    public ConsultRequest fromDto(ConsultRequestResponseDTO dto) {
        return ConsultRequest.builder()
                .id(dto.getId())
                .status(dto.getStatus())
                .emissor(Emissor.builder()
                        .cnpj(dto.getEmissor().getCnpj())
                        .status(dto.getEmissor().getStatus())
                        .build())
                .build();
    }

}
