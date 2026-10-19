package br.com.bytebank;

import br.com.bytebank.exception.OperacaoInvalidaException;
import br.com.bytebank.facade.BancoFacade;
import br.com.bytebank.model.Conta;
import br.com.bytebank.model.TipoConta;
import br.com.bytebank.observer.AlertaValorAlto;
import br.com.bytebank.observer.Extrato;
import br.com.bytebank.observer.NotificadorEmail;
import br.com.bytebank.singleton.RepositorioContas;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        BancoFacade banco = new BancoFacade();

        // Observer: inscrevendo os interessados nas transações
        Extrato extrato = new Extrato();
        banco.inscrever(extrato);
        banco.inscrever(new NotificadorEmail());
        banco.inscrever(new AlertaValorAlto(new BigDecimal("500.00")));

        titulo("1. Abrindo contas (Factory + Singleton)");
        Conta ana = banco.abrirConta(TipoConta.CORRENTE, "Ana");
        Conta bruno = banco.abrirConta(TipoConta.POUPANCA, "Bruno");
        Conta carla = banco.abrirConta(TipoConta.PREMIUM, "Carla");
        RepositorioContas.getInstancia().listar().forEach(c -> System.out.println("  " + c));

        titulo("2. Depósitos (Facade + Observer)");
        executar(() -> banco.depositar(ana.getNumero(), new BigDecimal("1500.00")));
        executar(() -> banco.depositar(bruno.getNumero(), new BigDecimal("800.00")));
        executar(() -> banco.depositar(carla.getNumero(), new BigDecimal("20000.00")));

        titulo("3. Saques com tarifa diferente por tipo de conta (Strategy)");
        executar(() -> banco.sacar(ana.getNumero(), new BigDecimal("100.00")));   // corrente: + R$ 2,00
        executar(() -> banco.sacar(bruno.getNumero(), new BigDecimal("100.00"))); // poupança: isento
        executar(() -> banco.sacar(carla.getNumero(), new BigDecimal("600.00"))); // premium: isento + alerta

        titulo("4. Transferências");
        executar(() -> banco.transferir(bruno.getNumero(), ana.getNumero(), new BigDecimal("200.00"))); // 1%

        titulo("5. Operações recusadas (Chain of Responsibility)");
        executar(() -> banco.depositar(ana.getNumero(), new BigDecimal("-50.00")));
        executar(() -> banco.sacar(bruno.getNumero(), new BigDecimal("600.00")));  // acima do limite
        executar(() -> banco.sacar(bruno.getNumero(), new BigDecimal("499.00")));  // saldo insuficiente
        executar(() -> banco.sacar("9999", new BigDecimal("10.00")));

        titulo("6. Saldos finais");
        RepositorioContas.getInstancia().listar().forEach(c -> System.out.println("  " + c));

        titulo("7. Extrato (Observer guardando o histórico)");
        extrato.getHistorico().forEach(t -> System.out.println("  " + t));
    }

    private static void executar(Runnable operacao) {
        try {
            operacao.run();
        } catch (OperacaoInvalidaException e) {
            System.out.println("  " + e.getMessage());
        }
    }

    private static void titulo(String texto) {
        System.out.println();
        System.out.println("=== " + texto + " ===");
    }
}
