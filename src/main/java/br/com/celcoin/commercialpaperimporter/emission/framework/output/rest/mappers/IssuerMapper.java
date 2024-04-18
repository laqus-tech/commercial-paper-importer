package br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.mappers;

import br.com.celcoin.commercialpaperimporter.emission.domain.entity.Issuer;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.AddressRequestDTO;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.BankDataRequestDTO;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.IssuerRequestDTO;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.IssuerRespondeDTO;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto.PhoneRequestDTO;

import java.util.stream.Collectors;

public class IssuerMapper {

    public Issuer fromDto(IssuerRespondeDTO dto) {
        return Issuer.builder()
                .id(dto.getId())
                .build();
    }

    public IssuerRequestDTO toDto(Issuer issuer) {
        return IssuerRequestDTO.builder()
                .tipoDeEmpresa(issuer.getTypeOfCompany())
                .funcao(issuer.getFunction())
                .cnpj(issuer.getCnpj())
                .razaoSocial(issuer.getCorporateName())
                .atividadePrincipal(issuer.getMainActivity())
                .faturamentoMedioMensal12Meses(issuer.getRevenueAverageMonthly12Months())
                .endereco(AddressRequestDTO.builder()
                        .cep(issuer.getAddress().getZipCode())
                        .rua(issuer.getAddress().getAddress())
                        .numero(issuer.getAddress().getNumber())
                        .complemento(issuer.getAddress().getComplement())
                        .bairro(issuer.getAddress().getNeighborhood())
                        .cidade(issuer.getAddress().getCity())
                        .uf(issuer.getAddress().getUf())
                        .build())
                .dadosBancarios(BankDataRequestDTO.builder()
                        .codigoDoBanco(issuer.getBankData().getBankCode())
                        .agencia(issuer.getBankData().getAgency())
                        .digitoDaAgencia(issuer.getBankData().getAgencyDigit())
                        .contaCorrente(issuer.getBankData().getCurrentAccount())
                        .digitoDaConta(issuer.getBankData().getDigitOfAccount())
                        .build())
                .telefones(issuer.getPhones().stream().map(phone -> PhoneRequestDTO.builder()
                        .numero(phone.getNumber())
                        .tipo(phone.getType())
                        .build()).collect(Collectors.toList()))
                .build();
    }

}
