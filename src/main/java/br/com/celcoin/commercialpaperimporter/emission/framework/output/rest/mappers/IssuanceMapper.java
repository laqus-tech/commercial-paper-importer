package br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.mappers;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.AttachSignedTerm;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.ConstitutiveTerm;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Emissor;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuance;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Investor;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.TabelaPrice;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.financialdata.FinancialData;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.persist.data.IssuanceData;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.RequestIssuanceApiResponseDTO;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class IssuanceMapper {

    private static final Logger LOG = LogManager.getLogger(IssuanceMapper.class);
    private static final String FORMATTER = "yyyy-MM-dd HH:mm:ss";

    public Issuance fromDto(RequestIssuanceApiResponseDTO dto) {
        final List<Investor> investidores = dto.getInvestidores().stream().map(investidor -> Investor.builder()
                .cpfCnpj(investidor.getCnpjCpf())
                .cnpjDoCustodiante(investidor.getCnpjDoCustodiante())
                .precoUnitario(investidor.getPrecoUnitario())
                .quantidadeSubscrita(investidor.getQuantidadeSubscrita()).build()).collect(Collectors.toList());

        var issuaceBuilder = Issuance.builder()
//                .id(dto.getId())
                .idIntegration(dto.getId())
                .tipo(dto.getTipo())
                .status(dto.getStatus())
                .numeroDaEmissao(String.valueOf(dto.getNumeroDaEmissao()))
                .numeroDaSerie(String.valueOf(dto.getNumeroDaSerie()))
                .dataDaEmissao(dto.getDataDaEmissao())
                .dataDoVencimento(dto.getDataDoVencimento())
                .cnpjDoEscriturador(dto.getCnpjDoEscriturador())
                .localDaEmissao(dto.getLocalDaEmissao())
                .ufDaEmissao(dto.getUfDaEmissao())
                .dadosFinanceiros(FinancialData.builder()
                        .custoDaEmissao(dto.getDadosFinanceiros().getCustoDaEmissao())
                        .valorNominalUnitario(dto.getDadosFinanceiros().getValorNominalUnitario())
                        .quantidade(dto.getDadosFinanceiros().getQuantidade())
                        .percentualDosJurosRemuneratorios(dto.getDadosFinanceiros().getPercentualDosJurosRemuneratorios())
                        .percentualDoIndexador(dto.getDadosFinanceiros().getPercentualDoIndexador())
                        .indexador(dto.getDadosFinanceiros().getIndexador())
                        .convencao(dto.getDadosFinanceiros().getConvencao())
                        .localDePagamento(dto.getDadosFinanceiros().getLocalDePagamento())
                        .ufDePagamento(dto.getDadosFinanceiros().getUfDePagamento())
                        .tipoDePagamento(dto.getDadosFinanceiros().getTipoDePagamento())
                        .tabelaPrice(TabelaPrice.builder()
//                                .percentualSobre(dto.getDadosFinanceiros().getTabelaPrice().getPercentualSobre())
//                                .vencimentoDa1aParcela(dto.getDadosFinanceiros().getTabelaPrice().getVencimentoDa1aParcela())
//                                .periodicidade(dto.getDadosFinanceiros().getTabelaPrice().getPeriodicidade())
                                .build())
                        .periodo(dto.getDadosFinanceiros().getPeriodo())
                        .build())
                .emissor(Emissor.builder().cnpj(dto.getEmissor().getCnpj()).build())
                .investidores(investidores)
                .build();

        LOG.info("fromDTO: {}", issuaceBuilder);
        return issuaceBuilder;
    }

    public Issuance fromData(IssuanceData data) {
        IssuanceData issuanceData = Optional.ofNullable(data).orElse(IssuanceData.builder().build());
        if (issuanceData == null || issuanceData.getId() == null || issuanceData.getId().isEmpty()){
            LOG.info("fromData: {}", issuanceData);
            return null;
        }

        Gson gson = new Gson();
        Investor[] investorArray = gson.fromJson(data.getInvestidoresJson(), Investor[].class);
        List<Investor> investorList = Arrays.asList(investorArray);

        var dataCriacao = (data.getCreateDate() != null) ? LocalDateTime.parse(data.getCreateDate(), this.getDateTimeFormatter()) : null;

        var issuanceBuilder = Issuance.builder()
                .id(data.getId())
                .numeroDaEmissao(data.getNumeroDaEmissao())
                .requestId(data.getRequestId())
                .numeroDaSerie(data.getNumeroDaSerie())
                .tipo(data.getTipo())
                .idIntegration(data.getIdItegration())
                .status(data.getStatus())
                .dataDaEmissao(data.getDataDaEmissao())
                .dataDoVencimento(data.getDataDoVencimento())
                .localDaEmissao(data.getLocalDaEmissao())
                .ufDaEmissao(data.getUfDaEmissao())
                .dadosFinanceiros(FinancialData.builder()
                        .custoDaEmissao(Double.valueOf(data.getCustoDaEmissao()))
                        .valorNominalUnitario(Double.valueOf(data.getValorNominalUnitario()))
                        .quantidade(Integer.valueOf(data.getQuantidade()))
                        .percentualDosJurosRemuneratorios(Double.valueOf(data.getPercentualDosJurosRemuneratorios()))
                        .percentualDoIndexador(Double.valueOf(data.getPercentualDoIndexador()))
                        .indexador(data.getIndexador())
                        .convencao(data.getConvencao())
                        .localDePagamento(data.getLocalDePagamento())
                        .ufDePagamento(data.getUfDePagamento())
                        .tipoDePagamento(data.getTipoDePagamento())
                        .tabelaPrice(TabelaPrice.builder()
                                .percentualSobre(data.getPercentualSobre())
                                .vencimentoDa1aParcela(data.getVencimentoDa1aParcela())
                                .periodicidade(data.getPeriodicidade())
                                .build())
                        .periodo(data.getPeriodo())
                        .build())
                .cnpjDoEmissor(data.getCnpjDoEmissor())
                .cnpjDoEscriturador(data.getCnpjDoEscriturador())
                .dataCriacao(dataCriacao)
                .investidores(investorList)
                .constitutiveTerm(ConstitutiveTerm.builder()
                        .bucketName(data.getBucketName())
                        .keyName(data.getKeyName())
                        .build())
                .build();

        LOG.info("fromData: {}", issuanceBuilder);
        return issuanceBuilder;
    }

    public IssuanceData toData(String id, Issuance entity) {
        return IssuanceData.builder()
                .id((id != null && !id.isEmpty()) ? id : entity.getId())
                .numeroDaEmissao(String.valueOf(entity.getNumeroDaEmissao()))
                .requestId(entity.getRequestId())
                .numeroDaSerie(String.valueOf(entity.getNumeroDaSerie()))
                .tipo(entity.getTipo())
                .status(entity.getStatus())
                .dataDaEmissao(entity.getDataDaEmissao())
                .dataDoVencimento(entity.getDataDoVencimento())
                .localDaEmissao(entity.getLocalDaEmissao())
                .ufDaEmissao(entity.getUfDaEmissao())
                .custoDaEmissao(entity.getDadosFinanceiros().getCustoDaEmissao().toString())
                .valorNominalUnitario(entity.getDadosFinanceiros().getValorNominalUnitario().toString())
                .quantidade(entity.getDadosFinanceiros().getQuantidade().toString())
                .percentualDosJurosRemuneratorios(entity.getDadosFinanceiros().getPercentualDosJurosRemuneratorios().toString())
                .percentualDoIndexador(entity.getDadosFinanceiros().getPercentualDoIndexador().toString())
                .indexador(entity.getDadosFinanceiros().getIndexador())
                .convencao(entity.getDadosFinanceiros().getConvencao())
                .localDePagamento(entity.getDadosFinanceiros().getLocalDePagamento())
                .ufDePagamento(entity.getDadosFinanceiros().getUfDePagamento())
                .tipoDePagamento(entity.getDadosFinanceiros().getTipoDePagamento())
                .percentualSobre(entity.getDadosFinanceiros().getTabelaPrice().getPercentualSobre())
                .vencimentoDa1aParcela(entity.getDadosFinanceiros().getTabelaPrice().getVencimentoDa1aParcela())
                .periodicidade(entity.getDadosFinanceiros().getTabelaPrice().getPeriodicidade())
                .periodo(entity.getDadosFinanceiros().getPeriodo())
                .cnpjDoEmissor(entity.getCnpjDoEmissor())
                .cnpjDoEscriturador(entity.getCnpjDoEscriturador())
                .updateDate(LocalDateTime.now().format(this.getDateTimeFormatter()))
                .bucketName(entity.getConstitutiveTerm().getBucketName())
                .keyName(entity.getConstitutiveTerm().getKeyName())
                .investidoresJson(this.investorsJson(entity))
                .createDate(entity.getDataCriacao().format(this.getDateTimeFormatter()))
                .build();
    }

    public IssuanceData toData(String id, Issuance entity, Issuance entityNew, AttachSignedTerm attachSignedTermEntity) {
        var issuanceDataBuilder = IssuanceData.builder()
                .id((id != null && !id.isEmpty()) ? id : entity.getId())
                .numeroDaEmissao(String.valueOf(entityNew.getNumeroDaEmissao()))
                .requestId(entity.getRequestId())
                .numeroDaSerie(String.valueOf(entity.getNumeroDaSerie()))
                .idItegration(entityNew.getIdIntegration())
                .tipo(entity.getTipo())
                .status(entity.getStatus())
                .dataDaEmissao(entity.getDataDaEmissao())
                .dataDoVencimento(entity.getDataDoVencimento())
                .localDaEmissao(entity.getLocalDaEmissao())
                .ufDaEmissao(entity.getUfDaEmissao())
                .custoDaEmissao(entity.getDadosFinanceiros().getCustoDaEmissao().toString())
                .valorNominalUnitario(entity.getDadosFinanceiros().getValorNominalUnitario().toString())
                .quantidade(entity.getDadosFinanceiros().getQuantidade().toString())
                .percentualDosJurosRemuneratorios(entity.getDadosFinanceiros().getPercentualDosJurosRemuneratorios().toString())
                .percentualDoIndexador(entity.getDadosFinanceiros().getPercentualDoIndexador().toString())
                .indexador(entity.getDadosFinanceiros().getIndexador())
                .convencao(entity.getDadosFinanceiros().getConvencao())
                .localDePagamento(entity.getDadosFinanceiros().getLocalDePagamento())
                .ufDePagamento(entity.getDadosFinanceiros().getUfDePagamento())
                .tipoDePagamento(entity.getDadosFinanceiros().getTipoDePagamento())
                .percentualSobre(entity.getDadosFinanceiros().getTabelaPrice().getPercentualSobre())
                .vencimentoDa1aParcela(entity.getDadosFinanceiros().getTabelaPrice().getVencimentoDa1aParcela())
                .periodicidade(entity.getDadosFinanceiros().getTabelaPrice().getPeriodicidade())
                .periodo(entity.getDadosFinanceiros().getPeriodo())
                .cnpjDoEmissor(entity.getCnpjDoEmissor())
                .cnpjDoEscriturador(entity.getCnpjDoEscriturador())
                .updateDate(LocalDateTime.now().format(this.getDateTimeFormatter()))
                .idAttachSignedTerm(attachSignedTermEntity.getId())
                .link(attachSignedTermEntity.getLink())
                .bucketName(entity.getConstitutiveTerm().getBucketName())
                .keyName(entity.getConstitutiveTerm().getKeyName())
                .investidoresJson(this.investorsJson(entity))
                .createDate(entity.getDataCriacao().format(this.getDateTimeFormatter()))
                .build();

//        IssuanceData issuanceDataBuilder = null;
//
//        if (entity.getDataCriacao() != null) {
//            issuanceDataBuilder = IssuanceData.builder()
//                    .id((id != null && !id.isEmpty()) ? id : entity.getId())
//                    .numeroDaEmissao(String.valueOf(entity.getNumeroDaEmissao()))
//                    .requestId(entity.getRequestId())
//                    .numeroDaSerie(String.valueOf(entity.getNumeroDaSerie()))
//                    .tipo(entity.getTipo())
//                    .status(entity.getStatus())
//                    .dataDaEmissao(entity.getDataDaEmissao())
//                    .dataDoVencimento(entity.getDataDoVencimento())
//                    .localDaEmissao(entity.getLocalDaEmissao())
//                    .ufDaEmissao(entity.getUfDaEmissao())
//                    .custoDaEmissao(entity.getDadosFinanceiros().getCustoDaEmissao().toString())
//                    .valorNominalUnitario(entity.getDadosFinanceiros().getValorNominalUnitario().toString())
//                    .quantidade(entity.getDadosFinanceiros().getQuantidade().toString())
//                    .percentualDosJurosRemuneratorios(entity.getDadosFinanceiros().getPercentualDosJurosRemuneratorios().toString())
//                    .percentualDoIndexador(entity.getDadosFinanceiros().getPercentualDoIndexador().toString())
//                    .indexador(entity.getDadosFinanceiros().getIndexador())
//                    .convencao(entity.getDadosFinanceiros().getConvencao())
//                    .localDePagamento(entity.getDadosFinanceiros().getLocalDePagamento())
//                    .ufDePagamento(entity.getDadosFinanceiros().getUfDePagamento())
//                    .tipoDePagamento(entity.getDadosFinanceiros().getTipoDePagamento())
//                    .percentualSobre(entity.getDadosFinanceiros().getTabelaPrice().getPercentualSobre())
//                    .vencimentoDa1aParcela(entity.getDadosFinanceiros().getTabelaPrice().getVencimentoDa1aParcela())
//                    .periodicidade(entity.getDadosFinanceiros().getTabelaPrice().getPeriodicidade())
//                    .periodo(entity.getDadosFinanceiros().getPeriodo())
//                    .cnpjDoEmissor(entity.getCnpjDoEmissor())
//                    .cnpjDoEscriturador(entity.getCnpjDoEscriturador())
//                    .createDate(entity.getDataCriacao().format(this.getDateTimeFormatter()))
//                    .idAttachSignedTerm(attachSignedTermEntity.getId())
//                    .link(attachSignedTermEntity.getLink())
//                    .build();
//        } else {
//            issuanceDataBuilder = IssuanceData.builder()
//                    .id((id != null && !id.isEmpty()) ? id : entity.getId())
//                    .numeroDaEmissao(String.valueOf(entity.getNumeroDaEmissao()))
//                    .requestId(entity.getRequestId())
//                    .numeroDaSerie(String.valueOf(entity.getNumeroDaSerie()))
//                    .tipo(entity.getTipo())
//                    .status(entity.getStatus())
//                    .dataDaEmissao(entity.getDataDaEmissao())
//                    .dataDoVencimento(entity.getDataDoVencimento())
//                    .localDaEmissao(entity.getLocalDaEmissao())
//                    .ufDaEmissao(entity.getUfDaEmissao())
//                    .custoDaEmissao(entity.getDadosFinanceiros().getCustoDaEmissao().toString())
//                    .valorNominalUnitario(entity.getDadosFinanceiros().getValorNominalUnitario().toString())
//                    .quantidade(entity.getDadosFinanceiros().getQuantidade().toString())
//                    .percentualDosJurosRemuneratorios(entity.getDadosFinanceiros().getPercentualDosJurosRemuneratorios().toString())
//                    .percentualDoIndexador(entity.getDadosFinanceiros().getPercentualDoIndexador().toString())
//                    .indexador(entity.getDadosFinanceiros().getIndexador())
//                    .convencao(entity.getDadosFinanceiros().getConvencao())
//                    .localDePagamento(entity.getDadosFinanceiros().getLocalDePagamento())
//                    .ufDePagamento(entity.getDadosFinanceiros().getUfDePagamento())
//                    .tipoDePagamento(entity.getDadosFinanceiros().getTipoDePagamento())
//                    .percentualSobre(entity.getDadosFinanceiros().getTabelaPrice().getPercentualSobre())
//                    .vencimentoDa1aParcela(entity.getDadosFinanceiros().getTabelaPrice().getVencimentoDa1aParcela())
//                    .periodicidade(entity.getDadosFinanceiros().getTabelaPrice().getPeriodicidade())
//                    .periodo(entity.getDadosFinanceiros().getPeriodo())
//                    .cnpjDoEmissor(entity.getCnpjDoEmissor())
//                    .cnpjDoEscriturador(entity.getCnpjDoEscriturador())
//                    .updateDate(LocalDateTime.now().format(this.getDateTimeFormatter()))
//                    .idAttachSignedTerm(attachSignedTermEntity.getId())
//                    .link(attachSignedTermEntity.getLink())
//                    .build();
//        }

        LOG.info("toData: {}", issuanceDataBuilder);
        return issuanceDataBuilder;
    }

    public IssuanceData toData(Issuance entity) {
        return IssuanceData.builder()
//                .id(entity.getId())
                .numeroDaEmissao(String.valueOf(entity.getNumeroDaEmissao()))
                .numeroDaSerie(String.valueOf(entity.getNumeroDaSerie()))
                .requestId(entity.getRequestId())
                .tipo(entity.getTipo())
                .status(entity.getStatus())
                .dataDaEmissao(entity.getDataDaEmissao())
                .dataDoVencimento(entity.getDataDoVencimento())
                .localDaEmissao(entity.getLocalDaEmissao())
                .ufDaEmissao(entity.getUfDaEmissao())
                .custoDaEmissao(entity.getDadosFinanceiros().getCustoDaEmissao().toString())
                .valorNominalUnitario(entity.getDadosFinanceiros().getValorNominalUnitario().toString())
                .quantidade(entity.getDadosFinanceiros().getQuantidade().toString())
                .percentualDosJurosRemuneratorios(entity.getDadosFinanceiros().getPercentualDosJurosRemuneratorios().toString())
                .percentualDoIndexador(entity.getDadosFinanceiros().getPercentualDoIndexador().toString())
                .indexador(entity.getDadosFinanceiros().getIndexador())
                .convencao(entity.getDadosFinanceiros().getConvencao())
                .localDePagamento(entity.getDadosFinanceiros().getLocalDePagamento())
                .ufDePagamento(entity.getDadosFinanceiros().getUfDePagamento())
                .tipoDePagamento(entity.getDadosFinanceiros().getTipoDePagamento())
                .percentualSobre(entity.getDadosFinanceiros().getTabelaPrice().getPercentualSobre())
                .vencimentoDa1aParcela(entity.getDadosFinanceiros().getTabelaPrice().getVencimentoDa1aParcela())
                .periodicidade(entity.getDadosFinanceiros().getTabelaPrice().getPeriodicidade())
                .periodo(entity.getDadosFinanceiros().getPeriodo())
                .cnpjDoEmissor(entity.getCnpjDoEmissor())
                .cnpjDoEscriturador(entity.getCnpjDoEscriturador())
                .createDate(entity.getDataCriacao().format(this.getDateTimeFormatter()))
                .bucketName(entity.getConstitutiveTerm().getBucketName())
                .keyName(entity.getConstitutiveTerm().getKeyName())
                .investidoresJson(this.investorsJson(entity))
                .build();
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern(FORMATTER);
    }

    private String investorsJson(Issuance entity) {
        var gson = new Gson();
        var investorsJson = gson.toJson(entity.getInvestidores());
        return investorsJson;
    }

}
