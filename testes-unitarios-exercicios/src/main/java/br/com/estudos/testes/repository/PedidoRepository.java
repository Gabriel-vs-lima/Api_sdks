package br.com.estudos.testes.repository;

import br.com.estudos.testes.dominio.Pedido;

import java.util.Optional;

public interface PedidoRepository {

    Optional<Pedido> findById(Long id);

    Pedido save(Pedido pedido);
}
