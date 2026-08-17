package br.com.estudos.testes.infra;

import br.com.estudos.testes.client.EstoqueClient;
import org.springframework.stereotype.Component;

/**
 * Faz de conta que fala com uma API externa de estoque.
 * Num teste unitario isso vira @Mock -- nenhuma rede e usada.
 */
@Component
public class EstoqueClientHttpFake implements EstoqueClient {

    @Override
    public boolean temDisponibilidade(String sku, int quantidade) {
        System.out.println("[estoque] consultando " + sku + " x" + quantidade);
        return quantidade <= 10;
    }

    @Override
    public void baixarEstoque(String sku, int quantidade) {
        System.out.println("[estoque] baixando " + sku + " x" + quantidade);
    }
}
