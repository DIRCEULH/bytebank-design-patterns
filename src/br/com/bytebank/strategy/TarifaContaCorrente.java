package br.com.bytebank.strategy;

import br.com.bytebank.model.TipoTransacao;

import java.math.BigDecimal;

/**
 * Conta corrente: tarifa fixa de R$ 2,00 em saques e transferências.
 */
public class TarifaContaCorrente implements TarifaStrategy {

    private static final BigDecimal TARIFA_FIXA = new BigDecimal("2.00");

    @Override
    public BigDecimal calcular(TipoTransacao tipo, BigDecimal valor) {
        if (tipo == TipoTransacao.DEPOSITO) {
            return BigDecimal.ZERO;
        }
        return TARIFA_FIXA;
    }
}
