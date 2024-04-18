package br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder(toBuilder = true)
@Getter
@ToString
@Jacksonized
@JsonIgnoreProperties
public class IssuerRequestDTO {

    private String tipoDeEmpresa;

    private String funcao;

    private String cnpj;

    private String razaoSocial;

    private String atividadePrincipal;

    private Integer faturamentoMedioMensal12Meses;

    private AddressRequestDTO endereco;

    private BankDataRequestDTO dadosBancarios;

    private List<PhoneRequestDTO> telefones;

}
