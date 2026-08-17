package br.com.estudos.testes.fundamentos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** GABARITO do Exercicio 1. */
class CalculadoraDeDescontoTest {

    private final CalculadoraDeDesconto calculadora = new CalculadoraDeDesconto();

    @Test
    @DisplayName("1.1 - compra de 3 unidades a 100,00 nao tem desconto e o total e 300,00")
    void naoDeveAplicarDescontoAbaixoDeCincoUnidades() {
        // Arrange
        double valorUnitario = 100.0;
        int quantidade = 3;

        // Act
        double total = calculadora.calcularTotal(valorUnitario, quantidade);

        // Assert
        assertEquals(300.0, total, 0.001);
    }

    @Test
    @DisplayName("1.2 - compra de 5 unidades a 100,00 recebe 5% e o total e 475,00")
    void deveAplicarCincoPorCentoAPartirDeCincoUnidades() {
        double total = calculadora.calcularTotal(100.0, 5);

        assertEquals(475.0, total, 0.001);
    }

    @Test
    @DisplayName("1.3 - compra de 10 unidades a 100,00 recebe 10% e o total e 900,00")
    void deveAplicarDezPorCentoAPartirDeDezUnidades() {
        double total = calculadora.calcularTotal(100.0, 10);

        assertEquals(900.0, total, 0.001);
    }

    @Test
    @DisplayName("1.4 - valor unitario negativo lanca IllegalArgumentException")
    void deveRecusarValorUnitarioNegativo() {
        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.calcularTotal(-1.0, 3)
        );

        assertEquals("valor unitario nao pode ser negativo", erro.getMessage());
    }

    @Test
    @DisplayName("1.5 - quantidade zero lanca IllegalArgumentException")
    void deveRecusarQuantidadeZero() {
        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.calcularTotal(100.0, 0)
        );

        assertEquals("quantidade deve ser no minimo 1", erro.getMessage());
    }

    // ---------------- BONUS: os tres primeiros casos em um teste so ----------------

    @ParameterizedTest(name = "{0} unidades a {1} => total {2}")
    @CsvSource({
            "1,  100.0,  100.0",
            "4,  100.0,  400.0",
            "5,  100.0,  475.0",
            "9,  100.0,  855.0",
            "10, 100.0,  900.0",
            "20, 100.0, 1800.0"
    })
    @DisplayName("BONUS - tabela de faixas de desconto")
    void deveCalcularTotalConformeAFaixa(int quantidade, double valorUnitario, double esperado) {
        assertEquals(esperado, calculadora.calcularTotal(valorUnitario, quantidade), 0.001);
    }
}
