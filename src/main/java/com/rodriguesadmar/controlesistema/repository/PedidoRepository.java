package com.rodriguesadmar.controlesistema.repository;

import com.rodriguesadmar.controlesistema.model.PedidoVenda;
import com.rodriguesadmar.controlesistema.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface PedidoRepository extends JpaRepository<PedidoVenda, Long> {

    List<PedidoVenda> findAllByOrderByDataHoraDesc();


    @Query("SELECT SUM(p.valorTotal) FROM PedidoVenda p")
    BigDecimal totalVendido();

    @Query("SELECT COALESCE(sum(v.valorTotal), 0) FROM PedidoVenda v WHERE v.dataHora >= :dataInicio")
    Double totalVendasApartirDe(@Param("dataInicio") LocalDateTime dataInicio);

    long count();
}
