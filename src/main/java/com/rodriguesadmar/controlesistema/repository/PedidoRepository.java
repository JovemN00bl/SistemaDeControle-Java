package com.rodriguesadmar.controlesistema.repository;

import com.rodriguesadmar.controlesistema.model.PedidoVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;


@Repository
public interface PedidoRepository extends JpaRepository<PedidoVenda, Long> {


    @Query("SELECT SUM(p.valorTotal) FROM PedidoVenda p")
    BigDecimal totalVendido();

    long count();
}
