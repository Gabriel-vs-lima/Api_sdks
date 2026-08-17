package br.com.estudos.testes.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
class ClienteServiceBuscaTest {

    // TODO: @ExtendWith na classe, @Mock nos repositorios, @InjectMocks no service

    @Test
    @DisplayName("3.1 - quando o cliente existe, devolve o cliente do repositorio")
    void deveDevolverClienteQuandoEncontrado() {
        // Arrange
        // TODO:
        //   var cliente = new Cliente(1L, "Ana", "ana@email.com");
        //   when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // Act
        // TODO: var resultado = clienteService.buscarPorId(1L);

        // Assert
        // TODO: assertEquals("Ana", resultado.getNome());
        fail("TODO: implementar este teste");
    }

    @Test
    @DisplayName("3.2 - quando o cliente nao existe, lanca ClienteNaoEncontradoException")
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        // TODO:
        //   when(clienteRepository.findById(99L)).thenReturn(Optional.empty());
        //   assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.buscarPorId(99L));
        fail("TODO: implementar este teste");
    }

    @Test
    @DisplayName("3.3 - a mensagem da excecao contem o id procurado")
    void mensagemDaExcecaoDeveConterOId() {
        // TODO
        // Dica: guarde o retorno do assertThrows numa variavel e cheque getMessage().
        fail("TODO: implementar este teste");
    }

    @Test
    @DisplayName("3.4 - buscar cliente chama o repositorio exatamente uma vez")
    void deveConsultarORepositorioUmaUnicaVez() {
        // TODO
        // Dica: verify(clienteRepository, times(1)).findById(1L);
        // Este teste verifica COMPORTAMENTO (a interacao), nao o valor retornado.
        fail("TODO: implementar este teste");
    }

    @Test
    @DisplayName("3.5 - uma busca nunca dispara e-mail")
    void buscarNaoDeveEnviarEmail() {
        // TODO
        // Dica: verify(notificadorDeEmail, never()).enviar(any(), any(), any());
        // Testar o que NAO deve acontecer e tao importante quanto o que deve.
        fail("TODO: implementar este teste");
    }
}
