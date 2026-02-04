package com.rodriguesadmar.controlesistema.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "itens_compra")
public class ItemCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_compra_id")
    private PedidoCompra pedidoCompra;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
    private Integer quantidade;
    private Double precoCustoUnitario;
    private Double valorTotalItem;
}
