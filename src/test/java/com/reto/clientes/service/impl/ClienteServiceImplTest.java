package com.reto.clientes.service.impl;

import com.reto.clientes.dto.request.ClienteRequest;
import com.reto.clientes.dto.response.ClienteResponse;
import com.reto.clientes.entity.Cliente;
import com.reto.clientes.mapper.ClienteMapper;
import com.reto.clientes.repository.ClienteRepository;
import com.reto.clientes.service.async.ClienteAsyncService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.UUID;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @Mock
    private ClienteAsyncService clienteAsyncService;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Test
    void crearCliente_debeGuardarYRetornarRespuesta() {

        ClienteRequest request = new ClienteRequest();
        request.setNombre("Renzo");
        request.setApellidoPaterno("Vasquez");
        request.setApellidoMaterno("Valverde");

        Cliente cliente = new Cliente();
        Cliente clienteGuardado = new Cliente();

        ClienteResponse responseEsperado = new ClienteResponse();
        responseEsperado.setId(UUID.randomUUID());
        responseEsperado.setNombreCompleto("Renzo Vasquez Valverde");

        when(clienteMapper.toEntity(request))
                .thenReturn(cliente);

        when(clienteRepository.save(cliente))
                .thenReturn(clienteGuardado);

        when(clienteMapper.toResponse(clienteGuardado))
                .thenReturn(responseEsperado);

        ClienteResponse response =
                clienteService.crearCliente(request);

        assertSame(responseEsperado, response);

        verify(clienteMapper).toEntity(request);
        verify(clienteRepository).save(cliente);
        verify(clienteMapper).toResponse(clienteGuardado);
        verify(clienteAsyncService).enviarCliente(responseEsperado);
    }

    @Test
    void listarClientes_debeRetornarListaDeClientes() {

        Cliente cliente1 = new Cliente();
        Cliente cliente2 = new Cliente();

        ClienteResponse response1 = new ClienteResponse();
        response1.setId(UUID.randomUUID());
        response1.setNombreCompleto("Renzo Vasquez Valverde");

        ClienteResponse response2 = new ClienteResponse();
        response2.setId(UUID.randomUUID());
        response2.setNombreCompleto("Juan Perez Gomez");

        when(clienteRepository.findAll())
                .thenReturn(List.of(cliente1, cliente2));

        when(clienteMapper.toResponse(cliente1))
                .thenReturn(response1);

        when(clienteMapper.toResponse(cliente2))
                .thenReturn(response2);

        List<ClienteResponse> resultado =
                clienteService.listarClientes();

        assertEquals(2, resultado.size());
        assertSame(response1, resultado.get(0));
        assertSame(response2, resultado.get(1));

        verify(clienteRepository).findAll();
        verify(clienteMapper).toResponse(cliente1);
        verify(clienteMapper).toResponse(cliente2);

        verifyNoInteractions(clienteAsyncService);
    }
}