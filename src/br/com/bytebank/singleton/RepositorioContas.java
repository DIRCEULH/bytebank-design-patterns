package br.com.bytebank.singleton;

import br.com.bytebank.exception.OperacaoInvalidaException;
import br.com.bytebank.model.Conta;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * SINGLETON: existe um único repositório de contas em toda a aplicação.
 * Usa a técnica "lazy holder": a instância só é criada no primeiro uso,
 * e a JVM garante que isso é thread-safe sem precisar de synchronized.
 */
public final class RepositorioContas {

    private final Map<String, Conta> contas = new LinkedHashMap<>();

    private RepositorioContas() {
    }

    private static class Holder {
        private static final RepositorioContas INSTANCIA = new RepositorioContas();
    }

    public static RepositorioContas getInstancia() {
        return Holder.INSTANCIA;
    }

    public void salvar(Conta conta) {
        contas.put(conta.getNumero(), conta);
    }

    public Conta buscar(String numero) {
        Conta conta = contas.get(numero);
        if (conta == null) {
            throw new OperacaoInvalidaException("Erro: conta " + numero + " nao encontrada");
        }
        return conta;
    }

    public Collection<Conta> listar() {
        return Collections.unmodifiableCollection(contas.values());
    }
}
