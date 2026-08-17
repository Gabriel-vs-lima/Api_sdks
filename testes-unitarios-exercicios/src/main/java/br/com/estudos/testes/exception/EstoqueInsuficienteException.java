package br.com.estudos.testes.exception;

public class EstoqueInsuficienteException extends RuntimeException {

    public EstoqueInsuficienteException(String sku) {
        super("Estoque insuficiente para o SKU: " + sku);
    }
}
