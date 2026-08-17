package br.com.estudos.testes.service;

import br.com.estudos.testes.client.EstoqueClient;
import br.com.estudos.testes.client.NotificadorDeEmail;
import br.com.estudos.testes.dominio.Cliente;
import br.com.estudos.testes.dominio.Pedido;
import br.com.estudos.testes.dominio.StatusDoPedido;
import br.com.estudos.testes.exception.EstoqueInsuficienteException;
import br.com.estudos.testes.exception.PedidoNaoEncontradoException;
import br.com.estudos.testes.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** GABARITO do Exercicio 5. */
@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private EstoqueClient estoqueClient;

    @Mock
    private NotificadorDeEmail notificadorDeEmail;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedidoAberto;

    @BeforeEach
    void prepararPedidoPadrao() {
        // So MONTAGEM DE OBJETO aqui. Nada de when(...) no @BeforeEach:
        // o MockitoExtension roda em modo strict e reclama de stub nao usado.
        Cliente cliente = new Cliente(1L, "Ana", "ana@email.com");
        pedidoAberto = new Pedido(10L, "SKU-1", 2, 500.0, cliente);
    }

    @Test
    @DisplayName("5.1 - caminho feliz: pedido com estoque vira CONFIRMADO e e salvo")
    void deveConfirmarPedidoQuandoHaEstoque() {
        // Arrange
        when(pedidoRepository.findById(10L)).thenReturn(Optional.of(pedidoAberto));
        when(estoqueClient.temDisponibilidade("SKU-1", 2)).thenReturn(true);
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(chamada -> chamada.getArgument(0));

        // Act
        Pedido confirmado = pedidoService.confirmar(10L);

        // Assert
        assertEquals(StatusDoPedido.CONFIRMADO, confirmado.getStatus());
        verify(pedidoRepository).save(pedidoAberto);
        verify(notificadorDeEmail).enviar(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("5.2 - caminho feliz: baixa o estoque com o SKU e a quantidade do pedido")
    void deveBaixarEstoqueComOsDadosDoPedido() {
        when(pedidoRepository.findById(10L)).thenReturn(Optional.of(pedidoAberto));
        when(estoqueClient.temDisponibilidade("SKU-1", 2)).thenReturn(true);
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(chamada -> chamada.getArgument(0));

        pedidoService.confirmar(10L);

        verify(estoqueClient).baixarEstoque("SKU-1", 2);
    }

    @Test
    @DisplayName("5.3 - pedido inexistente lanca PedidoNaoEncontradoException")
    void deveLancarExcecaoQuandoPedidoNaoExiste() {
        when(pedidoRepository.findById(999L)).thenReturn(Optional.empty());

        PedidoNaoEncontradoException erro = assertThrows(
                PedidoNaoEncontradoException.class,
                () -> pedidoService.confirmar(999L)
        );

        assertTrue(erro.getMessage().contains("999"));
        verify(estoqueClient, never()).temDisponibilidade(anyString(), anyInt());
    }

    @Test
    @DisplayName("5.4 - sem estoque: lanca EstoqueInsuficienteException e nao deixa efeito pela metade")
    void naoDeveConfirmarSemEstoque() {
        when(pedidoRepository.findById(10L)).thenReturn(Optional.of(pedidoAberto));
        when(estoqueClient.temDisponibilidade("SKU-1", 2)).thenReturn(false);

        assertThrows(EstoqueInsuficienteException.class, () -> pedidoService.confirmar(10L));

        assertEquals(StatusDoPedido.ABERTO, pedidoAberto.getStatus());
        verify(estoqueClient, never()).baixarEstoque(anyString(), anyInt());
        verify(pedidoRepository, never()).save(any());
        verify(notificadorDeEmail, never()).enviar(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("5.5 - pedido ja CANCELADO nao pode ser confirmado (IllegalStateException)")
    void naoDeveConfirmarPedidoQueNaoEstaAberto() {
        pedidoAberto.setStatus(StatusDoPedido.CANCELADO);
        when(pedidoRepository.findById(10L)).thenReturn(Optional.of(pedidoAberto));

        IllegalStateException erro = assertThrows(
                IllegalStateException.class,
                () -> pedidoService.confirmar(10L)
        );

        assertEquals("somente pedidos abertos podem ser confirmados", erro.getMessage());
        verify(estoqueClient, never()).temDisponibilidade(anyString(), anyInt());
        verify(pedidoRepository, never()).save(any());
    }

    // ---------------- BONUS ----------------

    @Test
    @DisplayName("BONUS - o estoque e baixado ANTES do pedido ser salvo")
    void deveBaixarEstoqueAntesDeSalvar() {
        when(pedidoRepository.findById(10L)).thenReturn(Optional.of(pedidoAberto));
        when(estoqueClient.temDisponibilidade("SKU-1", 2)).thenReturn(true);
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(chamada -> chamada.getArgument(0));

        pedidoService.confirmar(10L);

        InOrder ordem = inOrder(estoqueClient, pedidoRepository, notificadorDeEmail);
        ordem.verify(estoqueClient).baixarEstoque("SKU-1", 2);
        ordem.verify(pedidoRepository).save(any(Pedido.class));
        ordem.verify(notificadorDeEmail).enviar(anyString(), anyString(), anyString());
    }
}
