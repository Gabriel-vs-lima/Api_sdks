package br.com.estudos.testes.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * ======================================================================
 * EXERCICIO 5 - Varias dependencias, caminho feliz e caminhos tristes
 * ======================================================================
 *
 * Classe sob teste: PedidoService.confirmar(Long)
 *
 * Dependencias: PedidoRepository, EstoqueClient, NotificadorDeEmail
 *
 * Este e o exercicio mais parecido com o codigo do dia a dia: o metodo
 * orquestra tres colaboradores. Um bom teste aqui responde nao so
 * "deu certo?" mas "quem foi chamado, com o que, e quem NAO foi chamado".
 *
 * Sugestao: crie um metodo @BeforeEach que monta um pedido ABERTO padrao
 * para nao repetir o mesmo setup em todos os testes.
 *
 * Apague os fail(...) conforme for implementando.
 */
class PedidoServiceTest {

    // TODO: @ExtendWith(MockitoExtension.class)
    // TODO: @Mock PedidoRepository, @Mock EstoqueClient, @Mock NotificadorDeEmail
    // TODO: @InjectMocks PedidoService

    @Test
    @DisplayName("5.1 - caminho feliz: pedido com estoque vira CONFIRMADO e e salvo")
    void deveConfirmarPedidoQuandoHaEstoque() {
        // Arrange
        // TODO:
        //   var cliente = new Cliente(1L, "Ana", "ana@email.com");
        //   var pedido = new Pedido(10L, "SKU-1", 2, 500.0, cliente);
        //   when(pedidoRepository.findById(10L)).thenReturn(Optional.of(pedido));
        //   when(estoqueClient.temDisponibilidade("SKU-1", 2)).thenReturn(true);
        //   when(pedidoRepository.save(any(Pedido.class))).thenAnswer(i -> i.getArgument(0));
        // Act / Assert: status CONFIRMADO
        fail("TODO: implementar este teste");
    }

    @Test
    @DisplayName("5.2 - caminho feliz: baixa o estoque com o SKU e a quantidade do pedido")
    void deveBaixarEstoqueComOsDadosDoPedido() {
        // TODO
        // Dica: verify(estoqueClient).baixarEstoque("SKU-1", 2);
        fail("TODO: implementar este teste");
    }

    @Test
    @DisplayName("5.3 - pedido inexistente lanca PedidoNaoEncontradoException")
    void deveLancarExcecaoQuandoPedidoNaoExiste() {
        // TODO
        fail("TODO: implementar este teste");
    }

    @Test
    @DisplayName("5.4 - sem estoque: lanca EstoqueInsuficienteException e nao baixa, nao salva, nao notifica")
    void naoDeveConfirmarSemEstoque() {
        // TODO
        // Este e o teste mais valioso da lista: garante que uma falha no meio
        // do fluxo nao deixa efeito colateral pela metade.
        //   verify(estoqueClient, never()).baixarEstoque(any(), anyInt());
        //   verify(pedidoRepository, never()).save(any());
        //   verify(notificadorDeEmail, never()).enviar(any(), any(), any());
        fail("TODO: implementar este teste");
    }

    @Test
    @DisplayName("5.5 - pedido ja CANCELADO nao pode ser confirmado (IllegalStateException)")
    void naoDeveConfirmarPedidoQueNaoEstaAberto() {
        // TODO
        // Dica: monte o pedido e chame pedido.setStatus(StatusDoPedido.CANCELADO)
        // antes de ensinar o findById a devolve-lo.
        fail("TODO: implementar este teste");
    }

    // ------------------------------------------------------------------
    // BONUS (opcional): use InOrder para garantir que o estoque e baixado
    // ANTES do pedido ser salvo:
    //
    //   var inOrder = inOrder(estoqueClient, pedidoRepository);
    //   inOrder.verify(estoqueClient).baixarEstoque("SKU-1", 2);
    //   inOrder.verify(pedidoRepository).save(any());
    // ------------------------------------------------------------------
}
