package br.com.estudos.testes.fundamentos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * ======================================================================
 * EXERCICIO 1 - O basico: AAA, assertEquals e assertThrows
 * ======================================================================
 *
 * Classe sob teste: CalculadoraDeDesconto
 *
 * Escreva os 5 testes abaixo. Todos seguem o padrao AAA:
 *
 *   // Arrange  -> prepare os dados e o objeto
 *   // Act      -> chame o metodo que voce quer testar
 *   // Assert   -> verifique o resultado
 *
 * Lembre: numero quebrado (double) exige DELTA no assertEquals,
 * porque 0.1 + 0.2 nao da exatamente 0.3 em ponto flutuante.
 *   assertEquals(esperado, obtido, 0.001);
 *
 * Apague os fail(...) conforme for implementando.
 */
class CalculadoraDeDescontoTest {

    private final CalculadoraDeDesconto calculadora = new CalculadoraDeDesconto();

    @Test
    @DisplayName("1.1 - compra de 3 unidades a 100,00 nao tem desconto e o total e 300,00")
    void naoDeveAplicarDescontoAbaixoDeCincoUnidades() {
        // TODO: Arrange / Act / Assert
        // Dica: assertEquals(300.0, resultado, 0.001);
        fail("TODO: implementar este teste");
    }

    @Test
    @DisplayName("1.2 - compra de 5 unidades a 100,00 recebe 5% e o total e 475,00")
    void deveAplicarCincoPorCentoAPartirDeCincoUnidades() {
        // TODO
        // Este teste testa a FRONTEIRA da regra (exatamente 5).
        // Casos de fronteira sao onde mais aparecem bugs.
        fail("TODO: implementar este teste");
    }

    @Test
    @DisplayName("1.3 - compra de 10 unidades a 100,00 recebe 10% e o total e 900,00")
    void deveAplicarDezPorCentoAPartirDeDezUnidades() {
        // TODO
        fail("TODO: implementar este teste");
    }

    @Test
    @DisplayName("1.4 - valor unitario negativo lanca IllegalArgumentException")
    void deveRecusarValorUnitarioNegativo() {
        // TODO
        // Dica:
        //   var erro = assertThrows(IllegalArgumentException.class,
        //           () -> calculadora.calcularTotal(-1.0, 3));
        //   assertEquals("valor unitario nao pode ser negativo", erro.getMessage());
        fail("TODO: implementar este teste");
    }

    @Test
    @DisplayName("1.5 - quantidade zero lanca IllegalArgumentException")
    void deveRecusarQuantidadeZero() {
        // TODO
        fail("TODO: implementar este teste");
    }

    // ------------------------------------------------------------------
    // BONUS (opcional): reescreva os testes 1.1 a 1.3 num unico
    // @ParameterizedTest com @CsvSource, assim:
    //
    //   @ParameterizedTest(name = "{0} unidades a {1} => total {2}")
    //   @CsvSource({ "3, 100.0, 300.0", "5, 100.0, 475.0", "10, 100.0, 900.0" })
    //   void deveCalcularTotalComDesconto(int qtd, double preco, double esperado) { ... }
    // ------------------------------------------------------------------
}
