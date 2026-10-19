package br.com.bytebank.factory;

import br.com.bytebank.model.Conta;
import br.com.bytebank.model.TipoConta;
import br.com.bytebank.strategy.TarifaContaCorrente;
import br.com.bytebank.strategy.TarifaPoupanca;
import br.com.bytebank.strategy.TarifaPremium;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * FACTORY: centraliza a criação das contas. Quem pede uma conta informa
 * só o tipo; a fábrica decide a estratégia de tarifa, o limite de saque
 * e gera o número da conta.
 */
public final class ContaFactory {

    private static final AtomicInteger SEQUENCIA = new AtomicInteger(1000);

    private ContaFactory() {
    }

    public static Conta criar(TipoConta tipo, String titular) {
        String numero = String.valueOf(SEQUENCIA.incrementAndGet());

        switch (tipo) {
            case CORRENTE:
                return new Conta(numero, titular, tipo, new TarifaContaCorrente(), new BigDecimal("1000.00"));
            case POUPANCA:
                return new Conta(numero, titular, tipo, new TarifaPoupanca(), new BigDecimal("500.00"));
            case PREMIUM:
                return new Conta(numero, titular, tipo, new TarifaPremium(), new BigDecimal("10000.00"));
            default:
                throw new IllegalArgumentException("Tipo de conta não suportado: " + tipo);
        }
    }
}
