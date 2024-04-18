package br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.mappers;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.TabelaPrice;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.financialdata.FinancialData;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.FinancialDataResponseDTO;

public class FinancialDataMapper {

    public FinancialData fromDto(FinancialDataResponseDTO dto) {
        return FinancialData.builder()
                .custoDaEmissao(dto.getCustoDaEmissao())
                .valorNominalUnitario(dto.getValorNominalUnitario())
                .quantidade(dto.getQuantidade())
                .percentualDosJurosRemuneratorios(dto.getPercentualDosJurosRemuneratorios())
                .percentualDoIndexador(dto.getPercentualDoIndexador())
                .indexador(dto.getIndexador())
                .convencao(dto.getConvencao())
                .localDePagamento(dto.getLocalDePagamento())
                .ufDePagamento(dto.getUfDePagamento())
                .tipoDePagamento(dto.getTipoDePagamento())
                .tabelaPrice(TabelaPrice.builder()
                        .percentualSobre(dto.getTabelaPrice().getPercentualSobre())
                        .periodicidade(dto.getTabelaPrice().getPeriodicidade())
                        .vencimentoDa1aParcela(dto.getTabelaPrice().getVencimentoDa1aParcela())
                        .build())
                .periodo(dto.getPeriodo())
                .build();
    }

}
