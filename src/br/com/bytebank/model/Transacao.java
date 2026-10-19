package br.com.bytebank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Registro imutável de uma operação concluída.
 */
public final class Transacao {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final TipoTransacao tipo;
    private final Conta conta;
    private final BigDecimal valor;
    private final BigDecimal tarifa;
    private final LocalDateTime dataHora;

    public Transacao(TipoTransacao tipo, Conta conta, BigDecimal valor, BigDecimal tarifa) {
        this.tipo = tipo;
        this.conta = conta;
        this.valor = valor;
        this.tarifa = tarifa;
        this.dataHora = LocalDateTime.now();
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public Conta getConta() {
        return conta;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public BigDecimal getTarifa() {
        return tarifa;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s de R$ %.2f na conta %s (tarifa R$ %.2f)",
                dataHora.format(FORMATO), tipo, valor, conta.getNumero(), tarifa);
    }
}
