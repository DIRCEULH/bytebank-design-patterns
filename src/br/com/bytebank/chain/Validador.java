package br.com.bytebank.chain;

import br.com.bytebank.model.Conta;
import br.com.bytebank.model.TipoTransacao;

import java.math.BigDecimal;

/**
 * CHAIN OF RESPONSIBILITY: cada validador confere uma regra e,
 * se estiver tudo certo, passa a operação para o próximo da corrente.
 * Uma regra nova vira um elo novo, sem mexer nas existentes.
 */
public abstract class Validador {

    private Validador proximo;

    /**
     * Encadeia o próximo validador e o devolve, permitindo montar
     * a corrente assim: a.ligarCom(b).ligarCom(c).
     */
    public Validador ligarCom(Validador proximo) {
        this.proximo = proximo;
        return proximo;
    }

    public void validar(Conta conta, TipoTransacao tipo, BigDecimal valor) {
        verificar(conta, tipo, valor);
        if (proximo != null) {
            proximo.validar(conta, tipo, valor);
        }
    }

    protected abstract void verificar(Conta conta, TipoTransacao tipo, BigDecimal valor);
}
