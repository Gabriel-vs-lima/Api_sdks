package br.com.estudos.testes.fundamentos;

/**
 * EXERCICIO 1 - Fundamentos (JUnit 5 puro)
 *
 * Regra de negocio do desconto por quantidade:
 *   - quantidade >= 10  -> 10% de desconto
 *   - quantidade >= 5   ->  5% de desconto
 *   - quantidade <  5   ->  sem desconto
 *
 * Validacoes:
 *   - valorUnitario negativo   -> IllegalArgumentException("valor unitario nao pode ser negativo")
 *   - quantidade menor que 1   -> IllegalArgumentException("quantidade deve ser no minimo 1")
 *
 * NAO altere esta classe. Escreva os testes em
 * src/test/java/br/com/estudos/testes/fundamentos/CalculadoraDeDescontoTest.java
 */
public class CalculadoraDeDesconto {

    public double calcularTotal(double valorUnitario, int quantidade) {
        if (valorUnitario < 0) {
            throw new IllegalArgumentException("valor unitario nao pode ser negativo");
        }
        if (quantidade < 1) {
            throw new IllegalArgumentException("quantidade deve ser no minimo 1");
        }

        double bruto = valorUnitario * quantidade;
        return bruto - (bruto * percentualDeDesconto(quantidade));
    }

    public double percentualDeDesconto(int quantidade) {
        if (quantidade >= 10) {
            return 0.10;
        }
        if (quantidade >= 5) {
            return 0.05;
        }
        return 0.0;
    }
}
