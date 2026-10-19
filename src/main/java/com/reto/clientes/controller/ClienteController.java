package com.reto.clientes.controller;

import com.reto.clientes.dto.request.ClienteRequest;
import com.reto.clientes.dto.response.ClienteResponse;
import com.reto.clientes.enums.DeviceType;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.reto.clientes.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> crearCliente(
            @RequestHeader("consumerId") String consumerId,
            @RequestHeader("traceparent") String traceparent,
            @RequestHeader("deviceType") DeviceType deviceType,
            @RequestHeader("deviceId") String deviceId,
            @Valid @RequestBody ClienteRequest request
    ){
        ClienteResponse response = clienteService.crearCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> listarClientes(
            @RequestHeader("consumerId") String consumerId,
            @RequestHeader("traceparent") String traceparent,
            @RequestHeader("deviceType") DeviceType deviceType,
            @RequestHeader("deviceId") String deviceId
    ) {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

}
