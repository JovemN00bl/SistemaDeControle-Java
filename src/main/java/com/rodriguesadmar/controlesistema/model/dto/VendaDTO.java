package com.rodriguesadmar.controlesistema.model.dto;
import lombok.Data;

import java.util.List;

@Data
public class VendaDTO {
    private Long clienteId;
    private List<ItemVendaDTO> itens;

}
