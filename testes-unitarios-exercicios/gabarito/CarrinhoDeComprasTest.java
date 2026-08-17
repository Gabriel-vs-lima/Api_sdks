package br.com.estudos.testes.fundamentos;

import br.com.estudos.testes.fundamentos.CarrinhoDeCompras.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** GABARITO do Exercicio 2. */
class CarrinhoDeComprasTest {

    private CarrinhoDeCompras carrinho;

    @BeforeEach
    void prepararCarrinhoLimpo() {
        // Roda ANTES de cada @Test. Garante que um teste nunca herda o
        // estado deixado por outro.
        carrinho = new CarrinhoDeCompras();
    }

    @Test
    @DisplayName("2.1 - carrinho recem-criado esta vazio e com total zero")
    void carrinhoNovoDeveEstarVazio() {
        assertTrue(carrinho.estaVazio());
        assertEquals(0, carrinho.quantidadeDeItens());
        assertEquals(0.0, carrinho.total(), 0.001);
    }

    @Test
    @DisplayName("2.2 - ao adicionar 2 camisas de 199,90 o total e 399,80")
    void deveSomarPrecoVezesQuantidade() {
        // Arrange
        Item camisa = new Item("camisa", 199.90, 2);

        // Act
        carrinho.adicionar(camisa);

        // Assert
        assertEquals(399.80, carrinho.total(), 0.001);
        assertFalse(carrinho.estaVazio());
    }

    @Test
    @DisplayName("2.3 - remover um item que existe devolve true e tira o item do carrinho")
    void deveRemoverItemExistente() {
        carrinho.adicionar(new Item("camisa", 199.90, 1));
        carrinho.adicionar(new Item("calca", 299.90, 1));

        boolean removeu = carrinho.remover("camisa");

        assertTrue(removeu);
        assertEquals(1, carrinho.quantidadeDeItens());
        assertEquals(299.90, carrinho.total(), 0.001);
    }

    @Test
    @DisplayName("2.4 - remover um item que nao existe devolve false e nao mexe no carrinho")
    void deveIgnorarRemocaoDeItemInexistente() {
        carrinho.adicionar(new Item("camisa", 199.90, 1));

        boolean removeu = carrinho.remover("meia");

        assertFalse(removeu);
        assertEquals(1, carrinho.quantidadeDeItens());
    }

    @Test
    @DisplayName("2.5 - adicionar item nulo lanca IllegalArgumentException")
    void deveRecusarItemNulo() {
        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> carrinho.adicionar(null)
        );

        assertEquals("item nao pode ser nulo", erro.getMessage());
        assertTrue(carrinho.estaVazio());
    }

    // ---------------- BONUS ----------------

    @Test
    @DisplayName("BONUS - o 21o item distinto estoura o limite do carrinho")
    void deveRespeitarOLimiteDeItens() {
        for (int i = 0; i < CarrinhoDeCompras.LIMITE_DE_ITENS; i++) {
            carrinho.adicionar(new Item("item-" + i, 10.0, 1));
        }

        assertEquals(20, carrinho.quantidadeDeItens());
        assertThrows(IllegalStateException.class,
                () -> carrinho.adicionar(new Item("item-21", 10.0, 1)));
    }

    @Test
    @DisplayName("BONUS - quantidade zero e recusada")
    void deveRecusarQuantidadeZero() {
        assertThrows(IllegalArgumentException.class,
                () -> carrinho.adicionar(new Item("camisa", 199.90, 0)));
    }
}
