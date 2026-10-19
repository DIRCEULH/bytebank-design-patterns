package br.com.bytebank.chain;

import br.com.bytebank.exception.OperacaoInvalidaException;
import br.com.bytebank.model.Conta;
import br.com.bytebank.model.TipoTransacao;

import java.math.BigDecimal;

/**
 * Impede saques e transferências acima do limite por operação da conta.
 */
public class ValidadorLimiteSaque extends Validador {

    @Override
    protected void verificar(Conta conta, TipoTransacao tipo, BigDecimal valor) {
        if (tipo == TipoTransacao.DEPOSITO) {
            return;
        }
        if (valor.compareTo(conta.getLimiteSaque()) > 0) {
            throw new OperacaoInvalidaException(
                    "Erro: valor acima do limite por operacao (R$ " + conta.getLimiteSaque() + ")");
        }
    }
}
