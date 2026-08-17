# 5 Exercícios de Testes Unitários

Projeto Maven pronto para rodar, feito para você praticar o que viu nos vídeos:

- *Descomplicando Testes Unitários! (Guia Rápido Para Iniciantes Na Programação)*
- *COMEÇANDO com TESTES UNITÁRIOS no JAVA SPRING | Junit + Mockito*

**Formato:** o código a ser testado já está escrito e funcionando. O que falta são **os testes** — você escreve. Os arquivos de teste já existem com o esqueleto, o `@DisplayName` de cada cenário e dicas nos comentários.

Nível: iniciante. Exercícios 1 e 2 são JUnit 5 puro (fundamentos), 3 a 5 são Spring + Mockito.

---

## Como rodar

Requisitos: **JDK 17+** e **Maven** (ou abra a pasta no IntelliJ / VS Code, que ele resolve tudo sozinho).

```bash
mvn test
```

Na primeira execução o Maven baixa as dependências (JUnit 5, Mockito, AssertJ — tudo vem do `spring-boot-starter-test`).

**Ao rodar pela primeira vez, todos os testes vão falhar.** Isso é proposital: cada teste começa com um `fail("TODO: implementar este teste")`. Apague o `fail` e escreva o teste. Quando o `mvn test` ficar verde, você terminou.

Para rodar só um exercício:

```bash
mvn test -Dtest=CalculadoraDeDescontoTest
```

---

## Mapa do projeto

```
src/main/java/br/com/estudos/testes/
├── fundamentos/       Exercícios 1 e 2 — classes puras, sem framework
├── dominio/           Cliente, Pedido, StatusDoPedido
├── repository/        Interfaces (ClienteRepository, PedidoRepository)  <- viram @Mock
├── client/            EstoqueClient, NotificadorDeEmail                 <- viram @Mock
├── service/           ClienteService, PedidoService  <- as classes sob teste dos ex. 3-5
└── infra/             Implementações "de verdade" (em memória)
                       — existem só para você ver o que os mocks substituem

src/test/java/...      <- É AQUI QUE VOCÊ TRABALHA
gabarito/              <- Soluções completas. Só abra depois de tentar.
```

> **Não altere as classes de `src/main`.** Se um teste não passa, o problema está no teste (ou no seu entendimento da regra) — é exatamente isso que o exercício quer treinar.

---

## Exercício 1 — Fundamentos: AAA, `assertEquals` e `assertThrows`

**Arquivo:** `src/test/java/br/com/estudos/testes/fundamentos/CalculadoraDeDescontoTest.java`
**Classe sob teste:** `CalculadoraDeDesconto`

Regra de negócio: 10 unidades ou mais → 10% de desconto; 5 ou mais → 5%; abaixo disso → sem desconto. Valor negativo e quantidade menor que 1 lançam `IllegalArgumentException`.

Escreva 5 testes:

| # | Cenário | O que exercita |
|---|---------|----------------|
| 1.1 | 3 unidades a R$ 100 → total 300,00 | `assertEquals` com delta, padrão AAA |
| 1.2 | 5 unidades a R$ 100 → total 475,00 | teste de **fronteira** da regra |
| 1.3 | 10 unidades a R$ 100 → total 900,00 | segunda faixa |
| 1.4 | valor unitário negativo → `IllegalArgumentException` | `assertThrows` + conferir a mensagem |
| 1.5 | quantidade zero → `IllegalArgumentException` | validação de entrada |

**Critério de aceite:** os 5 testes passam, cada um tem um `@DisplayName` que descreve o comportamento (não o método), e todo `assertEquals` de `double` usa delta.

*Bônus:* junte 1.1–1.3 num `@ParameterizedTest` com `@CsvSource`.

---

## Exercício 2 — Estado, `@BeforeEach` e independência entre testes

**Arquivo:** `src/test/java/br/com/estudos/testes/fundamentos/CarrinhoDeComprasTest.java`
**Classe sob teste:** `CarrinhoDeCompras`

Diferente do exercício 1, esta classe **guarda estado**. Antes de escrever os testes, crie o campo `carrinho` e um método `@BeforeEach` que o recria a cada teste — é isso que garante que um teste não contamine o outro.

| # | Cenário | O que exercita |
|---|---------|----------------|
| 2.1 | carrinho novo está vazio e com total zero | `assertTrue`, estado inicial |
| 2.2 | 2 camisas de R$ 199,90 → total 399,80 | preço × quantidade |
| 2.3 | remover item existente → `true` e sai do carrinho | verificar retorno **e** estado |
| 2.4 | remover item inexistente → `false`, carrinho intacto | caminho triste sem exceção |
| 2.5 | adicionar item nulo → `IllegalArgumentException` | validação |

**Critério de aceite:** existe um `@BeforeEach`; os testes passam em qualquer ordem e passam também quando rodados individualmente.

*Bônus:* teste o limite de 20 itens (o 21º lança `IllegalStateException`).

---

## Exercício 3 — Primeiro Mockito: `@Mock`, `@InjectMocks`, `when`

**Arquivo:** `src/test/java/br/com/estudos/testes/service/ClienteServiceBuscaTest.java`
**Método sob teste:** `ClienteService.buscarPorId(Long)`

Montagem da classe de teste (faça antes dos testes):

```java
@ExtendWith(MockitoExtension.class)
class ClienteServiceBuscaTest {
    @Mock private ClienteRepository clienteRepository;
    @Mock private NotificadorDeEmail notificadorDeEmail;
    @InjectMocks private ClienteService clienteService;
}
```

| # | Cenário | O que exercita |
|---|---------|----------------|
| 3.1 | cliente existe → devolve o cliente | `when(...).thenReturn(Optional.of(...))` |
| 3.2 | cliente não existe → `ClienteNaoEncontradoException` | `Optional.empty()` + `assertThrows` |
| 3.3 | a mensagem da exceção contém o id | inspecionar a exceção capturada |
| 3.4 | chama o repositório exatamente 1 vez | `verify(..., times(1))` |
| 3.5 | uma busca nunca dispara e-mail | `verify(..., never())` |

**Critério de aceite:** nenhum `@SpringBootTest` e nenhum banco — teste unitário de service não sobe contexto Spring. Os 5 testes rodam em milissegundos.

---

## Exercício 4 — Efeitos colaterais: `verify`, `never` e `ArgumentCaptor`

**Arquivo:** `src/test/java/br/com/estudos/testes/service/ClienteServiceCadastroTest.java`
**Método sob teste:** `ClienteService.cadastrar(Cliente)`

Regras: e-mail duplicado é recusado; o nome é gravado sem espaços nas pontas; o cliente nasce ativo; depois de salvar, um e-mail de boas-vindas é enviado.

| # | Cenário | O que exercita |
|---|---------|----------------|
| 4.1 | cadastro válido salva e devolve o cliente | `thenAnswer(i -> i.getArgument(0))` |
| 4.2 | e-mail duplicado → `EmailJaCadastradoException` | caminho triste |
| 4.3 | duplicado não salva **e** não notifica | `verify(..., never())` em dois mocks |
| 4.4 | nome `"  Ana Souza  "` chega ao `save` como `"Ana Souza"` | **`ArgumentCaptor`** |
| 4.5 | envia e-mail com assunto "Bem-vindo!" para o e-mail do cliente | `verify` com `eq(...)` e `anyString()` |

**Pegadinha clássica:** `save()` num mock devolve `null` por padrão, e este service usa o retorno do `save`. Você precisa ensinar o mock a devolver algo.

**Outra pegadinha:** se usar matcher (`eq`, `any`) em um argumento, use em **todos** — senão o Mockito lança `InvalidUseOfMatchersException`.

**Critério de aceite:** o teste 4.4 usa `ArgumentCaptor` (não vale testar o objeto que você passou — tem que ser o que chegou no repositório).

---

## Exercício 5 — Orquestração: três dependências, caminho feliz e caminhos tristes

**Arquivo:** `src/test/java/br/com/estudos/testes/service/PedidoServiceTest.java`
**Método sob teste:** `PedidoService.confirmar(Long)`

O fluxo: busca o pedido → valida o status → consulta o estoque → baixa o estoque → salva como `CONFIRMADO` → notifica o cliente.

| # | Cenário | O que exercita |
|---|---------|----------------|
| 5.1 | com estoque → status vira `CONFIRMADO` e é salvo | caminho feliz com 3 mocks |
| 5.2 | baixa o estoque com o SKU e a quantidade do pedido | `verify` com argumentos exatos |
| 5.3 | pedido inexistente → `PedidoNaoEncontradoException` | `Optional.empty()` |
| 5.4 | sem estoque → exceção **e** nada de baixa, save ou e-mail | `never()` em 3 mocks |
| 5.5 | pedido `CANCELADO` → `IllegalStateException` | guarda de estado |

O teste 5.4 é o mais valioso da lista: ele garante que uma falha no meio do fluxo não deixa efeito colateral pela metade.

**Dica de armadilha:** o `MockitoExtension` roda em modo *strict*. Se você colocar `when(...)` dentro do `@BeforeEach` e algum teste não usar aquele stub, a suíte falha com `UnnecessaryStubbingException`. Deixe no `@BeforeEach` só a montagem dos objetos.

**Critério de aceite:** os 5 testes passam sem `@SpringBootTest`, e o 5.4 verifica os três `never()`.

*Bônus:* use `InOrder` para provar que o estoque é baixado **antes** do pedido ser salvo.

---

## Checklist de autoavaliação

Depois de terminar, releia seus testes e confira:

- [ ] Cada teste verifica **um** comportamento e tem nome que descreve esse comportamento
- [ ] Os três blocos (Arrange / Act / Assert) são visíveis
- [ ] Nenhum teste depende da ordem de execução ou do resultado de outro
- [ ] Nenhum teste toca banco, rede ou arquivo
- [ ] Você testou os caminhos tristes, não só o feliz
- [ ] Você testou as fronteiras das regras (4 e 5, 9 e 10, zero, nulo, vazio)
- [ ] Você testou o que **não** deve acontecer (`never()`)
- [ ] A suíte inteira roda em menos de 2 segundos

---

## Gabarito

A pasta `gabarito/` tem a solução completa de cada exercício, com comentários explicando as escolhas. Para comparar, copie o arquivo por cima do seu (ou abra lado a lado):

```bash
cp gabarito/CalculadoraDeDescontoTest.java src/test/java/br/com/estudos/testes/fundamentos/
cp gabarito/CarrinhoDeComprasTest.java      src/test/java/br/com/estudos/testes/fundamentos/
cp gabarito/ClienteServiceBuscaTest.java    src/test/java/br/com/estudos/testes/service/
cp gabarito/ClienteServiceCadastroTest.java src/test/java/br/com/estudos/testes/service/
cp gabarito/PedidoServiceTest.java          src/test/java/br/com/estudos/testes/service/
mvn test
```

Existe mais de uma resposta certa. Se o seu teste passa, é independente e verifica o comportamento descrito, ele está bom — mesmo que esteja escrito diferente do gabarito.
