package br.com.bytebank.model;

import br.com.bytebank.strategy.TarifaStrategy;

import java.math.BigDecimal;

/**
 * Conta bancária. A regra de tarifa é injetada como uma Strategy,
 * então a classe Conta não precisa saber como cada tipo cobra.
 */
public class Conta {

    private final String numero;
    private final String titular;
    private final TipoConta tipo;
    private final TarifaStrategy tarifaStrategy;
    private final BigDecimal limiteSaque;
    private BigDecimal saldo;

    public Conta(String numero, String titular, TipoConta tipo,
                 TarifaStrategy tarifaStrategy, BigDecimal limiteSaque) {
        this.numero = numero;
        this.titular = titular;
        this.tipo = tipo;
        this.tarifaStrategy = tarifaStrategy;
        this.limiteSaque = limiteSaque;
        this.saldo = BigDecimal.ZERO;
    }

    public void creditar(BigDecimal valor) {
        saldo = saldo.add(valor);
    }

    public void debitar(BigDecimal valor) {
        saldo = saldo.subtract(valor);
    }

    public BigDecimal calcularTarifa(TipoTransacao tipoTransacao, BigDecimal valor) {
        return tarifaStrategy.calcular(tipoTransacao, valor);
    }

    public String getNumero() {
        return numero;
    }

    public String getTitular() {
        return titular;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public BigDecimal getLimiteSaque() {
        return limiteSaque;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    @Override
    public String toString() {
        return String.format("Conta %s (%s) - %s - saldo R$ %.2f", numero, tipo, titular, saldo);
    }
}
