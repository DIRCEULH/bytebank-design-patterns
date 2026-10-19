package br.com.bytebank.chain;

import br.com.bytebank.exception.OperacaoInvalidaException;
import br.com.bytebank.model.Conta;
import br.com.bytebank.model.TipoTransacao;

import java.math.BigDecimal;

/**
 * Confere se o saldo cobre o valor mais a tarifa da operação.
 */
public class ValidadorSaldoSuficiente extends Validador {

    @Override
    protected void verificar(Conta conta, TipoTransacao tipo, BigDecimal valor) {
        if (tipo == TipoTransacao.DEPOSITO) {
            return;
        }
        BigDecimal total = valor.add(conta.calcularTarifa(tipo, valor));
        if (total.compareTo(conta.getSaldo()) > 0) {
            throw new OperacaoInvalidaException("Erro: saldo insuficiente");
        }
    }
}
