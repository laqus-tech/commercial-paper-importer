package br.com.celcoin.commercialpaperimporter.emission.domain.vo;

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
public class InputLegalRepresentative {

    private String fullName;

    private String cpf;

    private String email;

    private boolean attorney;

}
