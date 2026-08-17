package br.com.estudos.testes.service;

import br.com.estudos.testes.client.NotificadorDeEmail;
import br.com.estudos.testes.dominio.Cliente;
import br.com.estudos.testes.exception.EmailJaCadastradoException;
import br.com.estudos.testes.repository.ClienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** GABARITO do Exercicio 4. */
@ExtendWith(MockitoExtension.class)
class ClienteServiceCadastroTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private NotificadorDeEmail notificadorDeEmail;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    @DisplayName("4.1 - cadastro valido salva no repositorio e devolve o cliente salvo")
    void deveCadastrarClienteNovo() {
        // Arrange
        Cliente novo = new Cliente(null, "Ana Souza", "ana@email.com");
        when(clienteRepository.existsByEmail("ana@email.com")).thenReturn(false);
        // save() num mock devolveria null; aqui ensinamos a devolver o proprio
        // objeto recebido, imitando o comportamento de um repositorio real.
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(chamada -> chamada.getArgument(0));

        // Act
        Cliente salvo = clienteService.cadastrar(novo);

        // Assert
        assertEquals("Ana Souza", salvo.getNome());
        assertEquals("ana@email.com", salvo.getEmail());
        assertTrue(salvo.isAtivo());
        verify(clienteRepository).save(novo);
    }

    @Test
    @DisplayName("4.2 - e-mail ja cadastrado lanca EmailJaCadastradoException")
    void deveRecusarEmailDuplicado() {
        Cliente novo = new Cliente(null, "Ana Souza", "ana@email.com");
        when(clienteRepository.existsByEmail("ana@email.com")).thenReturn(true);

        EmailJaCadastradoException erro = assertThrows(
                EmailJaCadastradoException.class,
                () -> clienteService.cadastrar(novo)
        );

        assertTrue(erro.getMessage().contains("ana@email.com"));
    }

    @Test
    @DisplayName("4.3 - quando o e-mail ja existe, NADA e salvo e NENHUM e-mail e enviado")
    void naoDeveSalvarNemNotificarQuandoEmailDuplicado() {
        Cliente novo = new Cliente(null, "Ana Souza", "ana@email.com");
        when(clienteRepository.existsByEmail("ana@email.com")).thenReturn(true);

        assertThrows(EmailJaCadastradoException.class, () -> clienteService.cadastrar(novo));

        verify(clienteRepository, never()).save(any());
        verify(notificadorDeEmail, never()).enviar(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("4.4 - o nome e salvo sem espacos nas pontas (ArgumentCaptor)")
    void deveSalvarNomeSemEspacosNasPontas() {
        Cliente novo = new Cliente(null, "   Ana Souza   ", "ana@email.com");
        when(clienteRepository.existsByEmail("ana@email.com")).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(chamada -> chamada.getArgument(0));

        clienteService.cadastrar(novo);

        ArgumentCaptor<Cliente> captor = ArgumentCaptor.forClass(Cliente.class);
        verify(clienteRepository).save(captor.capture());

        Cliente enviadoAoRepositorio = captor.getValue();
        assertEquals("Ana Souza", enviadoAoRepositorio.getNome());
        assertTrue(enviadoAoRepositorio.isAtivo());
    }

    @Test
    @DisplayName("4.5 - apos salvar, envia e-mail de boas-vindas para o endereco do cliente")
    void deveEnviarEmailDeBoasVindas() {
        Cliente novo = new Cliente(null, "Ana Souza", "ana@email.com");
        when(clienteRepository.existsByEmail("ana@email.com")).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(chamada -> chamada.getArgument(0));

        clienteService.cadastrar(novo);

        // Se usar matcher em um argumento, use matcher em TODOS.
        verify(notificadorDeEmail).enviar(eq("ana@email.com"), eq("Bem-vindo!"), anyString());

        // Alternativa: capturar o corpo e inspecionar o conteudo.
        ArgumentCaptor<String> corpo = ArgumentCaptor.forClass(String.class);
        verify(notificadorDeEmail).enviar(anyString(), anyString(), corpo.capture());
        assertTrue(corpo.getValue().contains("Ana Souza"));
    }
}
