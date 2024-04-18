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
@EqualsAndHashCode(of = "numeroDaEmissao")
@AllArgsConstructor
@NoArgsConstructor
public class ConsultRequest {

    private String id;

    private String status;

    private Emissor emissor;

}
