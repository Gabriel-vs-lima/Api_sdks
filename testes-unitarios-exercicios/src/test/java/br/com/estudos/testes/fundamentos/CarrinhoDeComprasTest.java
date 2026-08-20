package br.com.estudos.testes.fundamentos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import br.com.estudos.testes.fundamentos.CarrinhoDeCompras.Item;  
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;        
import static org.junit.jupiter.api.Assertions.assertThrows;      
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

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

    private CarrinhoDeCompras  carrinho;
    @BeforeEach
    void prepararCarrinhoLimpo(){
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
        //arrange
        Item camisa = new Item("camisa", 199.90, 2);
        //act
        carrinho.adicionar(camisa);
        //assert
        assertEquals(399.80,carrinho.total(), 0.001);
    }

    @Test
    @DisplayName("2.3 - remover um item que existe devolve true e tira o item do carrinho")
    void deveRemoverItemExistente() {
        //arrange
        Item camisa = new Item("camisa polo",200,1);
        carrinho.adicionar(camisa);
        //act
         boolean removido = carrinho.remover("camisa polo");

        //assert
        assertTrue(removido);
        assertTrue(carrinho.estaVazio());
        assertEquals(0,carrinho.quantidadeDeItens());
    }

    @Test
    @DisplayName("2.4 - remover um item que nao existe devolve false e nao mexe no carrinho")
    void deveIgnorarRemocaoDeItemInexistente() {
        //arrange
        Item camisa = new Item("camisa polo",200,1);
        carrinho.adicionar(camisa);
        //act
         boolean removido = carrinho.remover("camisa regata");
         //assert
         assertFalse(removido);
            assertFalse(carrinho.estaVazio());
            assertEquals(1,carrinho.quantidadeDeItens());
        
    }

    @Test
    @DisplayName("2.5 - adicionar item nulo lanca IllegalArgumentException")
    void deveRecusarItemNulo() {
        //arrange
        Item itemNulo = null;
        //act
        var erro = assertThrows(
            IllegalArgumentException.class, 
            () -> carrinho.adicionar(itemNulo)
        );
        //assert
        assertEquals("item nao pode ser nulo", erro.getMessage());
        

    }

        @Test
    @DisplayName("teste o limite de 20 itens. Use um laco para adicionar 20 itens com nomes diferentes e verifique o que o 21o lanca IllegalStateException.")
    void deveRecusar21Item() {
        //arrange
        Item primeiroItem = new Item("item 1", 10, 1);
                //act
       var erro = assertThrows(
            IllegalStateException.class,
            () -> {
                for (int i = 1; i <= 20; i++) {
                    carrinho.adicionar(primeiroItem);
                }
                carrinho.adicionar(primeiroItem);
            }
        );
        //assert
        assertEquals("carrinho cheio: limite de " + CarrinhoDeCompras.LIMITE_DE_ITENS + " itens", erro.getMessage());
        

    }

    // ------------------------------------------------------------------
    // BONUS (opcional): teste o limite de 20 itens.
    // Use um laco para adicionar 20 itens com nomes diferentes e verifique
    // que o 21o lanca IllegalStateException.
    // ------------------------------------------------------------------
}
