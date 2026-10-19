# ByteBank – Padrões de Projeto com Java Puro

Projeto final do módulo de **Padrões de Projeto (Design Patterns)** da DIO.

O ByteBank é um banco digital simplificado: abre contas de tipos diferentes, faz depósitos, saques e transferências, cobra tarifas, valida as operações e avisa o cliente a cada movimentação. Cada uma dessas responsabilidades foi resolvida com um padrão de projeto diferente.

## Padrões aplicados

| Padrão | Onde está | Problema que resolve |
|---|---|---|
| **Singleton** | `singleton/RepositorioContas` | Garante um único repositório de contas em toda a aplicação. Usa a técnica *lazy holder*, que é thread-safe sem `synchronized`. |
| **Factory** | `factory/ContaFactory` | Centraliza a criação das contas: a partir do tipo (`CORRENTE`, `POUPANCA`, `PREMIUM`), define tarifa, limite de saque e número. |
| **Strategy** | `strategy/TarifaStrategy` e implementações | Cada tipo de conta tem sua regra de tarifa. Uma regra nova é só mais uma classe, sem `if/else` espalhado. |
| **Chain of Responsibility** | `chain/Validador` e validadores | As validações (valor positivo → limite por operação → saldo suficiente) formam uma corrente. Uma regra nova é só mais um elo. |
| **Observer** | `observer/TransacaoListener` e ouvintes | E-mail ao cliente, alerta de segurança e extrato são avisados a cada transação, sem o banco conhecer nenhum deles. |
| **Facade** | `facade/BancoFacade` | Ponto único de entrada (`abrirConta`, `depositar`, `sacar`, `transferir`) que esconde a coordenação entre todos os outros padrões. |

### Regras de tarifa (Strategy)

| Conta | Saque | Transferência | Limite por operação |
|---|---|---|---|
| Corrente | R$ 2,00 | R$ 2,00 | R$ 1.000,00 |
| Poupança | isento | 1% do valor | R$ 500,00 |
| Premium | isento | isento | R$ 10.000,00 |

### Como os padrões se conectam

```
Main
 └── BancoFacade (Facade)
      ├── ContaFactory (Factory) ── cria Conta com uma TarifaStrategy (Strategy)
      ├── RepositorioContas (Singleton)
      ├── Validador → Validador → Validador (Chain of Responsibility)
      └── List<TransacaoListener> (Observer)
            ├── NotificadorEmail
            ├── AlertaValorAlto
            └── Extrato
```

## Princípios SOLID presentes

- **SRP**: cada classe tem uma responsabilidade (validar, calcular tarifa, notificar, guardar contas).
- **OCP**: novas tarifas, validações e notificações entram como classes novas, sem alterar as existentes.
- **DIP**: `Conta` depende da interface `TarifaStrategy` e `BancoFacade` depende de `TransacaoListener`, não de implementações concretas.

## Estrutura

```
src/br/com/bytebank
├── Main.java                  # demonstração completa
├── chain/                     # Chain of Responsibility
├── exception/                 # exceção de negócio
├── facade/                    # Facade
├── factory/                   # Factory
├── model/                     # Conta, Transacao e enums
├── observer/                  # Observer
├── singleton/                 # Singleton
└── strategy/                  # Strategy
```

## Como executar

Requer **Java 11 ou superior**. Não usa nenhuma biblioteca externa.

Pela IDE (IntelliJ, Eclipse ou VS Code): abra a pasta do projeto, marque `src` como pasta de código-fonte e rode a classe `br.com.bytebank.Main`.

Pelo terminal, na raiz do projeto:

```bash
# Linux / macOS
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out br.com.bytebank.Main
```

```powershell
# Windows (PowerShell)
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out br.com.bytebank.Main
```

## Saída da demonstração (trecho)

```
=== 3. Saques com tarifa diferente por tipo de conta (Strategy) ===
  [E-mail] Olá, Ana! SAQUE de R$ 100.00 realizado. Saldo atual: R$ 1398.00
  [E-mail] Olá, Bruno! SAQUE de R$ 100.00 realizado. Saldo atual: R$ 700.00
  [E-mail] Olá, Carla! SAQUE de R$ 600.00 realizado. Saldo atual: R$ 19400.00
  [Segurança] Alerta: saída de R$ 600.00 na conta 1003. Foi você?

=== 5. Operações recusadas (Chain of Responsibility) ===
  Erro: valor invalido
  Erro: valor acima do limite por operacao (R$ 500.00)
  Erro: saldo insuficiente
  Erro: conta 9999 nao encontrada
```

## Possíveis evoluções

- Transformar em uma API REST com Spring Boot, mantendo a `BancoFacade` como camada de serviço.
- Persistir as contas em banco de dados (H2/PostgreSQL) no lugar do repositório em memória.
- Adicionar testes unitários com JUnit para cada estratégia e validador.

## Referências

- [Lab Padrões de Projeto com Java Puro – DIO](https://github.com/digitalinnovationone/lab-padroes-projeto-java)
- [Refactoring Guru – Design Patterns](https://refactoring.guru/pt-br/design-patterns)
