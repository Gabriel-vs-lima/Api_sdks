package br.com.estudos.testes.repository;

import br.com.estudos.testes.dominio.Cliente;

import java.util.Optional;

/**
 * Repositorio de clientes.
 *
 * Repare: e uma INTERFACE. Nos testes unitarios voce nunca vai usar a
 * implementacao real que fala com o banco -- voce vai criar um dublê
 * dela com @Mock. E por isso que o teste roda em milissegundos.
 */
public interface ClienteRepository {

    Optional<Cliente> findById(Long id);

    boolean existsByEmail(String email);

    Cliente save(Cliente cliente);

    void deleteById(Long id);
}
