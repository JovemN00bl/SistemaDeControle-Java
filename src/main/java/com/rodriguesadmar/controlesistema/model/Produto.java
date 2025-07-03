package com.rodriguesadmar.controlesistema.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "O nome é obrigatório.")
    @Column(nullable = false, unique = true, length = 180)
    private String nome;


    @NotNull(message = "O preço de custo é obrigatório.")
    @DecimalMin(value = "0.01", inclusive = true, message = "O preço de custo deve ser maior que zero.")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoCusto;

    @NotNull(message = "O preço de custo é obrigatório.")
    @DecimalMin(value = "0.01", inclusive = true, message = "O preço de venda deve ser maior que zero.")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoVenda;

    @Min(value = 0, message = "A quantidade em estoque não pode ser negativa.")
    @Column(nullable = false)
    private Integer quantidadeEstoque;

    @Size(max = 50, message = "O código de barras não pode exceder ciquenta caracteres.")
    @Column(unique = true, length = 50)
    private String codigoBarras;

}
