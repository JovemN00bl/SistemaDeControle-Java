package com.rodriguesadmar.controlesistema.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O cpf ou cnpj é obrigatório.")
    @Pattern(regexp = "([0-9]{11}|[0-9]{14})", message = "O CPF/CNPJ deve conter apenas números.")
    @Column(nullable = false, unique = true, length = 14)
    private String cpfOuCnpj;

    @NotBlank(message = "O email é obrigatório!")
    @Email(message = "Formato de email inválido.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "O telefone é obrigatório.")
    private String telefone;


    @NotBlank(message = "O cep é obrigatório!")
    @Pattern(regexp = "\\d{8}", message = "O cep deve conter 8 dígitos numéricos!")
    private String cep;

    @NotBlank(message = "O logradouro é obrigatório!")
    private String logradouro;

    @NotBlank(message = "O número é obrigatório!")
    private String numero;

    @NotBlank(message = "O bairro é obrigatório!")
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória!")
    private String cidade;

    @NotBlank(message = "A UF é obrigatória.")
    @Size(min = 2, max = 2, message = "A uf deve conter somente 2 caracteres.")
    private String uf;

}
