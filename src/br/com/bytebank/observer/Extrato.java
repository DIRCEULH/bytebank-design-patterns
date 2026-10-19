package br.com.bytebank.observer;

import br.com.bytebank.model.Transacao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Guarda o histórico de transações para emitir extrato.
 */
public class Extrato implements TransacaoListener {

    private final List<Transacao> historico = new ArrayList<>();

    @Override
    public void aoConcluir(Transacao transacao) {
        historico.add(transacao);
    }

    public List<Transacao> getHistorico() {
        return Collections.unmodifiableList(historico);
    }
}
