package com.reto.clientes.integration;

import com.reto.clientes.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    void limpiarBaseDeDatos() {
        clienteRepository.deleteAll();
    }

    @Test
    void debeCrearYListarCliente() throws Exception {

        String body = """
                {
                  "nombre": "Renzo",
                  "apellidoPaterno": "Vasquez",
                  "apellidoMaterno": "Valverde"
                }
                """;

        mockMvc.perform(post("/clientes")
                        .header("consumerId", "reto-tecnico")
                        .header("traceparent",
                                "00-4bf92f3577b34da6a3ce929d0e0e4736-00f067aa0ba902b7-01")
                        .header("deviceType", "AND")
                        .header("deviceId", "device-001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombreCompleto")
                        .value("Renzo Vasquez Valverde"));

        mockMvc.perform(get("/clientes")
                        .header("consumerId", "reto-tecnico")
                        .header("traceparent",
                                "00-4bf92f3577b34da6a3ce929d0e0e4736-00f067aa0ba902b7-01")
                        .header("deviceType", "AND")
                        .header("deviceId", "device-001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].nombreCompleto")
                        .value("Renzo Vasquez Valverde"))
                .andExpect(jsonPath("$[0].estado").doesNotExist())
                .andExpect(jsonPath("$[0].fechaCreacion").doesNotExist());
    }
}