package com.rodriguesadmar.controlesistema.model;

public enum StatusPedido {
    PENDENTE("Pendente"),
    FINALIZADO("Finalizado"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String GetDescricao() {
        return descricao;
    }

}
