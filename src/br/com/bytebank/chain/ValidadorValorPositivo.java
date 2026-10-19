package br.com.bytebank.chain;

import br.com.bytebank.exception.OperacaoInvalidaException;
import br.com.bytebank.model.Conta;
import br.com.bytebank.model.TipoTransacao;

import java.math.BigDecimal;

public class ValidadorValorPositivo extends Validador {

    @Override
    protected void verificar(Conta conta, TipoTransacao tipo, BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new OperacaoInvalidaException("Erro: valor invalido");
        }
    }
}
