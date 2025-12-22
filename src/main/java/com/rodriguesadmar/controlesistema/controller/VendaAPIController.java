package com.rodriguesadmar.controlesistema.controller;

import com.rodriguesadmar.controlesistema.model.Cliente;
import com.rodriguesadmar.controlesistema.model.ItemPedido;
import com.rodriguesadmar.controlesistema.model.PedidoVenda;
import com.rodriguesadmar.controlesistema.model.Produto;
import com.rodriguesadmar.controlesistema.model.dto.ItemVendaDTO;
import com.rodriguesadmar.controlesistema.model.dto.VendaDTO;
import com.rodriguesadmar.controlesistema.service.ClienteService;
import com.rodriguesadmar.controlesistema.service.PedidoService;
import com.rodriguesadmar.controlesistema.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vendas")
@RequiredArgsConstructor
public class VendaAPIController {

    private final ClienteService clienteService;
    private final PedidoService pedidoService;
    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<?> salvarVenda(@RequestBody VendaDTO vendaDTO) {
        System.out.println("DTO RECEBIDO: " + vendaDTO);
        System.out.println("Cliente ID: " + vendaDTO.getClienteId());
        if (vendaDTO.getItens() != null) {
            vendaDTO.getItens().forEach(i -> System.out.println("Item Prod ID: " + i.getProdutoId()));


            try {
                PedidoVenda pedido = new PedidoVenda();

                Cliente cliente = clienteService.findById(vendaDTO.getClienteId())
                        .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
                pedido.setCliente(cliente);

                for (ItemVendaDTO itemVenda : vendaDTO.getItens()) {
                    Produto produto = produtoService.findById(itemVenda.getProdutoId())
                            .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

                    ItemPedido item = new ItemPedido();
                    item.setProduto(produto);
                    item.setQuantitdade(itemVenda.getQuantidade());

                    pedido.adicionarItem(item);

                }
                PedidoVenda vendaSalva = pedidoService.registrarVenda(pedido);
                return ResponseEntity.ok("Venda realizada com sucesso! ID: " + vendaSalva.getId());

            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Erro ao processar venda :" + e.getMessage());
            }
        }
        return null;
    }


}




