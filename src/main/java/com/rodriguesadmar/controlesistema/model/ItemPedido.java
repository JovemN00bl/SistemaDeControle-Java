package com.rodriguesadmar.controlesistema.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private PedidoVenda pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private Integer quantitdade;

    @Column(nullable = false)
    private BigDecimal precoUnitario;

    public BigDecimal getSubTotal() {
        if (precoUnitario != null && quantitdade != null) {
            return precoUnitario.multiply(new BigDecimal(quantitdade));
        }
        return BigDecimal.ZERO;
    }
}
