package br.com.estudos.testes.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * ======================================================================
 * EXERCICIO 4 - verify, never e ArgumentCaptor
 * ======================================================================
 *
 * Classe sob teste: ClienteService.cadastrar(Cliente)
 *
 * Aqui o metodo nao so devolve um valor: ele PROVOCA EFEITOS (salva no
 * repositorio, dispara e-mail). Valor de retorno voce checa com assert;
 * efeito voce checa com verify.
 *
 * Monte a classe igual ao exercicio 3 (@ExtendWith + @Mock + @InjectMocks).
 *
 * Um detalhe que pega todo mundo na primeira vez:
 *   save() num @Mock devolve null por padrao. Se o service usa o retorno
 *   do save (e aqui usa!), voce PRECISA ensinar:
 *     when(clienteRepository.save(any(Cliente.class))).thenAnswer(i -> i.getArgument(0));
 *   ou devolver um objeto pronto com thenReturn(...).
 *
 * Apague os fail(...) conforme for implementando.
 */
class ClienteServiceCadastroTest {

    // TODO: @ExtendWith na classe, @Mock, @InjectMocks

    @Test
    @DisplayName("4.1 - cadastro valido salva no repositorio e devolve o cliente salvo")
    void deveCadastrarClienteNovo() {
        // Arrange
        // TODO:
        //   when(clienteRepository.existsByEmail("ana@email.com")).thenReturn(false);
        //   when(clienteRepository.save(any(Cliente.class))).thenAnswer(i -> i.getArgument(0));
        // Act / Assert
        fail("TODO: implementar este teste");
    }

    @Test
    @DisplayName("4.2 - e-mail ja cadastrado lanca EmailJaCadastradoException")
    void deveRecusarEmailDuplicado() {
        // TODO
        // Dica: when(clienteRepository.existsByEmail(...)).thenReturn(true);
        fail("TODO: implementar este teste");
    }

    @Test
    @DisplayName("4.3 - quando o e-mail ja existe, NADA e salvo e NENHUM e-mail e enviado")
    void naoDeveSalvarNemNotificarQuandoEmailDuplicado() {
        // TODO
        // Dica:
        //   verify(clienteRepository, never()).save(any());
        //   verify(notificadorDeEmail, never()).enviar(any(), any(), any());
        fail("TODO: implementar este teste");
    }

    @Test
    @DisplayName("4.4 - o nome e salvo sem espacos nas pontas (use ArgumentCaptor)")
    void deveSalvarNomeSemEspacosNasPontas() {
        // TODO: cadastre um cliente com nome "  Ana Souza  " e capture o objeto
        // que chegou no save para inspecionar o que foi realmente gravado:
        //
        //   var captor = ArgumentCaptor.forClass(Cliente.class);
        //   verify(clienteRepository).save(captor.capture());
        //   assertEquals("Ana Souza", captor.getValue().getNome());
        //
        // ArgumentCaptor responde "com QUAL argumento meu mock foi chamado?".
        fail("TODO: implementar este teste");
    }

    @Test
    @DisplayName("4.5 - apos salvar, envia e-mail de boas-vindas para o endereco do cliente")
    void deveEnviarEmailDeBoasVindas() {
        // TODO
        // Dica: verify(notificadorDeEmail).enviar(eq("ana@email.com"), eq("Bem-vindo!"), anyString());
        // Atencao: se usar eq() em UM argumento, use matcher em TODOS
        // (misturar valor cru com matcher gera InvalidUseOfMatchersException).
        fail("TODO: implementar este teste");
    }
}
