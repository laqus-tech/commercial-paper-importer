package br.com.celcoin.commercialpaperimporter.emission.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "cnpj")
@AllArgsConstructor
@NoArgsConstructor
public class LegalRepresentative {

    private String fullName;

    private String cpf;

    private String email;

    private boolean attorney;

}
