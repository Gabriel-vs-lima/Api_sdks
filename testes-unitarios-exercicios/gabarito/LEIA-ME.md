# Gabarito

Cinco arquivos, um por exercício. Tente antes de olhar — o valor do exercício está na tentativa, não na leitura.

| Arquivo | Exercício | Copiar para |
|---------|-----------|-------------|
| `CalculadoraDeDescontoTest.java` | 1 | `src/test/java/br/com/estudos/testes/fundamentos/` |
| `CarrinhoDeComprasTest.java` | 2 | `src/test/java/br/com/estudos/testes/fundamentos/` |
| `ClienteServiceBuscaTest.java` | 3 | `src/test/java/br/com/estudos/testes/service/` |
| `ClienteServiceCadastroTest.java` | 4 | `src/test/java/br/com/estudos/testes/service/` |
| `PedidoServiceTest.java` | 5 | `src/test/java/br/com/estudos/testes/service/` |

Os arquivos do gabarito trazem também os testes marcados como **BÔNUS** no enunciado (`@ParameterizedTest`, limite do carrinho, `InOrder`).

## As sete coisas que o gabarito quer te ensinar

1. **AAA sempre.** Arrange, Act, Assert — três blocos visíveis. Se o Act não cabe numa linha, provavelmente o método faz coisa demais.

2. **Nome de teste é documentação.** `deveRecusarEmailDuplicado()` diz o que o sistema faz. `testCadastrar2()` não diz nada. Use `@DisplayName` para a frase completa.

3. **Um mock devolve vazio por padrão.** `null`, `false`, `0`, `Optional.empty()`. Se o código sob teste usa o retorno de um método do mock, você precisa ensiná-lo com `when(...)`.

4. **`assert` verifica valor; `verify` verifica interação.** Quando o método não devolve nada de útil mas provoca efeitos (salvar, enviar, publicar), `verify` é a única forma de testar.

5. **`never()` vale tanto quanto `times(1)`.** Boa parte dos bugs de produção é algo que aconteceu quando não devia: cobrou duas vezes, enviou e-mail num fluxo que falhou, gravou pela metade.

6. **`ArgumentCaptor` responde "com o quê meu mock foi chamado?"** Use quando o objeto passado sofre transformação dentro do service (trim, cálculo, mudança de status).

7. **Modo strict do Mockito é seu aliado.** `UnnecessaryStubbingException` está te avisando que você configurou um cenário que o código nem chegou a usar — quase sempre o teste está testando coisa diferente do que você imagina.

## Erros mais comuns de quem está começando

| Sintoma | Causa | Correção |
|---------|-------|----------|
| `NullPointerException` no service | `save()` do mock devolveu `null` | `when(repo.save(any())).thenAnswer(i -> i.getArgument(0))` |
| `InvalidUseOfMatchersException` | misturou valor cru com matcher no mesmo `verify` | use `eq("x")` em vez de `"x"` |
| `UnnecessaryStubbingException` | `when(...)` no `@BeforeEach` não usado por algum teste | mova o stub para dentro do teste que precisa dele |
| Teste passa sozinho, falha na suíte | estado compartilhado entre testes | recrie o objeto no `@BeforeEach` |
| `assertEquals` falha com `399.79999...` | comparação de `double` sem delta | `assertEquals(399.80, total, 0.001)` |
| `@InjectMocks` deixa campo nulo | dependência sem `@Mock` declarado | declare um `@Mock` para cada parâmetro do construtor |
