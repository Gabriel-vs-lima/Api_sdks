package br.com.estudos.testes.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
 import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.estudos.testes.exception.ClienteNaoEncontradoException;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;

import br.com.estudos.testes.client.NotificadorDeEmail;
import br.com.estudos.testes.dominio.Cliente;
import br.com.estudos.testes.repository.ClienteRepository;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * ======================================================================
 * EXERCICIO 3 - Primeiro contato com Mockito: @Mock, @InjectMocks, when
 * ======================================================================
 *
 * Classe sob teste: ClienteService.buscarPorId(Long)
 *
 * Dependencias: ClienteRepository, NotificadorDeEmail
 *
 * TAREFA DE MONTAGEM (faca primeiro):
 *   1. anote a classe com  @ExtendWith(MockitoExtension.class)
 *      import org.junit.jupiter.api.extension.ExtendWith;
 *      import org.mockito.junit.jupiter.MockitoExtension;
 *   2. crie os dublês:
 *        @Mock private ClienteRepository clienteRepository;
 *        @Mock private NotificadorDeEmail notificadorDeEmail;
 *   3. crie o objeto real com os dublês dentro:
 *        @InjectMocks private ClienteService clienteService;
 *
 * IMPORTANTE: @Mock cria um dublê que, por padrao, devolve valores "vazios"
 * (null, false, 0, Optional.empty()). Voce ENSINA o dublê a responder com
 *   when(mock.metodo(argumento)).thenReturn(resposta);
 *
 * NAO suba o Spring aqui (@SpringBootTest). Teste unitario de service nao
 * precisa de contexto: e so uma classe Java com dependencias injetadas.
 *
 * Apague os fail(...) conforme for implementando.
 */
@ExtendWith(MockitoExtension.class)
class ClienteServiceBuscaTest {
   @Mock private ClienteRepository clienteRepository; 
    @Mock private NotificadorDeEmail notificadorDeEmail;
    //@Mock <- cria um dublê do tipo ClienteRepository, que devolve Optional.empty() por padrao
    @InjectMocks private ClienteService clienteService;
    //Ele coloca o dublê (valor) do ClienteRepository e do NotificadorDeEmail dentro do ClienteService, sem subir o Spring (ou seja sem subir o dado real)
        
    @Test
    @DisplayName("3.1 - quando o cliente existe, devolve o cliente do repositorio")
    void deveDevolverClienteQuandoEncontrado() {
        // Arrange
           var cliente = new Cliente(1L, "Ana", "ana@email.com");
           when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // Act
           var resultado = clienteService.buscarPorId(1L);

        // Assert
           assertEquals("Ana", resultado.getNome());

    }

    @Test
    @DisplayName("3.2 - quando o cliente nao existe, lanca ClienteNaoEncontradoException")
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        //arrange
                when(clienteRepository.findById(1L)).thenReturn(Optional.empty());
        //act
        assertThrows (ClienteNaoEncontradoException.class, 
            () -> clienteService.buscarPorId(1L));


    }

@Test
@DisplayName("3.3 - a mensagem da excecao contem o id procurado")
void mensagemDaExcecaoDeveConterOId() {
    // Arrange
    when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

    // Act
    var erro = assertThrows(ClienteNaoEncontradoException.class,
            () -> clienteService.buscarPorId(99L));

    // Assert
    assertTrue(erro.getMessage().contains("99"));
}

    @Test
    @DisplayName("3.4 - buscar cliente chama o repositorio exatamente uma vez")
    void deveConsultarORepositorioUmaUnicaVez() {
        // Arrange
           var cliente = new Cliente(1L, "Ana", "ana@email.com");
           when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // Act
            clienteService.buscarPorId(1L);
            //assert

         verify(clienteRepository, times(1)).findById(1L);
        // Este teste verifica COMPORTAMENTO (a interacao), nao o valor retornado.
    }

    @Test
    @DisplayName("3.5 - uma busca nunca dispara e-mail")
    void buscarNaoDeveEnviarEmail() {
            // Arrange
           var cliente = new Cliente(1L, "Ana", "ana@email.com");
           when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // Act
            clienteService.buscarPorId(1L);

        verify(notificadorDeEmail, never()).enviar(any(), any(), any());
        // Testar o que NAO deve acontecer e tao importante quanto o que deve acontecer. 
    }
}
