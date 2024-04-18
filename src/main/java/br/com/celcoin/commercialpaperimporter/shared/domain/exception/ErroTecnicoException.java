package br.com.celcoin.commercialpaperimporter.shared.domain.exception;

public class ErroTecnicoException extends RuntimeException {

    public ErroTecnicoException() {
    }

    public ErroTecnicoException(String message) {
        super(message);
    }

    public ErroTecnicoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErroTecnicoException(Throwable cause) {
        super(cause);
    }

}
