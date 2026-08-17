package br.com.estudos.testes.client;

/**
 * Simula uma chamada a um servico externo de estoque (uma API HTTP, por exemplo).
 * No teste unitario isso vira um @Mock -- nenhuma chamada de rede acontece.
 */
public interface EstoqueClient {

    boolean temDisponibilidade(String sku, int quantidade);

    void baixarEstoque(String sku, int quantidade);
}
