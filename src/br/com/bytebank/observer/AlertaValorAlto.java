package br.com.bytebank.observer;

import br.com.bytebank.model.TipoTransacao;
import br.com.bytebank.model.Transacao;

import java.math.BigDecimal;

/**
 * Emite um alerta de segurança para saídas acima de um valor de referência.
 */
public class AlertaValorAlto implements TransacaoListener {

    private final BigDecimal valorReferencia;

    public AlertaValorAlto(BigDecimal valorReferencia) {
        this.valorReferencia = valorReferencia;
    }

    @Override
    public void aoConcluir(Transacao transacao) {
        boolean saida = transacao.getTipo() != TipoTransacao.DEPOSITO;
        if (saida && transacao.getValor().compareTo(valorReferencia) >= 0) {
            System.out.printf("  [Segurança] Alerta: saída de R$ %s na conta %s. Foi você?%n",
                    transacao.getValor(), transacao.getConta().getNumero());
        }
    }
}
