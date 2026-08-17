package br.com.estudos.testes.service;

import br.com.estudos.testes.client.NotificadorDeEmail;
import br.com.estudos.testes.dominio.Cliente;
import br.com.estudos.testes.exception.ClienteNaoEncontradoException;
import br.com.estudos.testes.repository.ClienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** GABARITO do Exercicio 3. */
@ExtendWith(MockitoExtension.class)
class ClienteServiceBuscaTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private NotificadorDeEmail notificadorDeEmail;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    @DisplayName("3.1 - quando o cliente existe, devolve o cliente do repositorio")
    void deveDevolverClienteQuandoEncontrado() {
        // Arrange
        Cliente cliente = new Cliente(1L, "Ana", "ana@email.com");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // Act
        Cliente resultado = clienteService.buscarPorId(1L);

        // Assert
        assertEquals(1L, resultado.getId().longValue());
        assertEquals("Ana", resultado.getNome());
        assertEquals("ana@email.com", resultado.getEmail());
    }

    @Test
    @DisplayName("3.2 - quando o cliente nao existe, lanca ClienteNaoEncontradoException")
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.buscarPorId(99L));
    }

    @Test
    @DisplayName("3.3 - a mensagem da excecao contem o id procurado")
    void mensagemDaExcecaoDeveConterOId() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        ClienteNaoEncontradoException erro = assertThrows(
                ClienteNaoEncontradoException.class,
                () -> clienteService.buscarPorId(99L)
        );

        assertTrue(erro.getMessage().contains("99"), "mensagem foi: " + erro.getMessage());
    }

    @Test
    @DisplayName("3.4 - buscar cliente chama o repositorio exatamente uma vez")
    void deveConsultarORepositorioUmaUnicaVez() {
        when(clienteRepository.findById(1L))
                .thenReturn(Optional.of(new Cliente(1L, "Ana", "ana@email.com")));

        clienteService.buscarPorId(1L);

        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("3.5 - uma busca nunca dispara e-mail")
    void buscarNaoDeveEnviarEmail() {
        when(clienteRepository.findById(1L))
                .thenReturn(Optional.of(new Cliente(1L, "Ana", "ana@email.com")));

        clienteService.buscarPorId(1L);

        verify(notificadorDeEmail, never()).enviar(anyString(), anyString(), anyString());
        verify(clienteRepository, never()).save(any());
    }
}
