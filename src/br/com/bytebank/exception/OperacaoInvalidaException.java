package br.com.bytebank.exception;

/**
 * Exceção de negócio lançada quando uma operação bancária é recusada.
 */
public class OperacaoInvalidaException extends RuntimeException {

    public OperacaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
