package br.com.estudos.testes.infra;

import br.com.estudos.testes.dominio.Cliente;
import br.com.estudos.testes.repository.ClienteRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementacao "de verdade" do repositorio (aqui, em memoria; num projeto
 * real seria o banco de dados).
 *
 * O ponto pedagogico: nos testes unitarios voce NUNCA usa esta classe.
 * Ela e substituida por um @Mock. E por isso que o teste roda em
 * milissegundos e nao depende de infraestrutura nenhuma.
 */
@Repository
public class ClienteRepositoryEmMemoria implements ClienteRepository {

    private final Map<Long, Cliente> banco = new LinkedHashMap<>();
    private final AtomicLong sequencia = new AtomicLong(0);

    @Override
    public Optional<Cliente> findById(Long id) {
        return Optional.ofNullable(banco.get(id));
    }

    @Override
    public boolean existsByEmail(String email) {
        return banco.values().stream().anyMatch(c -> c.getEmail().equalsIgnoreCase(email));
    }

    @Override
    public Cliente save(Cliente cliente) {
        if (cliente.getId() == null) {
            cliente.setId(sequencia.incrementAndGet());
        }
        banco.put(cliente.getId(), cliente);
        return cliente;
    }

    @Override
    public void deleteById(Long id) {
        banco.remove(id);
    }
}
