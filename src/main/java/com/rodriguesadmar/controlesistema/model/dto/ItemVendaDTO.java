package com.rodriguesadmar.controlesistema.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemVendaDTO {
    @NotNull
    private Long produtoId;
    @NotNull
    private Integer quantidade;
}
