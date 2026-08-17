package br.com.estudos.testes.fundamentos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * EXERCICIO 2 - Fundamentos (JUnit 5 puro, com @BeforeEach)
 *
 * Um carrinho simples, com estado. E justamente o estado que faz este
 * exercicio pedir @BeforeEach: cada teste precisa comecar com um
 * carrinho novo e vazio.
 *
 * Regras:
 *   - adicionar(item) com item nulo -> IllegalArgumentException
 *   - adicionar(item) com quantidade <= 0 -> IllegalArgumentException
 *   - remover(nome) de item inexistente -> retorna false (nao lanca excecao)
 *   - total() soma preco * quantidade de todos os itens
 *   - limite de 20 itens distintos: o 21o lanca IllegalStateException
 *
 * NAO altere esta classe.
 */
public class CarrinhoDeCompras {

    public static final int LIMITE_DE_ITENS = 20;

    private final List<Item> itens = new ArrayList<>();

    public void adicionar(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item nao pode ser nulo");
        }
        if (item.quantidade() <= 0) {
            throw new IllegalArgumentException("quantidade deve ser maior que zero");
        }
        if (itens.size() >= LIMITE_DE_ITENS) {
            throw new IllegalStateException("carrinho cheio: limite de " + LIMITE_DE_ITENS + " itens");
        }
        itens.add(item);
    }

    public boolean remover(String nome) {
        return itens.removeIf(item -> item.nome().equals(nome));
    }

    public double total() {
        return itens.stream()
                .mapToDouble(item -> item.preco() * item.quantidade())
                .sum();
    }

    public int quantidadeDeItens() {
        return itens.size();
    }

    public boolean estaVazio() {
        return itens.isEmpty();
    }

    public List<Item> itens() {
        return Collections.unmodifiableList(itens);
    }

    /** Um item do carrinho. Record = classe imutavel com equals/hashCode prontos. */
    public record Item(String nome, double preco, int quantidade) {
    }
}
