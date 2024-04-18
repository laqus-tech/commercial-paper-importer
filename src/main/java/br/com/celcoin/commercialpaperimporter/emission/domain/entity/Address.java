package br.com.celcoin.commercialpaperimporter.emission.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String zipCode;

    private String address;

    private String number;

    private String complement;

    private String neighborhood;

    private String city;

    private String uf;

}
