package br.com.estudos.testes.exception;

public class ClienteNaoEncontradoException extends RuntimeException {

    public ClienteNaoEncontradoException(Long id) {
        super("Cliente nao encontrado: " + id);
    }
}
