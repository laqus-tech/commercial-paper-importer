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
public class AddressRequestDTO {

    private String cep;

    private String rua;

    private String numero;

    private String complemento;

    private String bairro;

    private String cidade;

    private String uf;

}
