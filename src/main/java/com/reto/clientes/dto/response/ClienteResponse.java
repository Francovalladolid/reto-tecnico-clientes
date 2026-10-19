package com.reto.clientes.dto.response;

import java.util.UUID;

public class ClienteResponse {

    private UUID id;
    private String nombreCompleto;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
}
