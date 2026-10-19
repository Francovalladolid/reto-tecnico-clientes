package com.reto.clientes.service.async;

import com.reto.clientes.dto.response.ClienteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ClienteAsyncService {
    private static final Logger log = LoggerFactory.getLogger(ClienteAsyncService.class);

    @Async
    public void enviarCliente(ClienteResponse cliente){
        String json = """
                {
                  "id": "%s",
                  "nombreCompleto": "%s"
                }
                """.formatted(
                cliente.getId(),
                cliente.getNombreCompleto()
        );

        log.info("Envío asíncrono a sistema externo: {}", json);

    }
}
