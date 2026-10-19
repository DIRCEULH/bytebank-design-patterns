package br.com.bytebank.observer;

import br.com.bytebank.model.Transacao;

/**
 * Simula o envio de um e-mail ao titular.
 */
public class NotificadorEmail implements TransacaoListener {

    @Override
    public void aoConcluir(Transacao transacao) {
        System.out.printf("  [E-mail] Olá, %s! %s de R$ %s realizado. Saldo atual: R$ %s%n",
                transacao.getConta().getTitular(),
                transacao.getTipo(),
                transacao.getValor(),
                transacao.getConta().getSaldo());
    }
}
