package com.rodriguesadmar.controlesistema.repository;

import com.rodriguesadmar.controlesistema.model.PedidoVenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<PedidoVenda, Long> {
}
