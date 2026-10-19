package br.com.bytebank.strategy;

import br.com.bytebank.model.TipoTransacao;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Poupança: saque isento, transferência cobra 1% do valor.
 */
public class TarifaPoupanca implements TarifaStrategy {

    private static final BigDecimal PERCENTUAL = new BigDecimal("0.01");

    @Override
    public BigDecimal calcular(TipoTransacao tipo, BigDecimal valor) {
        if (tipo == TipoTransacao.TRANSFERENCIA) {
            return valor.multiply(PERCENTUAL).setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }
}
