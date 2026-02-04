package com.rodriguesadmar.controlesistema.repository;

import com.rodriguesadmar.controlesistema.model.PedidoCompra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoCompraRepository extends JpaRepository<PedidoCompra, Long> {

    List<PedidoCompra> findAllByOrderByDataCompraDesc();
}
