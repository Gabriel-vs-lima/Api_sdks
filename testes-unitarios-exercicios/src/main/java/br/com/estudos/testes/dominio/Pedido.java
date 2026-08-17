package br.com.estudos.testes.dominio;

public class Pedido {

    private Long id;
    private String skuDoProduto;
    private int quantidade;
    private double valorTotal;
    private StatusDoPedido status = StatusDoPedido.ABERTO;
    private Cliente cliente;

    public Pedido() {
    }

    public Pedido(Long id, String skuDoProduto, int quantidade, double valorTotal, Cliente cliente) {
        this.id = id;
        this.skuDoProduto = skuDoProduto;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuDoProduto() {
        return skuDoProduto;
    }

    public void setSkuDoProduto(String skuDoProduto) {
        this.skuDoProduto = skuDoProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public StatusDoPedido getStatus() {
        return status;
    }

    public void setStatus(StatusDoPedido status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
