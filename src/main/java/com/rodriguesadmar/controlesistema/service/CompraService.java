package com.rodriguesadmar.controlesistema.service;

import com.rodriguesadmar.controlesistema.model.ItemCompra;
import com.rodriguesadmar.controlesistema.model.PedidoCompra;
import com.rodriguesadmar.controlesistema.model.Produto;
import com.rodriguesadmar.controlesistema.repository.ItemCompraRepository;
import com.rodriguesadmar.controlesistema.repository.PedidoCompraRepository;
import com.rodriguesadmar.controlesistema.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final PedidoCompraRepository pedidoCompraRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemCompraRepository itemCompraRepository;

    public PedidoCompra salvar(PedidoCompra compra) {
        compra.setDataCompra(LocalDateTime.now());
        double totalCompra = 0.0;

        for (ItemCompra item : compra.getItens()) {
            item.setPedidoCompra(compra);

            Long idProduto = item.getProduto().getId();

            Produto produtoReal = produtoRepository.findById(idProduto)
                    .orElseThrow(() ->
                            new IllegalArgumentException("Produto não encontrado ID: " + idProduto));

            double subtotal = item.getQuantidade() * item.getPrecoCustoUnitario();
            item.setValorTotalItem(subtotal);
            totalCompra += subtotal;

            int estoqueAtual = (produtoReal.getQuantidadeEstoque() != null) ? produtoReal.getQuantidadeEstoque() : 0;
            int novoEstoque = estoqueAtual + item.getQuantidade();
            produtoReal.setQuantidadeEstoque(novoEstoque);

            produtoReal.setPrecoCusto(BigDecimal.valueOf(item.getPrecoCustoUnitario()));
            item.setProduto(produtoReal);
            produtoRepository.save(produtoReal);
         }
        compra.setValorTotal(totalCompra);
        return pedidoCompraRepository.save(compra);
    }
}
