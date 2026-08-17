package br.com.estudos.testes.fundamentos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * ======================================================================
 * EXERCICIO 2 - Estado, @BeforeEach e independencia entre testes
 * ======================================================================
 *
 * Classe sob teste: CarrinhoDeCompras
 *
 * O ponto deste exercicio: o carrinho GUARDA ESTADO. Se um teste sujar o
 * carrinho, o proximo teste quebra -- e voce perde a caracteristica mais
 * importante de um bom teste unitario: ser independente e repetivel.
 *
 * TAREFA EXTRA (faca primeiro):
 *   1. declare o campo   private CarrinhoDeCompras carrinho;
 *   2. crie um metodo anotado com @BeforeEach que faz
 *      carrinho = new CarrinhoDeCompras();
 *   3. so entao escreva os testes
 *
 * Apague os fail(...) conforme for implementando.
 */
class CarrinhoDeComprasTest {

    // TODO: campo carrinho + metodo @BeforeEach

    @Test
    @DisplayName("2.1 - carrinho recem-criado esta vazio e com total zero")
    void carrinhoNovoDeveEstarVazio() {
        // TODO
        // Dica: assertTrue(...), assertEquals(0.0, ..., 0.001)
        fail("TODO: implementar este teste");
    }

    @Test
    @DisplayName("2.2 - ao adicionar 2 camisas de 199,90 o total e 399,80")
    void deveSomarPrecoVezesQuantidade() {
        // TODO
        // Dica: new CarrinhoDeCompras.Item("camisa", 199.90, 2)
        fail("TODO: implementar este teste");
    }

    @Test
    @DisplayName("2.3 - remover um item que existe devolve true e tira o item do carrinho")
    void deveRemoverItemExistente() {
        // TODO
        // Verifique DUAS coisas: o retorno true E o estado do carrinho depois.
        fail("TODO: implementar este teste");
    }

    @Test
    @DisplayName("2.4 - remover um item que nao existe devolve false e nao mexe no carrinho")
    void deveIgnorarRemocaoDeItemInexistente() {
        // TODO
        // Repare: aqui NAO ha excecao. O caminho "triste" tambem e um valor de retorno.
        fail("TODO: implementar este teste");
    }

    @Test
    @DisplayName("2.5 - adicionar item nulo lanca IllegalArgumentException")
    void deveRecusarItemNulo() {
        // TODO
        fail("TODO: implementar este teste");
    }

    // ------------------------------------------------------------------
    // BONUS (opcional): teste o limite de 20 itens.
    // Use um laco para adicionar 20 itens com nomes diferentes e verifique
    // que o 21o lanca IllegalStateException.
    // ------------------------------------------------------------------
}
