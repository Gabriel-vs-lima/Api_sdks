package br.com.estudos.testes.fundamentos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * EXERCICIO 1 - O basico: AAA, assertEquals e assertThrows
 */
class CalculadoraDeDescontoTest {

    private final CalculadoraDeDesconto calculadora = new CalculadoraDeDesconto();

    @Test
    @DisplayName("1.1 - compra de 3 unidades a 100,00 nao tem desconto e o total e 300,00")
    void naoDeveAplicarDescontoAbaixoDeCincoUnidades() {
        //Arrange - cria os dados para teste
        double valorUnitario = 100.0;
        int quantidade = 3;

        //Act - executa o metodo sob teste
        double total = calculadora.calcularTotal(valorUnitario, quantidade);

        //Assert - compara o valor obtido com o esperado
        assertEquals(300.0, total, 0.001);
        //                          ^ delta: tolerancia para comparacao de doubles
    }

    @Test
    @DisplayName("1.2 - compra de 5 unidades a 100,00 recebe 5% e o total e 475,00")
    void deveAplicarCincoPorCentoAPartirDeCincoUnidades() {
        //Arrange
        double valorUnitario = 100.0;
        int quantidade = 5;

        //Act
        double total = calculadora.calcularTotal(valorUnitario, quantidade);

        //Assert
        assertEquals(475.0, total, 0.001);
    }

    @Test
    @DisplayName("1.3 - compra de 10 unidades a 100,00 recebe 10% e o total e 900,00")
    void deveAplicarDezPorCentoAPartirDeDezUnidades() {
        //Arrange
        double valorUnitario = 100.0;
        int quantidade = 10;

        //Act
        double total = calculadora.calcularTotal(valorUnitario, quantidade);

        //Assert
        assertEquals(900.0, total, 0.001);
    }

    @Test
    @DisplayName("1.4 - valor unitario negativo lanca IllegalArgumentException")
    void deveRecusarValorUnitarioNegativo() {
        //Arrange
        double valorUnitario = -25.0;
        int quantidade = 8;

        //Act + Assert
        // "assertThrows" executa e verifica ao mesmo tempo.
        // E o oposto do assertEquals: la voce diz "o resultado tem que ser X";
        // aqui voce diz "nao pode haver resultado - tem que dar erro, e um erro especifico".
        var erro = assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.calcularTotal(valorUnitario, quantidade)
        );
        // O "() ->" empacota a chamada em vez de executa-la agora, para que o
        // assertThrows a rode dentro do try/catch dele e consiga capturar a excecao.

        assertEquals("valor unitario nao pode ser negativo", erro.getMessage());
        // getMessage() identifica QUAL das duas validacoes disparou: as duas
        // lancam IllegalArgumentException, so a mensagem as distingue.
        // Alternativa mais tolerante a mudanca de redacao:
        //   assertTrue(erro.getMessage().contains("valor unitario"));
    }

    @Test
    @DisplayName("1.5 - quantidade zero lanca IllegalArgumentException")
    void deveRecusarQuantidadeZero() {
        //Arrange
        double valorUnitario = 100.0;
        int quantidade = 0;

        //Act + Assert
        var erro = assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.calcularTotal(valorUnitario, quantidade)
        );

        assertEquals("quantidade deve ser no minimo 1", erro.getMessage());
    }
}