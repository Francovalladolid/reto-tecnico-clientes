package com.reto.clientes.service;

import com.reto.clientes.dto.request.ClienteRequest;
import com.reto.clientes.dto.response.ClienteResponse;

import java.util.List;


public interface ClienteService {

    ClienteResponse crearCliente(ClienteRequest request);
    List<ClienteResponse> listarClientes();
}
