package br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.mappers;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.EmissionQuantity;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.EmissionQuantityResponseDTO;

public class EmissionQuantityMapper {

    public EmissionQuantity fromDto(EmissionQuantityResponseDTO dto) {

        return EmissionQuantity.builder()
                .emissaoAtual(dto.getEmissaoAtual())
                .proximaEmissao(dto.getProximaEmissao())
                .build();
    }

}
