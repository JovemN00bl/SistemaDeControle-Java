package com.rodriguesadmar.controlesistema.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor


@Entity
public class Produto {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 180)
    private String nome;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoCusto;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoVenda;

    @Column(nullable = false)
    private Integer quantidadeEstoque;

    @Column(unique = true, length = 50)
    private String codigoBarras;

}
