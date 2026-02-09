package com.rodriguesadmar.controlesistema.model.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioDTO {

    @NotNull(message = "O nome do usuario é obrigatório")
    private String username;
    private Long UsuarioId;
    private String role;

    private String password;

}
