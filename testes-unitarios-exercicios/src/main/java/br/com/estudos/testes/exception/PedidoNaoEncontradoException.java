package br.com.estudos.testes.exception;

public class PedidoNaoEncontradoException extends RuntimeException {

    public PedidoNaoEncontradoException(Long id) {
        super("Pedido nao encontrado: " + id);
    }
}
