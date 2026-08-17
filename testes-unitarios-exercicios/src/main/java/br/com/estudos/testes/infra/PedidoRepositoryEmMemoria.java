package br.com.estudos.testes.infra;

import br.com.estudos.testes.dominio.Pedido;
import br.com.estudos.testes.repository.PedidoRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PedidoRepositoryEmMemoria implements PedidoRepository {

    private final Map<Long, Pedido> banco = new LinkedHashMap<>();
    private final AtomicLong sequencia = new AtomicLong(0);

    @Override
    public Optional<Pedido> findById(Long id) {
        return Optional.ofNullable(banco.get(id));
    }

    @Override
    public Pedido save(Pedido pedido) {
        if (pedido.getId() == null) {
            pedido.setId(sequencia.incrementAndGet());
        }
        banco.put(pedido.getId(), pedido);
        return pedido;
    }
}
