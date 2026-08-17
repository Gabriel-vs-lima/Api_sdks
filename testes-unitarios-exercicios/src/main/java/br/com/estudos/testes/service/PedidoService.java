package br.com.estudos.testes.service;

import br.com.estudos.testes.client.EstoqueClient;
import br.com.estudos.testes.client.NotificadorDeEmail;
import br.com.estudos.testes.dominio.Pedido;
import br.com.estudos.testes.dominio.StatusDoPedido;
import br.com.estudos.testes.exception.EstoqueInsuficienteException;
import br.com.estudos.testes.exception.PedidoNaoEncontradoException;
import br.com.estudos.testes.repository.PedidoRepository;
import org.springframework.stereotype.Service;

/**
 * EXERCICIO 5 - Spring + Mockito com TRES dependencias
 *
 * NAO altere esta classe.
 */
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final EstoqueClient estoqueClient;
    private final NotificadorDeEmail notificadorDeEmail;

    public PedidoService(PedidoRepository pedidoRepository,
                         EstoqueClient estoqueClient,
                         NotificadorDeEmail notificadorDeEmail) {
        this.pedidoRepository = pedidoRepository;
        this.estoqueClient = estoqueClient;
        this.notificadorDeEmail = notificadorDeEmail;
    }

    /**
     * Confirma um pedido.
     *
     * Fluxo:
     *   1. busca o pedido; se nao existir -> PedidoNaoEncontradoException
     *   2. se o pedido nao estiver ABERTO -> IllegalStateException
     *      ("somente pedidos abertos podem ser confirmados")
     *   3. pergunta ao estoque se ha disponibilidade;
     *      se nao houver -> EstoqueInsuficienteException e NADA mais acontece
     *      (nao baixa estoque, nao salva, nao notifica)
     *   4. baixa o estoque
     *   5. muda o status para CONFIRMADO e salva
     *   6. notifica o cliente por e-mail
     *   7. devolve o pedido salvo
     */
    public Pedido confirmar(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));

        if (pedido.getStatus() != StatusDoPedido.ABERTO) {
            throw new IllegalStateException("somente pedidos abertos podem ser confirmados");
        }

        if (!estoqueClient.temDisponibilidade(pedido.getSkuDoProduto(), pedido.getQuantidade())) {
            throw new EstoqueInsuficienteException(pedido.getSkuDoProduto());
        }

        estoqueClient.baixarEstoque(pedido.getSkuDoProduto(), pedido.getQuantidade());

        pedido.setStatus(StatusDoPedido.CONFIRMADO);
        Pedido salvo = pedidoRepository.save(pedido);

        notificadorDeEmail.enviar(
                salvo.getCliente().getEmail(),
                "Pedido confirmado",
                "Seu pedido " + salvo.getId() + " foi confirmado."
        );

        return salvo;
    }
}
