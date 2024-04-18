package br.com.celcoin.commercialpaperimporter.shared.domain.exception;

public class ErroNegocioException extends RuntimeException {

    public ErroNegocioException() {
    }

    public ErroNegocioException(String message) {
        super(message);
    }

    public ErroNegocioException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErroNegocioException(Throwable cause) {
        super(cause);
    }

}
