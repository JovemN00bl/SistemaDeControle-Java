package com.rodriguesadmar.controlesistema.service;


import ch.qos.logback.core.net.server.Client;
import com.rodriguesadmar.controlesistema.model.ItemPedido;
import com.rodriguesadmar.controlesistema.model.PedidoVenda;
import com.rodriguesadmar.controlesistema.model.Produto;
import com.rodriguesadmar.controlesistema.model.StatusPedido;
import com.rodriguesadmar.controlesistema.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository PedidoVendaRepository;
    private final ProdutoService produtoService;

    public List<PedidoVenda> findAll() {
        return PedidoVendaRepository.findAll();
    }

    @Transactional
    public PedidoVenda registrarVenda(PedidoVenda pedido) {
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus(StatusPedido.FINALIZADO);
        pedido.setValorTotal(BigDecimal.ZERO);

        BigDecimal totalPedido = BigDecimal.ZERO;

        for (ItemPedido item : pedido.getItens()) {
            Produto produto = produtoService.findById(item.getProduto().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

            item.setPrecoUnitario(produto.getPrecoVenda());
            BigDecimal subTotal = item.getSubTotal();
            totalPedido = totalPedido.add(subTotal);

            produtoService.darBaixaNoEstoque(produto.getId(), item.getQuantitdade());

            item.setPedido(pedido);
            item.setProduto(produto);
        }
        pedido.setValorTotal(totalPedido);

        return PedidoVendaRepository.save(pedido);

    }





}
