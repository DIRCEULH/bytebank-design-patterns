package br.com.bytebank.observer;

import br.com.bytebank.model.Transacao;

/**
 * OBSERVER: interessados em transações se inscrevem e são avisados
 * a cada operação concluída, sem que o banco precise conhecê-los.
 */
public interface TransacaoListener {

    void aoConcluir(Transacao transacao);
}
