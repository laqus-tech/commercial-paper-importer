package br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.mappers;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Emissor;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Investor;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Request;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.TabelaPrice;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.financialdata.FinancialData;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.EmissorResponseDTO;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.FinancialDataResponseDTO;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.RequestApiResponseDTO;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.TabelaPriceDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SubmissionMapper {

    private static final Logger LOG = LogManager.getLogger(SubmissionMapper.class);

    public Request fromDto(RequestApiResponseDTO dto) {
        List<Investor> investorList = Optional.ofNullable(dto.getInvestidores()).orElse(Collections.emptyList()).stream().map(investor -> Investor.builder()
                .cpfCnpj(investor.getCnpjCpf())
                .cnpjDoCustodiante(investor.getCnpjDoCustodiante())
                .precoUnitario(investor.getPrecoUnitario())
                .quantidadeSubscrita(investor.getQuantidadeSubscrita())
                .build()).collect(Collectors.toList());

        FinancialDataResponseDTO dadosFinanceirosDTO = Optional.ofNullable(dto.getDadosFinanceiros()).orElse(FinancialDataResponseDTO.builder().build());
        TabelaPriceDTO tabelaPriceDTO = Optional.ofNullable(dadosFinanceirosDTO.getTabelaPrice()).orElse(TabelaPriceDTO.builder().build());
        EmissorResponseDTO emissorResponseDTO = Optional.ofNullable(dto.getEmissor()).orElse(EmissorResponseDTO.builder().build());

        return Request.builder()
                .id(dto.getId())
                .codigo(dto.getCodigo())
                .tipo(dto.getTipo())
                .status(dto.getStatus())
                .numeroDaEmissao(dto.getNumeroDaEmissao())
                .numeroDaSerie(dto.getNumeroDaSerie())
                .dataDaEmissao(dto.getDataDaEmissao())
                .dataDoVencimento(dto.getDataDoVencimento())
                .cnpjDoEscriturador(dto.getCnpjDoEscriturador())
                .localDaEmissao(dto.getLocalDaEmissao())
                .ufDaEmissao(dto.getUfDaEmissao())
                .dadosFinanceiros(FinancialData.builder()
                        .custoDaEmissao(dadosFinanceirosDTO.getCustoDaEmissao())
                        .valorNominalUnitario(dadosFinanceirosDTO.getValorNominalUnitario())
                        .quantidade(dadosFinanceirosDTO.getQuantidade())
                        .percentualDosJurosRemuneratorios(dadosFinanceirosDTO.getPercentualDosJurosRemuneratorios())
                        .percentualDoIndexador(dadosFinanceirosDTO.getPercentualDoIndexador())
                        .indexador(dadosFinanceirosDTO.getIndexador())
                        .convencao(dadosFinanceirosDTO.getConvencao())
                        .localDePagamento(dadosFinanceirosDTO.getLocalDePagamento())
                        .ufDePagamento(dadosFinanceirosDTO.getUfDePagamento())
                        .tipoDePagamento(dadosFinanceirosDTO.getTipoDePagamento())
                        .tabelaPrice(TabelaPrice.builder()
                                .percentualSobre(tabelaPriceDTO.getPercentualSobre())
                                .vencimentoDa1aParcela(tabelaPriceDTO.getVencimentoDa1aParcela())
                                .periodicidade(tabelaPriceDTO.getPeriodicidade())
                                .build())
                        .build())
                .emissor(Emissor.builder()
                        .cnpj(emissorResponseDTO.getCnpj())
                        .build())
                .investidores(investorList)
                .build();
    }

}
