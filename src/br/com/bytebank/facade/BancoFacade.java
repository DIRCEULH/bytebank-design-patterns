package br.com.bytebank.facade;

import br.com.bytebank.chain.Validador;
import br.com.bytebank.chain.ValidadorLimiteSaque;
import br.com.bytebank.chain.ValidadorSaldoSuficiente;
import br.com.bytebank.chain.ValidadorValorPositivo;
import br.com.bytebank.factory.ContaFactory;
import br.com.bytebank.model.Conta;
import br.com.bytebank.model.TipoConta;
import br.com.bytebank.model.TipoTransacao;
import br.com.bytebank.model.Transacao;
import br.com.bytebank.observer.TransacaoListener;
import br.com.bytebank.singleton.RepositorioContas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * FACADE: ponto único de entrada do banco. Quem usa só chama
 * abrirConta, depositar, sacar e transferir; a fachada coordena
 * fábrica, repositório, validações, tarifas e notificações por trás.
 */
public class BancoFacade {

    private final RepositorioContas repositorio = RepositorioContas.getInstancia();
    private final List<TransacaoListener> listeners = new ArrayList<>();
    private final Validador validacoes;

    public BancoFacade() {
        Validador inicio = new ValidadorValorPositivo();
        inicio.ligarCom(new ValidadorLimiteSaque())
              .ligarCom(new ValidadorSaldoSuficiente());
        this.validacoes = inicio;
    }

    public void inscrever(TransacaoListener listener) {
        listeners.add(listener);
    }

    public Conta abrirConta(TipoConta tipo, String titular) {
        Conta conta = ContaFactory.criar(tipo, titular);
        repositorio.salvar(conta);
        return conta;
    }

    public void depositar(String numeroConta, BigDecimal valor) {
        Conta conta = repositorio.buscar(numeroConta);
        validacoes.validar(conta, TipoTransacao.DEPOSITO, valor);
        conta.creditar(valor);
        notificar(new Transacao(TipoTransacao.DEPOSITO, conta, valor, BigDecimal.ZERO));
    }

    public void sacar(String numeroConta, BigDecimal valor) {
        Conta conta = repositorio.buscar(numeroConta);
        validacoes.validar(conta, TipoTransacao.SAQUE, valor);
        BigDecimal tarifa = conta.calcularTarifa(TipoTransacao.SAQUE, valor);
        conta.debitar(valor.add(tarifa));
        notificar(new Transacao(TipoTransacao.SAQUE, conta, valor, tarifa));
    }

    public void transferir(String origem, String destino, BigDecimal valor) {
        Conta contaOrigem = repositorio.buscar(origem);
        Conta contaDestino = repositorio.buscar(destino);
        validacoes.validar(contaOrigem, TipoTransacao.TRANSFERENCIA, valor);

        BigDecimal tarifa = contaOrigem.calcularTarifa(TipoTransacao.TRANSFERENCIA, valor);
        contaOrigem.debitar(valor.add(tarifa));
        contaDestino.creditar(valor);

        notificar(new Transacao(TipoTransacao.TRANSFERENCIA, contaOrigem, valor, tarifa));
    }

    public Conta consultar(String numeroConta) {
        return repositorio.buscar(numeroConta);
    }

    private void notificar(Transacao transacao) {
        for (TransacaoListener listener : listeners) {
            listener.aoConcluir(transacao);
        }
    }
}
