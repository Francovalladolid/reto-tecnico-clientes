package com.reto.clientes.service.impl;

import com.reto.clientes.entity.Cliente;

import com.reto.clientes.dto.request.ClienteRequest;
import com.reto.clientes.dto.response.ClienteResponse;
import com.reto.clientes.service.ClienteService;
import com.reto.clientes.service.async.ClienteAsyncService;
import org.springframework.stereotype.Service;

import com.reto.clientes.mapper.ClienteMapper;
import com.reto.clientes.repository.ClienteRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final ClienteAsyncService clienteAsyncService;

    public ClienteServiceImpl(
            ClienteRepository clienteRepository,
            ClienteMapper clienteMapper, ClienteAsyncService clienteAsyncService
    ) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.clienteAsyncService = clienteAsyncService;
    }

    @Override
    @Transactional
    public ClienteResponse crearCliente(ClienteRequest request) {
        Cliente cliente = clienteMapper.toEntity(request);
        Cliente clienteGuardado = clienteRepository.save(cliente);
        ClienteResponse response = clienteMapper.toResponse(clienteGuardado);
        clienteAsyncService.enviarCliente(response);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponse> listarClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toResponse)
                .toList();
    }
}
