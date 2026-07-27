# 📚 Roteiro de Estudos: JavaScript para Engenharia de Prompt (1 Semana)

Este roteiro foi estruturado para ser executado em **7 dias (3 horas por dia / 21 horas no total)**, focando exatamente nos conceitos de JavaScript necessários para aplicar a engenharia de prompt de forma técnica e programática via Node.js e consumo de APIs de LLM (como o Google Gemini).

---

## 📺 Recomendação de Material Didático (Gratuito)

Para acompanhar a parte teórica e prática durante a semana, utilize estes materiais em português no YouTube:

1. **[Curso de JavaScript Completo - Curso em Vídeo (Prof. Gustavo Guanabara)](https://www.youtube.com/playlist?list=PLntvgXM11X6pi7mW0O4ZmfUI1xDGiG8d2)**
   * **Foco:** Manipulação de strings, objetos, arrays e sintaxe básica do JS.
2. **[Playlist de JavaScript Assíncrono e Avançado](https://www.youtube.com/results?search_query=javascript+assincrono+async+await+promises)**
   * **Foco:** Promises, `async/await`, consumo de APIs REST e tratamento de JSON.

---

## 🗓️ Roteiro Diário de Estudos

### 🔹 Dia 1: Template Literals e Manipulação de Prompts Dinâmicos
* **Carga horária:** 3h
* **Objetivo:** Dominar a construção de strings dinâmicas sem quebrar a formatação dos prompts.
* **Tópicos:**
  * Concatenação tradicional vs. **Template Literals** (uso de `` ` ``).
  * Interpolação de variáveis e expressões com `${variavel}`.
  * Preservação de quebras de linha e recuos para criar prompts estruturados (Few-Shot, System Instructions).
  * Métodos essenciais de string: `.trim()`, `.replace()`, `.includes()`, `.slice()`.

---

### 🔹 Dia 2: Objetos, Desestruturação e Manipulação de Dados
* **Carga horária:** 3h
* **Objetivo:** Aprender a montar os payloads das requisições e extrair propriedades específicas das respostas da API.
* **Tópicos:**
  * Estrutura de Objetos e Arrays em JS (`{ chave: valor }`).
  * **Destructuring** (Desestruturação): extraindo dados de objetos aninhados (`const { text } = response`).
  * Operador Spread (`...`) para mesclar contextos ou adicionar novos elementos ao histórico do prompt.

---

### 🔹 Dia 3: JSON (Serialize, Deserialize e Parsing para LLMs)
* **Carga horária:** 3h
* **Objetivo:** Lidar com o envio e recebimento de dados estruturados em JSON na engenharia de prompt.
* **Tópicos:**
  * Diferença entre Objeto JS e String JSON.
  * `JSON.stringify()`: convertendo objetos de contexto para texto inteligível pela IA.
  * `JSON.parse()`: convertendo a resposta em texto da IA de volta para um objeto JS manipulável.
  * Tratamento do erro clássico: sanitizar respostas quando a IA retorna o JSON cercado por blocos de markdown (```json ... ```).

---

### 🔹 Dia 4: Assincronicidade — Promises e Ciclo de Vida de Requisições
* **Carga horária:** 3h
* **Objetivo:** Compreender o comportamento assíncrono do JS ao realizar chamadas para APIs de IA.
* **Tópicos:**
  * Conceito prático do *Event Loop* do Node.js.
  * O que são **Promises** (estados: *pending*, *fulfilled*, *rejected*).
  * Encadeamento com `.then()`, `.catch()` e `.finally()`.

---

### 🔹 Dia 5: Async / Await e Tratamento de Erros
* **Carga horária:** 3h
* **Objetivo:** Escrever código limpo, moderno e resiliente para integrar com a API do Gemini.
* **Tópicos:**
  * Sintaxe de funções `async` e palavra-chave `await`.
  * Blocos `try...catch` para capturar falhas de rede, limites de cota da API ou erros de parsing.
  * Padrão de funções assíncronas reutilizáveis para consulta a modelos.

---

### 🔹 Dia 6: Streams e Iteradores Assíncronos (`for await...of`)
* **Carga horária:** 3h
* **Objetivo:** Processar respostas em tempo real (efeito de digitação/streaming de tokens).
* **Tópicos:**
  * O que são *Data Streams* e melhoria da experiência do usuário (UX).
  * Consumindo geradores e iteradores assíncronos.
  * Utilizando a estrutura `for await (const chunk of responseStream)`.

---

### 🔹 Dia 7: Consolidação, Exercício e Prova
* **Carga horária:** 3h
* **Objetivo:** Aplicar os conhecimentos adquiridos resolvendo o exercício prático e a avaliação de fixação.

---

## 🏋️ Exercício Prático de Fixação

Monte um script em Node.js (ou em um ambiente online como Replit) sem utilizar bibliotecas externas, simulando o fluxo de envio e recebimento de dados com IA:

1. **Entrada de Dados:** Crie um objeto chamado `produto` com as propriedades `nome`, `categoria` e `descricao`.
2. **Construção do Prompt:** Utilizando **Template Literals**, crie uma instrução contendo o contexto do produto e solicitando que a IA gere uma legenda de vendas.
3. **Simulação de Chamada de API:** Crie uma função assíncrona `chamarIAsimulada(prompt)` que retorne uma `Promise`. Utilize `setTimeout` para aguardar 2 segundos e retorne uma string representando uma resposta em JSON formatado.
4. **Processamento:** Utilize `async/await` e `try/catch` para executar a função, realizar o `JSON.parse()` da resposta e exibir o resultado formatado no console.

---

## 📝 Prova de Fixação (Avaliação de Conceitos)

### Questão 1 (Template Literals)
Dado o objeto:
```javascript
const sistema = { papel: "Especialista em SEO", idioma: "PT-BR" };
```
Escreva um *Template Literal* atribuído a uma constante `systemInstruction` que instrua o modelo a atuar conforme o papel e idioma definidos, utilizando quebras de linha reais.

### Questão 2 (JSON e Manipulação)
A API do Gemini retornou a seguinte string contendo dados JSON:
```javascript
const respostaIA = '{"status": "sucesso", "itens": ["calça", "camisa"]}';
```
Escreva o código em JS para extrair o array de itens e exibir apenas o segundo item (`"camisa"`) no console.

### Questão 3 (Async / Await)
Identifique o erro no código abaixo e escreva a versão corrigida:
```javascript
function buscarRespostaPrompt(prompt) {
    const resultado = await apiGemini.generateContent(prompt);
    console.log(resultado.text);
}
```

### Questão 4 (Tratamento de Erros)
Por que é indispensável envolver requisições de API de LLMs dentro de um bloco `try...catch`? O que acontece com a execução do Node.js se a API retornar um erro de limite de cota (429) e a exceção não for capturada?

---

## 🔑 Gabarito da Prova de Fixação

<details>
<summary>Clique para expandir o gabarito</summary>

### Resposta 1:
```javascript
const systemInstruction = `Atue como um ${sistema.papel}.
Sua resposta deve ser estritamente gerada no idioma ${sistema.idioma}.`;
```

### Resposta 2:
```javascript
const dados = JSON.parse(respostaIA);
console.log(dados.itens[1]);
```

### Resposta 3:
Faltou a palavra-chave `async` na declaração da função para permitir o uso do `await`.
```javascript
async function buscarRespostaPrompt(prompt) {
    const resultado = await apiGemini.generateContent(prompt);
    console.log(resultado.text);
}
```

### Resposta 4:
Sem o bloco `try...catch`, qualquer exceção não tratada (como falha de rede ou erro HTTP 429) lança um erro do tipo *Unhandled Promise Rejection*, o que interrompe imediatamente a execução do processo Node.js e derruba a aplicação. Com o tratamento correto, é possível tratar o erro graciosamente, registrar logs ou tentar uma nova requisição.

</details>
