package br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Getter
@ToString
@Jacksonized
@JsonIgnoreProperties
public class BankDataRequestDTO {

    private String codigoDoBanco;

    private String agencia;

    private String digitoDaAgencia;

    private String contaCorrente;

    private String digitoDaConta;

}
