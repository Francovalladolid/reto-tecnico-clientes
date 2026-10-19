package com.reto.clientes.mapper;

import com.reto.clientes.dto.request.ClienteRequest;
import com.reto.clientes.dto.response.ClienteResponse;
import com.reto.clientes.entity.Cliente;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(target = "estado", constant = "true")
    Cliente toEntity(ClienteRequest request);

    @Mapping(
            target = "nombreCompleto",
            expression = "java((cliente.getNombre() + \" \" + cliente.getApellidoPaterno() + \" \" + cliente.getApellidoMaterno()).trim())"
    )
    ClienteResponse toResponse(Cliente cliente);
}
