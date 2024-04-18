package br.com.celcoin.commercialpaperimporter.emission.domain.entity;

import br.com.celcoin.commercialpaperimporter.shared.domain.exception.ErroNegocioException;

public enum Status {

    CREATED("Criado"),
    IN_PROGRESS("Em Andamento"),

    AWAITING_SETTLEMENT("Aguardando Liquidação"),

    REGISTERED_ISSUER("Emissor Registrado");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static Status getByStatus(String status) {
        for(Status st : values()){
            if(st.status.equals(status)){
                return st;
            }
        }
        throw new ErroNegocioException("Enum code not exists");
    }

}
