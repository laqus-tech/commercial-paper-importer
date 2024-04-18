package br.com.celcoin.commercialpaperimporter.emission.domain.service;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.ConstitutiveTerm;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Investor;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuance;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Status;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.TabelaPrice;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.financialdata.FinancialData;
import br.com.celcoin.commercialpaperimporter.emission.domain.specification.CommercialPaperSpecification;
import br.com.celcoin.commercialpaperimporter.emission.domain.vo.InputRequestIssuance;
import br.com.celcoin.commercialpaperimporter.shared.domain.exception.ErroNegocioException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public interface IssuanceService {

    static final Logger LOG = LogManager.getLogger(IssuanceService.class);

    static Issuance fromInput(InputRequestIssuance input) {
        final List<Investor> investorList = input.getInvestors().stream().map(investor -> Investor.builder()
                .cpfCnpj(investor.getCpfCnpj())
                .cnpjDoCustodiante(investor.getCnpjCustodian())
                .precoUnitario(investor.getUnitPrice())
                .quantidadeSubscrita(investor.getQuantitySubscribed())
                .build()).collect(Collectors.toList());

        var data = Issuance.builder()
                .requestId(input.getRequestId())
                .tipo(input.getType())
//                .numeroDaEmissao(nextEmission)
                .numeroDaSerie(input.getSeriesNumber())
                .dataDaEmissao(input.getEmissionDate())
                .dataDoVencimento(input.getDueDate())
                .localDaEmissao(input.getEmissaoLocation())
                .ufDaEmissao(input.getUfOfTheEmission())
                .cnpjDoEmissor(input.getIssuer().getCnpj())
                .cnpjDoEscriturador(input.getCnpjOfTheBookkeeper())
                .investidores(investorList)
                .constitutiveTerm(ConstitutiveTerm.builder()
                        .bucketName(input.getConstitutiveTerm().getBucketName())
                        .keyName(input.getConstitutiveTerm().getKeyName())
                        .build())
                .build();

        var financialDataBuilder = FinancialData.builder()
                .custoDaEmissao(input.getFinancialData().getCostOfIssuance())
                .valorNominalUnitario(input.getFinancialData().getValueNominalUnitary())
                .quantidade(input.getFinancialData().getAmount())
                .percentualDosJurosRemuneratorios(input.getFinancialData().getPercentageOfInterestRemuneration())
                .percentualDoIndexador(input.getFinancialData().getIndexerPercentage())
                .indexador(input.getFinancialData().getIndexer())
                .convencao(input.getFinancialData().getConvention())
                .localDePagamento(input.getFinancialData().getPaymentPlace())
                .ufDePagamento(input.getFinancialData().getUfPayment())
                .tipoDePagamento(input.getFinancialData().getTypeOfPayment())
                .tabelaPrice(TabelaPrice.builder()
                        .percentualSobre(input.getFinancialData().getTablePrice().getPercentageAbout())
                        .vencimentoDa1aParcela(input.getFinancialData().getTablePrice().getDueDateOfThe1stInstallment())
                        .periodicidade(input.getFinancialData().getTablePrice().getFrequency())
                        .build())
                .periodo(input.getFinancialData().getPeriod())
                .build();

        data.setDadosFinanceiros(financialDataBuilder);

        CommercialPaperSpecification.cnpjDoEmissorIsNotEmtpy().check(data);


        return data;
    }

    static Issuance addFinancialData(Issuance issuanceCreate, FinancialData inputFinancial) {
        issuanceCreate.setDadosFinanceiros(FinancialDataService.fromData(inputFinancial));
        LOG.info("addFinancialData: {}", issuanceCreate);
        return issuanceCreate;
    }

    static Issuance addCreateDate(Issuance issuanceCreate) {
        issuanceCreate.setDataCriacao(LocalDateTime.now());
        return issuanceCreate;
    }

    static Issuance addRequestId(Issuance issuance, String requestId) {
        issuance.setRequestId(requestId);
        return issuance;
    }

    static Issuance addStatusCreate(Issuance issuance) {
        issuance.setStatus(Status.CREATED.name());
        return issuance;
    }

    static Issuance nextEmission(Issuance issuance, String nextEmission) {
        if (nextEmission == null || nextEmission.isEmpty()) {
            issuance.setNumeroDaEmissao("1");
            return issuance;
        }
        issuance.setNumeroDaEmissao(nextEmission);
        return issuance;
    }

    static Issuance updateStatus(Issuance issuance) {
        issuance.setStatus(Status.IN_PROGRESS.name());
        return issuance;
    }

    static Issuance updateStatus(Issuance issuance, String statusSubmission) {
        LOG.info("status retornato: {}", statusSubmission);
        var status = Status.getByStatus(statusSubmission);
        if (status.getStatus().equals(Status.AWAITING_SETTLEMENT.getStatus())){
            issuance.setStatus(Status.AWAITING_SETTLEMENT.name());
            return issuance;
        }
        if (status.getStatus().equals(Status.REGISTERED_ISSUER.getStatus())) {
            issuance.setStatus(Status.REGISTERED_ISSUER.name());
            return issuance;
        }
        issuance.setStatus(Status.IN_PROGRESS.name());
        return issuance;
    }

    static Issuance updateStatusRegisteredIssuer(Issuance issuance) {
        issuance.setStatus(Status.REGISTERED_ISSUER.name());
        return issuance;
    }

    /**
     * Caso o status tenha chegado ao final para instegração é retornado uma exception
     * @param issuance
     */
    static void finalStatusForIntegration(Issuance issuance) throws ErroNegocioException {
        if (issuance.getStatus().equals(Status.AWAITING_SETTLEMENT.name())) {
            throw new ErroNegocioException("Integração chegou ao fim");
        }
    }

}
