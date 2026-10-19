package br.com.bytebank.strategy;

import br.com.bytebank.model.TipoTransacao;

import java.math.BigDecimal;

/**
 * STRATEGY: cada tipo de conta tem sua própria regra de tarifa.
 * Para criar um novo tipo de cobrança, basta criar outra implementação,
 * sem alterar o código existente (princípio Open/Closed).
 */
public interface TarifaStrategy {

    BigDecimal calcular(TipoTransacao tipo, BigDecimal valor);
}
