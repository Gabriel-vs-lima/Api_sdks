package br.com.estudos.testes.fundamentos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        //arrange - cria os dados para teste
        double valorUnitario = 100.0;
        int quantidade = 3;
        //act - executa o teste sob o metodo de teste
        double total = calculadora.calcularTotal(valorUnitario, quantidade);

        //Assert - compara o valor obtido com o valor esperado 
        assertEquals(300.0, total, 0.001);
        //                           ^
        //valor de tolerancia (delta) para comparacao de doubles         
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
      assertEquals(475.0, total,0.001);


    }

    @Test
    @DisplayName("1.3 - compra de 10 unidades a 100,00 recebe 10% e o total e 900,00")
    void deveAplicarDezPorCentoAPartirDeDezUnidades() {
      double valorUnitario = 100.0;
      int quantidade = 10;

      double total = calculadora.calcularTotal(valorUnitario, quantidade);

      assertEquals(900, total,0.001);
    }

    @Test
    @DisplayName("1.4 - valor unitario negativo lanca IllegalArgumentException")
    void deveRecusarValorUnitarioNegativo() {
        double valorUnitario = -25;
        int quantidade = 8;

        var erro = assertThrows(
            IllegalArgumentException.class,
            () -> calculadora.calcularTotal(valorUnitario, quantidade));
        //usamos "->" como um comando de pausa para a execução de uma classe
        // Dica:
        // "asserTrhows" = executa e verifica 
        //É o oposto do assertEquals. Ali você diz "o resultado tem que ser X". Aqui você diz "não pode haver resultado — tem que dar erro, e um erro específico"
        assertEquals("valor unitario nao pode ser negativo", erro.getMessage());
        //  erro.getMessage - valida que a resposta esperada é UMA DAS opções de resposta de erro da classe: IlegalArgumentExcption
        //vida real, contains costuma ser a escolha mais sensata para mensagens que podem ser reescritas.
        //Ex:assertTrue(erro.getMessage().contains("valor unitario"));
    }

    @Test
    @DisplayName("1.5 - quantidade zero lanca IllegalArgumentException")
    void deveRecusarQuantidadeZero() {
        double valorUnitario = 100.0;
        int quantidade = 0;

        var erro = assertThrows(IllegalArgumentException.class, 
         () -> calculadora.calcularTotal(valorUnitario, quantidade));

        assertEquals("quantidade deve ser no minimo 1", erro.getMessage());
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
