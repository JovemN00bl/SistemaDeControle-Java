package com.rodriguesadmar.controlesistema.model.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class VendaDTO {
    @NotNull(message = "teste")
    private Long clienteId;
    @NotNull
    private List<ItemVendaDTO> itens;


}
