package br.com.estudos.testes.service;

import br.com.estudos.testes.client.NotificadorDeEmail;
import br.com.estudos.testes.dominio.Cliente;
import br.com.estudos.testes.exception.ClienteNaoEncontradoException;
import br.com.estudos.testes.exception.EmailJaCadastradoException;
import br.com.estudos.testes.repository.ClienteRepository;
import org.springframework.stereotype.Service;

/**
 * EXERCICIOS 3 e 4 - Spring + Mockito
 *
 * Repare na injecao por CONSTRUTOR. E o que permite o Mockito montar
 * este service com dublês via @InjectMocks, sem subir o Spring.
 *
 * NAO altere esta classe.
 */
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final NotificadorDeEmail notificadorDeEmail;

public ClienteService(ClienteRepository clienteRepository, NotificadorDeEmail notificadorDeEmail) {
this.clienteRepository = clienteRepository;
this.notificadorDeEmail = notificadorDeEmail;
}

    /**
     * EXERCICIO 3
     *
     * Busca um cliente pelo id.
     *
     * @throws ClienteNaoEncontradoException se o repositorio devolver Optional.empty()
     */
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));
    }

    /**
     * EXERCICIO 4
     *
     * Cadastra um novo cliente.
     *
     * Regras:
     *   1. se o e-mail ja existe -> lanca EmailJaCadastradoException e NAO salva
     *   2. o nome e sempre gravado sem espacos nas pontas (trim)
     *   3. o cliente nasce ativo
     *   4. depois de salvar, envia um e-mail de boas-vindas
     *   5. retorna o objeto devolvido pelo repositorio (o que ja tem id)
     */
    public Cliente cadastrar(Cliente novoCliente) {
        if (clienteRepository.existsByEmail(novoCliente.getEmail())) {
            throw new EmailJaCadastradoException(novoCliente.getEmail());
        }

        novoCliente.setNome(novoCliente.getNome().trim());
        novoCliente.setAtivo(true);

        Cliente salvo = clienteRepository.save(novoCliente);

        notificadorDeEmail.enviar(
                salvo.getEmail(),
                "Bem-vindo!",
                "Ola, " + salvo.getNome() + "! Sua conta foi criada."
        );

        return salvo;
    }
}
