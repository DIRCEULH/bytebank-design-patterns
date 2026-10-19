package br.com.bytebank.strategy;

import br.com.bytebank.model.TipoTransacao;

import java.math.BigDecimal;

/**
 * Premium: nenhuma tarifa.
 */
public class TarifaPremium implements TarifaStrategy {

    @Override
    public BigDecimal calcular(TipoTransacao tipo, BigDecimal valor) {
        return BigDecimal.ZERO;
    }
}
